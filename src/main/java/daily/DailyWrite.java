package daily;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import log.LogDAO;
import util.Util;

@WebServlet("/dailyWrite")
public class DailyWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DailyWrite() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./dailyWrite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// 경로 얻기
		String path = request.getSession().getServletContext().getRealPath("/");
		// System.out.println(request.getRequestURI());
		String thumbPath = path + "thumbnail" + File.separator;
		File testfile = new File(thumbPath);
		if (!testfile.isDirectory()) {
			System.out.println("Not a directory: 디렉토리를 새로 생성합니다.");
			testfile.mkdir();
		}

		MultipartRequest multi = new MultipartRequest(request, thumbPath, 1024 * 1024 * 5, "UTF-8",
				new DefaultFileRenamePolicy());

		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		/*
		 * dtitle = dtitle.replaceAll(">", "&gt;"); 
		 * dtitle = dtitle.replaceAll("<",
		 * "&lt;"); dtitle = dtitle.replaceAll("/", "&#47;");
		 */

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		DailyDAO dao = DailyDAO.getInstance();
		int result = 0;

		if (multi.getFilesystemName("thumbnailFile") != null) {
			String thumbnailFile = multi.getFilesystemName("thumbnailFile");
//			File thumbnail = null;

//			/////이미지 다시 그리기 시작
//			//받은 이미지 읽기
//			BufferedImage inputImg = ImageIO.read(new File(thumbPath + thumbnailFile2)); 
//			//가로세로 크기 지정
//			int width = 120;
//			int height = 120;
//			//이미지 확장자 확인 
//			String[] imgs = {"png", "gif", "jpg", "bmp"};
//			for(String format : imgs) {
//				//렌더링 사양 설정 (크기, 색상 모드)
//				//색상 모드는 BufferedImage 클래스 안에 상수로 선언되어 있음.
//				BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//				//그리기(리사이징)
//				Graphics2D gr2d = outputImg.createGraphics();
//				gr2d.drawImage(inputImg, 0, 0, width, height, null);
//				
//				//위에서 작업한 내용 저장할 파일 만들기
//				//파일 쓰기 :				경로			파일
//				thumbnail = new File(thumbPath + thumbnailFile2);
//				//폴더가 없다면 생성
//				if(thumbnail.exists()) {
//					thumbnail.mkdirs(); //여러 폴더 생성 / mkdir() -> 단일 폴더 생성
//				}
//				//스트림으로 파일 생성 및 출력
//				FileOutputStream fos = new FileOutputStream(thumbnail);
//				
//				//리사이즈한 내용 집어넣기
//				ImageIO.write(outputImg, format, thumbnail);
//			}

			result = dao.write(title, content, id, thumbnailFile);

		} else {
			result = dao.write(title, content, id);
		}
		
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("id", session.getAttribute("id"));
		log.put("target", "DailyBoardWrite");

		if (result == 1) {
			log.put("etc", "게시글 작성 성공");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("./daily");
		} else {
			log.put("etc", "게시글 작성 실패");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("./error");
		}

	}

}
