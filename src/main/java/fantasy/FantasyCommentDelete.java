package fantasy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/fantasyCommentDelete")
public class FantasyCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FantasyCommentDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Util.str2Int(request.getParameter("fno")) != 0 && Util.str2Int(request.getParameter("fcno")) != 0) {
			FantasyDAO dao = FantasyDAO.getInstance();
			HttpSession session = request.getSession();

			int fcno = Util.str2Int(request.getParameter("fcno"));
			String id = (String) session.getAttribute("id");
			int result = dao.deleteComment(fcno, id);
			if (result == 1) {
				response.sendRedirect("./fantasyBoardDetail?fno=" + request.getParameter("fno") + "&pageNo="
						+ request.getParameter("pageNo"));
			} else {
				response.sendRedirect("./error?code=1234&fno=" + request.getParameter("fno"));
			}
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
