package omok;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import omok.MemberVO;

public class MemberDAO {
	private PreparedStatement pstmt;
	private Connection con;
	private ResultSet rs;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MemberVO memberCheck(String id,String pwd) throws Exception {
		con = dataFactory.getConnection();
		String query = "SELECT * FROM t_member WHERE id=? AND pwd=?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, pwd);
		rs = pstmt.executeQuery();
		System.out.println(query);
		MemberVO vo = null;
		if (rs.next()) {
			vo = new MemberVO();
			vo.setId(rs.getString("id"));
			vo.setPwd(rs.getString("pwd"));
			vo.setNickname(rs.getString("nickname"));
		}
		return vo;
	}
	public boolean memberCheck(String id) throws Exception {
		con = dataFactory.getConnection();
		String query = "SELECT * FROM t_member WHERE id=?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		System.out.println(query);
		MemberVO vo = null;
		if (rs.next()) {
			vo = new MemberVO();
			vo.setId(rs.getString("id"));
			vo.setPwd(rs.getString("pwd"));
			vo.setNickname(rs.getString("nickname"));
		}
		if(vo==null) {
			return true;
		}
		else {
			return false;
		}
	}
	public List<MemberVO> memberList() throws Exception {
		con = dataFactory.getConnection();
		String query = "SELECT * FROM t_member";
		pstmt = con.prepareStatement(query);
		rs = pstmt.executeQuery();
		System.out.println(query);
		List<MemberVO> voList = null;
		if (rs.next()) {
			MemberVO vo = new MemberVO();
			vo.setId(rs.getString("id"));
			vo.setPwd(rs.getString("pwd"));
			vo.setNickname(rs.getString("nickname"));
			voList.add(vo);
		}
		return voList;
	}
	public void memberJoin(String id, String pwd, String nickname) throws Exception {
		con = dataFactory.getConnection();
		String query = "INSERT INTO t_member VALUES(?,?,?)";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, nickname);
			pstmt.executeUpdate(); // 추가 성공하면 1 반환

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




//	public List<BoardVO> boardList() {
//		List<BoardVO> list = new ArrayList<BoardVO>();
//		try {
//			con = dataFactory.getConnection();
//			String query = "select * from board order by boardno";
//			System.out.println(query);
//			pstmt = con.prepareStatement(query);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				String boardno=rs.getString("boardno");
//				String title=rs.getString("title");
//				String text=rs.getString("text");
//				Date insertdate=rs.getDate("insertdate");
//				BoardVO vo = new BoardVO();
//				vo.setBoardno(boardno);
//				vo.setTitle(title);
//				vo.setText(text);
//				vo.setInsertdate(insertdate);
//				list.add(vo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {rs.close();}catch(Exception e) {};
//			try {pstmt.close();}catch(Exception e) {};
//			try {con.close();}catch(Exception e) {};
//		}
//		System.out.println(list);
//		return list;
//	}
//	public void update(BoardVO MemberVO,String title,String text) {
//		try {
//			con = dataFactory.getConnection();
//			String query = "UPDATE board set title=?,text=? WHERE boardno=?";
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, title);
//			pstmt.setString(2, text);
//			pstmt.setString(3, MemberVO.getBoardno());
//			System.out.println(query);
//			int r = pstmt.executeUpdate();
//			pstmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	
//	
//	public boolean addBoard(BoardVO MemberVO) {
//		boolean result = false;
//		try {
//			con = dataFactory.getConnection();
//			String query = "INSERT INTO board (boardno,title,text) VALUES (seq.nextval,?,?)";
//			pstmt = con.prepareStatement(query);
//			System.out.println(query);
//			int pstmtInt = 1;
//			pstmt.setString(pstmtInt++, MemberVO.getTitle());
//			pstmt.setString(pstmtInt++, MemberVO.getText());
//			int r = pstmt.executeUpdate();
//			if (r > 0) result = true;
//			pstmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public void delBoard(String boardno) {
//		try {
//			con = dataFactory.getConnection();
//			String query = "DELETE FROM board WHERE boardno=?";
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, boardno);
//			System.out.println(query);
//			int r = pstmt.executeUpdate();
//			pstmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}	

