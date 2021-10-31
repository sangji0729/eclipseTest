package admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.LogDAO;
import util.Util;

@WebServlet("/adminLog")
public class AdminLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LogDAO dao = LogDAO.getInstance();

	public AdminLog() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// session 검사해주세요. grade가 있으면 아래 문장 실행
		// 없으면 "접근할 수 없습니다."로 출력하기
		// admin.jsp 디스패쳐로 연결해주세요.
		// log내용을 가져오기
		// 1. 페이징 처리 꼭 해주세요.
		int page = 1;
		/* System.out.println("id : " + request.getParameter("id")); */
		ArrayList<HashMap<String, Object>> list = null;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}
		if (request.getParameter("id") == null && request.getParameter("ip") == null
				&& request.getParameter("target") == null) {
			list = dao.logList((page - 1) * 20);

		} else if (request.getParameter("id") != "" && request.getParameter("ip") != ""
				&& request.getParameter("target") == "") {
			String id = request.getParameter("id");
			String ip = request.getParameter("ip");
			list = dao.selectIdIp(id, ip, (page - 1) * 10);
		}

		else if (request.getParameter("id") != "" && request.getParameter("ip") == ""
				&& request.getParameter("target") != "") {
			String id = request.getParameter("id");
			String target = request.getParameter("target");
			list = dao.selectIdTarget(id, target, (page - 1) * 10);
		}

		else if (request.getParameter("id") != "" && request.getParameter("ip") == ""
				&& request.getParameter("target") == "") {
			String id = request.getParameter("id");
			list = dao.selectId(id, (page - 1) * 10);
		}

		else if (request.getParameter("id") == "" && request.getParameter("ip") != ""
				&& request.getParameter("target") == "") {
			String ip = request.getParameter("ip");
			list = dao.selectIp(ip, (page - 1) * 10);
		}

		else if (request.getParameter("id") == "" && request.getParameter("ip") == ""
				&& request.getParameter("target") != "") {
			String target = request.getParameter("target");
			System.out.println(request.getParameter("id"));
			System.out.println(request.getParameter("ip"));
			list = dao.selectTarget(target, (page - 1) * 10);
		}

		else if (request.getParameter("id") == "" && request.getParameter("ip") != ""
				&& request.getParameter("target") != "") {
			String ip = request.getParameter("ip");
			String target = request.getParameter("target");
			list = dao.selectIpTarget(ip, target, (page - 1) * 10);
		}

		else if (request.getParameter("id") != "" && request.getParameter("ip") != ""
				&& request.getParameter("target") != "") {
			String id = request.getParameter("id");
			String ip = request.getParameter("ip");
			String target = request.getParameter("target");
			list = dao.selectIdIpTarget(id, ip, target, (page - 1) * 10);

		}
		// idList SELECT DISTINCT log_id FROM logview
		ArrayList<String> idList = dao.list("log_id");

		// ipList SELECT DISTINCT log_ip FROM logview
		ArrayList<String> ipList = dao.list("log_ip");

		// targetList SELECT DISTINCT log_target FROM logview
		ArrayList<String> targetList = dao.list("log_target");

		RequestDispatcher rd = request.getRequestDispatcher("./adminLog.jsp");
		// 가져갈 값은 여기
		request.setAttribute("list", list);
		request.setAttribute("idList", idList);
		request.setAttribute("ipList", ipList);
		request.setAttribute("targetList", targetList);
		request.setAttribute("ida", request.getParameter("id"));
		request.setAttribute("ip", request.getParameter("ip"));
		request.setAttribute("target", request.getParameter("target"));
		// System.out.println(ipList);
		// System.out.println(targetList);
		// 2. totalCount
		if (list != null && list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).get("totalcount"));
		}
		// 3. page
		request.setAttribute("page", page);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Util.str2Int(request.getParameter("page"));
		}

		String search = request.getParameter("search");
		String searchName = request.getParameter("searchname");
		// System.out.println(searchName);
		// System.out.println(search);

		ArrayList<HashMap<String, Object>> list = dao.search(searchName, search, page);

		RequestDispatcher rd = request.getRequestDispatcher("adminLog.jsp");
		// totalCount
		if (list != null && list.size() > 0) {
			request.setAttribute("totalCount", list.get(0).get("totalcount"));
		}
		// page도 보내야 합니다.
		request.setAttribute("search", search);
		request.setAttribute("searchName", searchName);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		rd.forward(request, response);
	}

}
