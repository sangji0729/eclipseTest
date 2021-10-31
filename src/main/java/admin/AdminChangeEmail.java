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

@WebServlet("/adminChangeEmail")
public class AdminChangeEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminChangeEmail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int no = Util.str2Int2(request.getParameter("no"));
		String email = request.getParameter("email");

		if (no != 0 && email != null) {

			HashMap<String, Object> dto = new HashMap<String, Object>();
			dto.put("no", no);
			dto.put("email", email);

			LogDAO dao = LogDAO.getInstance();
			int result = dao.adminChangeEmail(dto);

			if (result == 1) {
				response.sendRedirect("./adminMember");
			} else {
				response.sendRedirect("./error?code=adminChangeEmailError&no=" + no);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
