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

import fantasy.FantasyDAO;
import util.BoardList;
import util.Util;

@WebServlet("/adminSearch")
public class AdminSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminSearch() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		AdminCategoryDAO dao = AdminCategoryDAO.getInstance();
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}

		RequestDispatcher rd = request.getRequestDispatcher("./adminSearch.jsp");
			String searchColumn = request.getParameter("searchColumn");
			String searchWord = request.getParameter("searchWord");
			
			request.setAttribute("searchColumn", searchColumn);
			request.setAttribute("searchWord", searchWord);
			request.setAttribute("page", page);
			rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		AdminCategoryDAO dao = AdminCategoryDAO.getInstance();
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
			String tableName= request.getParameter("tableName");
			HttpSession hs = request.getSession();
			hs.setAttribute("tableName", tableName);
			String searchColumn = request.getParameter("searchColumn");
			String searchWord = request.getParameter("searchWord");
			System.out.println(tableName);
			System.out.println(searchColumn );//name
			System.out.println(searchWord);
			String column =  dao.getColumnNameByTable(tableName, searchColumn);//
			
			
			System.out.println("column:" + column);
			request.setAttribute("column", column);
			String Action = "Action";
			String Daily= "Daily";
			String Drama = "Drama";
			String Fantasy = "Fantasy";
			String Romance = "Romance";
			String Thriller = "Thriller";
			if (searchWord == null) {
				searchWord = "";
			}
			RequestDispatcher rd = request.getRequestDispatcher("./adminSearch.jsp");
			if (tableName.contentEquals(Action)) {
				ArrayList<HashMap<String, Object>> list = dao.searchA(column, searchWord, page );
				request.setAttribute("salist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}
			if (tableName.contentEquals(Daily)) {
				ArrayList<HashMap<String, Object>> list = dao.searchDA(column,  searchWord, page);
				request.setAttribute("sdalist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}

			else if (tableName.contentEquals(Drama)) {
				ArrayList<HashMap<String, Object>> list = dao.searchD(column, searchWord, page);
				request.setAttribute("sdlist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}

			else if (tableName.contentEquals(Fantasy)) {
				ArrayList<HashMap<String, Object>> list = dao.searchF(column, searchWord, page);
				request.setAttribute("sflist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}

			else if (tableName.contentEquals(Romance)) {
				ArrayList<HashMap<String, Object>> list = dao.searchR(column, searchWord, page);
				request.setAttribute("srlist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}

			else if (tableName.contentEquals(Thriller)) {
				ArrayList<HashMap<String, Object>> list = dao.searchT(column, searchWord, page);
				request.setAttribute("stlist", list);
				if (list != null && list.size() > 0) {
					request.setAttribute("totalcount", list.get(0).get("totalcount"));
				}

			}
			request.setAttribute("searchColumn", searchColumn);
			request.setAttribute("searchWord", searchWord);
		request.setAttribute("page", page);
		rd.forward(request, response);

	}

	}

