package daily;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class DailyCommentDAO {

	private DailyCommentDAO() {
	}

	private static DailyCommentDAO instance = new DailyCommentDAO();

	public static DailyCommentDAO getInstance() {
		return instance;
	}

	public int commentInput(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO DailyComment (daccontent, dano, no) "
				+ "VALUES(?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("content"));
			pstmt.setInt(2, (int) map.get("dano"));
			pstmt.setString(3, (String) map.get("id"));
			System.out.println(pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public ArrayList<HashMap<String, Object>> dailyCommentList(int dano) {
		ArrayList<HashMap<String, Object>> dailyCommentList = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM DailyCommentView WHERE dano=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dano);
			rs = pstmt.executeQuery();

			if (rs != null) {
				dailyCommentList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("dacno", rs.getInt("dacno"));
					map.put("dano", rs.getInt("dano"));
					map.put("daccontent", rs.getString("daccontent"));
					map.put("dacdate", rs.getString("dacdate"));
					map.put("dacip", rs.getString("dacip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					dailyCommentList.add(map);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return dailyCommentList;
	}

	public int dailyCommentDelete(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM DailyComment WHERE dacno=? " + "AND dano=? AND no=(SELECT no FROM Login WHERE id =?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String) map.get("dacno")));
			pstmt.setInt(2, Util.str2Int((String) map.get("dano")));
			pstmt.setString(3, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int dailyCommentModify(int dacno, String daccontent, String id) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE DailyComment SET daccontent =? WHERE dacno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, daccontent);
			pstmt.setInt(2, dacno);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
