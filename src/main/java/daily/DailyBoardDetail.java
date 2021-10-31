package daily;

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

@WebServlet("/dailyBoardDetail")
public class DailyBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyBoardDetail() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("dano") != null && Util.str2Int2(request.getParameter("dano")) != 0) {

			int dano = Util.str2Int2(request.getParameter("dano"));
			DailyDAO dao = DailyDAO.getInstance();
			DailyCommentDAO commentDao = DailyCommentDAO.getInstance();
			HashMap<String, Object> dto = dao.detail(dano);
			dao.countUp(dano);
			

			RequestDispatcher rd = request.getRequestDispatcher("./dailyBoardDetail.jsp");
			request.setAttribute("dto", dto);
			
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				int canLike = dao.canLike(dano, (String)session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}

			if (((int) dto.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = commentDao.dailyCommentList(dano);
				request.setAttribute("commentList", commentList);
			}
			rd.forward(request, response);

		} else {
			response.sendRedirect("./daily");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
