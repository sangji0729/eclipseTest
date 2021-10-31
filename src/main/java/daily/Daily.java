package daily;

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

import util.BoardList;
import util.Util;

@WebServlet("/daily")
public class Daily extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DailyDAO dao = DailyDAO.getInstance();
		BoardList boardList = null;
		RequestDispatcher rd = request.getRequestDispatcher("./daily.jsp");

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

		HttpSession session = request.getSession();
		
		if(session.getAttribute("id") != null) {
			ArrayList<HashMap<String, Object>> likeList = dao.isLike((String)session.getAttribute("id"));
			if (likeList != null && likeList.size() > 0) {
				request.setAttribute("likeList", likeList);
			}
		}
		int best = 1;
		ArrayList<HashMap<String, Object>> list2 = DailyDAO.getInstance().bestBoardList((best - 1) * 3);
		request.setAttribute("boardList", boardList);
		request.setAttribute("list2", list2);
		request.setAttribute("pageNo", pageNo);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
}
