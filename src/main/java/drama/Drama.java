package drama;

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

import action.ActionBoardDAO;
import util.BoardList;
import util.Util;

@WebServlet("/drama")
public class Drama extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BoardDAO dao = BoardDAO.getInstance();
		BoardList boardList = null;
		RequestDispatcher rd = request.getRequestDispatcher("./drama.jsp");
		
		int pageNo = 1;
		if (request.getParameter("pageNo") != null) {
			pageNo = Util.str2Int(request.getParameter("pageNo"));
		}
			
		ArrayList<HashMap<String, Object>> list = dao.selectList((pageNo - 1) * 10, 10);
		if (list != null && list.size() > 0) {
			boardList = new BoardList((int) list.get(0).get("totalcount"), pageNo, 10, list);
		} else if (list.size() == 0) {
			boardList = new BoardList(0, pageNo, 10, list);
		}

		ArrayList<HashMap<String, Object>> bestList = dao.bestBoardList();
		request.setAttribute("bestList", bestList);
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("id") != null) {
			ArrayList<HashMap<String, Object>> likeList = dao.isLike((String)session.getAttribute("id"));
			if (likeList != null && likeList.size() > 0) {
				request.setAttribute("likeList", likeList);
			}
		}
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNo", pageNo);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		// 한글 처리
//		response.setContentType("text/html; charset=UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		request.setCharacterEncoding("UTF-8");
//
//		BoardDAO dao = BoardDAO.getInstance();
//		BoardList boardList = null;
//
//		int pageNo = 1;
//		if (request.getParameter("pageNo") != null) {
//			pageNo = Util.str2Int(request.getParameter("pageNo"));
//		}
//
//		String searchColumn = request.getParameter("searchColumn");
//		String searchWord = request.getParameter("searchWord");
//
//		ArrayList<HashMap<String, Object>> list = dao.searchList(searchColumn, searchWord, (pageNo - 1) * 10, 10);
//
//		if (list != null && list.size() > 0) {
//			boardList = new BoardList((int) list.get(0).get("totalcount"), pageNo, 10, list);
//			request.setAttribute("hitcount", (int) list.get(0).get("totalcount"));
//		} else if (list.size() == 0) {
//			boardList = new BoardList(0, pageNo, 10, list);
//			request.setAttribute("hitcount", 0);
//		}
//		request.setAttribute("boardList", boardList);
//		request.setAttribute("searchColumn", searchColumn);
//		request.setAttribute("searchWord", searchWord);
//		RequestDispatcher rd = request.getRequestDispatcher("./drama.jsp");
//		rd.forward(request, response);
	}

}
