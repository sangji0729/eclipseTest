package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class MainDAO {
	private MainDAO() {
		
	}
	private static MainDAO instance = new MainDAO();
	public static MainDAO getInstance() {
		return instance;
	}

	public ArrayList<HashMap<String, Object>> indexBestBoardList(int best) {
		 ArrayList<HashMap<String, Object>> list = null;
		 Connection conn = DBConnection.dbConnection();
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String sql = "SELECT * FROM Actionview AS av JOIN DailyView AS dav, DramaView AS dv, fantasyview AS fv, RomanceView AS rv, ThrillerView AS tv "
		 		+ "ON dav.dalike = av.alike, dv.dlike = av.alike, fv.flike = av.alike, rv.rlike = av.alike, tv.tlike = av.alike ORDER BY alike DESC LIMIT ?, 5";
		 
		 try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("alike", rs.getInt("alike"));
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("dadate", rs.getDate("dadate"));
					map.put("daip", rs.getString("daip"));
					map.put("dacount", rs.getInt("dacount"));
					map.put("dalike", rs.getInt("dalike"));
					map.put("dno", rs.getInt("dno"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("ddate", rs.getDate("ddate"));
					map.put("dip", rs.getString("dip"));
					map.put("dcount", rs.getInt("dcount"));
					map.put("dlike", rs.getInt("dlike"));
					map.put("fno", rs.getInt("fno"));
					map.put("ftitle", rs.getString("ftitle"));
					map.put("fcontent", rs.getString("fcontent"));
					map.put("fthumbnail", rs.getString("fthumbnail"));
					map.put("fdate", rs.getDate("fdate"));
					map.put("fip", rs.getString("fip"));
					map.put("fcount", rs.getInt("fcount"));
					map.put("flike", rs.getInt("flike"));
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rlike", rs.getInt("rlike"));
					map.put("tno", rs.getInt("tno"));
					map.put("ttitle", rs.getString("ttitle"));
					map.put("tcontent", rs.getString("tcontent"));
					map.put("tthumbnail", rs.getString("tthumbnail"));
					map.put("tdate", rs.getDate("tdate"));
					map.put("tip", rs.getString("tip"));
					map.put("tcount", rs.getInt("tcount"));
					map.put("tlike", rs.getInt("tlike"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> indexBestCommentBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM AllViewByCOMMENT ORDER BY commentcount DESC LIMIT ?, 1";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String tableName = rs.getString("atable");
					String num = viewToNo(tableName);
					String boardNo = rs.getString("ano");
					map.put("num", num);
					map.put("boardNo", boardNo);
					map.put("tableName", tableName);
					map.put("atable", rs.getString("atable"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("commentcount", rs.getInt("commentcount"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> indexBestLikeBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM AllViewByLIKE ORDER BY alike DESC LIMIT ?, 5";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String tableName = rs.getString("atable");
					String num = viewToNo(tableName);
					String boardNo = rs.getString("ano");
					map.put("num", num);
					map.put("boardNo", boardNo);
					map.put("tableName", tableName);
					map.put("atable", rs.getString("atable"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("alike", rs.getInt("alike"));
					
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> indexSecondLikeBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM AllViewByCOMMENT ORDER BY commentcount DESC LIMIT ?, 2";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String tableName = rs.getString("atable");
					String num = viewToNo(tableName);
					String boardNo = rs.getString("ano");
					map.put("num", num);
					map.put("boardNo", boardNo);
					map.put("tableName", tableName);
					map.put("atable", rs.getString("atable"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("commentcount", rs.getInt("commentcount"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> indexBestReadBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM AllViewByREADED ORDER BY acount DESC LIMIT ?, 3";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String tableName = rs.getString("atable");
					String num = viewToNo(tableName);
					String boardNo = rs.getString("ano");
					map.put("num", num);
					map.put("boardNo", boardNo);
					map.put("tableName", tableName);
					map.put("atable", rs.getString("atable"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("acount", rs.getInt("acount"));
					
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> indexBestDateBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM AllViewByDATE ORDER BY adate DESC LIMIT ?, 5";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if(rs != null) {
				list = new ArrayList<HashMap<String,Object>>();
				while(rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					String tableName = rs.getString("atable");
					String num = viewToNo(tableName);
					String boardNo = rs.getString("ano");
					
					map.put("atable", rs.getString("atable"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getString("adate"));
					map.put("num", num);
					map.put("boardNo", boardNo);
					map.put("tableName", tableName);
					
					list.add(map);
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String viewToNo(String tableName) {
		switch (tableName) {// 키값이 tableName
		case "action": {
			return "ano";
		}
		case "daily": {
			return "dano";
		}
		case "drama": {
			return "dno";
		}
		case "fantasy": {
			return "fno";
		}
		case "romance": {
			return "rno";
		}
		case "thriller": {
			return "tno";
		}
		case "Action": {
			return "ano";
		}
		case "Daily": {
			return "dano";
		}
		case "Drama": {
			return "dno";
		}
		case "Fantasy": {
			return "fno";
		}
		case "Romance": {
			return "rno";
		}
		case "Thriller": {
			return "tno";
		}
		

		}
		return null;
	}
	
	public String getNoName(String boardNo) {
		switch (boardNo) {// 키값이 tableName
		case "ano": {
			return "ano";
		}
		case "dano": {
			return "dano";
		}
		case "dno": {
			return "dno";
		}
		case "fno": {
			return "fno";
		}
		case "rno": {
			return "rno";
		}
		case "tno": {
			return "tno";
		}

		}
		return boardNo;
	}
	
}
