package login;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import join.JoinDAO;

@WebServlet("/findID")
public class FindID extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoinDAO dao = JoinDAO.getInstance();

	public FindID() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("./findID.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("./findID.jsp");

		if (request.getParameter("email") != null) {
			String email = request.getParameter("email");
			String result = dao.findID(email);

			if (result != null) {
				request.setAttribute("result", "1");
				request.setAttribute("id", result);

			} else {
				request.setAttribute("result", "2");
			}
		}

		rd.forward(request, response);

	}
}