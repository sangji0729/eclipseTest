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

import admin.AdminDAO;
import login.LoginDAO;
import util.Util;

@WebServlet("/fantasyAdminDetail")
public class FantasyAdminDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FantasyDAO dao = FantasyDAO.getInstance();

	public FantasyAdminDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoginDAO ldao = LoginDAO.getInstance();
		HashMap<String, Object> member = ldao.login();
		HttpSession hs = request.getSession();
		hs.setAttribute("grade", member.get("grade"));
		int fno1 = Util.str2Int2(request.getParameter("fno"));

		if ((int) member.get("grade") == 1) {
			hs.setAttribute("grade", member.get("grade"));
			/* int grade = 1; */
			AdminDAO dao = AdminDAO.getInstance();
			HashMap<String, Object> admin = dao.detailAdmin();
			request.setAttribute("admin", admin);

		}

		if (request.getParameter("fno") != null) {
			int fno = Integer.parseInt(request.getParameter("fno"));

			HashMap<String, Object> detail = dao.detail(fno);
			if (((int) detail.get("commentcount")) > 0) {

				ArrayList<HashMap<String, Object>> commentList = dao.commentList(fno);
				request.setAttribute("commentList", commentList);

			}

			RequestDispatcher rd = request.getRequestDispatcher("./fantasyAdminDetail.jsp");

			request.setAttribute("d", detail);
			rd.forward(request, response);

		} else {
			response.sendRedirect("./fantasy");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
