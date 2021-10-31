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

@WebServlet("/adminChangePW")
public class AdminChangePW extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminChangePW() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int no = Util.str2Int2(request.getParameter("no"));
		String pw = request.getParameter("pw");

		if (no != 0 && pw != null) {

			HashMap<String, Object> dto = new HashMap<String, Object>();
			dto.put("no", no);
			dto.put("pw", pw);

			LogDAO dao = LogDAO.getInstance();
			int result = dao.adminChangePW(dto);

			if (result == 1) {
				response.sendRedirect("./adminMember");
			} else {
				response.sendRedirect("./error?code=adminChangePWError&no=" + no);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
