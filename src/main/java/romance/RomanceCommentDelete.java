package romance;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/romanceCommentDelete")
public class RomanceCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceCommentDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println(request.getParameter("rcno"));
		System.out.println(request.getParameter("rno"));
		System.out.println(request.getParameter("rccontent"));
		System.out.println(session.getAttribute("id"));

		if (request.getParameter("rcno") != null && request.getParameter("rno") != null
				&& Util.str2Int(request.getParameter("rcno")) != 0 && Util.str2Int(request.getParameter("rno")) != 0
				&& session.getAttribute("id") != null) {

			int rcno = Util.str2Int(request.getParameter("rcno"));
			int rno = Util.str2Int(request.getParameter("rno"));
			String id = (String) session.getAttribute("id");
			String rccontent = request.getParameter("rccontent");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rcno", rcno);
			map.put("rno", rno);
			map.put("id", id);
			map.put("rccontent", rccontent);
			RomanceCommentDAO dao = RomanceCommentDAO.getInstance();

			int result = dao.commentDelete(map);

			if (result == 1) {
				response.sendRedirect("./romanceBoardDetail?rno=" + rno);

			} else {
				response.sendRedirect("./error?code=RomanceCommentDelete");
			}

		} else {
			response.sendRedirect("./error?code=delete");
		}
	}
}