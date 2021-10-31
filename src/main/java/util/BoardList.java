package util;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardList {

	private int total; // 총 레코드 수
	private int currentPage; // 현재 페이지
	private int size; // 한 번에 출력되는 페이지 수
	private ArrayList<HashMap<String, Object>> list;
	private int totalPages; // 총 페이지 수
	private int startPage; // 페이지 이동bar 시작점
	private int endPage; // 페이지 이동bar 끝점

	public BoardList(int total, int currentPage, int size, ArrayList<HashMap<String, Object>> list) {
		this.total = total;
		this.currentPage = currentPage;
		this.size = size;
		this.list = list;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size;
			if (total % size > 0)
				++totalPages;
			// 페이지 이동bar에 5페이지씩 표시
			startPage = (currentPage / 5) * 5 + 1;
			if (currentPage % 5 == 0)
				startPage -= 5;
			endPage = startPage + (5 - 1);
			if (endPage > totalPages)
				endPage = totalPages;
		}
	}

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public int getTotal() {
		return total;
	}

	public boolean hasArticles() {
		return total > 0;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

}