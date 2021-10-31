package romance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/romanceBoardDetail")
public class RomanceBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceBoardDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		if (request.getParameter("rno") != null && Util.str2Int(request.getParameter("rno")) != 0) {
			int rno = Util.str2Int(request.getParameter("rno"));

			RomanceDAO dao = RomanceDAO.getInstance();

			HashMap<String, Object> list = dao.detail(rno);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rno", rno);
			int result = dao.boardCount(rno);

			if (((int) list.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(rno);
				request.setAttribute("commentList", commentList);
			}

			HttpSession session = request.getSession();

			if (session.getAttribute("id") != null) {
				int canLike = dao.canLike(rno, (String) session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}

			if (result == 1) {
				RequestDispatcher rd = request.getRequestDispatcher("./romanceBoardDetail.jsp");
				request.setAttribute("list", list);
				rd.forward(request, response);
			}

		} else {
			response.sendRedirect("./romanceBoard");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}