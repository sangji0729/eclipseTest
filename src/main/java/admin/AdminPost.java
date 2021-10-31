package admin;

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

@WebServlet("/adminPost")
public class AdminPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminCategoryDAO dao = AdminCategoryDAO.getInstance();

	public AdminPost() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;// page
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		
		// categories
		String category = request.getParameter("category");
		System.out.println(category);
		HttpSession hs = request.getSession();
		hs.setAttribute("category", category );//adminpostpaging에서 사용됨.세션으로 넘겨주기
		
		if (category == null) {
			category = "Action";// 초기 페이지
		}
		int maxLimit = 20;
		if (category == "Fantasy")
			maxLimit = 20;

		ArrayList<HashMap<String, Object>> list = dao.getContentsByCategory(category ,(page-1) * 10, maxLimit);
		RequestDispatcher rd = request.getRequestDispatcher("adminPost.jsp");
		request.setAttribute("list", list);
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("target", request.getParameter("target"));

		if (list != null && list.size() > 0) {
			request.setAttribute("totalcount", list.get(0).get("totalcount"));
		}

		request.setAttribute("page", page);

		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
