package romance;

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

import log.LogDAO;
import util.Util;

@WebServlet("/romanceBoardWrite")
public class RomanceBoardWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceBoardWrite() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./romanceBoardWrite.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = request.getSession().getServletContext().getRealPath("/");
		String realPath = path + "upload" + File.separator;
		System.out.println("경로 : " + path);

		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath, size, "UTF-8", new DefaultFileRenamePolicy());

		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		String saveFile = multi.getFilesystemName("file1");

		System.out.println("title : " + title);
		System.out.println("cotnent : " + content);
		System.out.println("저장시 이름 : " + saveFile);

		if (saveFile != null) {
			String thumbnailPath = path + "thumbnail" + File.separator;
			BufferedImage inputImg = ImageIO.read(new File(realPath + saveFile));

			int width = 160;
			int height = 160;
			String[] imgs = { "png", "gif", "jpg", "bmp" };

			for (String format : imgs) {
				BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D gr2d = outputImg.createGraphics();
				gr2d.drawImage(inputImg, 0, 0, width, height, null);

				File thumb = new File(thumbnailPath + saveFile);

				if (thumb.exists()) {
					thumb.mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(thumb);
				ImageIO.write(outputImg, format, thumb);
				fos.close();
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("ip", Util.getIP(request));
		map.put("saveFile", saveFile);
		map.put("thumbnail", saveFile);

		HttpSession session = request.getSession();
		map.put("id", session.getAttribute("id"));

		String id = (String) session.getAttribute("id");
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("id", id);
		log.put("target", "RomanceBoardWrite");

		RomanceDAO dao = RomanceDAO.getInstance();
		int result = dao.write(map);

		if (result == 1) {
			log.put("etc", "게시글 작성 성공");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("romanceBoard");

		} else {
			log.put("etc", "게시글 작성 실패");
			log.put("ip", Util.getIP(request));
			LogDAO.insertLog(log);
			response.sendRedirect("./error?code=WriteError");
		}
	}
}