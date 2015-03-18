import java.net.UnknownHostException;
import java.util.*;
import com.mongodb.*;


public class InfluList {
	
	public static FormGraph graph = new FormGraph();
	
	public static double getWeiInflu(long fans, long retweets, long comments) {
	    double weiInflu = Math.log(fans+1) + Math.sqrt(retweets) + comments;
		return weiInflu;
	}
	
	public static long getDouInflu(ArrayList<Float> ratings) {
		long douInflu = 0;
		Collections.sort(ratings);
		int total = ratings.size();
		for(int i = 0; i<total; i++) {
			if(total-i < ratings.get(i)*ratings.get(i)) {
				douInflu = total-i;
				break;
			}
		}
		return douInflu;
	}
	
	public double influence(Node actor) {
		double influ = 0;
		long neighborID = 0;
		ArrayList<Double> neiInfluList = new ArrayList<Double>();
		ArrayList<Long> edgeList = actor.getEdgeList();
		for(int i = 0; i<edgeList.size(); i++) {
			long edgeID = edgeList.get(i);
			Edge edge = graph.getEdgeList().get(edgeID);
			if (edge.getNode1() == actor.getNodeID()) {
				neighborID = edge.getNode2();
			}
			else {
				neighborID = edge.getNode1();
			}
			Node node = graph.getNodeList().get(neighborID);
			double neiInflu = node.getDouInflu()*node.getWeiInflu()*edge.getWeight();
			neiInfluList.add(neiInflu);
		}
		Collections.sort(neiInfluList);
		long neighbor_count = neiInfluList.size();
		for(int i = 0; i<neighbor_count; i++) {
			if(neighbor_count-i < neiInfluList.get(i)) {
				influ = neighbor_count-i;
				break;
			}
		}
		return influ;		
	}
	
	public static void main(String []args) throws UnknownHostException {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("actors");
		DBCollection actorInfo = db.getCollection("actor_info");
		DBCollection actorWeiInfo = db.getCollection("actor_weiboInfo");
		DBCollection weiboInfo = db.getCollection("weibo_info");
		DBCollection actorMovie = db.getCollection("actor_movie");
		DBCollection movieInfo = db.getCollection("movie_info");
		DBCursor nodeInfos = actorInfo.find();
		while (nodeInfos.hasNext()) {
			DBObject nodeInfo = nodeInfos.next();
			Node node = new Node(nodeInfo.get("name").toString(),
					Long.parseLong(nodeInfo.get("did").toString()));
			DBObject actor_fan = actorWeiInfo.findOne(new BasicDBObject("actor", nodeInfo.get("name").toString()));
			long fans = Long.parseLong(actor_fan.get("fans_number").toString());
			DBCursor actor_weibos = weiboInfo.find(new BasicDBObject("actor", nodeInfo.get("name").toString()));
			long retweets = 0;
			long comments = 0;
			while (actor_weibos.hasNext()) {
				DBObject weibo = actor_weibos.next();
				if (weibo.get("retweet_number").toString() != "") {
					retweets = retweets + Long.parseLong(weibo.get("retweet_number").toString());
				}
				if (weibo.get("comment_number").toString() != "") {
					comments = comments + Long.parseLong(weibo.get("comment_number").toString());
				}
			}
			if (actor_weibos.size() != 0) {
				retweets = retweets/actor_weibos.size();
				comments = comments/actor_weibos.size();
			}
			double weiboInflu = getWeiInflu(fans, retweets, comments);
			System.out.println(weiboInflu);
			Map movies = (Map)actorMovie.findOne(new BasicDBObject("actor_name", 
					nodeInfo.get("name").toString())).get("movieList");
			//System.out.println(movies);
			Set movie_names = movies.keySet();
			//System.out.println(movies.size());
			Iterator it = movie_names.iterator();
			ArrayList<Float> ratings = new ArrayList<Float>();
			while (it.hasNext()) {
				String movie_name = it.next().toString();
				//System.out.println(movie_name);
				if (movieInfo.findOne(new BasicDBObject("movie", movie_name))
						.get("average_rating").toString() != "") {
					ratings.add(Float.parseFloat(movieInfo
							.findOne(new BasicDBObject("movie", movie_name))
							.get("average_rating").toString()));
				}
			}
			long doubanInflu = getDouInflu(ratings);
			//System.out.println(doubanInflu);
			node.setDouInflu(doubanInflu);
			node.setWeiInflu(weiboInflu);
			graph.addNode(node);
		}
		DBCursor actorFollows = actorWeiInfo.find();
		while (actorFollows.hasNext()) {
			DBObject actorFollow = actorFollows.next();
			String[] followings = (String[]) actorFollow.get("following");
			String actorName = actorFollow.get("actor").toString();
			for (int i = 0; i < followings.length; i++) {
				DBObject followAct = actorWeiInfo.findOne(new BasicDBObject("nickname", followings[i]));
				if (followAct != null) {
					long followActID = Long.parseLong(actorInfo
							.findOne(new BasicDBObject("name", followAct.get("actor").toString()))
							.get("did").toString());
				}
			}
		}
	}
}
