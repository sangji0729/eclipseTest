package admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.LogDAO;
import util.Util;

@WebServlet("/adminDelete")
public class AdminDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDelete() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int no = Util.str2Int2(request.getParameter("no"));

		if (no != 0) {
			LogDAO dao = LogDAO.getInstance();
			int result = dao.adminDelete(no);

			if (result == 1) {
				response.sendRedirect("./adminMember");
			} else {
				response.sendRedirect("./error?code=1234&dano=" + request.getParameter("dano"));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
