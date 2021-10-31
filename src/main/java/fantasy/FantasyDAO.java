package fantasy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class FantasyDAO {

	private FantasyDAO() {
	}

	private static FantasyDAO instance = new FantasyDAO();

	public static FantasyDAO getInstance() {

		return instance;
	}

	public int write(String ftitle, String fcontent, String id, String thumbnailFile) {
		int result = 0;
		String query = "INSERT INTO Fantasy (ftitle, fcontent, fthumbnail, no) "
				+ "VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ftitle);
			pstmt.setString(2, fcontent);
			pstmt.setString(3, thumbnailFile);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public int write(String ftitle, String fcontent, String id) {
		int result = 0;
		String query = "INSERT INTO Fantasy (ftitle, fcontent, no) " + "VALUES (?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ftitle);
			pstmt.setString(2, fcontent);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	public ArrayList<HashMap<String, Object>> fantasyList(int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM fantasyview LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);

			rs = pstmt.executeQuery();

			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {

					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("fno", rs.getInt("fno"));
					map.put("ftitle", rs.getString("ftitle"));
					map.put("fcontent", rs.getString("fcontent"));
					map.put("fthumbnail", rs.getString("fthumbnail"));
					map.put("ffilename", rs.getString("ffilename"));
					map.put("fdate", rs.getString("fdate"));
					map.put("fcount", rs.getInt("fcount"));
					map.put("fip", rs.getString("fip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					// boardList.add(d)
					list.add(map);
				}

			}

		} catch (Exception e) {

		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return list;

	}

	public HashMap<String, Object> detail(int fno) {
		HashMap<String, Object> d = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM fantasyview WHERE fno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				d = new HashMap<String, Object>();
				d.put("commentcount", rs.getInt("commentcount"));
				d.put("fno", rs.getInt("fno"));
				d.put("ftitle", rs.getString("ftitle"));
				d.put("fcontent", rs.getString("fcontent"));
				d.put("fthumbnail", rs.getString("fthumbnail"));
				d.put("filename", rs.getString("ffilename"));
				d.put("fdate", rs.getString("fdate"));
				d.put("fcount", rs.getInt("fcount") + 1);
				d.put("fip", rs.getString("fip"));
				d.put("no", rs.getInt("no"));
				d.put("id", rs.getString("id"));
				d.put("name", rs.getString("name"));
				d.put("flike", rs.getInt("flike"));
			}
			countUp(fno);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return d;
	}

	public int delete(int fno, String id) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Fantasy WHERE fno=? AND no=(SELECT no FROM Login where id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	public int commentInsert(int fno, String fccontent, String id) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FantasyComment (fno, fccontent, no) "
				+ "VALUES (?, ?, (SELECT no FROM Login WHERE id=?))";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, fccontent);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public ArrayList<HashMap<String, Object>> commentList(int fno) {
		ArrayList<HashMap<String, Object>> commentList = null;

		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM FantasyCommentView WHERE fno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			rs = pstmt.executeQuery();

			if (rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("fcno", rs.getInt("fcno"));
					map.put("fno", rs.getInt("fno"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("fccontent", rs.getString("fccontent"));
					map.put("fcdate", rs.getString("fcdate"));
					map.put("fcip", rs.getString("fcip"));

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

	public int modify(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Fantasy SET ftitle=?, fcontent=? " + "WHERE fno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("fno"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;

	}

	public int modifyDelete(int fno, String ftitle, String fcontent, String id, String thumbnailFile) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Fantasy WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}
		return result;
	}

	public ArrayList<String> FindFile(HashMap<String, Object> map) {
		ArrayList<String> filename = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT fthumbnail, ffilename from fantasyview WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String) map.get("fno")));
			pstmt.setString(2, ((String) map.get("id")));

			rs = pstmt.executeQuery();
			if (rs.next()) {
				filename = new ArrayList<String>();

				String fthumbnail = rs.getString("fthumbnail");
				if (fthumbnail != null && fthumbnail.contains(".")) {
					filename.add(fthumbnail);
				} else {
					filename.add(null);
				}
				String ffilename = rs.getString("fthumbnail");
				if (ffilename != null && ffilename.contains(".")) {
					filename.add(ffilename);
				} else {
					filename.add(null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return filename;
	}

	public int delete(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Fantasy WHERE fno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int2((String) map.get("fno")));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}
		return result;
	}

	public HashMap<String, Object> viewModify(int fno, String id) {
		HashMap<String, Object> dto = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fantasyview WHERE fno=? and no=(SELECT no FROM Login WHERE id=?)";
		countUp(fno);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new HashMap<String, Object>();
				dto.put("totalcount", rs.getInt("totalcount"));
				dto.put("commentcount", rs.getInt("commentcount"));
				dto.put("fno", rs.getInt("fno"));
				dto.put("ftitle", rs.getString("ftitle"));
				dto.put("fcontent", rs.getString("fcontent"));
				dto.put("fthumbnail", rs.getString("fthumbnail"));
				dto.put("file", rs.getString("ffilename"));
				dto.put("fdate", rs.getString("fdate"));
				dto.put("fcount", rs.getInt("fcount"));
				dto.put("fip", rs.getString("fip"));
				dto.put("no", rs.getInt("no"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return dto;
	}

	public void countUp(int fno) {
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Fantasy SET fcount=fcount+1 WHERE fno=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
	}

	public HashMap<String, Object> viewModifyComment(int fno, String id, int fcno) {
		HashMap<String, Object> dto = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT fcno, fno, id, fccontent FROM FantasyCommentView WHERE fcno=? " + "AND fno=? AND id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setString(2, id);
			pstmt.setInt(3, fcno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new HashMap<String, Object>();
				dto.put("fcip", rs.getString("fcip"));
				dto.put("fccontent", rs.getString("fccontent"));
				dto.put("fcdate", rs.getString("fcdate"));
				dto.put("fcount", rs.getInt("fcount"));
				dto.put("fip", rs.getString("fip"));
				dto.put("no", rs.getInt("no"));
				dto.put("fno", rs.getInt("fno"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return dto;
	}

	public int modifyComment(int fcno, String fccontent, String id) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE FantasyComment SET fccontent=? WHERE fcno=? AND no=(SELECT no FROM Login WHERE id=?);";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fccontent);
			pstmt.setInt(2, fcno);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}

		return result;
	}
	

	public int deleteComment(int fcno, String id) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM FantasyComment WHERE fcno=? AND no=(SELECT no from Login WHERE id=?)";
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fcno);
			pstmt.setString(2, id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}

		return count;
	}

	public int fantasyUpdate(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Fantasy SET ftitle=?, fcontent=? WHERE fno=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("fno"));
			pstmt.setString(4, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int fantasyUpdateFile(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Fantasy SET ftitle=?, fcontent=?, ffilename=?, fthumbnail=? WHERE fno=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setInt(5, (int) map.get("fno"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	public int likeUp(int fno) {
		int result = 0;
		String query = "UPDATE Fantasy SET flike=flike+1 WHERE fno=?";
		try {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		System.out.println(pstmt);
		pstmt.setInt(1, fno);

		result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
		}

	public boolean likeUpAvailable(HashMap<String, Object> map) {

		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM FantasyLike WHERE no=? AND fno =?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("fno"));
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

	public int likeUpInsert(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FantasyLike (no, fno, ip) VALUES (?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("fno"));
			pstmt.setString(3, (String) map.get("ip"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> searchFantasy(String search, String searchInput, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM fantasyview WHERE " + search
				+ " LIKE CONCAT('%',?,'%')) as totalcount, "
				+ "commentcount, fno, ftitle, fcontent, ftable, fthumbnail, fdate, fip, fcount, ffilename, flike, no, id, name, grade FROM fantasyview"
				+ " WHERE " + search + " LIKE CONCAT('%',?,'%') ORDER BY fno DESC LIMIT ?, 5";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchInput);
			pstmt.setString(2, searchInput);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("totalcount", rs.getInt("totalcount"));
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("fno", rs.getInt("fno"));
					map.put("ftitle", rs.getString("ftitle"));
					map.put("fcontent", rs.getString("fcontent"));
					map.put("fthumbnail", rs.getString("fthumbnail"));
					map.put("fdate", rs.getDate("fdate"));
					map.put("fip", rs.getString("fip"));
					map.put("fcount", rs.getInt("fcount"));
					map.put("ffilename", rs.getString("ffilename"));
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

	public int delete1(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Fantasy WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int2((String) map.get("fno")));
			pstmt.setString(2, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);

		}
		return result;
	}
	public ArrayList<HashMap<String, Object>> bestBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM fantasyview ORDER BY flike DESC LIMIT ?, 3;";

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
					map.put("fno", rs.getInt("fno"));
					map.put("ftitle", rs.getString("ftitle"));
					map.put("fcontent", rs.getString("fcontent"));
					map.put("fthumbnail", rs.getString("fthumbnail"));
					map.put("fdate", rs.getString("fdate"));
					map.put("fcount", rs.getInt("fcount"));
					map.put("fip", rs.getString("fip"));
					map.put("flike", rs.getInt("flike"));
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

		public ArrayList<HashMap<String, Object>> searchList(String searchColumn, String searchWord, int startRow, int size) {
			ArrayList<HashMap<String, Object>> list = null;
			Connection conn = DBConnection.dbConnection();

			String query = "SELECT (SELECT COUNT(*) FROM fantasyview" + " WHERE " + searchColumn
					+ " LIKE CONCAT('%', ?, '%')) AS totalcount,"
					+ " commentcount, fno, rownum, ftitle, fcontent, fthumbnail, fdate, fcount, fip, no, id, name FROM fantasyview"
					+ " WHERE " + searchColumn + " LIKE CONCAT('%', ?, '%') LIMIT ?, ?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt =conn.prepareStatement(query);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, startRow);
				pstmt.setInt(4, size);
				rs = pstmt.executeQuery();
				if (rs != null) {
					list = new ArrayList<HashMap<String, Object>>();
					while (rs.next()) {
						HashMap<String, Object> map = new HashMap<>();
						map.put("totalcount", rs.getInt("totalcount"));
						map.put("commentcount", rs.getInt("commentcount"));
						map.put("fno", rs.getInt("fno"));
						map.put("rownum", rs.getInt("rownum"));
						map.put("ftitle", rs.getString("ftitle"));
						map.put("fcontent", rs.getString("fcontent"));
						map.put("fthumbnail", rs.getString("fthumbnail"));
						map.put("fdate", rs.getString("fdate"));
						map.put("fcount", rs.getInt("fcount"));
						map.put("fip", rs.getString("fip"));
						map.put("no", rs.getString("no"));
						map.put("id", rs.getString("id"));
						map.put("name", rs.getString("name"));
						list.add(map);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				Util.closeAll(rs, pstmt, conn);
				
			}
			return list;
		}

		public ArrayList<HashMap<String, Object>> selectList(int startRow, int size) {
			ArrayList<HashMap<String, Object>> list = null;

			String query = "SELECT * FROM fantasyview LIMIT ?, ?";
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, size);
				 rs= pstmt.executeQuery();
				if (rs != null) {
					list = new ArrayList<HashMap<String, Object>>();
					while (rs.next()) {
						HashMap<String, Object> map = new HashMap<>();
						map.put("totalcount", rs.getInt("totalcount"));
						map.put("commentcount", rs.getInt("commentcount"));
						map.put("fno", rs.getInt("fno"));
						map.put("rownum", rs.getInt("rownum"));
						map.put("ftitle", rs.getString("ftitle"));
						map.put("fcontent", rs.getString("fcontent"));
						map.put("fthumbnail", rs.getString("fthumbnail"));
						map.put("fdate", rs.getString("fdate"));
						map.put("fcount", rs.getInt("fcount"));
						map.put("fip", rs.getString("fip"));
						map.put("no", rs.getString("no"));
						map.put("id", rs.getString("id"));
						map.put("name", rs.getString("name"));
						list.add(map);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				Util.closeAll(rs, pstmt, conn);
			}
			return list;
		}

		public int modify(int fno, String ftitle, String fcontent, String id, String thumbnailFile) {
			int result = 0;
			String query = "UPDATE Fantasy SET ftitle=?, fcontent=?, fthumbnail=? WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
			try {
				Connection conn = DBConnection.dbConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, ftitle);
				pstmt.setString(2, fcontent);
				pstmt.setString(3, thumbnailFile);
				pstmt.setInt(4, fno);
				pstmt.setString(5, id);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		public int modify(int fno, String ftitle, String fcontent, String id) {
			int result = 0;
			String query = "UPDATE Fantasy SET ftitle=?, fcontent=? WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
			try {
				Connection conn = DBConnection.dbConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, ftitle);
				pstmt.setString(2, fcontent);
				pstmt.setInt(3, fno);
				pstmt.setString(4, id);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		public int canLike(int fno, String id) {
			//-1이면 에러 발생, 0이면 추천 가능, 1이면 추천 불가능(중복)
			String query = "SELECT COUNT(*) FROM FantasyLike WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
			try {
				Connection conn = DBConnection.dbConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, fno);
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
		public int deleteLike(int fno, String id) {
			int result = 0;
			String query = "DELETE FROM FantasyLike WHERE fno=? AND no=(SELECT no FROM Login WHERE id=?)";
			try {
				Connection conn = DBConnection.dbConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, fno);
				pstmt.setString(2, id);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		



}
