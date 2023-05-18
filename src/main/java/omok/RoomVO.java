package omok;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import org.json.simple.JSONObject;

import lombok.Data;

@Data
public class RoomVO {
	
	private int roomNo;
	private List<Session> userList;

	private int playerCount;
	
	private int board_size=19;
    // 고유 값
    private int[][] board = new int[board_size][board_size];
    //돌 선택
    private String black;
    private String white;
    private int finish;		// 게임 종료여부 끝 : 1
    private int turnCount=0;	// 현재 턴 수
    
	RoomVO() {
		this.userList = new ArrayList<Session>();
		this.playerCount = 0;
		this.turnCount = 1;
	}
	
	// userList에 소켓 세션(플레이어) 추가
	public void addLs(Session userSession) {
		
		if(this.userList.contains(userSession)) {
			removeLs(userSession);
		}
		this.userList.add(userSession);
		this.playerCount++;
	}
	
	public void removeLs(Session userSession) {
		if(this.userList.indexOf(userSession) != -1) {
			this.userList.remove(this.userList.indexOf(userSession));
			this.playerCount--;
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
	
	public void putStone(int x, int y, int stone) {
		board[x][y] = stone;
	}
	
	public void sendObjectBroad(Session userSession, JSONObject obj) throws IOException {
		userList.forEach(session -> {
			if(session == userSession) {
				return;
			}
			try {
				session.getBasicRemote().sendText(obj.toJSONString());
			} catch (IOException e) {
		        e.printStackTrace();
		    }
		});
	}
	
	public void sendObject(Session userSession, JSONObject obj) throws IOException {
		userList.forEach(session -> {
			try {
				session.getBasicRemote().sendText(obj.toJSONString());
			} catch (IOException e) {
		        e.printStackTrace();
		    }
		});
	}
}