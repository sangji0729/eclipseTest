package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class LoginDAO {
	private LoginDAO() {

	}

	private static LoginDAO instance = new LoginDAO();

	public static LoginDAO getInstance() {
		return instance;
	}

	public HashMap<String, Object> login(String id, String pw) {
		HashMap<String, Object> login = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id, pw, name, grade FROM Login WHERE id=? AND pw=? AND grade BETWEEN 1 AND 2";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			if (rs != null) {
				login = new HashMap<String, Object>();

				while (rs.next()) {
					login.put("id", rs.getString("id"));
					login.put("pw", rs.getString("pw"));
					login.put("name", rs.getString("name"));
					login.put("grade", rs.getInt("grade"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return login;
	}

	public HashMap<String, Object> login() {
		HashMap<String, Object> login = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * from Login";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				login = new HashMap<String, Object>();
				login.put("id", rs.getString("id"));
				login.put("name", rs.getString("name"));
				login.put("grade", rs.getInt("grade"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		
		return login;
	}
}