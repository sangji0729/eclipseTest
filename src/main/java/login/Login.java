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

import log.LogDAO;
import util.Util;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		LoginDAO dao = LoginDAO.getInstance();

		HashMap<String, Object> member = dao.login(id, pw);
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("id", id);
		log.put("target", "Login");

		if (member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", member.get("id"));
			session.setAttribute("pw", member.get("pw"));
			session.setAttribute("name", member.get("name"));

			log.put("etc", "로그인 성공");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);

			// 관리자등급 = 1, 일반등급 = 2
			if ((int) member.get("grade") == 1) {
				session.setAttribute("grade", member.get("grade"));
			}else {
				
			}
			
			if (request.getAttribute("nomember") != null) {
				request.removeAttribute("nomember");
			} else {
			request.setAttribute("nomember", "해당하는 계정이 없습니다. 다시 로그인해주세요.");
			}		

			System.out.println("로그인한 아이디 : " + session.getAttribute("id"));
			System.out.println("로그인한 이름 : " + session.getAttribute("name"));
			System.out.println("로그인한 비밀번호 : " + session.getAttribute("pw"));
			System.out.println("로그인한 등급 : " + session.getAttribute("grade"));

			response.sendRedirect("./index");

		} else {
			log.put("etc", "로그인 실패");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("./login");
		}
	}
}