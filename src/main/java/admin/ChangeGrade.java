package admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.LogDAO;
import util.Util;

@WebServlet("/changeGrade")
public class ChangeGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangeGrade() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* HttpSession session = request.getSession(); */
		/*
		 * System.out.println(Util.str2Int2(request.getParameter("no")) != 0);
		 * System.out.println(request.getParameter("no"));
		 * System.out.println(session.getAttribute("grade"));
		 */

		int no = Util.str2Int2(request.getParameter("no"));
		int grade = Util.str2Int2(request.getParameter("grade"));

		if (no != 0 && grade != 0) {

			HashMap<String, Object> dto = new HashMap<String, Object>();
			dto.put("no", no);
			dto.put("grade", grade);

			LogDAO dao = LogDAO.getInstance();
			int result = dao.changeGrade(dto);

			if (result == 1) {
				response.sendRedirect("./adminMember");
			} else {
				response.sendRedirect("./error?code=1234&dano=" + request.getParameter("dano"));
			}

			/*
			 * RequestDispatcher rd = request.getRequestDispatcher("./member.jsp");
			 * request.setAttribute("dto", dto); rd.forward(request, response);
			 */
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
