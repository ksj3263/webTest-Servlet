package com.webTest.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.webTest.testmvc.database.DBConnection;
import com.webTest.testmvc.vo.Reply;

public class ReplyDAO {
	private static ReplyDAO dao = null;
	
	private ReplyDAO() {
		
	}
	
	public static ReplyDAO getInstance() {
		if(dao == null) {
			dao = new ReplyDAO();
		}
		return dao;
	}
	
	public void writeReply(Reply reply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into reply(r_writer, r_content, r_date, b_idx, u_idx) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getR_writer());
			pstmt.setString(2, reply.getR_content());
			pstmt.setString(3, reply.getR_date());
			pstmt.setInt(4, reply.getB_idx());
			pstmt.setInt(5, reply.getU_idx());
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
	
	public ArrayList<Reply> getReply(int idx) {
		ArrayList<Reply> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from reply where b_idx = ?";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setInt(1,  idx);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Reply>();

	        while(rs.next()){     
	        	Reply reply = new Reply();
	        	reply.setR_idx(rs.getInt("r_idx"));
	        	reply.setB_idx(rs.getInt("b_idx"));
	        	reply.setU_idx(rs.getInt("u_idx"));
	        	reply.setR_content(rs.getString("r_content"));
	        	reply.setR_date(rs.getString("r_date"));
	        	reply.setR_writer(rs.getString("r_writer"));
       	       	list.add(reply);
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
	
	public Reply findReply(String idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Reply reply = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from reply where r_idx = ?";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setString(1,  idx);
	        rs = pstmt.executeQuery();

	        while(rs.next()){   
	        	reply = new Reply();
	        	reply.setR_idx(rs.getInt("r_idx"));
	        	reply.setB_idx(rs.getInt("b_idx"));
	        	reply.setU_idx(rs.getInt("u_idx"));
	        	reply.setR_content(rs.getString("r_content"));
	        	reply.setR_date(rs.getString("r_date"));
	        	reply.setR_writer(rs.getString("r_writer"));
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
		
		return reply;
	}
	
	public void deleteReply(String idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "delete from reply where r_idx = ?";
	       	pstmt = conn.prepareStatement(query);
	       	pstmt.setString(1,  idx);
	        pstmt.executeUpdate();		
	    } catch (Exception e) {
			
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void editReply(Reply reply) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String query = "update reply set r_content=?, r_date=? where r_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getR_content());
			pstmt.setString(2, reply.getR_date());			
			pstmt.setInt(3, reply.getR_idx());
			pstmt.executeUpdate();
		} catch (Exception e) {
			
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
