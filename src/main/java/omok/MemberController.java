package omok;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.events.StartDocument;

@WebServlet("*.do")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("하이");
		doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String[] url = request.getRequestURI().split("/");
		String fileName = url[url.length-1];
		String path=request.getContextPath();
		System.out.println(path);
		System.out.println(fileName);
		if("start.do".equals(fileName)) {
			String id=request.getParameter("userId");
			String pwd=request.getParameter("userPwd");
			MemberDAO dao=new MemberDAO();
			System.out.println(id);
			System.out.println(pwd);
			try {
				MemberVO vo=dao.memberCheck(id, pwd);
				HttpSession sess = request.getSession();
				sess.setAttribute("loginInfo", vo);
				if(vo!=null) {
					sess = request.getSession();
					sess.setAttribute("loginInfo", vo);
					request.getRequestDispatcher("waiting.jsp").forward(request, response);
				}
				else {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println("<script>alert('아이디 또는 비밀번호가 일치하지 않습니다.');location.href='start.jsp'</script>"); 
					writer.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		else if("createID.do".equals(fileName)) {
			String id=request.getParameter("userId");
			String pwd=request.getParameter("userPwd");
			String nickname=request.getParameter("nickname");
			MemberDAO dao=new MemberDAO();
			try {
				boolean idCheck=dao.memberCheck(id);
				if(idCheck==true && pwd.trim()!=""&&nickname.trim()!="") {
					dao.memberJoin(id, pwd, nickname);
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println("<script>alert('회원가입에 성공!'); location.href='start.jsp';</script>"); 
					writer.close();
				}
				else {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println("<script>alert('입력이 올바르지 않습니다.'); location.href='createID.jsp';</script>"); 
					writer.close();
				}
			} catch (Exception e) {
				
			}
		}
	}
	
}
