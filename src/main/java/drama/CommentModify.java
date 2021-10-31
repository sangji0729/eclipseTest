package drama;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/commentModify")
public class CommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CommentModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (Util.str2Int(request.getParameter("dno")) != 0 && Util.str2Int(request.getParameter("dcno")) != 0
				&& request.getParameter("dccontent") != null && !(request.getParameter("dccontent").equals(""))) {

			BoardDAO dao = BoardDAO.getInstance();
			HttpSession session = request.getSession();

			String dccontent = request.getParameter("dccontent");
			dccontent = dccontent.replaceAll(">", "&gt;");
			dccontent = dccontent.replaceAll("<", "&lt;");
			dccontent = dccontent.replaceAll("/", "&#47;");
			dccontent = dccontent.replaceAll("\n", "<br>");

			int dcno = Util.str2Int(request.getParameter("dcno"));
			String id = (String) session.getAttribute("id");
			int result = dao.modifyComment(dcno, dccontent, id);
			if (result == 1) {
				response.sendRedirect("./dramaBoardDetail?dno=" + request.getParameter("dno") + "&pageNo="
						+ request.getParameter("pageNo"));
			} else {
				response.sendRedirect("./error?code=1234&dno=" + request.getParameter("dno"));
			}
		}
	}

}
