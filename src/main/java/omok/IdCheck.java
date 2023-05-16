//package omok;
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/idCheck.do")
//public class IdCheck extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String id = request.getParameter("id");
//		MemberDAO dao=new MemberDAO();
//		try {
//			boolean check=dao.memberCheck(id);		
//			System.out.println(check);
//			response.getWriter().print(check);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
