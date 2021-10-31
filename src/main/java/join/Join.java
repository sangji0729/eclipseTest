package join;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

@WebServlet("/join")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Join() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("pw"));
		System.out.println(request.getParameter("pw2"));
		// HttpSession session = request.getSession();

		if (request.getParameter("id") != null && request.getParameter("name") != null
				&& request.getParameter("pw") != null && request.getParameter("pw2") != null
				&& request.getParameter("email") != null
				&& request.getParameter("pw").equals(request.getParameter("pw2"))) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String email = request.getParameter("email");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("name", name);
			map.put("pw", pw);
			map.put("email", email);

			JoinDAO dao = JoinDAO.getInstance();
			int result = dao.join(map);

			RequestDispatcher rd = request.getRequestDispatcher("./joinResult.jsp");
			request.setAttribute("result", 1);
			request.setAttribute("id", id);
			rd.forward(request, response);

		} else {
			response.sendRedirect("error.jsp?error=?");
		}
	}
}

/*
 * RequestDispatcher dispatcher = request.getRequestDispatcher("이동할 페이지");
 * request.setAttribute("호출할 이름", 전송할 값); rd.forward(request, response);
 */
