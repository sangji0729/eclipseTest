package thriller;

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

@WebServlet("/thrillerUpdate")
public class ThrillerUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThrillerUpdate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		int tno = Util.str2Int(request.getParameter("tno"));
		if (session.getAttribute("id") != null && request.getParameter("tno") != null
				&& Util.str2Int(request.getParameter("tno")) != 0) {

			String id = (String) session.getAttribute("id");
			System.out.println("오는 데이터는 : " + tno + " , " + id);

			ThrillerDAO dao = ThrillerDAO.getInstance();
			HashMap<String, Object> map = dao.detail(tno);

			RequestDispatcher rd = request.getRequestDispatcher("./thrillerUpdate.jsp");
			request.setAttribute("map", map);
			rd.forward(request, response);
		} else {
			response.sendRedirect("thrillerBoardDetail?tno=" + tno);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String path = request.getSession().getServletContext().getRealPath("/");
		String realPath = path + "upload/" + File.separator;
		String thumbnailPath = path + "thumbnail/" + File.separator;
		System.out.println("경로 : " + path + realPath);

		HttpSession session = request.getSession();

		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath, size, "UTF-8", new DefaultFileRenamePolicy());

		if (session.getAttribute("id") != null && multi.getParameter("tno") != null
				&& Util.str2Int(multi.getParameter("tno")) != 0) {

			String title = multi.getParameter("title");
			String id = (String) session.getAttribute("id");
			String content = multi.getParameter("content");
			int tno = (Util.str2Int(multi.getParameter("tno")));

			System.out.println(multi.getParameter("title"));
			System.out.println(session.getAttribute("id"));
			System.out.println(multi.getParameter("tno"));
			System.out.println(multi.getParameter("content"));
			System.out.println(multi.getOriginalFileName("file"));

			ThrillerDAO dao = ThrillerDAO.getInstance();

			System.out.println("file : " + multi.getOriginalFileName("file"));
			String tfilename = multi.getOriginalFileName("tfilename");

			HashMap<String, Object> map = new HashMap<String, Object>();
			if (tfilename != null) {

				HashMap<String, Object> dto = dao.detail(tno);
				System.out.println("기존 파일 명 : " + dto.get("tfilename"));
				File deleteFile = new File(realPath + dto.get("tfilename"));
				File deleteThumbFile = new File(thumbnailPath + dto.get("tthumbnail"));
				if (deleteFile.exists()) {
					deleteFile.delete();
					System.out.println(deleteFile + ": 파일 삭제 성공");
				} else {
					System.out.println(deleteFile + ": 파일 삭제 실패");
				}
				if (deleteThumbFile.exists()) {
					deleteThumbFile.delete();
					System.out.println(deleteThumbFile + ": 썸네일 파일 삭제 성공");
				} else {
					System.out.println(deleteThumbFile + ": 썸네일 파일 삭제 실패");
				}

				tfilename = multi.getFilesystemName("tfilename");
				BufferedImage inputImg = ImageIO.read(new File(realPath + tfilename));

				int width = 160;
				int height = 160;

				String[] imgs = { "png", "gif", "jpg", "bmp" };
				for (String format : imgs) {
					BufferedImage outpugImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

					Graphics2D gr2d = outpugImg.createGraphics();
					gr2d.drawImage(inputImg, 0, 0, width, height, null);

					File thumb = new File(thumbnailPath + tfilename);

					if (thumb.exists()) {
						thumb.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(thumb);
					ImageIO.write(outpugImg, format, thumb);
					fos.close();
					map.put("saveFile", tfilename);
					map.put("thumbnail", tfilename);
				}
			} else {
				System.out.println("file = null");
			}
			tfilename = multi.getFilesystemName("tfilename");
			System.out.println("file : " + tfilename);

			map.put("title", title);
			map.put("id", id);
			map.put("content", content);
			map.put("tno", tno);
			// map.put("saveFile", file);
			// map.put("thumbnail", file);

			int result = 0;
			if (tfilename == null) {
				result = dao.update(map);
			} else {
				result = dao.updateFile(map);
			}

			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "ThrillerBoardModify");

			if (result == 1) {
				log.put("etc", "게시글 수정 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./thrillerBoardDetail?tno=" + tno);
			} else {
				log.put("etc", "게시글 수정 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=updateError01");

			}
		}
	}
}