package omok;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//WebSocket 호스트 설정
@ServerEndpoint("/websocket/{roomId}")
public class Websocket {
	// 접속 된 클라이언트 WebSocket session 관리 리스트
	private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
    private static Map<Integer, RoomVO> sessionUsers2 = Collections.synchronizedMap(new HashMap<Integer, RoomVO>());
    //synchronizedMap : 소켓 연결 시 내부적으로 쓰레드가 생성되고 실행된다.
    // 쓰레드 간의 데이터 동기화를 보존하기 위해
    // 고유 값
    private int[][] board = new int[20][20];
    //돌 선택
    private String black;
    private String white;
	
    public Map<Integer, RoomVO> getSessionUsers2() {
    		return sessionUsers2;
    }
    @OnOpen
    public void handleOpen(Session userSession, @PathParam("roomId") String roomId) {
	    	// 클라이언트가 접속하면 WebSocket세션을 리스트에 저장한다.
	    	sessionUsers.add(userSession);
	    	
	    	System.out.println("roomid : " + roomId);
	    	
	    	// Test Code
	    int roomNum = Integer.parseInt(roomId);
	    System.out.println("방 객체 유무 : " + sessionUsers2.get(roomNum)); // 방 객체가 없을 때 -> 방 객체를 생성하면서 주입
	    if(sessionUsers2.get(roomNum) == null) {
	    		RoomVO ro = new RoomVO();
	    		ro.setRoomNo(roomNum);
	    		System.out.println("방 번호 : " + roomNum);
	    		ro.addLs(userSession);
	    		System.out.println("Session 값 " + userSession);
	    		sessionUsers2.put(roomNum, ro);
	    		System.out.println("객체 추가 결과 " + sessionUsers2);
	    } else { // 있으면 추가만
	    		sessionUsers2.get(roomNum).addLs(userSession);
	    		System.out.println("추가만 " + sessionUsers2);
	    }
    }
    // WebSocket으로 메시지가 오면 요청되는 함수
    @SuppressWarnings("unchecked")
	@OnMessage
    public void handleMessage(String message, Session userSession, @PathParam("roomId") String roomId) throws IOException {
    		System.out.println(message);
    		try {
			
			//server to client
			
			//client to server
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(message);
			
			int type = Integer.parseInt(jsonObject.get("type").toString());
			
			// 타입별 메시지 처리
			if(type == 1) {
				String user_id = jsonObject.get("id").toString();
				takeGame(user_id);
				
				JSONObject obj = new JSONObject();
				obj.put("type", 1);
				obj.put("black", black);
				obj.put("white", white);
				obj.put("board", board);
				
				sessionUsers.forEach(session -> {
					try {
						session.getBasicRemote().sendText(obj.toJSONString());
					} catch(Exception e) {
						e.printStackTrace();
					}
				});
			} else if(type == 2) { // 돌
				int xPos = Integer.parseInt(jsonObject.get("xPos").toString());
				int yPos = Integer.parseInt(jsonObject.get("yPos").toString());
				int turn = Integer.parseInt(jsonObject.get("turn").toString());
				JSONObject obj = new JSONObject();
				obj.put("type", 2);
				obj.put("xPos", xPos);
				obj.put("yPos", yPos);
				obj.put("turn", turn);
			} else if (type == 3) { // 채팅				
//				String name = "anonymous";
			}
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    @OnClose	
    public void handleClose(Session userSession, @PathParam("roomId") String roomId) {
      sessionUsers.remove(userSession);
      sessionUsers2.get(Integer.parseInt(roomId)).getUserList().remove(userSession);
      System.out.println("client is now disconnected...");
    }
    //준비되면 돌 선택하는 메소드
    public void takeGame(String user_id) {
	    	if (black == null) black = user_id;
	    	else if(white == null && !user_id.equals(black)) white = user_id;
	    	System.out.println(black + " " + white);
    }
}
// 클라 to 서버
// 게임 종료 여부   => 0 1
// 몇 번째 턴인지 t  => int
// 자기가 놓은 좌표 값  => 1열씩 => 0 1 흑 2 백
// 채팅인지 아니면 데이터 값인지 flag => 1 데이터 2 채팅 0 기본값
// 자기가 흑인지, 백인지 (관전자 구분)
// 서버 to 클라
//게임 종료 여부   => 0 1
//몇 번째 턴인지 t  => int
//보드 전체 그림  => 1열씩 => 0 1 흑 2 백
//채팅인지 아니면 데이터 값인지 flag => 1 데이터 2 채팅 0 기본값