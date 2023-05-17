package omok;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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

    private static Map<Integer, RoomVO> roomList = Collections.synchronizedMap(new HashMap<Integer, RoomVO>());
    //synchronizedMap : 소켓 연결 시 내부적으로 쓰레드가 생성되고 실행된다.
    // 쓰레드 간의 데이터 동기화를 보존하기 위해

    public Map<Integer, RoomVO> getSessionUsers2() {
    		return roomList;
    }
    @OnOpen
    public void handleOpen(Session userSession, @PathParam("roomId") String roomId) {
    	
    	// Test Code
    int roomNum = Integer.parseInt(roomId);
    System.out.println("방 객체 유무 : " + roomList.get(roomNum)); // 방 객체가 없을 때 -> 방 객체를 생성하면서 주입
    if(roomList.get(roomNum) == null) {
    		RoomVO ro = new RoomVO();
    		ro.setRoomNo(roomNum);
    		System.out.println("방 번호 : " + roomNum);
    		ro.addLs(userSession);
    		System.out.println("Session 값 " + userSession);
    		roomList.put(roomNum, ro);
    		System.out.println("객체 추가 결과 " + roomList);
    } else { // 있으면 추가만
    		roomList.get(roomNum).addLs(userSession);
    		System.out.println("추가만 " + roomList);
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
			int roomNum = Integer.parseInt(roomId);
			RoomVO vo = (RoomVO) roomList.get(roomNum);
			
			// 타입별 메시지 처리
			if(type == 1) {
				String user_id = jsonObject.get("id").toString();
				int stone = Integer.parseInt(jsonObject.get("stone").toString());
				
				vo.setStone(user_id, stone);
				
				JSONObject obj = new JSONObject();
				obj.put("type", 1);
				obj.put("black", vo.getBlack());
				obj.put("white", vo.getWhite());
				obj.put("turn", vo.getTurn_count());
					
				vo.getUserList().forEach(session -> {
					try {
	        			session.getBasicRemote().sendText(obj.toJSONString());
	        		}catch(Exception e) {
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
    	
    	RoomVO vo = (RoomVO) roomList.get(roomId);
    	//room에서 black? white? 관전자?
    	// 그 session을 remove 해야됨
    	roomList.get(Integer.parseInt(roomId)).getUserList().remove(userSession);
    	System.out.println("client is now disconnected...");
    }
    
    
    public int isReady(int roomId) {
    	
    	int result= 0;
      	
    	RoomVO vo = roomList.get(roomId);
    	
    	
    	System.out.println(vo.getBlack()+" "+vo.getWhite());
		if(vo.getBlack()==null || vo.getWhite()==null) result=0;
		else result=vo.getTurn_count();
    	
    	return result;
    }
    
}
// 클라 to 서버
// 게임 종료 여부   => 0 1
// 방번호 => 
// 몇 번째 턴인지 t  => int
// 자기가 놓은 좌표 값  => 1열씩 => 0 1 흑 2 백
// 채팅인지 아니면 데이터 값인지 flag => 1 데이터 2 채팅 0 기본값
// 자기가 흑인지, 백인지 (관전자 구분)
// 서버 to 클라
//게임 종료 여부   => 0 1
//몇 번째 턴인지 t  => int
//보드 전체 그림  => 1열씩 => 0 1 흑 2 백
//채팅인지 아니면 데이터 값인지 flag => 1 데이터 2 채팅 0 기본값