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
import javax.servlet.http.HttpSession;

import admin.AdminDAO;
import login.LoginDAO;
import util.Util;

@WebServlet("/actionBoardDetail")
public class ActionBoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionBoardDetail() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	
		
		
		
		
		if (request.getParameter("ano") != null && Util.str2Int(request.getParameter("ano")) != 0) {
			int ano = Util.str2Int(request.getParameter("ano"));
			ActionBoardDAO dao = ActionBoardDAO.getInstance();

			HashMap<String, Object> list = dao.detail(ano);

			// 조회수
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ano", ano);
			int result = dao.boardCount(ano);

			if (((int) list.get("commentcount")) > 0) {
				ArrayList<HashMap<String, Object>> commentList = dao.commentList(ano);
				request.setAttribute("commentList", commentList);
			}
			HttpSession session = request.getSession();
			if(session.getAttribute("id") != null) {
				int canLike = dao.canLike(ano, (String)session.getAttribute("id"));
				request.setAttribute("canLike", canLike);
			}

			if (result == 1) {// 조회수 구현 map에서 결과값이 있을시
				RequestDispatcher rd = request.getRequestDispatcher("./actionBoardDetail.jsp");
				request.setAttribute("list", list);
				rd.forward(request, response);
			}
	

		} else {
			response.sendRedirect("./actionBoard");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
