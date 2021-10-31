package romance;

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

@WebServlet("/romanceBoardDelete")
public class RomanceBoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceBoardDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println("삭제 요청한 ID : " + session.getAttribute("id"));
		System.out.println("삭제 게시글 번호 : " + request.getParameter("rno"));
		System.out.println("삭제 게시글 번호2 : " + Util.str2Int2(request.getParameter("rno")));

		if (session.getAttribute("id") != null && request.getParameter("rno") != null
				&& (Util.str2Int2(request.getParameter("rno")) != 0)) {

			String id = (String) session.getAttribute("id");
			int rno = Util.str2Int(request.getParameter("rno"));

			RomanceDAO dao = RomanceDAO.getInstance();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("rno", rno);

			ArrayList<String> fileName = dao.findFileName(map);
			System.out.println("fileName : " + fileName);

			if (fileName != null) {
				FileThing ft = new FileThing();
				String path = request.getSession().getServletContext().getRealPath("/");

				if (fileName.get(0) != null) {
					ft.fileDelete(path + "upload" + File.separator, fileName.get(0));

				} else if (fileName.get(1) != null) {
					ft.fileDelete(path + "thumbnail" + File.separator, fileName.get(1));
				}
			}

			int result = dao.delete(map);

			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "RomanceBoardDelete");

			if (result == 1) {
				log.put("etc", "게시물 삭제 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);				
				response.sendRedirect("./romanceBoard");

			} else {
				log.put("etc", "게시물 삭제 실패");
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

		System.out.println("no" + no);
		System.out.println("user" + request.getParameter("lowertable"));
		System.out.println("user" + request.getParameter("lowerTC"));
		System.out.println("user" + request.getParameter("number"));

		if (no != -1) {

			AdminCategoryDAO dao = AdminCategoryDAO.getInstance();
			String table = dao.changeToCap(lowertable);
			dao.delete(no, lowertable, lowerTC);
			System.out.print("post deletion sequence complete in administrative mode");
			response.sendRedirect("./adminPost?category=" + table);			
		}
	}
}
