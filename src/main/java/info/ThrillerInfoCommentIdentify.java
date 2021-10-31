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



@WebServlet("/thrillerInfoCommentIdentify")
public class ThrillerInfoCommentIdentify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ThrillerInfoCommentIdentify() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		RequestDispatcher rd = request.getRequestDispatcher("thrillerInfoCommentIdentify.jsp");
		
		if(session.getAttribute("id") != null && session.getAttribute("name") != null) {
			String id = (String)session.getAttribute("id");
			String table = "ThrillerComment";
			
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
			InfoDAO dao = InfoDAO.getInstance();
			
			list = dao.thrillerCommentWriteList(table, id, (page - 1) * 5);
			
			request.setAttribute("list", list);
			
			if(list != null && list.size() > 0) {
				request.setAttribute("totalCount", list.get(0).get("totalcount"));
			}
			
			request.setAttribute("page", page);
			rd.forward(request, response);
		}
	}

}
