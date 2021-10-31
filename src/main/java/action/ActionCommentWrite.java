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

@WebServlet("/actionCommentWrite")
public class ActionCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionCommentWrite() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("id"));
		System.out.println(session.getAttribute("name"));
		System.out.println(request.getParameter("ano"));
		System.out.println(Util.str2Int2(request.getParameter("ano")));
		System.out.println(request.getParameter("accontent"));
		
		if (Util.str2Int(request.getParameter("ano")) != 0 && request.getParameter("accontent") != null
				&& !(request.getParameter("accontent").equals(""))) {

			String id = (String) session.getAttribute("id");
			String accontent = request.getParameter("accontent");
			int ano = Util.str2Int(request.getParameter("ano"));
			String ip = Util.getIP(request);

			HashMap<String, Object> map = new HashMap<String, Object>();
			accontent = Util.strRe(accontent);

			map.put("id", id);
			map.put("accontent", accontent);
			map.put("ano", ano);
			map.put("ip", ip);
			ActionBoardCommentDAO dao = ActionBoardCommentDAO.getInstance();

			int result = dao.commentWrite(map);

			if (result == 1) {
				response.sendRedirect("./actionBoardDetail?ano=" + ano);
			} else {
				response.sendRedirect("./error?code=1234");
			}
		
		}else {
			response.sendRedirect("./error?code=5555");
		}

	}

}
