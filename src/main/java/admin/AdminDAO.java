package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class AdminDAO {
	private AdminDAO() {
	}

	private static AdminDAO instance = new AdminDAO();

	public static AdminDAO getInstance() {

		return instance;
	}

	public ArrayList<HashMap<String, Object>> postDaList(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM DailyView LIMIT ?, 10";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("dano", rs.getInt("dano"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("datable", rs.getString("datable"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("joindate", rs.getString("dadate2"));
					map.put("dacount", rs.getInt("dacount"));

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

	public ArrayList<HashMap<String, Object>> search(String search, String searchName, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM Log WHERE log_" + search
				+ " LIKE CONCAT('%',?,'%')) as totalcount, " + "log_no, log_ip, log_date, log_id, log_etc, log_target "
				+ "FROM Log WHERE log_" + search + " LIKE CONCAT('%',?,'%') limit ?, 20";


		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchName);
			pstmt.setString(2, searchName);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			System.out.println(pstmt);

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("log_no", rs.getInt("log_no"));
					map.put("log_ip", rs.getString("log_ip"));
					map.put("log_date", rs.getString("log_date"));
					map.put("log_target", rs.getString("log_target"));
					map.put("log_id", rs.getString("log_id"));
					map.put("log_etc", rs.getString("log_etc"));
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
}