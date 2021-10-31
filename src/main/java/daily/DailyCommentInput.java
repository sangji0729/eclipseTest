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

@WebServlet("/dailyCommentInput")
public class DailyCommentInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyCommentInput() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		if (request.getParameter("content") != null && request.getParameter("dano") != null
				&& (Util.str2Int2(request.getParameter("dano")) != 0) && session.getAttribute("id") != null
				&& session.getAttribute("name") != null) {

			String content = request.getParameter("content");
			int dano = Util.str2Int2(request.getParameter("dano"));
			String id = (String) session.getAttribute("id");

			HashMap<String, Object> map = new HashMap<String, Object>();
			content = Util.strRe(content);
			map.put("content", content);
			map.put("dano", dano);
			map.put("id", id);

			DailyCommentDAO dao = DailyCommentDAO.getInstance();
			int result = dao.commentInput(map);

			if (result == 1) {
				response.sendRedirect("./dailyBoardDetail?dano=" + dano + "&pageNo=" + request.getParameter("pageNo"));
			} else {
				response.sendRedirect("./error?code=commentInsertError");
			}
		} else {
			response.sendRedirect("./error?code=commentInsertError");
		}
	}
}