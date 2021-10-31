package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/actionLikeDelete")
public class ActionLikeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ActionLikeDelete() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
		
		if (request.getParameter("ano") != null
				&& Util.str2Int(request.getParameter("ano")) != 0
				&& session.getAttribute("id") != null) {
			
		String id = (String) session.getAttribute("id");
		int ano = Util.str2Int(request.getParameter("ano"));
		
		ActionBoardDAO dao = ActionBoardDAO.getInstance();
		
		int result = dao.deleteLike(ano, id);
		
		PrintWriter pw = response.getWriter();
		pw.print(result);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
