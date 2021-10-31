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

import log.LogDAO;
import util.Util;




@WebServlet("/adminLogSearch")
public class AdminLogSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AdminLogSearch() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("./adminLogSearch.jsp");
		String search = request.getParameter("search");
		String searchName = request.getParameter("searchName");
		
		LogDAO dao = LogDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String,Object>>();
		searchList = dao.search(search, searchName, page );
		
		if(searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		
		request.setAttribute("search", search);
		request.setAttribute("searchName", searchName);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		//System.out.println(request.getParameter("search"));
		//System.out.println(request.getParameter("searchInput"));
		String search = request.getParameter("search");
		String searchName = request.getParameter("searchName");
		
		if(searchName == null) {
			searchName = "";
		}
		RequestDispatcher rd = request.getRequestDispatcher("./adminLogSearch.jsp");
		LogDAO dao = LogDAO.getInstance();
		ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String,Object>>();
		searchList = dao.search(search, searchName, page );
		
		if(searchList != null && searchList.size() > 0) {
			request.setAttribute("totalCount", searchList.get(0).get("totalcount"));
		}
		request.setAttribute("search", search);
		request.setAttribute("searchName", searchName);
		request.setAttribute("page", page);
		request.setAttribute("list", searchList);
		rd.forward(request, response);
		
	}

}
