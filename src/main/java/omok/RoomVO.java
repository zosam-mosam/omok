package omok;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import lombok.Data;
@Data
public class RoomVO {
	
	private int roomNo;
	private List<Session> userList;
	private int playerCount;
	
    // 고유 값
    private int[][] board = new int[20][20];
    //돌 선택
    private String black;
    private String white;
	
    private int turn_count;
    
	RoomVO() {
		this.userList = new ArrayList<Session>();
		this.playerCount = 0;
	}
	
	public void addLs(Session userSession) {
		this.userList.add(userSession);
		this.playerCount++;
	}
	
	public void removeLs(Session userSession) {
		for(int i=0; i<userList.size(); ++i) {
			if(userSession.equals(this.userList.get(i))) {
				this.userList.remove(i);
				this.playerCount--;
			}
		}
	}
	
	public boolean setStone(String user_id, int stone) {
    	boolean result=false;
    	
    	if (stone==1 && black==null) {
    		black = user_id;    		
    		result=true;
    	}
    	else if(stone==2 && white==null) {
    		white = user_id;
    		result=true;
    	}
 
    	return result;
	}
	
}