package action;

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
import util.FileThing;
import util.Util;
import log.LogDAO;

@WebServlet("/actionBoardDelete")
public class ActionBoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionBoardDelete() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println("삭제 요청한 아이디 : " + session.getAttribute("id"));
		System.out.println("삭제 게시글 번호 : " + request.getParameter("ano"));
		System.out.println("삭제 게시글 번호2 : " + Util.str2Int2(request.getParameter("ano")));

		if (session.getAttribute("id") != null && request.getParameter("ano") != null
				&& (Util.str2Int2(request.getParameter("ano")) != 0)) {

			String id = (String) session.getAttribute("id");
			int ano = Util.str2Int(request.getParameter("ano"));

			ActionBoardDAO dao = ActionBoardDAO.getInstance();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("ano", ano);

			ArrayList<String> fileName = dao.findFileName(map);
			System.out.println("fileName : " + fileName);

			if (fileName != null) {
				FileThing ft = new FileThing();
				String path = request.getSession().getServletContext().getRealPath("/");
				// String path2 = request.getServletContext().getRealPath("/");
				if (fileName.get(0) != null) {
					ft.fileDelete(path + "upload" + File.separator, fileName.get(0));
				} else if (fileName.get(1) != null) {
					ft.fileDelete(path + "thumbnail" + File.separator, fileName.get(1));
				}
			}

			int result = dao.delete(map);
			// 로그남기기
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "ActionBoardDelete");

			if (result == 1) {
				/* System.out.println("유저 " + id + "의 " + ano + "번글 삭제완료"); */
				log.put("etc", "게시글 삭제 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./actionBoard");
			} else {
				log.put("etc", "게시글 삭제 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=delError1");
			}
		} else {
			response.sendRedirect("./error?code=delError2");
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
