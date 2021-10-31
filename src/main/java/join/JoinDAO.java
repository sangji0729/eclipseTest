package join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class JoinDAO {

	private JoinDAO() {

	}

	private static JoinDAO instance = new JoinDAO();

	public static JoinDAO getInstance() {
		return instance;
	}

	public int join(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Login (id, name, pw, email) VALUES (?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("id"));
			pstmt.setString(2, (String) map.get("name"));
			pstmt.setString(3, (String) map.get("pw"));
			pstmt.setString(4, (String) map.get("email"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int idCheck(String id) {
		int result = 1;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM Login WHERE id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("count(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return result;
	}

	public int emCheck(String email) {
		int result = 1;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM Login WHERE email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("count(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return result;
	}

	public String findID(String email) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String result = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM Login WHERE email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return result;
	}

	public String findPW(String id) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String result = null;
		ResultSet rs = null;
		String sql = "SELECT pw FROM Login WHERE id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString("pw");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return result;
	}
}