package com.webTest.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.webTest.testmvc.database.DBConnection;
import com.webTest.testmvc.vo.Board;

public class BoardDAO {
	private static BoardDAO dao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public void writeBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into board(b_title, b_content, b_date, b_writer, u_idx, b_order) values(?,?,?,?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_date());
			pstmt.setString(4, board.getB_writer());
			pstmt.setInt(5, board.getU_idx());
			pstmt.executeUpdate();
			
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			if(board.getB_order() == 0) {
				String query = "SELECT * FROM board WHERE b_base=? ORDER BY b_order DESC limit 1";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, board.getB_base());
				rs = pstmt.executeQuery();			
				while(rs.next()) {
					count = rs.getInt("b_order");
				}
			}
			else {
				count = board.getB_order() - 1;
			}			
			
			String sql = "insert into board(b_title, b_content, b_date, b_writer, u_idx, b_order, b_base, b_layer) values(?,?,?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, board.getB_title());
			pstmt2.setString(2, board.getB_content());
			pstmt2.setString(3, board.getB_date());
			pstmt2.setString(4, board.getB_writer());
			pstmt2.setInt(5, board.getU_idx());
			pstmt2.setInt(6, count+1);
			pstmt2.setInt(7, board.getB_base());
			pstmt2.setInt(8, board.getB_layer());
			pstmt2.executeUpdate();
			
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(rs != null) rs.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeBase() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE board SET b_base = b_idx WHERE b_idx = (SELECT b_idx FROM board WHERE b_base is NULL)";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Board> getBoards(int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = (page-1)*7;
		
		try {
			conn = DBConnection.getConnection();
			String query = new StringBuilder()
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM, ta.*\n")
					.append("FROM 			board ta,\n")
					.append("				(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM board ta)) tb\n")
					.append("ORDER BY		b_base DESC, b_order asc\n")
					.append("LIMIT			?, 7\n")
					.toString();
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1,  pageNum);
	       	pstmt.setInt(2,  pageNum);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Board>();

	        while(rs.next()){
	        	Board board = new Board();
	        	board.setRownum(rs.getInt("ROWNUM"));
	        	board.setB_idx(rs.getInt("b_idx"));
	        	board.setB_title(rs.getString("b_title"));
	        	board.setB_content(rs.getString("b_content"));
	        	board.setB_date(rs.getString("b_date"));
	        	board.setB_writer(rs.getString("b_writer"));
	        	board.setU_idx(rs.getInt("u_idx"));
       	       	list.add(board);
	        }
		} catch (Exception e) {
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public ArrayList<Board> searchBoard(String key, int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> list = null;
		int pageNum = (page-1)*7;
		
		try {
			conn = DBConnection.getConnection();
			String query = new StringBuilder()
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM, ta.*\n")
					.append("FROM 			board ta,\n")
					.append("				(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM board ta)) tb\n")
					.append("where			b_title like ?")					
					.append("ORDER BY		b_base DESC, b_order asc\n")
					.append("LIMIT			?, 7\n")
					.toString();
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1,  pageNum);
	       	pstmt.setString(2, "%" + key + "%");
	       	pstmt.setInt(3,  pageNum);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Board>();

	        while(rs.next()){
	        	Board board = new Board();
	        	board.setRownum(rs.getInt("ROWNUM"));
	        	board.setB_idx(rs.getInt("b_idx"));
	        	board.setB_title(rs.getString("b_title"));
	        	board.setB_content(rs.getString("b_content"));
	        	board.setB_date(rs.getString("b_date"));
	        	board.setB_writer(rs.getString("b_writer"));
	        	board.setU_idx(rs.getInt("u_idx"));
       	       	list.add(board);
	        }
		} catch (Exception e) {
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Board findBoard(String idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		Board board = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "select * from board where b_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getString("b_date"));
				board.setB_writer(rs.getString("b_writer"));
				board.setU_idx(rs.getInt("u_idx"));
				board.setB_layer(rs.getInt("b_layer"));
				board.setB_base(rs.getInt("b_base"));
				board.setB_order(rs.getInt("b_order"));				
			}
		} catch(Exception ex) {
			System.out.println("SQLException : "+ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return board;
	}
	
	public void editBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "update board set b_title=?, b_content=?, b_date=? where b_idx=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setString(3, board.getB_date());
			pstmt.setInt(4, board.getB_idx());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select count(*) count from board";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("count");
			}
		} catch(Exception e) {
			
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	public void deleteBoard(String idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "delete from board where b_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(idx));
			pstmt.executeUpdate();
		} catch(Exception e) {
			
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch(SQLException e) { 
				e.printStackTrace();
			}
		}
	}
}