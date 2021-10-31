package thriller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/thrillerCommentDelete")
public class ThrillerCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerCommentDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("서블릿에 들어옴");
		HttpSession session = request.getSession();
		System.out.println(request.getParameter("tcno"));
		System.out.println(request.getParameter("tno"));
		System.out.println(request.getParameter("tccontent"));
		System.out.println(session.getAttribute("id"));
		if (request.getParameter("tcno") != null && request.getParameter("tno") != null
				&& Util.str2Int(request.getParameter("tcno")) != 0 && Util.str2Int(request.getParameter("tno")) != 0
				&& session.getAttribute("id") != null) {

			System.out.println("이프문 충족함");
			int tcno = Util.str2Int(request.getParameter("tcno"));
			int tno = Util.str2Int(request.getParameter("tno"));
			String id = (String) session.getAttribute("id");
			String tccontent = request.getParameter("tccontent");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tcno", tcno);
			map.put("tno", tno);
			map.put("id", id);
			map.put("tccontent", tccontent);
			ThrillerCommentDAO dao = ThrillerCommentDAO.getInstance();

			int result = dao.commentDelete(map);

			if (result == 1) {
				response.sendRedirect("./thrillerBoardDetail?tno=" + tno);
			} else {
				response.sendRedirect("./error?code=CommentDeleteError");
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
