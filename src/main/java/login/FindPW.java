package login;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import join.JoinDAO;

@WebServlet("/findPW")
public class FindPW extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoinDAO dao = JoinDAO.getInstance();

	public FindPW() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("./findPW.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("./findPW.jsp");

		if (request.getParameter("id") != null) {
			String id = request.getParameter("id");
			String result = dao.findPW(id);

			if (result != null) {
				request.setAttribute("result", "1");
				request.setAttribute("pw", result);

			} else {
				request.setAttribute("result", "2");
			}
		}

		rd.forward(request, response);

	}
}