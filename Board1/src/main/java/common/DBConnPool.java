package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
	public DBConnPool() {
		try {
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env");	//현재 루트 디렉터리에서 Context 객체 얻기
			DataSource source = (DataSource)ctx.lookup("dbcp_myoracle");	//context.xml에 추가한 dbcp_myoracle 얻기
			
			con = source.getConnection();	//커넥션 풀을 이용해 연결
			
			System.out.println("DB 커넥션 풀 연결 성공");
		} catch (Exception e) {
			System.out.println("DB 커넥션 풀 연결 실패");
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close();
			
			System.out.println("DB커넥션 풀 자원 해제");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
