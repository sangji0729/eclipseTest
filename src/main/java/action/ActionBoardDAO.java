package action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class ActionBoardDAO {

	private ActionBoardDAO() {

	}

	private static ActionBoardDAO instance = new ActionBoardDAO();

	public static ActionBoardDAO getInstance() {
		return instance;
	}

	public ArrayList<HashMap<String, Object>> boardList(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Actionview LIMIT ?, 5;";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("alike", rs.getInt("alike"));
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

	public int write(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Action (atitle, acontent, aip, afilename, athumbnail, no) VALUES (?, ?, ?, ?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("ip"));
			pstmt.setString(4, (String) map.get("saveFile"));
			pstmt.setString(5, (String) map.get("thumbnail"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public HashMap<String, Object> detail(int ano) {
		HashMap<String, Object> map = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Actionview WHERE ano=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ano);
			rs = pstmt.executeQuery();
			if (rs != null) {
				map = new HashMap<String, Object>();
				while (rs.next()) {
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("afilename", rs.getString("afilename"));
					map.put("alike", rs.getInt("alike"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return map;
	}

	public ArrayList<HashMap<String, Object>> commentList(int ano) {
		ArrayList<HashMap<String, Object>> commentList = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ActionCommentView WHERE ano=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ano);
			rs = pstmt.executeQuery();

			if (rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("acno", rs.getInt("acno"));
					map.put("ano", rs.getInt("ano"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("accontent", rs.getString("accontent"));
					map.put("acdate", rs.getDate("acdate"));
					map.put("acip", rs.getString("acip"));
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

	public ArrayList<String> findFileName(HashMap<String, Object> map) {
		ArrayList<String> fileName = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT afilename, athumbnail FROM Actionview WHERE ano=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,(int)map.get("ano"));
			pstmt.setString(2, (String) map.get("id"));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				fileName = new ArrayList<String>();

				String afile = rs.getString("afilename");// index 0
				if (afile != null && afile.contains(".")) {
					fileName.add(afile);
				} else {
					fileName.add(null);
				}

				String athumb = rs.getString("athumbnail");// index 1
				if (athumb != null && athumb.contains(".")) {
					fileName.add(athumb);
				} else {
					fileName.add(null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return fileName;
	}

	public int delete(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Action WHERE ano=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,(int)map.get("ano"));
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
		String sql = "UPDATE Action SET atitle=?, acontent=? WHERE ano=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("ano"));
			pstmt.setString(4, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public HashMap<String, Object> update(int ano, String id) {
		HashMap<String, Object> map = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Actionview WHERE ano=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ano);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if (rs != null) {
				map = new HashMap<String, Object>();
				while (rs.next()) {

					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("afilename", rs.getString("afilename"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return map;
	}

	public int likeUp(int ano, String id, String aip) {
		int result = 0;
		String query = "INSERT INTO ActionLike (ano, no, aip) VALUES (?, (SELECT no FROM Login WHERE id=?), ?)";
		try {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, ano);
		pstmt.setString(2, id);
		pstmt.setString(3, aip);
		result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	public int LikeUpCheck(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ActionLike (no, ano, aip) VALUES (?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("ano"));
			pstmt.setString(3, (String) map.get("aip"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int boardCount(int ano) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Action SET acount=acount+1 WHERE ano=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ano);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int modifyFile(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Action SET atitle=?, acontent=?, afilename=?, athumbnail=? WHERE ano=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setInt(5, (int) map.get("ano"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public ArrayList<HashMap<String, Object>> search(String searchColumn, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM Actionview WHERE " + searchColumn
				+ " LIKE CONCAT('%',?,'%')) as totalcount, "
				+ "commentcount, ano, atitle, acontent, atable, athumbnail, adate, aip, acount, afilename, alike, no, id, name, grade FROM Actionview"
				+ " WHERE " + searchColumn + " LIKE CONCAT('%',?,'%') ORDER BY ano DESC LIMIT ?, 5";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("afilename", rs.getString("afilename"));
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

	public boolean likeUpAvailable(HashMap<String, Object> map) {
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ActionLike WHERE no=? AND ano =?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("ano"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return true;
	}

	public ArrayList<HashMap<String, Object>> bestBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Actionview ORDER BY alike DESC LIMIT ?, 3;";

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
					map.put("ano", rs.getInt("ano"));
					map.put("atitle", rs.getString("atitle"));
					map.put("acontent", rs.getString("acontent"));
					map.put("athumbnail", rs.getString("athumbnail"));
					map.put("adate", rs.getDate("adate"));
					map.put("aip", rs.getString("aip"));
					map.put("acount", rs.getInt("acount"));
					map.put("alike", rs.getInt("alike"));
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

	public int deleteLike(int ano, String id) {
		int result = 0;
		String query = "DELETE FROM ActionLike WHERE ano=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, ano);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int canLike(int ano, String id) {
		//-1이면 에러 발생, 0이면 추천 가능, 1이면 추천 불가능(중복)
				String query = "SELECT COUNT(*) FROM ActionLike WHERE ano=? AND no=(SELECT no FROM Login WHERE id=?)";
				try {
					Connection conn = DBConnection.dbConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, ano);
					pstmt.setString(2, id);
					ResultSet rs = pstmt.executeQuery();
						if(rs.next()) {
							if (rs.getInt("COUNT(*)") == 0) {
								return 0;
							} else if(rs.getInt("COUNT(*)") > 0) {
								return 1;
							}
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return -1;
	}

}
