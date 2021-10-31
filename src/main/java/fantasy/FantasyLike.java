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

@WebServlet("/fantasyLike")
public class FantasyLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FantasyLike() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("fno") != null
				&& Util.str2Int(request.getParameter("fno")) != 0
				&& session.getAttribute("id") != null) {
			System.out.println(request.getParameter("fno") );
			System.out.println(session.getAttribute("id")  );
			
		String id = (String) session.getAttribute("id");
		int fno = Util.str2Int(request.getParameter("fno"));
		String fip =  Util.getIP(request);
		System.out.println(fip  );

		FantasyDAO dao = FantasyDAO.getInstance();
		
		int result = dao.likeUp(fno);
		System.out.println("liked");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}