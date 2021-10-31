package thriller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

@WebServlet(name = "thrillerCommentUpdate", urlPatterns = { "/thrillerCommentUpdate" })
public class ThrillerCommentUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerCommentUpdate() {
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

		int tno = Util.str2Int(request.getParameter("tno"));
		int tcno = Util.str2Int(request.getParameter("tcno"));
		String tccontent = request.getParameter("tccontent");

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tno", tno);
		map.put("tcno", tcno);
		map.put("tccontent", tccontent);

		ThrillerCommentDAO dao = ThrillerCommentDAO.getInstance();
		int result = dao.modify(map);

		if (result == 1) {
			response.sendRedirect("./thrillerBoardDetail?tno=" + tno);
		} else {
			response.sendRedirect("./error?code=commentModifyError");
		}
	}

}
