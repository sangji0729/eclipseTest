package daily;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class IntegurationDAO {

	public int Write(HashMap<String, Object> map, String tableName) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO tableName(title, content, filename, thumbnail, no)"
				+ " VALUE(?, ?, ?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setString(5, (String) map.get("id"));
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int likeUpInsert(HashMap<String, Object> map, String tableName) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO tableName (login_no, no, ip) VALUES (?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("dano"));
			pstmt.setString(3, (String) map.get("daip"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return result;
	}
}