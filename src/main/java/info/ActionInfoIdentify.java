package info;

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

@WebServlet("/actionInfoIdentify")
public class ActionInfoIdentify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionInfoIdentify() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSession session = request.getSession();
		// System.out.println(session.getAttribute("id") + "!!!!!!!!!!!!!");
		// System.out.println(session.getAttribute("name")+"$$$$$$$$$$$");
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//System.out.println(session.getAttribute("id"));
		//System.out.println(session.getAttribute("name"));

		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}

		RequestDispatcher rd = request.getRequestDispatcher("actionInfoIdentify.jsp");
		if (session.getAttribute("id") != null && session.getAttribute("name") != null) {

			String id = (String) session.getAttribute("id");
			// String name = (String)session.getAttribute("name");
			String table = "Action";

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

			InfoDAO dao = InfoDAO.getInstance();
			list = dao.actionWriteList(table, id, (page - 1) * 5);

			request.setAttribute("list", list);

			if (list != null && list.size() > 0) {
				request.setAttribute("totalCount", list.get(0).get("totalcount"));
			}

			request.setAttribute("page", page);
			rd.forward(request, response);
		}
	}
}
