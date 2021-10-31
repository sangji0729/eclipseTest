package action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/actionCommentDelete")
public class ActionCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionCommentDelete() {
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
		System.out.println(request.getParameter("acno"));
		System.out.println(request.getParameter("ano"));
		System.out.println(request.getParameter("accontent"));
		System.out.println(session.getAttribute("id"));
		if (request.getParameter("acno") != null && request.getParameter("ano") != null
				&& Util.str2Int(request.getParameter("acno")) != 0 && Util.str2Int(request.getParameter("ano")) != 0
				&& session.getAttribute("id") != null) {

			int acno = Util.str2Int(request.getParameter("acno"));
			int ano = Util.str2Int(request.getParameter("ano"));
			String id = (String) session.getAttribute("id");
			String accontent = request.getParameter("accontent");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("acno", acno);
			map.put("ano", ano);
			map.put("id", id);
			map.put("accontent", accontent);
			ActionBoardCommentDAO dao = ActionBoardCommentDAO.getInstance();

			int result = dao.commentDelete(map);

			if (result == 1) {
				response.sendRedirect("./actionBoardDetail?ano=" + ano);
			} else {
				response.sendRedirect("./error?code=CommentDeleteError");
			}
		}else {
			response.sendRedirect("./error?code=delete");
		}
	}
}
