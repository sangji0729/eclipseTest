package action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import util.Util;
import log.LogDAO;

@WebServlet("/actionBoardWrite")
public class ActionBoardWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionBoardWrite() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./actionBoardWrite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("UTF-8");

		// multipart
		// 경로 = webapp가 최상위 경로입니다 == /
		String path = request.getSession().getServletContext().getRealPath("/");
		// String realPath = path + "upload/";아래로 수정
		String realPath = path + "upload" + File.separator;
		System.out.println("경로 : " + path);
		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath, size, "UTF-8", new DefaultFileRenamePolicy());

		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		// String upFile = multi.getOriginalFileName("file1"); //실제 업로드시 파일 이름
		String saveFile = multi.getFilesystemName("file1");// 저장된 이름
		// title과 content에 html코드 특수문자로 변경해주세요.

		System.out.println("title : " + title);
		System.out.println("cotnent : " + content);
		// System.out.println("업로드시 이름 : "+ upFile);
		System.out.println("저장시 이름 : " + saveFile);

		// 저장시켜주세요
		if (saveFile != null) {// 첨부파일이 없을경우 javax.imageio.IIOException: Can't read input file! 에러가 발생하여
								// if문으로 잡아주었음
			// 썸네일 만들겠습니다.
			String thumbnailPath = path + "thumbnail" + File.separator;
			// C:\workspaceJSP\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\jul19web/upload/파일명
			BufferedImage inputImg = ImageIO.read(new File(realPath + saveFile));

			// 가로세로 크기 지정
			int width = 160;
			int height = 160;

			// 이미지 확장자 확인
			String[] imgs = { "png", "gif", "jpg", "bmp" };

			for (String format : imgs) {
				BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D gr2d = outputImg.createGraphics();
				gr2d.drawImage(inputImg, 0, 0, width, height, null);

				// 파일쓰기
				File thumb = new File(thumbnailPath + saveFile);
				// 폴더가 없다면 생성
				if (thumb.exists()) {
					// thumb.mkdir();//폴더 생성
					thumb.mkdirs();// 여러 폴더 생성
				}
				FileOutputStream fos = new FileOutputStream(thumb);
				ImageIO.write(outputImg, format, thumb);
				fos.close();
			}
		}

		// 객체 생성
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("ip", Util.getIP(request));
		map.put("saveFile", saveFile);
		map.put("thumbnail", saveFile);// 썸네일입니다. 변경할겁니다.

		// ID값도 보내겠습니다
		HttpSession session = request.getSession();
		map.put("id", session.getAttribute("id"));

		// 로그남기기
		String id = (String) session.getAttribute("id");
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("id", id);
		log.put("target", "ActionBoardWrite");

		// DAO호출
		ActionBoardDAO dao = ActionBoardDAO.getInstance();
		int result = dao.write(map);
		// 페이지 이동
		if (result == 1) {
			log.put("etc", "게시글 작성 성공");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("actionBoard");
		} else {
			log.put("etc", "게시글 작성 실패");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("./error?code=WriteError");
		}
	}

}
