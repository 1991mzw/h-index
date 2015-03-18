import java.util.ArrayList;
import java.util.Collections;

public class DoubanInflu {
	
	public long getDouInflu(ArrayList<Float> ratings) {
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

}
