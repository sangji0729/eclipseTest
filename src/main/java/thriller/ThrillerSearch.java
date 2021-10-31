package thriller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

@WebServlet("/thrillerSearch")
public class ThrillerSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ThrillerSearch() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("./thrillerSearch.jsp");
		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");

		if (searchWord == null) {
			searchWord = "";
		}
		int best = 1;
		ThrillerDAO dao = ThrillerDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		ArrayList<HashMap<String, Object>> list2 = dao.bestBoardList((best - 1) * 3);
		
		if (searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		request.setAttribute("list2", list2);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("./thrillerSearch.jsp");
		
		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");

		if (searchWord == null) {
			searchWord = "";
		}
		int best = 1;
		ThrillerDAO dao = ThrillerDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		ArrayList<HashMap<String, Object>> list2 = dao.bestBoardList((best - 1) * 3);
		
		if (searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		System.out.println(searchColumn);
		System.out.println(searchWord);
		
		
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		request.setAttribute("list2", list2);
		rd.forward(request, response);
	}

}
