package thriller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.AdminCategoryDAO;
import log.LogDAO;
import util.FileThing;
import util.Util;

@WebServlet("/thrillerBoardDelete")
public class ThrillerBoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerBoardDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		System.out.println("여기까지는 들어오나요?");
		if (request.getParameter("tno") != null && Util.str2Int2(request.getParameter("tno")) != 0
				&& session.getAttribute("id") != null) {
			System.out.println("여기는?");
			System.out.println(request.getParameter("tno"));
			System.out.println(session.getAttribute("id"));

			String id = (String) session.getAttribute("id");
			int tno = Util.str2Int(request.getParameter("tno"));
			ThrillerDAO dao = ThrillerDAO.getInstance();

			// map을 만들어서 보내주기 tno, id
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tno", tno);
			map.put("id", id);

			ArrayList<String> fileName = dao.findFileName(map);
			System.out.println("fileName : " + fileName);

			if (fileName != null) {
				FileThing ft = new FileThing();
				String path = request.getSession().getServletContext().getRealPath("/");
				if (fileName.get(0) != null) {
					ft.fileDelete(path + "upload" + File.separator, fileName.get(0));
				}
				if (fileName.get(1) != null) {
					ft.fileDelete(path + "thumbnail" + File.separator, fileName.get(1));
				}
			}
			// 파일 먼저 삭제
			// 데이터 베이스 삭제
			System.out.println(map);
			int result = dao.delete(map);

			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", session.getAttribute("id"));
			log.put("target", "ThrillerBoardDelete");
			if (result == 1) {
				log.put("etc", "게시글 삭제 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./thriller");
			} else {
				log.put("etc", "게시글 삭제 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=1");
			}
		} else {
			response.sendRedirect("./error?code=12");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lowertable = request.getParameter("lowertable");
		String lowerTC = request.getParameter("lowerTC");
		int no = Util.str2Int2(request.getParameter(lowerTC + "no"));

		System.out.println("no" + no);// 413

		System.out.println("user" + request.getParameter("lowertable"));
		System.out.println("user" + request.getParameter("lowerTC"));
		System.out.println("user" + request.getParameter("number"));

		if (no != -1) {

			AdminCategoryDAO dao = AdminCategoryDAO.getInstance();
			String table = dao.changeToCap(lowertable);
			dao.delete(no, lowertable, lowerTC);
			System.out.print("post deletion sequence complete in administrative mode");
			response.sendRedirect("./adminPost?category=" + table);
			////////////////////
		}
	}

}
