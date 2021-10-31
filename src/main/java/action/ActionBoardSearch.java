package action;

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

@WebServlet("/actionBoardSearch")
public class ActionBoardSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionBoardSearch() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//System.out.println("여기는 겟");
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("./actionBoardSearch.jsp");
		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");

		if (searchWord == null) {
			searchWord = "";
		}
		int best = 1;
		ActionBoardDAO dao = ActionBoardDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		//searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		ArrayList<HashMap<String, Object>> list2 = ActionBoardDAO.getInstance().bestBoardList((best - 1) * 3);
		
		if (searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		request.setAttribute("list2", list2);
		//System.out.println(page);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//System.out.println("여기는 포스트");
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("./actionBoardSearch.jsp");
		//System.out.println(request.getParameter("searchColumn"));
		//System.out.println(request.getParameter("searchWord"));
		
		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");

		if (searchWord == null) {
			searchWord = "";
		}
		int best = 1;
		ActionBoardDAO dao = ActionBoardDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		//searchList = dao.search(searchColumn, searchWord, (page - 1) * 5);
		ArrayList<HashMap<String, Object>> list2 = ActionBoardDAO.getInstance().bestBoardList((best - 1) * 3);
		
		if (searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		request.setAttribute("list2", list2);
		//System.out.println(page);
		rd.forward(request, response);

	}

}
