package omok;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//WebSocket 호스트 설정
@ServerEndpoint("/websocket4")
public class Websocket{
	
	private static final long serialVersionUID = 1L;
	
	// 접속 된 클라이언트 WebSocket session 관리 리스트
	private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
    
	//돌 선택
	private String black;
	private String white;
	
	public Websocket() {
        super();
    }
    
    @OnOpen
    public void handleOpen(Session userSession) {
    	// 클라이언트가 접속하면 WebSocket세션을 리스트에 저장한다.
    	System.out.println(userSession);
    	sessionUsers.add(userSession);
    	// 콘솔에 접속 로그를 출력한다.
    	System.out.println("client is now connected...");
    }
    
    @SuppressWarnings("unchecked")
	@OnMessage
    public void handleMessage(String message, Session userSession) throws IOException {
    	
      try {
    	  JSONParser parser = new JSONParser();
    	  
    	  //server to client
    	  
    	  //client to server
    	  JSONObject jsonObject = (JSONObject) parser.parse(message);
    	  System.out.println(jsonObject);
    	  
    	  int type = Integer.parseInt(jsonObject.get("type").toString());
    	  String user_id = jsonObject.get("id").toString();
    	  
    	  if(type==1) {
    		  
    		  takeGame(user_id);
    		  
    		  JSONObject obj = new JSONObject();
    		  obj.put("type", 1);
    		  obj.put("black", black);
    		  obj.put("white", white);
    		  
    		  sessionUsers.forEach(session -> {
    			  try {
    				  session.getBasicRemote().sendText(obj.toJSONString());
    			  }catch(Exception e) {
    				  e.printStackTrace();
    			  }
    		  });
    	  }
      }catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      
      
      
    }
    
    @OnClose	//로그인 
    public void handleClose(Session userSession) {
      // session 리스트로 접속 끊은 세션을 제거한다.
      sessionUsers.remove(userSession);
      // 콘솔에 접속 끊김 로그를 출력한다.
      System.out.println("client is now disconnected...");
    }
    
    //준비되면 돌 선택하는 메소드
    public void takeGame(String user_id) {
    	
    	if (black==null) black=user_id;
    	else if(white==null && !user_id.equals(black)) white=user_id;
    	System.out.println(black+" "+white);
    	
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