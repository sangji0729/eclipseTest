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

import drama.BoardDAO;
import util.BoardList;
import util.Util;

@WebServlet("/fantasySearch")
public class FantasySearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FantasySearch() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		FantasyDAO dao = FantasyDAO.getInstance();
		BoardList boardList = null;
		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {
			pageNo = Util.str2Int(request.getParameter("pageNo"));
		}

		if (request.getParameter("searchWord") != null) {
			String searchColumn = (String) request.getParameter("searchColumn");
			String searchWord = (String) request.getParameter("searchWord");
			
			ArrayList<HashMap<String, Object>> list = dao.searchList(searchColumn, searchWord, (pageNo - 1) * 10, 10);

			if (list != null && list.size() > 0) {
				boardList = new BoardList((int) list.get(0).get("totalcount"), pageNo, 10, list);
				request.setAttribute("hitcount", (int) list.get(0).get("totalcount"));
			} else if (list.size() == 0) {
				boardList = new BoardList(0, pageNo, 10, list);
				request.setAttribute("hitcount", 0);
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("./fantasySearch.jsp");
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNo", pageNo);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		FantasyDAO dao = FantasyDAO.getInstance();
		BoardList boardList = null;

		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {
			pageNo = Util.str2Int(request.getParameter("pageNo"));
		}

		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");
		if (searchWord == null) {
			searchWord="";
		}
		int best = 1; 
		ArrayList<HashMap<String, Object>> list = dao.searchList(searchColumn, searchWord, (pageNo - 1) * 10, 10);
		ArrayList<HashMap<String, Object>> list2 = dao.bestBoardList((best - 1) * 3);
		request.setAttribute("list2", list2);

		if (list != null && list.size() > 0) {
			boardList = new BoardList((int) list.get(0).get("totalcount"), pageNo, 10, list);
			request.setAttribute("hitcount", (int) list.get(0).get("totalcount"));
		} else if (list.size() == 0) {
			boardList = new BoardList(0, pageNo, 10, list);
			request.setAttribute("hitcount", 0);
		}
		if (list2 != null && list.size() > 0) {
			boardList = new BoardList((int) list.get(0).get("totalcount"), pageNo, 10, list);
		} else if (list2.size() == 0) {
			boardList = new BoardList(0, pageNo, 10, list);
		}
		request.setAttribute("boardList", boardList);
		// RequestDispatcher rd =
		// request.getRequestDispatcher("./dramaSearch?searchColumn="+searchColumn+"&searchWord="+searchWord);
		request.setAttribute("searchColumn", searchColumn);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("pageNo", pageNo);

		RequestDispatcher rd = request.getRequestDispatcher("./fantasySearch.jsp");
		rd.forward(request, response);
	}

}