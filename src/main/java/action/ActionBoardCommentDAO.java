package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class ActionBoardCommentDAO {
	private ActionBoardCommentDAO() {

	}

	private static ActionBoardCommentDAO instance = new ActionBoardCommentDAO();

	public static ActionBoardCommentDAO getInstance() {
		return instance;
	}

	public int commentWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ActionComment (ano, accontent, acip, no) VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("ano"));
			pstmt.setString(2, (String) map.get("accontent"));
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
		String sql = "UPDATE ActionComment SET accontent=? WHERE acno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("accontent"));
			pstmt.setInt(2, (int) map.get("acno"));
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
		String sql = "DELETE FROM ActionComment WHERE acno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("acno"));
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
