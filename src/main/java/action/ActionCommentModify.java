package action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Util;

@WebServlet("/actionCommentModify")
public class ActionCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionCommentModify() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("acno"));
		// RequestDispatcher rd =
		// request.getRequestDispatcher("./actionCommentModify.jsp");
		// rd.forward(request, response);
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		System.out.println(request.getParameter("ano"));
		System.out.println(request.getParameter("acno"));
		System.out.println(request.getParameter("accontent"));

		int ano = Util.str2Int(request.getParameter("ano"));
		int acno = Util.str2Int(request.getParameter("acno"));
		String accontent = request.getParameter("accontent");

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ano", ano);
		map.put("acno", acno);
		map.put("accontent", accontent);

		ActionBoardCommentDAO dao = ActionBoardCommentDAO.getInstance();
		int result = dao.modify(map);

		if (result == 1) {
			response.sendRedirect("./actionBoardDetail?ano=" + ano);
		} else {
			response.sendRedirect("./error?code=commentModifyError");
		}

	}

}
