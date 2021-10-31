package thriller;

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

@WebServlet("/thrillerBoardDetail")
public class ThrillerBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerBoardDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("tno") != null && Util.str2Int(request.getParameter("tno")) != 0) {
			int tno = Util.str2Int(request.getParameter("tno"));
			ThrillerDAO dao = ThrillerDAO.getInstance();
			
			HashMap<String, Object> dto = dao.detail(tno);

			if (((int) dto.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(tno);
				request.setAttribute("commentList", commentList);
			}
			
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				int canLike = dao.canLike(tno, (String)session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("./thrillerBoardDetail.jsp");
			request.setAttribute("dto", dto);
			System.out.println(request.getParameter("id"));
			rd.forward(request, response);

		} else {
			response.sendRedirect("./thriller");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
