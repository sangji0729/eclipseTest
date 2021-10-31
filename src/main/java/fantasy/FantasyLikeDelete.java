package fantasy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/fantasyLikeDelete")
public class FantasyLikeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FantasyLikeDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("fno") != null
				&& Util.str2Int(request.getParameter("fno")) != 0
				&& session.getAttribute("id") != null) {
			
		String id = (String) session.getAttribute("id");
		int fno = Util.str2Int(request.getParameter("fno"));
		
		FantasyDAO dao = FantasyDAO.getInstance();
		
		int result = dao.deleteLike(fno, id);
		
		PrintWriter pw = response.getWriter();
		pw.print(result);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}