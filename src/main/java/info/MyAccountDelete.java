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


@WebServlet("/myAccountDelete")
public class MyAccountDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MyAccountDelete() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("id") != null && session.getAttribute("name") != null) {
			String id = (String)session.getAttribute("id");
			String name = (String)session.getAttribute("name");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("name", name);
			InfoDAO dao = InfoDAO.getInstance();
			int result = dao.accountDelete(map);
			
			if(result == 1) {
				if (session.getAttribute("id") != null) {
					session.removeAttribute("id");
				}
				if (session.getAttribute("name") != null) {
					session.removeAttribute("name");
				}
				
				
				
				RequestDispatcher rd = request.getRequestDispatcher("./index.jsp");
				rd.forward(request, response);
			}else {
				response.sendRedirect("error?code=resultError");
			}
			
		}else {
			response.sendRedirect("error?code=Error");
		}
	}

}
