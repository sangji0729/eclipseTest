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

@WebServlet("/romanceBoardModify")
public class RomanceBoardModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RomanceBoardModify() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println(Util.str2Int2(request.getParameter("rno")) != 0);
		System.out.println(request.getParameter("rno"));
		System.out.println(session.getAttribute("id"));
		int rno = Util.str2Int2(request.getParameter("rno"));

		if (Util.str2Int2(request.getParameter("rno")) != 0 && session.getAttribute("id") != null) {

			RomanceDAO dao = RomanceDAO.getInstance();
			HashMap<String, Object> map = dao.detail(rno);

			RequestDispatcher rd = request.getRequestDispatcher("./romanceBoardModify.jsp");
			request.setAttribute("map", map);
			rd.forward(request, response);

		} else {
			response.sendRedirect("romanceBoardDetail?rno=" + rno);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = request.getSession().getServletContext().getRealPath("/");
		String realPath = path + "upload/" + File.separator;
		String thumbnailPath = path + "thumbnail/" + File.separator;
		System.out.println("경로 : " + path);
		System.out.println("진짜 경로 : " + realPath);

		HttpSession session = request.getSession();

		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath, size, "UTF-8", new DefaultFileRenamePolicy());

		System.out.println(multi.getParameter("title"));
		System.out.println(session.getAttribute("id"));
		System.out.println(multi.getParameter("rno"));
		System.out.println(multi.getParameter("content"));
		// System.out.println(multi.getParameter("upFile"));
		System.out.println(multi.getOriginalFileName("rfilename"));

		if (session.getAttribute("id") != null && multi.getParameter("rno") != null
				&& Util.str2Int(multi.getParameter("rno")) != 0) {

			String title = multi.getParameter("title");
			String id = (String) session.getAttribute("id");
			String content = multi.getParameter("content");
			int rno = (Util.str2Int(multi.getParameter("rno")));

			RomanceDAO dao = RomanceDAO.getInstance();

			System.out.println("rfilename : " + multi.getOriginalFileName("rfilename"));
			String rfilename = multi.getOriginalFileName("rfilename");

			HashMap<String, Object> map = new HashMap<String, Object>();
			if (rfilename != null) {
				HashMap<String, Object> dto = dao.detail(rno);
				System.out.println("기존 파일 명 : " + dto.get("rfilename"));

				File deleteFile = new File(realPath + dto.get("rfilename"));
				File deleteThumbFile = new File(thumbnailPath + dto.get("rfilename"));

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

				rfilename = multi.getFilesystemName("rfilename");
				BufferedImage inputImg = ImageIO.read(new File(realPath + rfilename));

				int width = 160;
				int height = 160;

				String[] imgs = { "png", "gif", "jpg", "bmp" };

				for (String format : imgs) {
					BufferedImage outpugImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

					Graphics2D gr2d = outpugImg.createGraphics();
					gr2d.drawImage(inputImg, 0, 0, width, height, null);

					File thumb = new File(thumbnailPath + rfilename);

					if (thumb.exists()) {
						thumb.mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(thumb);
					ImageIO.write(outpugImg, format, thumb);
					fos.close();
					map.put("saveFile", rfilename);
					map.put("thumbnail", rfilename);
				}
			}

			rfilename = multi.getFilesystemName("rfilename");
			System.out.println("file : " + rfilename);

			map.put("title", title);
			map.put("id", id);
			map.put("content", content);
			map.put("rno", rno);

			int result = 0;

			if (rfilename == null) {
				result = dao.modify(map);

			} else {
				result = dao.modifyFile(map);
			}

			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "RomanceBoardModify");

			if (result == 1) {
				log.put("etc", "게시글 수정 성공");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./romanceBoardDetail?rno=" + rno);

			} else {
				log.put("etc", "게시글 수정 실패");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=updateError01");
			}
		}
	}
}