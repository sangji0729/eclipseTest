package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class AdminCategoryDAO {
	private AdminCategoryDAO() {
	}

	private static AdminCategoryDAO instance = new AdminCategoryDAO();

	public static AdminCategoryDAO getInstance() {
		return instance;
	}

	// category
	public ArrayList<HashMap<String, Object>> getContentsByCategory(String tableName, int page, int maxLimit) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String viewName = getViewNameByCategory(tableName);
		String sql = String.format("SELECT * FROM %s limit ?, %d", viewName, maxLimit);

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			// 몇개 출력, 몇번쨰부터
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("no", rs.getInt(getColumnNameByViewName(viewName, "no")));
					map.put("id", rs.getString("id"));// id
					map.put("name", rs.getString("name"));

					map.put("table", rs.getString(getColumnNameByViewName(viewName, "table")));// 각 table명
					map.put("title", rs.getString(getColumnNameByViewName(viewName, "title")));// 각 title 값
					map.put("content", rs.getString(getColumnNameByViewName(viewName, "content")));// 각 title 값
					map.put("fileName", rs.getString(getColumnNameByViewName(viewName, "thumbnail")));// 각 thumbnail값

					map.put("joinDate", rs.getString(getColumnNameByViewName(viewName, "date")));// 각 date값
					map.put("count", rs.getInt(getColumnNameByViewName(viewName, "count")));// 각 count값
					map.put("like", rs.getInt(getColumnNameByViewName(viewName, "like")));// 각 like 값
					map.put("viewName", rs.getString(getColumnNameByViewName(viewName, "viewName")));// 각 date값
					map.put("totalcount", rs.getInt("totalcount"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	public HashMap<String, Object> detailAdmin() {
		HashMap<String, Object> id = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM Login WHERE grade=1";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = new HashMap<String, Object>();
				id.put("id", rs.getString("id"));
				id.put("grade", rs.getString("grade"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}

		return id;
	}

	public ArrayList<HashMap<String, Object>> search(String tableName, String search, String firstInitial, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String viewName = getViewNameByCategory(tableName);
		String sql = "SELECT * FROM " + viewName + " WHERE " + firstInitial + "title"
				+ " LIKE CONCAT('%',?,'%') LIMIT ?, 20";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search);
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", rs.getString("id"));// id
					map.put("no", rs.getInt(firstInitial + "no"));// id

					map.put("name", rs.getString("name"));// jsp에는 안 집어넣었지만 혹시 몰라서 나중에 필요하면 쓰기로..

					map.put("table", rs.getString(firstInitial + "table"));// 각 table명
					map.put("title", rs.getString(firstInitial + "title"));// 각 title 값
					map.put("content", rs.getString(firstInitial + "content"));// 각 title 값
					map.put("fileName", rs.getString(firstInitial + "thumbnail"));// 각 thumbnail값
					map.put("joinDate", rs.getString(firstInitial + "date"));// 각 date값
					map.put("count", rs.getInt(firstInitial + "count"));// 각 count값
					map.put("like", rs.getInt(firstInitial + "like"));// 각 like 값
					map.put("viewName", rs.getString(firstInitial + "viewName"));// 각 date값
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> searchA(String searchColumn, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM Actionview" + " WHERE " + searchColumn
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, aviewname, atable, ano, atitle, acontent, athumbnail, adate, acount, aip, no, id, name FROM Actionview"
				+ " WHERE " + searchColumn + " LIKE CONCAT('%', ?, '%') limit ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("table", rs.getString("atable"));
					map.put("viewName", rs.getString("aviewname"));
					map.put("title", rs.getString("atitle"));
					map.put("content", rs.getString("acontent"));
					map.put("thumbnail", rs.getString("athumbnail"));
					map.put("joinDate", rs.getString("adate"));
					map.put("ip", rs.getString("aip"));
					map.put("count", rs.getInt("acount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));

					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return list;
	}

	public ArrayList<HashMap<String, Object>> searchDA(String Column, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM DailyView" + " WHERE " + Column
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, daviewname, dano,totalcount, datable, datitle, dacontent, dathumbnail, dadate, dacount, daip, no, id, name FROM DailyView"
				+ " WHERE " + Column + " LIKE CONCAT('%', ?, '%') limit ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dano", rs.getInt("dano"));
					map.put("table", rs.getString("datable"));
					map.put("daviewName", rs.getString("daviewName"));
					map.put("title", rs.getString("datitle"));
					map.put("content", rs.getString("dacontent"));
					map.put("thumbnail", rs.getString("dathumbnail"));
					map.put("joinDate", rs.getString("dadate"));
					map.put("daip", rs.getString("daip"));
					map.put("count", rs.getInt("dacount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return list;
	}

	public ArrayList<HashMap<String, Object>> searchD(String column, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM DramaView" + " WHERE " + column
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, dno, rownum, dviewName, dtable, dtitle, dcontent, dthumbnail, ddate, dcount, dip, no, id, name FROM DramaView"
				+ " WHERE " + column + " LIKE CONCAT('%', ?, '%') limit ?, 10";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dno", rs.getInt("dno"));
					map.put("table", rs.getString("dtable"));
					map.put("viewName", rs.getString("dviewName"));
					map.put("title", rs.getString("dtitle"));
					map.put("content", rs.getString("dcontent"));
					map.put("thumbnail", rs.getString("dthumbnail"));
					map.put("joinDate", rs.getString("ddate"));
					map.put("ip", rs.getString("dip"));
					map.put("count", rs.getInt("dcount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> searchR(String column, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM RomanceView" + " WHERE " + column
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, rno,rtable, rviewName, rtitle, rcontent, rthumbnail, rdate, rcount, rip, no, id, name FROM RomanceView"
				+ " WHERE " + column + " LIKE CONCAT('%', ?, '%') limit ?, 10";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("rno", rs.getInt("rno"));
					map.put("table", rs.getString("rtable"));
					map.put("viewName", rs.getString("rviewname"));
					map.put("title", rs.getString("rtitle"));
					map.put("content", rs.getString("rcontent"));
					map.put("thumbnail", rs.getString("rthumbnail"));
					map.put("joinDate", rs.getString("rdate"));
					map.put("ip", rs.getString("rip"));
					map.put("count", rs.getInt("rcount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> searchT(String column, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM ThrillerView" + " WHERE " + column
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, tno, tviewName, ttable, ttitle, tcontent, tthumbnail, tdate, tcount, tip, no, id, name FROM ThrillerView"
				+ " WHERE " + column + " LIKE CONCAT('%', ?, '%') limit ?, 10";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("tno", rs.getInt("tno"));
					map.put("table", rs.getString("ttable"));
					map.put("viewName", rs.getString("tviewname"));
					map.put("title", rs.getString("ttitle"));
					map.put("content", rs.getString("tcontent"));
					map.put("thumbnail", rs.getString("tthumbnail"));
					map.put("joinDate", rs.getString("tdate"));
					map.put("ip", rs.getString("tip"));
					map.put("count", rs.getInt("tcount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> searchF(String column, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM fantasyview" + " WHERE " + column
				+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
				+ " commentcount, fno, rownum, ftable, fviewName,ftitle, fcontent, fthumbnail, fdate, fcount, fip, no, id, name FROM fantasyview"
				+ " WHERE " + column + " LIKE CONCAT('%', ?, '%') limit ?, 10";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);


			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("fno", rs.getInt("fno"));
					map.put("table", rs.getString("ftable"));
					map.put("viewName", rs.getString("fviewname"));
					map.put("title", rs.getString("ftitle"));
					map.put("content", rs.getString("fcontent"));
					map.put("thumbnail", rs.getString("fthumbnail"));
					map.put("joinDate", rs.getString("fdate"));
					map.put("ip", rs.getString("fip"));
					map.put("count", rs.getInt("fcount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;
	}

	public int delete(int no, String lowertable, String lowerTC) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String lowerToCapTable = changeToCap(lowertable);
		String sql = "DELETE FROM " + lowerToCapTable + " WHERE " + lowerTC + "no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}
		return result;
	}

	private String getViewNameByCategory(String category) {
		switch (category) {// 장르
		case "Action": {
			return "Actionview";
		}
		case "Daily": {
			return "DailyView";
		}
		case "Drama": {
			return "DramaView";
		}
		case "Fantasy": {
			return "fantasyview";
		}
		case "Romance": {
			return "RomanceView";
		}
		case "Thriller": {
			return "ThrillerView";
		}

		}
		return null;
	}

	private String getColumnNameByViewName(String viewName, String columnName) {// viewname이 들어오면, a,da,d로 바꿔주기
		// columnName은 table, title...
		String result = null;
		switch (viewName) {
		case "Actionview": {
			result = "a";
			break;
		}
		case "DailyView": {
			result = "da";
			break;
		}
		case "DramaView": {
			result = "d";
			break;
		}
		case "fantasyview": {
			result = "f";
			break;
		}
		case "RomanceView": {
			result = "r";
			break;
		}
		case "ThrillerView": {
			result = "t";
			break;
		}
		}

		return result + columnName;
	}

	public String getColumnNameByTable(String tableName, String searchColumn) {// viewname이 들어오면, a,da,d로 바꿔주기
		// columnName은 table, title...
		// 그래서 이함수를 뜯어고치면 되지않을까 생각해서..
		String result = null;

		if (searchColumn.equals("name") || searchColumn.equals("id")) {//// 이렇게 해야할듯 싶어요... 넘 감사합니다...^^ 식사ㅗ하시고 하세요 넵!
			return searchColumn;
			// 여기서 이름하고 아이디를 목 걸러내네요
		} else {

			switch (tableName) {
			case "Action": {

				result = "a" + searchColumn;// 여기서 a붙이네요 네 그래서 위에 if문으로 name id만 걸러낼려고 했어서

				break;
			}
			case "Daily": {
				result = "da" + searchColumn;
				// 여기로 이동해서 da + name으로 작동되네요

				break;
			}
			case "Drama": {
				result = "d" + searchColumn;
				break;
			}
			case "Fantasy": {
				result = "f" + searchColumn;
				break;
			}
			case "Romance": {
				result = "r" + searchColumn;
				break;
			}
			case "Thriller": {
				result = "t" + searchColumn;
				break;
			}
			}
		}
		return result;
	}

	public String getColumnNameByTable(String tableName) {// viewname이 들어오면, a,da,d로 바꿔주기
		// columnName은 table, title...
		String result = null;
		switch (tableName) {
		case "Action": {
			result = "a";
			break;
		}
		case "Daily": {
			result = "da";
			break;
		}
		case "Drama": {
			result = "d";
			break;
		}
		case "Fantasy": {
			result = "f";
			break;
		}
		case "Romance": {
			result = "r";
			break;
		}
		case "Thriller": {
			result = "t";
			break;
		}
		}

		return result;
	}

	public String changeToCap(String lowertable) {
		String result = null;
		switch (lowertable) {
		case "action": {
			result = "Action";
			break;
		}
		case "daily": {
			result = "Daily";
			break;
		}
		case "drama": {
			result = "Drama";
			break;
		}
		case "fantasy": {
			result = "Fantasy";
			break;
		}
		case "romance": {
			result = "Romance";
			break;
		}
		case "thriller": {
			result = "Thriller";
			break;
		}
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> search(String searchColumn, String searchWord, int i, int page) {
		// TODO Auto-generated method stub
		return null;
	}
}