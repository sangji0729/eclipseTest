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

@WebServlet("/thrillerCommentWrite")
public class ThrillerCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerCommentWrite() {
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
		System.out.println(request.getParameter("tno"));
		System.out.println(Util.str2Int2(request.getParameter("tno")));
		System.out.println(request.getParameter("tccontent"));

		if (request.getParameter("tccontent") != null && request.getParameter("tno") != null
				&& (Util.str2Int(request.getParameter("tno")) != 0) && session.getAttribute("id") != null
				&& session.getAttribute("name") != null) {

			String id = (String) session.getAttribute("id");
			String tccontent = request.getParameter("tccontent");
			int tno = Util.str2Int(request.getParameter("tno"));
			String ip = Util.getIP(request);

			HashMap<String, Object> map = new HashMap<String, Object>();
			tccontent = Util.strRe(tccontent);

			map.put("id", id);
			map.put("tccontent", tccontent);
			map.put("tno", tno);
			map.put("ip", ip);
			ThrillerCommentDAO dao = ThrillerCommentDAO.getInstance();

			int result = dao.commentWrite(map);

			if (result == 1) {
				response.sendRedirect("./thrillerBoardDetail?tno=" + tno);
			} else {
				response.sendRedirect("./error?code=1234");
			}
		} else {
			response.sendRedirect("./error?code=5555");
		}

	}

}
