package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("./index.jsp");
		int best = 1;
		
		//ArrayList<HashMap<String, Object>> list = MainDAO.getInstance().indexBestBoardList((best - 1) * 5);
		ArrayList<HashMap<String, Object>> bestCommentList = MainDAO.getInstance().indexBestCommentBoardList(best - 1);
		ArrayList<HashMap<String, Object>> secondCommentList = MainDAO.getInstance().indexSecondLikeBoardList(best);
		ArrayList<HashMap<String, Object>> bestLikeList = MainDAO.getInstance().indexBestLikeBoardList((best - 1) * 5);
		ArrayList<HashMap<String, Object>> bestReadList = MainDAO.getInstance().indexBestReadBoardList((best - 1) * 3);
		ArrayList<HashMap<String, Object>> bestDateList = MainDAO.getInstance().indexBestDateBoardList((best - 1) * 5);
		
		
		//System.out.println(bestDateList);
		//request.setAttribute("list", list);
		request.setAttribute("bestCommentList", bestCommentList);
		request.setAttribute("secondCommentList", secondCommentList);
		request.setAttribute("bestLikeList", bestLikeList);
		request.setAttribute("bestReadList", bestReadList);
		request.setAttribute("bestDateList", bestDateList);
		
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}
}