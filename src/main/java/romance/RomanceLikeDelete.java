package romance;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

@WebServlet("/romanceLikeDelete")
public class RomanceLikeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceLikeDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (request.getParameter("rno") != null && Util.str2Int(request.getParameter("rno")) != 0
				&& session.getAttribute("id") != null) {

			String id = (String) session.getAttribute("id");
			int rno = Util.str2Int(request.getParameter("rno"));

			RomanceDAO dao = RomanceDAO.getInstance();
			int result = dao.deleteLike(rno, id);

			PrintWriter pw = response.getWriter();
			pw.print(result);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}