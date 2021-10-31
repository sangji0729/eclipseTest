package log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class LogDAO {
	private LogDAO() {
	}

	private static LogDAO instance = new LogDAO();

	public static LogDAO getInstance() {
		return instance;
	}

	public static void insertLog(HashMap<String, Object> log) {
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Log (log_id, log_target, log_etc, log_ip) " + "VALUES (?, ?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) log.get("id"));
			pstmt.setString(2, (String) log.get("target"));
			pstmt.setString(3, (String) log.get("etc"));
			pstmt.setString(4, (String) log.get("ip"));
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

	}

	public ArrayList<HashMap<String, Object>> logList(int page) {
		ArrayList<HashMap<String, Object>> logList = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM LogView LIMIT ?, 20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				logList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("log_no", rs.getInt("log_no"));
					map.put("log_id", rs.getString("log_id"));
					map.put("log_target", rs.getString("log_target"));
					map.put("log_etc", rs.getString("log_etc"));
					map.put("log_date", rs.getString("log_date"));
					map.put("log_ip", rs.getString("log_ip"));
					logList.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}

		return logList;
	}

	public ArrayList<String> list(String name) {
		ArrayList<String> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT " + name + " FROM LogView";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<String>();
				while (rs.next()) {
					list.add(rs.getString(name));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> selectId(String id, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log Logview WHERE log_id=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc " + "FROM Log WHERE log_id=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.setInt(3, i);
			rs = pstmt.executeQuery();
			System.out.println(pstmt);
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> selectIp(String ip, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log Logview WHERE log_ip=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc " + "FROM Log WHERE log_ip=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setString(2, ip);
			pstmt.setInt(3, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> selectTarget(String target, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log LogView WHERE log_target=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc, log_target "
				+ "FROM LogView WHERE log_target=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, target);
			pstmt.setString(2, target);
			pstmt.setInt(3, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> selectIdIpTarget(String id, String ip, String target, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log LogView WHERE log_id=? AND log_ip=? AND log_target=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc, log_target "
				+ "FROM LogView WHERE log_id=? AND log_ip=? AND log_target=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, ip);
			pstmt.setString(3, target);
			pstmt.setString(4, id);
			pstmt.setString(5, ip);
			pstmt.setString(6, target);
			pstmt.setInt(7, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> search(String searchName, String search, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM Log WHERE log_" + searchName
				+ " LIKE CONCAT('%',?,'%')) as totalcount, " + "log_no, log_ip, log_date, log_id, log_etc, log_target "
				+ "FROM Log WHERE log_" + searchName + " LIKE CONCAT('%',?,'%') limit ?, 10";
		// System.out.println(sql);
		// SELECT * FROM logview WHERE log_"+ searchName +" LIKE CONCAT('%','?','%')

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, search);
			pstmt.setString(2, search);
			pstmt.setInt(3, page);

			rs = pstmt.executeQuery();
			System.out.println(pstmt);
			// sql : 'SELECT (SELECT count(*) FROM log WHERE log_ip LIKE CONCAT('%',?,'%'))
			// as totalcount, log_no, log_ip, log_date, log_id, log_etc, log_target FROM log
			// WHERE log_ip LIKE CONCAT('%',?,'%') limit ?, 10', parameters :
			// ['222','222',1]
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

	public ArrayList<HashMap<String, Object>> memberList(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT COUNT(*) FROM Login) AS totalcount, " + "l.* FROM Login l LIMIT ?, 20";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("pw", rs.getString("pw"));
					map.put("email", rs.getString("email"));
					map.put("joindate", rs.getString("joindate"));
					map.put("grade", rs.getInt("grade"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> selectIdIp(String id, String ip, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log LogView WHERE log_id=? AND log_ip=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc, log_target "
				+ "FROM LogView WHERE log_id=? AND log_ip=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, ip);
			pstmt.setString(3, id);
			pstmt.setString(4, ip);
			pstmt.setInt(5, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> selectIdTarget(String id, String target, int i) {

		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log LogView WHERE log_id=? AND log_target=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc, log_target "
				+ "FROM LogView WHERE log_id=? AND log_target=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, target);
			pstmt.setString(3, id);
			pstmt.setString(4, target);
			pstmt.setInt(5, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public ArrayList<HashMap<String, Object>> selectIpTarget(String ip, String target, int i) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT(SELECT count(*) FROM Log LogView WHERE log_ip=? AND log_target=? AND log_ip=?) as totalcount, "
				+ "log_no, log_ip, log_date, log_target, log_id, log_etc, log_target "
				+ "FROM LogView WHERE log_ip=? AND log_target=? limit ? ,20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setString(2, target);
			pstmt.setString(3, ip);
			pstmt.setString(4, target);
			pstmt.setInt(5, i);
			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					// log_no, log_ip, log_date, log_target, log_id, log_etc
					// totalcount는 없으니 view로 만들어주세요
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

	public int changeGrade(HashMap<String, Object> dto) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Login SET grade=? WHERE no=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) dto.get("grade"));
			pstmt.setInt(2, (int) dto.get("no"));
			/* System.out.println(pstmt); */
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int adminChangePW(HashMap<String, Object> dto) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Login SET pw=? WHERE no=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) dto.get("pw"));
			pstmt.setInt(2, (int) dto.get("no"));
			/* System.out.println(pstmt); */
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int adminDelete(int no) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Login WHERE no=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int adminChangeEmail(HashMap<String, Object> dto) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Login SET email=? WHERE no=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) dto.get("email"));
			pstmt.setInt(2, (int) dto.get("no"));
			/* System.out.println(pstmt); */
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}
}