package romance;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/romanceCommentWrite")
public class RomanceCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceCommentWrite() {
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
		System.out.println(request.getParameter("rno"));
		System.out.println(Util.str2Int2(request.getParameter("rno")));
		System.out.println(request.getParameter("rccontent"));

		if (Util.str2Int(request.getParameter("rno")) != 0 && request.getParameter("rccontent") != null
				&& !(request.getParameter("rccontent").equals(""))) {

			String id = (String) session.getAttribute("id");
			String rccontent = request.getParameter("rccontent");
			int rno = Util.str2Int(request.getParameter("rno"));
			String ip = Util.getIP(request);

			HashMap<String, Object> map = new HashMap<String, Object>();
			rccontent = Util.strRe(rccontent);

			map.put("id", id);
			map.put("rccontent", rccontent);
			map.put("rno", rno);
			map.put("ip", ip);

			RomanceCommentDAO dao = RomanceCommentDAO.getInstance();

			int result = dao.commentWrite(map);

			if (result == 1) {
				response.sendRedirect("./romanceBoardDetail?rno=" + rno);

			} else {
				response.sendRedirect("./error?code=1234");
			}

		} else {
			response.sendRedirect("./error?code=5555");
		}
	}
}