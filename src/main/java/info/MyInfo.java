package info;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/myInfo")
public class MyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MyInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("id"));
		
		if(session.getAttribute("id") != null) {
			
		String id = (String) session.getAttribute("id");
		
		InfoDAO dao = InfoDAO.getInstance();
		HashMap<String, Object> my = dao.myinfo(id);
		request.setAttribute("my", my);
		
		System.out.println("받은 값 입니다 -> " + my);
		
			RequestDispatcher rd = request.getRequestDispatcher("myInfoUpdate.jsp");
			rd.forward(request, response);
		
		
		} else {
			System.out.println("들어온 id 없음");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
