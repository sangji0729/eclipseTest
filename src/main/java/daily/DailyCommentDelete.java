package daily;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/dailyCommentDelete")
public class DailyCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyCommentDelete() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("dacno") != null && request.getParameter("dano") != null
				&& Util.str2Int2(request.getParameter("dacno")) != 0 && Util.str2Int2(request.getParameter("dano")) != 0
				&& session.getAttribute("id") != null) {
			DailyCommentDAO dao = DailyCommentDAO.getInstance();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dacno", request.getParameter("dacno"));
			map.put("dano", request.getParameter("dano"));
			map.put("id", session.getAttribute("id"));

			int result = dao.dailyCommentDelete(map);
			/* System.out.println(map); */
			if (result == 1) {
				response.sendRedirect("./dailyBoardDetail?dano=" + request.getParameter("dano"));
			} else {
				response.sendRedirect("./error?code=DailyCommentDelete");
			}
		} else {
			response.sendRedirect("./error?code=DailyCommentDelete");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
