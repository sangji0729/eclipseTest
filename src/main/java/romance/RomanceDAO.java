package romance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class RomanceDAO {

	private RomanceDAO() {

	}

	private static RomanceDAO instance = new RomanceDAO();

	public static RomanceDAO getInstance() {
		return instance;
	}

	public ArrayList<HashMap<String, Object>> boardList(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceView LIMIT ?, 5;";

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
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rlike", rs.getInt("rlike"));
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
		String sql = "INSERT INTO Romance (rtitle, rcontent, rip, rfilename, rthumbnail, no) VALUES (?, ?, ?, ?, ?, (SELECT no FROM Login WHERE id=?))";

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

	public HashMap<String, Object> detail(int rno) {
		HashMap<String, Object> map = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceView WHERE rno=?";
		boardCount(rno);

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			rs = pstmt.executeQuery();

			if (rs != null) {
				map = new HashMap<String, Object>();

				while (rs.next()) {
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rfilename", rs.getString("rfilename"));
					map.put("rlike", rs.getInt("rlike"));
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

	public ArrayList<HashMap<String, Object>> commentList(int rno) {
		ArrayList<HashMap<String, Object>> commentList = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceCommentView WHERE rno=?";

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
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("rccontent", rs.getString("rccontent"));
					map.put("rcdate", rs.getDate("rcdate"));
					map.put("rcip", rs.getString("rcip"));
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
		String sql = "SELECT rfilename, rthumbnail FROM RomanceView WHERE rno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("rno"));
			pstmt.setString(2, (String) map.get("id"));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				fileName = new ArrayList<String>();

				String rfile = rs.getString("rfilename");

				if (rfile != null && rfile.contains(".")) {
					fileName.add(rfile);

				} else {
					fileName.add(null);
				}

				String rthumb = rs.getString("rthumbnail");

				if (rthumb != null && rthumb.contains(".")) {
					fileName.add(rthumb);

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
		String sql = "DELETE FROM Romance WHERE rno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("rno"));
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
		String sql = "UPDATE Romance SET rtitle=?, rcontent=? WHERE rno=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("rno"));
			pstmt.setString(4, (String) map.get("id"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public HashMap<String, Object> update(int rno, String id) {
		HashMap<String, Object> map = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceView WHERE rno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();

			if (rs != null) {
				map = new HashMap<String, Object>();

				while (rs.next()) {
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rfilename", rs.getString("rfilename"));
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

	public int modifyFile(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Romance SET rtitle=?, rcontent=?, rfilename=?, rthumbnail=? WHERE rno=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setInt(5, (int) map.get("rno"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int likeUp(int rno, String id, String rip) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT RomanceLike (rno, no, rip) VALUES (?, (SELECT no FROM Login WHERE id=?), ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			pstmt.setString(2, id);
			pstmt.setString(3, rip);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		likeCountUp(rno);
		return result;
	}

	public int LikeUpCheck(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO RomanceLike (no, rno, rip) VALUES (?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("rno"));
			pstmt.setString(3, (String) map.get("rip"));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public boolean likeUpAvailable(HashMap<String, Object> map) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceLike WHERE no=? AND rno =?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("rno"));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return true;
	}

	public ArrayList<HashMap<String, Object>> search(String searchColumn, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM RomanceView WHERE " + searchColumn
				+ " LIKE CONCAT('%',?,'%')) as totalcount, "
				+ "commentcount, rno, rtitle, rcontent, rtable, rthumbnail, rdate, rip, rcount, rfilename, 'rlike', no, id, name, grade FROM RomanceView"
				+ " WHERE " + searchColumn + " LIKE CONCAT('%',?,'%') ORDER BY rno DESC LIMIT ?, 5";

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
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rfilename", rs.getString("rfilename"));
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

	public ArrayList<HashMap<String, Object>> bestBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM RomanceView ORDER BY 'rlike' DESC LIMIT ?, 3;";

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
					map.put("rno", rs.getInt("rno"));
					map.put("rtitle", rs.getString("rtitle"));
					map.put("rcontent", rs.getString("rcontent"));
					map.put("rthumbnail", rs.getString("rthumbnail"));
					map.put("rdate", rs.getDate("rdate"));
					map.put("rip", rs.getString("rip"));
					map.put("rcount", rs.getInt("rcount"));
					map.put("rlike", rs.getInt("rlike"));
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

	public int deleteLike(int rno, String id) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM RomanceLike WHERE rno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		likeCountDown(rno);
		return result;
	}

	private int likeCountUp(int rno) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE RomanceView SET 'rlike'='rlike'+1 WHERE rno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			result = pstmt.executeUpdate();
			System.out.println("실행되었습니다");

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		System.out.println(rno);
		return result;
	}

	private int likeCountDown(int rno) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Romance SET 'rlike'='rlike'-1 WHERE rno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int canLike(int rno, String id) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(*) FROM RomanceLike WHERE rno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
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

	public int boardCount(int rno) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Romance SET rcount=rcount+1 WHERE rno=?";
		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rno);
			pstmt.execute();
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}
}