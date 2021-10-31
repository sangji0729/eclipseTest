package daily;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class DailyDAO {
	private DailyDAO() {
	}

	private static DailyDAO dailyDAO = new DailyDAO();

	public static DailyDAO getInstance() {
		return dailyDAO;
	}

	public int dailyWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Daily(datitle, dacontent, dafilename, dathumbnail, no)"
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

	public int dailyUpdate(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Daily SET datitle=?, dacontent=? WHERE dano=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("dano"));
			pstmt.setString(4, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int dailyUpdateFile(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Daily SET datitle=?, dacontent=?, dafilename=?, dathumbnail=? WHERE dano=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setInt(5, (int) map.get("dano"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int dailyDelete(HashMap<String, Object> map) {
		// System.out.println( Util.str2Int((String)map.get("dano")));
		// System.out.println((String) map.get("id"));
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Daily WHERE dano=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String) map.get("dano")));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return result;
	}

	public ArrayList<String> findFileName(HashMap<String, Object> map) {
		ArrayList<String> filename = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT dafilename, dathumbnail FROM DailyView "
				+ "WHERE dano =? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String) map.get("dano")));
			pstmt.setString(2, (String) map.get("id"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				filename = new ArrayList<String>();

				String dafilename = rs.getString("dafilename");
				if (dafilename != null && dafilename.contains(".")) {
					filename.add(dafilename);
				} else {
					filename.add(null);
				}
				String dathumbnail = rs.getString("dathumbnail");
				if (dathumbnail != null && dathumbnail.contains(".")) {
					filename.add(dathumbnail);
				} else {
					filename.add(null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return filename;
	}

	public HashMap<String, Object> detail(int dano) {
		HashMap<String, Object> dto = null;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM DailyView WHERE dano=?";
		countUp(dano);

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dano);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new HashMap<String, Object>();
				dto.put("commentcount", rs.getInt("commentcount"));
				dto.put("dano", rs.getInt("dano"));
				dto.put("datitle", rs.getString("datitle"));
				dto.put("dacontent", rs.getString("dacontent"));
				dto.put("dadate", rs.getString("dadate"));
				dto.put("dacount", rs.getInt("dacount"));
				dto.put("daip", rs.getString("daip"));
				dto.put("dalike", rs.getInt("dalike"));
				dto.put("no", rs.getInt("no"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
				dto.put("file", rs.getString("dafilename"));
				dto.put("dathumbnail", rs.getString("dathumbnail"));
				/* System.out.println(dto); */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return dto;
	}

	void countUp(int dano) {
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Daily SET dacount=dacount+1 WHERE dano=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dano);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
	}

	public int likeUp2(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Daily SET dalike=dalike+1 WHERE dano=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("dano"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> search(String searchColumn, String searchWord, int startRow, int size) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM DailyView WHERE " + searchWord
				+ " LIKE CONCAT('%',?,'%')) as totalcount, "
				+ "commentcount, dano, datitle, dacontent, datable, dathumbnail, dadate, daip, dacount, dafilename, dalike, no, id, name, grade FROM DailyView"
				+ " WHERE " + searchWord + " LIKE CONCAT('%',?,'%') ORDER BY dano DESC LIMIT ?, ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, size);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("dadate", rs.getDate("dadate"));
					map.put("daip", rs.getString("daip"));
					map.put("dacount", rs.getInt("dacount"));
					map.put("dafilename", rs.getString("dafilename"));
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

	public ArrayList<HashMap<String, Object>> searchList(String searchColumn, String searchWord, int startRow,
			int size) {
		ArrayList<HashMap<String, Object>> list = null;

		String query = "SELECT (SELECT COUNT(*) FROM DailyView" + " WHERE " + searchColumn
				+ " LIKE CONCAT('%', ?, '%')) AS hitcount,"
				+ " commentcount, dano, datitle, dacontent, dathumbnail, dadate, dacount, daip, no, id, name FROM DailyView"
				+ " WHERE " + searchColumn + " LIKE CONCAT('%', ?, '%') LIMIT ?, ?";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, size);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("hitcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("dadate", rs.getString("dadate"));
					map.put("dacount", rs.getInt("dacount"));
					map.put("daip", rs.getString("daip"));
					map.put("no", rs.getString("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> selectList(int startRow, int size) {
		ArrayList<HashMap<String, Object>> list = null;

		String query = "SELECT * FROM DailyView LIMIT ?, ?";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("dadate", rs.getString("dadate2"));
					map.put("dacount", rs.getInt("dacount"));
					map.put("daip", rs.getString("daip"));
					map.put("no", rs.getString("no"));
					map.put("dalike", rs.getInt("dalike"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int write(String title, String content, String id, String thumbnailFile) {
		int result = 0;
		String query = "INSERT INTO Daily (datitle, dacontent, dathumbnail, no) "
				+ "VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, thumbnailFile);
			pstmt.setString(4, id);
			System.out.println(pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int write(String title, String content, String id) {
		int result = 0;
		String query = "INSERT INTO Daily (datitle, dacontent, no) "
				+ "VALUES (?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, id);
			System.out.println(pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, Object> mapForModify(String id, int dano) {
		HashMap<String, Object> map = null;

		String query = "SELECT * FROM DailyView WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dano);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					map = new HashMap<>();
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("no", rs.getString("no"));
					map.put("id", rs.getString("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public int modify(int dano, String datitle, String dacontent, String id, String thumbnailFile) {
		int result = 0;
		String query = "UPDATE Daily SET datitle=?, dacontent=?, dathumbnail=? WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, datitle);
			pstmt.setString(2, dacontent);
			pstmt.setString(3, thumbnailFile);
			pstmt.setInt(4, dano);
			pstmt.setString(5, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int modify(int dano, String datitle, String dacontent, String id) {
		int result = 0;
		String query = "UPDATE Daily SET datitle=?, dacontent=? WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, datitle);
			pstmt.setString(2, dacontent);
			pstmt.setInt(3, dano);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String isFile(int dano, String id) {
		String query = "SELECT dathumbnail FROM Daily WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dano);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String filename = rs.getString("dathumbnail");
				return filename;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int likeUp(int dano, String id, String ip) {
		int result = 0;
		String query = "INSERT INTO DailyLike (dano, no, daip) VALUES (?, (SELECT no FROM Login WHERE id=?), ?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dano);
			pstmt.setString(2, id);
			pstmt.setString(3, ip);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	public int deleteLike(int dano, String id) {
		int result = 0;
		String query = "DELETE FROM DailyLike WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dano);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int canLike(int dano, String id) {
		// -1이면 에러 발생, 0이면 추천 가능, 1이면 추천 불가능(중복)
		String query = "SELECT COUNT(*) FROM DailyLike WHERE dano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dano);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("COUNT(*)") == 0) {
					return 0;
				} else if (rs.getInt("COUNT(*)") > 0) {
					return 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<HashMap<String, Object>> commentList(int dano) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<HashMap<String, Object>> isLike(String id) {
		ArrayList<HashMap<String, Object>> likeList = null;

		String query = "SELECT * FROM DailyLikeView WHERE no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				likeList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("dano", rs.getInt("dano"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					likeList.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return likeList;

	}

	public ArrayList<HashMap<String, Object>> bestBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM DailyView ORDER BY dalike DESC LIMIT ?, 3;";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, best);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dano", rs.getInt("dano"));
					map.put("datitle", rs.getString("datitle"));
					map.put("dacontent", rs.getString("dacontent"));
					map.put("dathumbnail", rs.getString("dathumbnail"));
					map.put("dadate", rs.getDate("dadate"));
					map.put("daip", rs.getString("daip"));
					map.put("dacount", rs.getInt("dacount"));
					map.put("dalike", rs.getInt("dalike"));
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

}
