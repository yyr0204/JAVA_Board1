package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class BoardDAO extends DBConnPool{
	public BoardDAO() {}
	
	//검색조건에 맞는 게시물 수 
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM board";
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement();	//쿼리문 생성
			rs = stmt.executeQuery(query);	//쿼리문 실행
			rs.next();						// 커서를 첫번째 행으로 이동
			totalCount = rs.getInt(1);		//첫번째 컬럼 값을 가져옴
		} catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}

	//검색 조건에 맞는 게시물 목록 가져오기
	public List<BoardDTO> selectList(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		String query = "SELECT * FROM board ";
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		query += " ORDER BY num DESC ";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getNString("num"));
				dto.setTitle(rs.getNString("title"));
				dto.setContent(rs.getNString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getNString("id"));
				dto.setVisitcount(rs.getNString("visitcount"));
				
				bbs.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
}
