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

@WebServlet("/myInfoUpdate")
public class MyInfoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyInfoUpdate() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		 HttpSession session = request.getSession();
		 
		 if (session.getAttribute("id") != null && request.getParameter("name") != null
				 && request.getParameter("email") != null
		            && request.getParameter("pw1") != null && request.getParameter("pw2") != null
		            && request.getParameter("pw1").equals(request.getParameter("pw2"))) {
			 
			 String id = (String) session.getAttribute("id");
	         String name = request.getParameter("name");
	         String pw = request.getParameter("pw1");
	         String email = request.getParameter("email");
	         
	         System.out.println(id + " : " + name + " : " + pw + " : " + email);
	         
	         HashMap<String, Object> map = new HashMap<String, Object>();
	         map.put("id",id);
	         map.put("name", name);
	         map.put("pw", pw);
	         map.put("email", email);
	         
	         InfoDAO dao = InfoDAO.getInstance();
	         int result = dao.accountUpdate(map);
			 
	         RequestDispatcher rd = request.getRequestDispatcher("./myInfoUpdateResult.jsp");
	         request.setAttribute("map", map);
	         request.setAttribute("result", 1);
	         rd.forward(request, response);
	         
		 } else {
			 System.out.println("먼가 틀림ㅜ...");
			 response.sendRedirect("error.jsp?error=?");
		 }
		 
	}

}
