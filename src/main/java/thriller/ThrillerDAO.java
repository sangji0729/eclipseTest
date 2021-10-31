package thriller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import db.DBConnection;
import util.Util;

public class ThrillerDAO {
	private ThrillerDAO() {
	}

	private static ThrillerDAO thrillerDAO = new ThrillerDAO();

	public static ThrillerDAO getInstance() {
		return thrillerDAO;
	}

	// 게시판
	public ArrayList<HashMap<String, Object>> thrillerList(int page) {
		ArrayList<HashMap<String, Object>> thrillerList = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ThrillerView LIMIT ?, 5";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				thrillerList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("tno", rs.getInt("tno"));
					map.put("ttitle", rs.getString("ttitle"));
					map.put("tdate", rs.getString("tdate"));
					map.put("tthumbnail", rs.getString("tthumbnail"));
					map.put("tcount", rs.getInt("tcount"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("tlike", rs.getInt("tlike"));
					map.put("commentcount", rs.getInt("commentcount"));
					thrillerList.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return thrillerList;
	}

	// 상세보기
	public HashMap<String, Object> detail(int tno) {
		HashMap<String, Object> dto = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ThrillerView WHERE tno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new HashMap<String, Object>();
				dto.put("totalcount", rs.getInt("totalcount"));
				dto.put("commentcount", rs.getInt("commentcount"));
				dto.put("tno", rs.getInt("tno"));
				dto.put("ttitle", rs.getString("ttitle"));
				dto.put("tcontent", rs.getString("tcontent"));
				dto.put("tdate", rs.getString("tdate"));
				dto.put("tcount", rs.getInt("tcount"));
				dto.put("tip", rs.getString("tip"));
				dto.put("tthumbnail", rs.getString("tthumbnail"));
				dto.put("tfilename", rs.getString("tfilename"));
				dto.put("no", rs.getInt("no"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
				dto.put("tlike", rs.getInt("tlike"));

			}
			countUp(tno);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return dto;
	}

	// 글쓰기
	public int thrillerWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO Thriller (ttitle, tcontent, tip, tfilename, tthumbnail, no) VALUES(?, ?, ?, ?, ?, (SELECT no FROM Login WHERE id=?))";

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

	// 수정
	public int update(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Thriller SET ttitle=?, tcontent=? WHERE tno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setInt(3, (int) map.get("tno"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	/*
	 * public HashMap<String, Object> update(int tno, String id) { HashMap<String,
	 * Object> map = null; Connection conn = DBConnection.dbConn();
	 * PreparedStatement pstmt = null; ResultSet rs = null; String sql =
	 * "SELECT * FROM ThrillerView WHERE tno=? AND no=(SELECT no FROM Login WHERE id=?)"
	 * ;
	 * 
	 * try { pstmt = conn.prepareStatement(sql); pstmt.setInt(1, tno);
	 * pstmt.setString(2, id); rs = pstmt.executeQuery();
	 * 
	 * if(rs != null) { map = new HashMap<String, Object>(); while(rs.next()) {
	 * 
	 * map.put("totalcount", rs.getInt("totalcount")); map.put("commentcount",
	 * rs.getInt("commentcount")); map.put("tno", rs.getInt("tno"));
	 * map.put("ttitle", rs.getString("ttitle")); map.put("tcontent",
	 * rs.getString("tcontent")); map.put("tthumbnail", rs.getString("tthumbnail"));
	 * map.put("tdate", rs.getDate("tdate")); map.put("tip", rs.getString("tip"));
	 * map.put("tcount", rs.getInt("tcount")); map.put("tfilename",
	 * rs.getString("tfilename")); map.put("no", rs.getInt("no")); map.put("id",
	 * rs.getString("id")); map.put("name", rs.getString("name")); } } } catch
	 * (SQLException e) { e.printStackTrace(); } finally { Util.closeAll(rs, pstmt,
	 * conn); }
	 * 
	 * return map; }
	 */

	public int updateFile(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Thriller SET ttitle=?, tcontent=?, tfilename=?, tthumbnail=? WHERE tno=? AND no = (SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setInt(5, (int) map.get("tno"));
			pstmt.setString(6, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}

	// 삭제
	public int delete(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM Thriller WHERE tno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("tno"));
			System.out.println(pstmt);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	// 파일찾기
	public ArrayList<String> findFileName(HashMap<String, Object> map) {
		ArrayList<String> fileName = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT tfilename, tthumbnail FROM ThrillerView WHERE tno=? AND no=(SELECT no FROM Login WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("tno"));
			pstmt.setString(2, (String) map.get("id"));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				fileName = new ArrayList<String>();
				String tfile = rs.getString("tfilename");
				if (tfile != null && tfile.contains(".")) {
					fileName.add(tfile);
				} else {
					fileName.add(null);
				}

				String tthumb = rs.getString("tthumbnail");
				if (tthumb != null && tthumb.contains(".")) {
					fileName.add(tthumb);
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

	// 조회수
	private void countUp(int tno) {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE ThrillerView SET tcount=tcount+1 WHERE tno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
	}
	
	private int likeCountUp(int tno) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Thriller SET tlike=tlike+1 WHERE tno=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		
		return result;
	}

	private int likeCountDown(int tno) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE Thriller SET tlike=tlike-1 WHERE tno=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		
		return result;
	}
	
	// 조아요
	public int likeUp(int tno, String id, String tip) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ThrillerLike (tno, no, tip) VALUES (?, (SELECT no FROM Login WHERE id=?), ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			pstmt.setString(2, id);
			pstmt.setString(3, tip);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		likeCountUp(tno);
		return result;
	}

	public int LikeUpCheck(HashMap<String, Object> map) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO ThrillerLike (no, tno, tip) VALUES (?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (int) map.get("no"));
			pstmt.setInt(2, (int) map.get("tno"));
			pstmt.setString(3, (String) map.get("tip"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}

		return result;
	}

	// 댓댓댓댓댓글.. 하 힘들다...
	public ArrayList<HashMap<String, Object>> commentList(int tno) {
		ArrayList<HashMap<String, Object>> commentList = null;

		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ThrillerCommentView WHERE tno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tno);
			rs = pstmt.executeQuery();

			if (rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("tcno", rs.getInt("tcno"));
					map.put("tno", rs.getInt("tno"));
					map.put("tccontent", rs.getString("tccontent"));
					map.put("tcdate", rs.getDate("tcdate"));
					map.put("tcip", rs.getString("tcip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
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

	public ArrayList<HashMap<String, Object>> search(String searchColumn, String searchWord, int page) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT count(*) FROM ThrillerView WHERE " + searchColumn
				+ " LIKE CONCAT('%',?,'%')) as totalcount, "
				+ "commentcount, tno, ttitle, tcontent, ttable, tthumbnail, tdate, tip, tcount, tfilename, tlike, no, id, name, grade FROM ThrillerView"
				+ " WHERE " + searchColumn + " LIKE CONCAT('%',?,'%') ORDER BY tno DESC LIMIT ?, 5";

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
					map.put("tno", rs.getInt("tno"));
					map.put("ttitle", rs.getString("ttitle"));
					map.put("tcontent", rs.getString("tcontent"));
					map.put("tthumbnail", rs.getString("tthumbnail"));
					map.put("tdate", rs.getDate("tdate"));
					map.put("tip", rs.getString("tip"));
					map.put("tcount", rs.getInt("tcount"));
					map.put("tfilename", rs.getString("tfilename"));
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
		System.out.println(searchColumn);
		System.out.println(searchWord);
		return list;
	}

	public ArrayList<HashMap<String, Object>> bestBoardList(int best) {
		ArrayList<HashMap<String, Object>> list = null;
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ThrillerView ORDER BY tlike DESC LIMIT ?, 3;";

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
					map.put("tno", rs.getInt("tno"));
					map.put("ttitle", rs.getString("ttitle"));
					map.put("tcontent", rs.getString("tcontent"));
					map.put("tthumbnail", rs.getString("tthumbnail"));
					map.put("tdate", rs.getDate("tdate"));
					map.put("tip", rs.getString("tip"));
					map.put("tcount", rs.getInt("tcount"));
					map.put("tlike", rs.getInt("tlike"));
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

	public int canLike(int tno, String id) {
		//-1이면 에러 발생, 0이면 추천 가능, 1이면 추천 불가능(중복)
				String query = "SELECT COUNT(*) FROM ThrillerLike WHERE tno=? AND no=(SELECT no FROM Login WHERE id=?)";
				try {
					Connection conn = DBConnection.dbConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, tno);
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

	public int deleteLike(int tno, String id) {
		int result = 0;
		Connection conn = DBConnection.dbConnection();
		String query = "DELETE FROM ThrillerLike WHERE tno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, tno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		likeCountDown(tno);
		return result;
	}
}