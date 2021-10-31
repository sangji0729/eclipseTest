package daily;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/dailyCommentModify")
public class DailyCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyCommentModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (Util.str2Int(request.getParameter("dano")) != 0 && Util.str2Int(request.getParameter("dacno")) != 0
				&& request.getParameter("daccontent") != null && !(request.getParameter("daccontent").equals(""))) {
		/*
		 * System.out.println("content : " + request.getParameter("content"));
		 * System.out.println("dano : " + request.getParameter("dano"));
		 * System.out.println("dacno : " + request.getParameter("dacno"));
		 */
		HttpSession session = request.getSession();
		DailyCommentDAO dao = DailyCommentDAO.getInstance();
		
		String daccontent = request.getParameter("daccontent");
		daccontent = daccontent.replaceAll(">", "&gt;");
		daccontent = daccontent.replaceAll("<", "&lt;");
		daccontent = daccontent.replaceAll("/", "&#47;");
		daccontent = daccontent.replaceAll("\n", "<br>");
		
		int dacno = Util.str2Int(request.getParameter("dacno"));
		String id = (String) session.getAttribute("id");
		int result = dao.dailyCommentModify(dacno, daccontent, id);

		if (result == 1) {
			response.sendRedirect("./dailyBoardDetail?dano=" + request.getParameter("dano") + "&pageNo="+ request.getParameter("pageNo"));
		} else {
			response.sendRedirect("./error?code=1234&dano=" + request.getParameter("dano"));
		}

	}
	}
}
