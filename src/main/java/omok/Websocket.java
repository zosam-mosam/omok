package omok;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

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

    private static Map<Integer, RoomVO> roomList = Collections.synchronizedMap(new HashMap<Integer, RoomVO>());
    //synchronizedMap : 소켓 연결 시 내부적으로 쓰레드가 생성되고 실행된다.
    // 쓰레드 간의 데이터 동기화를 보존하기 위해

    public Map<Integer, RoomVO> getSessionUsers2() {
    		return roomList;
    }
    @OnOpen
    public void handleOpen(Session userSession, @PathParam("roomId") String roomId) {
    	
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
    		System.out.println("M: "+message);
    		try {
			
			//server to client
    			
			//client to server
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(message);
			
			int type = Integer.parseInt(jsonObject.get("type").toString());			
			RoomVO vo = (RoomVO) roomList.get(Integer.parseInt(roomId));		
			
			OmokCheck omok = new OmokCheck(vo.getBoard());

			
			// 타입별 메시지 처리
			if(type ==0) {
				
				JSONObject obj = new JSONObject();
				obj.put("type", 0);
				obj.put("black", vo.getBlack());
				obj.put("white", vo.getWhite());
				obj.put("turn", vo.getTurnCount()); 
				obj.put("board", Arrays.deepToString(omok.getBoard()));
				//전송
				userSession.getBasicRemote().sendText(obj.toJSONString());
				
			}else if(type == 1) {
				String user_id = jsonObject.get("id").toString();
				int stone = Integer.parseInt(jsonObject.get("stone").toString());
				
				vo.setStone(user_id, stone);
				
				JSONObject obj = new JSONObject();
				obj.put("type", 1);
				obj.put("black", vo.getBlack());
				obj.put("white", vo.getWhite());
				obj.put("turn", vo.getTurnCount());
					
				vo.getUserList().forEach(session -> {
					try {
	        			session.getBasicRemote().sendText(obj.toJSONString());
	        		}catch(Exception e) {
	        			e.printStackTrace();
	        		}
	        	});
				
			} else if(type == 2) { // 돌
				int posX = Integer.parseInt(jsonObject.get("posX").toString());
				int posY = Integer.parseInt(jsonObject.get("posY").toString());
				int turnCount = Integer.parseInt(jsonObject.get("turnCount").toString());

				// 맞는 턴일때만 실행됨
				if(turnCount  == vo.getTurnCount())
				{
					// 보드에 좌표 셋
					roomList.get(Integer.parseInt(roomId)).putStone(posX, posY, turnCount % 2 == 1 ? 1 : 2);
					
					// 결과 값
					omok.setFinish(omok.isFinish(posX, posY, turnCount % 2 == 1 ? 1 : 2));
					
					// 서버->클라 - 계속 진행
					if(omok.getFinish() == 0)
					{
						//다음턴 넘기기
						vo.setTurnCount(vo.getTurnCount()+1);
						JSONObject obj = new JSONObject();
						obj.put("type", 2);
						obj.put("finish", omok.getFinish() );
						obj.put("board", Arrays.deepToString(omok.getBoard()));
						// 다음턴이라고 보내줌
						obj.put("turnCount", vo.getTurnCount());
						roomList.get(Integer.parseInt(roomId)).sendObject(userSession,obj);
						
					}
					// 서버->클라 - 게임 종료
					else if(omok.getFinish()  == 1)
					{
						JSONObject obj = new JSONObject();
						obj.put("type", 2);
						obj.put("finish", omok.getFinish() );
						obj.put("board", Arrays.deepToString(omok.getBoard()));
						obj.put("id", turnCount % 2  == 1 ? vo.getBlack() : vo.getWhite());
						roomList.get(Integer.parseInt(roomId)).sendObject(userSession,obj);
					}
				}
				else {
					return;
				}
			} else if (type == 3) { // 채팅
				roomList.get(Integer.parseInt(roomId)).sendObject(userSession, jsonObject);
			} else if (type == 4) { //기권
				String user_id = jsonObject.get("id").toString();
				JSONObject obj = new JSONObject();
				obj.put("type", 4);
				obj.put("finish", 1); // 기권하면...끝이닉가!!!!!!!
				obj.put("id", user_id.equals(vo.getWhite()) ? vo.getBlack() : vo.getWhite());
				obj.put("board", Arrays.deepToString(omok.getBoard()));
				roomList.get(Integer.parseInt(roomId)).sendObject(userSession,obj);
			}
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    @OnClose	
    public void handleClose(Session userSession, @PathParam("roomId") String roomId) {
    	
	    	roomList.get(Integer.parseInt(roomId));
	    	//room에서 black? white? 관전자?
	    	// 그 session을 remove 해야됨
	    	roomList.get(Integer.parseInt(roomId)).removeLs(userSession);
	    	System.out.println("client is now disconnected...");
    }
    
    
    public int isReady(int roomId) {
    	
    	int result= 0;
      	
    	RoomVO vo = roomList.get(roomId);
    	
    	
    	System.out.println(vo.getBlack()+" "+vo.getWhite());
		if(vo.getBlack()==null || vo.getWhite()==null) result=0;
		else result=vo.getTurnCount();
    	
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