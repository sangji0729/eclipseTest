package fantasy;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import drama.BoardDAO;
import util.Util;

@WebServlet("/comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FantasyDAO dao = FantasyDAO.getInstance();

	public Comment() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (Util.str2Int(request.getParameter("fno")) != 0 && request.getParameter("fccontent") != null
				&& !(request.getParameter("fccontent").equals(""))) {

			FantasyDAO dao = FantasyDAO.getInstance();
			HttpSession session = request.getSession();

			String fccontent = request.getParameter("fccontent");
			fccontent = fccontent.replaceAll(">", "&gt;");
			fccontent = fccontent.replaceAll("<", "&lt;");
			fccontent = fccontent.replaceAll("/", "&#47;");
			fccontent = fccontent.replaceAll("\n", "<br>");

			int fno = Util.str2Int(request.getParameter("fno"));
			String id = (String) session.getAttribute("id");
			int result = dao.commentInsert(fno, fccontent, id);
			if (result == 1) {
				response.sendRedirect("./fantasyBoardDetail?fno=" + fno + "&pageNo=" + request.getParameter("pageNo"));
			} else {
				response.sendRedirect("./error?code=1234&fno=" + fno);
			}
		}
	}

}
