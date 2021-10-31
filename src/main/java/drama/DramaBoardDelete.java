package drama;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.AdminCategoryDAO;
import log.LogDAO;
import util.Util;

@WebServlet("/dramaBoardDelete")
public class DramaBoardDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DramaBoardDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (Util.str2Int(request.getParameter("dno")) != 0 && session.getAttribute("id") != null) {
			int dno = Util.str2Int(request.getParameter("dno"));
			String id = (String) session.getAttribute("id");

			String path = request.getSession().getServletContext().getRealPath("/");
			String thumbPath = path + "thumbnail" + File.separator;

			BoardDAO dao = BoardDAO.getInstance();

			if (dao.isFile(dno, id) != null) {
				File deleteFile = new File(thumbPath + dao.isFile(dno, id));
				if (deleteFile.exists()) {
					deleteFile.delete();
					System.out.println(deleteFile + ": 파일 삭제 성공");
				} else {
					System.out.println(deleteFile + ": 파일 삭제 실패");
				}
			}
			int result = dao.delete(dno, id);
			
			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", session.getAttribute("id"));
			log.put("target", "DramaBoardDelete");
			
			if (result == 1) {
				log.put("etc", "게시글 삭제 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./drama?pageNo=" + request.getParameter("pageNo"));
			} else {
				log.put("etc", "게시글 삭제 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=1234&dno=" + dno);
			}
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
