package info;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import info.InfoDAO;
import log.LogDAO;
import util.Util;

public class TransForMyInfo {

	@WebServlet("/transForMyInfo")
	public class LoginForMyInfo extends HttpServlet {
		private static final long serialVersionUID = 1L;

		public LoginForMyInfo() {
			super();

		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			HttpSession session = request.getSession();
			if (request.getParameter("myInfoPass") != null
					&& session.getAttribute("id") != null) {
				
				InfoDAO dao = InfoDAO.getInstance();
				
				String id = (String) session.getAttribute("id");
			
					HashMap<String, Object> my = dao.myinfo(id);
					request.setAttribute("my", my);
					
					System.out.println("받은 값 입니다 -> " + my);
					
						RequestDispatcher rd = request.getRequestDispatcher("myinfoUpdate.jsp");
						rd.forward(request, response);
					
					} else {
						response.sendRedirect("./error.jsp");
					}
				}
		
	}

}
