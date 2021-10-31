package drama;

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

@WebServlet("/dramaBoardDetail")
public class DramaBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DramaBoardDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("dno") != null && Util.str2Int(request.getParameter("dno")) != 0) {
			int dno = Util.str2Int(request.getParameter("dno"));
			BoardDAO dao = BoardDAO.getInstance();
			HashMap<String, Object> dto = dao.detail(dno);
			dao.countUp(dno);

			RequestDispatcher rd = request.getRequestDispatcher("./dramaBoardDetail.jsp");
			request.setAttribute("dto", dto);
			
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				int canLike = dao.canLike(dno, (String)session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}

			if (((int) dto.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(dno);
				request.setAttribute("commentList", commentList);
			}
			
			rd.forward(request, response);
			
		} else {
			response.sendRedirect("./drama");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
