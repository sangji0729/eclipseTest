package login;

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

@WebServlet("/loginForMyInfo")
public class LoginForMyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginForMyInfo() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("pw") != null
				&& session.getAttribute("id") != null) {
			
			InfoDAO dao = InfoDAO.getInstance();
			
			String pw = request.getParameter("pw");
			String id = (String) session.getAttribute("id");
		
				HashMap<String, Object> my = dao.myinfo(id);
				request.setAttribute("my", my);
				
				session.setAttribute("myInfoPass", "myInfoPass");
				
				System.out.println("받은 값 입니다 -> " + my);
				
					RequestDispatcher rd = request.getRequestDispatcher("myinfo.jsp");
					rd.forward(request, response);
				
				} else {
					//원래는 비번 틀렸으면 틀렸다고 알려주는 것도 추가해야 하지만..........시간 때문에....
					response.sendRedirect("./error.jsp");
				}
			}
	
}
