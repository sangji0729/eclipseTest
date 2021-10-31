package daily;

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

@WebServlet("/dailyBoardDelete")
public class DailyBoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyBoardDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("dano") != null && Util.str2Int2(request.getParameter("dano")) != 0
				&& session.getAttribute("id") != null) {
			DailyDAO dao = DailyDAO.getInstance();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dano", request.getParameter("dano"));
			map.put("id", session.getAttribute("id"));

			ArrayList<String> fileName = dao.findFileName(map);

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
			int result = dao.dailyDelete(map);
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", session.getAttribute("id"));
			log.put("target", "DailyBoardDelete");
			if (result == 1) {
				log.put("etc", "게시글 삭제 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./daily");
			} else {
				log.put("etc", "게시글 삭제 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=DailyDelete");
			}
		} else {
			response.sendRedirect("./error?code=DailyDelete");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			////////////////////모든 테이블에서에서 삭제할려면 이 섹션이 반드시 있어야 됨. 돤다
			
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
