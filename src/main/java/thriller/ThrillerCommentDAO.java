package thriller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class ThrillerCommentDAO {
	private ThrillerCommentDAO() {

	}

	private static ThrillerCommentDAO instance = new ThrillerCommentDAO();

	public static ThrillerCommentDAO getInstance() {
		return instance;
	}

	public int commentWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ThrillerComment (tno, tccontent, tcip, no) VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("tno"));
			pstmt.setString(2, (String) map.get("tccontent"));
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

	public int modify(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE ThrillerComment SET tccontent=? WHERE tcno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("tccontent"));
			pstmt.setInt(2, (int) map.get("tcno"));
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
		String sql = "DELETE FROM ThrillerComment WHERE tcno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("tcno"));
			pstmt.setString(2, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}
}