package fantasy;

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

import fantasy.FantasyDAO;
import util.Util;

@WebServlet("/fantasyBoardDetail")
public class FantasyBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FantasyDAO dao = FantasyDAO.getInstance();

	public FantasyBoardDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		if (request.getParameter("fno") != null && Util.str2Int(request.getParameter("fno")) != 0) {
			int fno = Util.str2Int(request.getParameter("fno"));
			FantasyDAO dao = FantasyDAO.getInstance();
			HashMap<String, Object> dto = dao.detail(fno);
			dao.countUp(fno);

			RequestDispatcher rd = request.getRequestDispatcher("./fantasyBoardDetail.jsp");
			request.setAttribute("dto", dto);
			
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				int canLike = dao.canLike(fno, (String)session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}

			if (((int) dto.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(fno);
				request.setAttribute("commentList", commentList);
			}
			
			rd.forward(request, response);
			
		} else {
			response.sendRedirect("./fantasy");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
