package omok;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.util.json.*;
 
// WebSocket 호스트 설정
@ServerEndpoint("/websocket2/{roomId}")
public class WebSocketTest {
  // 접속 된 클라이언트 WebSocket session 관리 리스트
	private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
  // 메시지에서 유저 명을 취득하기 위한 정규식 표현
  private static Pattern pattern = Pattern.compile("^\\{\\{.*?\\}\\}");
  
  // 고유 값
  private int[][] board = new int[20][20];
  
  // WebSocket으로 브라우저가 접속하면 요청되는 함수
  @OnOpen
  public void handleOpen(Session userSession, @PathParam("roomId") String roomId) {
    // 클라이언트가 접속하면 WebSocket세션을 리스트에 저장한다.
    sessionUsers.add(userSession);
    
    System.out.println(roomId);
    // 콘솔에 접속 로그를 출력한다.
    System.out.println("client is now connected...");
    
    System.out.println("user Session : " + userSession);
    System.out.println(sessionUsers.toString());
    
   System.out.println("board Val :  " + ++board[0][0]);
  }
  
  // WebSocket으로 메시지가 오면 요청되는 함수
  @OnMessage
  public void handleMessage(String message, Session userSession) throws IOException { 
    // 메시지 내용을 콘솔에 출력한다.
    System.out.println(message);
    
    JSONParser parser = new JSONParser(message);
    try {
//        LinkedHashMap<String, Object> lsh = parser.object();
//        lsh.get("name");
    		
//    		Object obj = parser.parse();
//        JSONObject jsonObj = (JSONObject) obj;
    		LinkedHashMap<String, Object> lsh = parser.parseObject();
        System.out.println(lsh);
        System.out.println(lsh.get("name"));
        System.out.println(lsh.get("age"));
        System.out.println(lsh.get("city"));
        System.out.println(lsh.get("field1"));
    } catch (Exception e) {
    		System.out.println(e.getMessage());
    }
    
    // 초기 유저 명
    String name = "anonymous";
    
    // 메시지로 유저 명을 추출한다.
    Matcher matcher = pattern.matcher(message);
    // 메시지 예: {{유저명}}메시지
    if (matcher.find()) {
      name = matcher.group();
    }
    // 클로져를 위해 변수의 상수화
    final String msg = message.replaceAll(pattern.pattern(), "");
    final String username = name.replaceFirst("^\\{\\{", "").replaceFirst("\\}\\}$", "");
    // session관리 리스트에서 Session을 취득한다.
    sessionUsers.forEach(session -> {
      // 리스트에 있는 세션과 메시지를 보낸 세션이 같으면 메시지 송신할 필요없다.
      if (session == userSession) {
        return;
      }
      try {
        // 리스트에 있는 모든 세션(메시지 보낸 유저 제외)에 메시지를 보낸다. (형식: 유저명 => 메시지)
        session.getBasicRemote().sendText(username + " => " + msg);
      } catch (IOException e) {
        // 에러가 발생하면 콘솔에 표시한다.
        e.printStackTrace();
      }
    });
  }
  // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
  @OnClose
  public void handleClose(Session userSession) {
    // 콘솔에 접속 끊김 로그를 출력한다.
    System.out.println(userSession + " client is now disconnected...");
    // session 리스트로 접속 끊은 세션을 제거한다.
    sessionUsers.remove(userSession);
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