package drama;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import log.LogDAO;
import util.Util;

@WebServlet("/dramaLikeDelete")
public class DramaLikeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DramaLikeDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (request.getParameter("dno") != null
				&& Util.str2Int(request.getParameter("dno")) != 0
				&& session.getAttribute("id") != null) {
			
		String id = (String) session.getAttribute("id");
		int dno = Util.str2Int(request.getParameter("dno"));
		
		BoardDAO dao = BoardDAO.getInstance();
		
		int result = dao.deleteLike(dno, id);
		
		PrintWriter pw = response.getWriter();
		pw.print(result);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	}

}