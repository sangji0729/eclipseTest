package drama;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import db.DBConnection;
import util.Util;

public class BoardDAO {
	private BoardDAO() {
	}

	private static class Singleton {
		private static final BoardDAO INSTANCE = new BoardDAO();
	}

	public static BoardDAO getInstance() {
		return Singleton.INSTANCE;
	}

	public ArrayList<HashMap<String, Object>> selectList(int startRow, int size) {
		ArrayList<HashMap<String, Object>> list = null;

		String query = "SELECT * FROM DramaView LIMIT ?, ?";
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
					map.put("dno", rs.getInt("dno"));
					map.put("rownum", rs.getInt("rownum"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("ddate", rs.getString("ddate2"));
					map.put("dcount", rs.getInt("dcount"));
					map.put("dip", rs.getString("dip"));
					map.put("no", rs.getString("no"));
					map.put("dlike", rs.getInt("dlike"));
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

	public void countUp(int dno) {
		String query = "UPDATE Drama SET dcount=dcount+1 WHERE dno=?";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Object> detail(int dno) {
		HashMap<String, Object> map = null;

		String query = "SELECT * FROM DramaView WHERE dno=?";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					map = new HashMap<>();
					map.put("commentcount", rs.getInt("commentcount"));
					map.put("dno", rs.getInt("dno"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("ddate", rs.getString("ddate"));
					map.put("dcount", rs.getInt("dcount"));
					map.put("dip", rs.getString("dip"));
					map.put("dlike", rs.getInt("dlike"));
					map.put("no", rs.getString("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public int write(String dtitle, String dcontent, String id, String thumbnailFile) {
		int result = 0;
		String query = "INSERT INTO Drama (dtitle, dcontent, dthumbnail, no) "
				+ "VALUES (?, ?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dtitle);
			pstmt.setString(2, dcontent);
			pstmt.setString(3, thumbnailFile);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int write(String dtitle, String dcontent, String id) {
		int result = 0;
		String query = "INSERT INTO Drama (dtitle, dcontent, no) " + "VALUES (?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dtitle);
			pstmt.setString(2, dcontent);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, Object> mapForModify(String id, int dno) {
		HashMap<String, Object> map = null;

		String query = "SELECT * FROM DramaView WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					map = new HashMap<>();
					map.put("dno", rs.getInt("dno"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("no", rs.getString("no"));
					map.put("id", rs.getString("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public int modify(int dno, String dtitle, String dcontent, String id, String thumbnailFile) {
		int result = 0;
		String query = "UPDATE Drama SET dtitle=?, dcontent=?, dthumbnail=? WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dtitle);
			pstmt.setString(2, dcontent);
			pstmt.setString(3, thumbnailFile);
			pstmt.setInt(4, dno);
			pstmt.setString(5, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int modify(int dno, String dtitle, String dcontent, String id) {
		int result = 0;
		String query = "UPDATE Drama SET dtitle=?, dcontent=? WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dtitle);
			pstmt.setString(2, dcontent);
			pstmt.setInt(3, dno);
			pstmt.setString(4, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String isFile(int dno, String id) {
		String query = "SELECT dthumbnail FROM Drama WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			pstmt.setString(2, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String filename = rs.getString("dthumbnail");
				return filename;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int delete(int dno, String id) {
		int result = 0;
		String query = "DELETE FROM Drama WHERE dno=?";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> searchList(String searchColumn, String searchWord, int startRow,
			int size) {
		ArrayList<HashMap<String, Object>> list = null;

		String query = "SELECT (SELECT COUNT(*) FROM DramaView" + " WHERE " + searchColumn
				+ " LIKE CONCAT('%', ?, '%')) AS hitcount,"
				+ " commentcount, dno, rownum, dtitle, dcontent, dthumbnail, ddate2, dlike, dcount, dip, no, id, name FROM DramaView"
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
					map.put("dno", rs.getInt("dno"));
					map.put("rownum", rs.getInt("rownum"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("ddate", rs.getString("ddate2"));
					map.put("dcount", rs.getInt("dcount"));
					map.put("dip", rs.getString("dip"));
					map.put("dlike", rs.getInt("dlike"));
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
	
	
	public ArrayList<HashMap<String, Object>> bestBoardList() {
		ArrayList<HashMap<String, Object>> list = null;
		
		String query = "SELECT * FROM DramaView ORDER BY dlike DESC LIMIT 3;";
		
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				list = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("dno", rs.getInt("dno"));
					map.put("rownum", rs.getInt("rownum"));
					map.put("dtitle", rs.getString("dtitle"));
					map.put("dcontent", rs.getString("dcontent"));
					map.put("dthumbnail", rs.getString("dthumbnail"));
					map.put("dlike", rs.getInt("dlike"));
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
	
	
	
	
	//추천 세계	
	public int canLike(int dno, String id) {
		//-1이면 에러 발생, 0이면 추천 가능, 1이면 추천 불가능(중복)
		String query = "SELECT COUNT(*) FROM DramaLike WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
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

	
	public ArrayList<HashMap<String, Object>> isLike(String id) {
		ArrayList<HashMap<String, Object>> likeList = null;
		
		String query = "SELECT * FROM DramaLikeView WHERE no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				likeList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<>();
					map.put("dno", rs.getInt("dno"));
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
	
	public int likeUp(int dno, String id, String ip) {
		int result = 0;
		String query = "INSERT INTO DramaLike (dno, no, dip) VALUES (?, (SELECT no FROM Login WHERE id=?), ?)";
		try {
		Connection conn = DBConnection.dbConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, dno);
		pstmt.setString(2, id);
		pstmt.setString(3, ip);
		result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
		}
	
	
	public int deleteLike(int dno, String id) {
		int result = 0;
		String query = "DELETE FROM DramaLike WHERE dno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 코멘트 세계
	public ArrayList<HashMap<String, Object>> commentList(int dno) {
		ArrayList<HashMap<String, Object>> commentList = null;

		String query = "SELECT * FROM DramaCommentView WHERE dno=?";

		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				commentList = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("dcno", rs.getInt("dcno"));
					map.put("dno", rs.getInt("dno"));
					map.put("dccontent", rs.getString("dccontent"));
					map.put("dcdate", rs.getString("dcdate"));
					map.put("dcip", rs.getString("dcip"));
					map.put("no", rs.getInt("no"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					commentList.add(map);
				}
			}
			// System.out.println(commentList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentList;
	}

	public int commentInsert(int dno, String dccontent, String id) {
		int result = 0;
		String query = "INSERT INTO DramaComment (dno, dccontent, no) "
				+ "VALUES (?, ?, (SELECT no FROM Login WHERE id=?))";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dno);
			pstmt.setString(2, dccontent);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int modifyComment(int dcno, String dccontent, String id) {
		// dcno와 dno를 안맞춘 게 보안적으로 문제가 될 수 있을까??
		int result = 0;
		String query = "UPDATE DramaComment SET dccontent=? WHERE dcno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dccontent);
			pstmt.setInt(2, dcno);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteComment(int dcno, String id) {
		int result = 0;
		String query = "DELETE FROM DramaComment WHERE dcno=? AND no=(SELECT no FROM Login WHERE id=?)";
		try {
			Connection conn = DBConnection.dbConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dcno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	
}