package drama;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/commentDelete")
public class CommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CommentDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Util.str2Int(request.getParameter("dno")) != 0 && Util.str2Int(request.getParameter("dcno")) != 0) {
			BoardDAO dao = BoardDAO.getInstance();
			HttpSession session = request.getSession();

			int dcno = Util.str2Int(request.getParameter("dcno"));
			String id = (String) session.getAttribute("id");
			int result = dao.deleteComment(dcno, id);
			if (result == 1) {
				response.sendRedirect("./dramaBoardDetail?dno=" + request.getParameter("dno") + "&pageNo="
						+ request.getParameter("pageNo"));
			} else {
				response.sendRedirect("./error?code=1234&dno=" + request.getParameter("dno"));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
