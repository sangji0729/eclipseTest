package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import join.JoinDAO;

@WebServlet("/emCheck")
public class EMCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EMCheck() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int result = 1;
		String email = request.getParameter("email");
		JoinDAO dao = JoinDAO.getInstance();
		result = dao.emCheck(email);

		PrintWriter pw = response.getWriter();
		pw.print(result);
	}
}