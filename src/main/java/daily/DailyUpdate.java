package daily;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import log.LogDAO;
import util.Util;

@WebServlet("/dailyUpdate")
public class DailyUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyUpdate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("id") != null && request.getParameter("dano") != null
				&& Util.str2Int(request.getParameter("dano")) != 0) {
			String id = (String) session.getAttribute("id");
			int dano = Util.str2Int(request.getParameter("dano"));

			DailyDAO dao = DailyDAO.getInstance();
			HashMap<String, Object> map = dao.mapForModify(id, dano);

			if (map != null) {
				RequestDispatcher rd = request.getRequestDispatcher("dailyUpdate.jsp");
				request.setAttribute("map", map);
				rd.forward(request, response);
			} else {
				response.sendRedirect("error?code=dailyUpdate");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// 경로 얻기
		String path = request.getSession().getServletContext().getRealPath("/");
		String thumbPath = path + "thumbnail" + File.separator;
		File testfile = new File(thumbPath);
		if (!testfile.isDirectory()) {
			System.out.println("Not a directory: 디렉토리 새로 생성");
			testfile.mkdir();
		}

		HttpSession session = request.getSession();

		MultipartRequest multi = new MultipartRequest(request, thumbPath, 1024 * 1024 * 5, "UTF-8",
				new DefaultFileRenamePolicy());

		int result = 0;

		if (session.getAttribute("id") != null && multi.getParameter("dano") != null
				&& Util.str2Int(multi.getParameter("dano")) != 0) {

			int dano = Util.str2Int(multi.getParameter("dano"));
			String datitle = multi.getParameter("title");
			String dacontent = multi.getParameter("content");
			datitle = datitle.replaceAll(">", "&gt;");
			datitle = datitle.replaceAll("<", "&lt;");
			datitle = datitle.replaceAll("/", "&#47;");
			String id = (String) session.getAttribute("id");

			DailyDAO dao = DailyDAO.getInstance();

			if (multi.getFilesystemName("thumbnailFile") != null) {
				String thumbnailFile = multi.getFilesystemName("thumbnailFile");
				if (multi.getParameter("waitDelete") != null) {
					File deleteFile = new File(thumbPath + multi.getParameter("waitDelete"));
					if (deleteFile.exists()) {
						deleteFile.delete();
						System.out.println(deleteFile + ": 파일 삭제 성공");
					} else {
						System.out.println(deleteFile + ": 파일 삭제 실패");
					}
				}
				result = dao.modify(dano, datitle, dacontent, id, thumbnailFile);
			} else {
				result = dao.modify(dano, datitle, dacontent, id);
			}
			
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "DailyBoardModify");

			if (result == 1) {
				log.put("etc", "게시글 수정 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("dailyBoardDetail?dno=" + dano + "&pageNo=" + multi.getParameter("pageNo"));
			} else {
				log.put("etc", "게시글 수정 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("error?code=dramaModify");
			}
		}

	}

}
