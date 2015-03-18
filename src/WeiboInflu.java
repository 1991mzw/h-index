import java.lang.Math;

public class WeiboInflu {
	
	public double getWeiInflu(long fans, long retweets, long comments) {
	    double weiInflu = Math.log(fans+1) + Math.sqrt(retweets) + comments;
		return weiInflu;
	}

}
