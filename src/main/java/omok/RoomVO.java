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
}