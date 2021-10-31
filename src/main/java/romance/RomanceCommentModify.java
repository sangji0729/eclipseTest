package romance;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

@WebServlet("/romanceCommentModify")
public class RomanceCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceCommentModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("rcno"));
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int rno = Util.str2Int(request.getParameter("rno"));
		int rcno = Util.str2Int(request.getParameter("rcno"));
		String rccontent = request.getParameter("rccontent");

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rno", rno);
		map.put("rcno", rcno);
		map.put("rccontent", rccontent);

		RomanceCommentDAO dao = RomanceCommentDAO.getInstance();
		int result = dao.commentModify(map);

		if (result == 1) {
			response.sendRedirect("./romanceBoardDetail?rno=" + rno);

		} else {
			response.sendRedirect("./error?code=commentModifyError");
		}
	}
}