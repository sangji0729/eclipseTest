package romance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class RomanceCommentDAO {
	private RomanceCommentDAO() {

	}

	private static RomanceCommentDAO instance = new RomanceCommentDAO();

	public static RomanceCommentDAO getInstance() {
		return instance;
	}

	public int commentWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO RomanceComment (rno, rccontent, rcip, no) VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("rno"));
			pstmt.setString(2, (String) map.get("rccontent"));
			pstmt.setString(3, (String) map.get("ip"));
			pstmt.setString(4, (String) map.get("id"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int commentModify(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE RomanceComment SET rccontent=? WHERE rcno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("rccontent"));
			pstmt.setInt(2, (int) map.get("rcno"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int commentDelete(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM RomanceComment WHERE rcno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("rcno"));
			pstmt.setString(2, (String) map.get("id"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> commentList(int rno) {
		ArrayList<HashMap<String, Object>> commentList = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceCommentView WHERE dano=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			rs = pstmt.executeQuery();

			if (rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();

				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("rcno", rs.getInt("rcno"));
					map.put("rno", rs.getInt("rno"));
					map.put("rccontent", rs.getString("rccontent"));
					map.put("rcdate", rs.getString("rcdate"));
					map.put("rcip", rs.getString("rcip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					commentList.add(map);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return commentList;
	}
}