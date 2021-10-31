package daily;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/dailyLike")
public class DailyLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyLike() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("dano") != null
				&& Util.str2Int(request.getParameter("dano")) != 0
				&& session.getAttribute("id") != null) {
			
		String id = (String) session.getAttribute("id");
		int dano = Util.str2Int(request.getParameter("dano"));
		String ip =  Util.getIP(request);
		
		DailyDAO dao = DailyDAO.getInstance();
		
		int result = dao.likeUp(dano, id, ip);
		
		PrintWriter pw = response.getWriter();
		pw.print(result);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}