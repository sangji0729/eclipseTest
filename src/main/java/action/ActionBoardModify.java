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
import action.ActionBoardDAO;
import log.LogDAO;

@WebServlet("/actionBoardModify")
public class ActionBoardModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionBoardModify() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		System.out.println(Util.str2Int2(request.getParameter("ano")) != 0);
		System.out.println(request.getParameter("ano"));
		System.out.println(session.getAttribute("id"));
		int ano = Util.str2Int2(request.getParameter("ano"));
		if (Util.str2Int2(request.getParameter("ano")) != 0 && session.getAttribute("id") != null) {

			ActionBoardDAO dao = ActionBoardDAO.getInstance();
			HashMap<String, Object> map = dao.detail(ano);

			RequestDispatcher rd = request.getRequestDispatcher("./actionBoardModify.jsp");
			request.setAttribute("map", map);
			rd.forward(request, response);
		} else {
			response.sendRedirect("actionBoardDetail?ano=" + ano);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/");
		String realPath = path + "upload/" + File.separator;
		String thumbnailPath = path + "thumbnail/" + File.separator;
		System.out.println("?????? : " + path);
		System.out.println("?????? ?????? : " + realPath);

		HttpSession session = request.getSession();

		int size = 1024 * 1024 * 5;
		MultipartRequest multi = new MultipartRequest(request, realPath, size, "UTF-8", new DefaultFileRenamePolicy());

		System.out.println(multi.getParameter("title"));
		System.out.println(session.getAttribute("id"));
		System.out.println(multi.getParameter("ano"));
		System.out.println(multi.getParameter("content"));
		// System.out.println(multi.getParameter("upFile"));
		System.out.println(multi.getOriginalFileName("afilename"));// ?????? ????????????

		if (session.getAttribute("id") != null && multi.getParameter("ano") != null
				&& Util.str2Int(multi.getParameter("ano")) != 0) {

			String title = multi.getParameter("title");
			String id = (String) session.getAttribute("id");
			String content = multi.getParameter("content");
			int ano = (Util.str2Int(multi.getParameter("ano")));

			ActionBoardDAO dao = ActionBoardDAO.getInstance();

			System.out.println("afilename : " + multi.getOriginalFileName("afilename"));
			String afilename = multi.getOriginalFileName("afilename");

			HashMap<String, Object> map = new HashMap<String, Object>();
			if (afilename != null) {
				// file??? ????????? ??? ????????? ?????????,
				// ???????????? ?????? ????????? ???????????? ??????????????? = dano
				HashMap<String, Object> dto = dao.detail(ano);
				System.out.println("?????? ?????? ??? : " + dto.get("afilename"));
				File deleteFile = new File(realPath + dto.get("afilename"));
				File deleteThumbFile = new File(thumbnailPath + dto.get("afilename"));
				if (deleteFile.exists()) {
					deleteFile.delete();
					System.out.println(deleteFile + ": ?????? ?????? ??????");
				} else {
					System.out.println(deleteFile + ": ?????? ?????? ??????");
				}
				if (deleteThumbFile.exists()) {
					deleteThumbFile.delete();
					System.out.println(deleteThumbFile + ": ????????? ?????? ?????? ??????");
				} else {
					System.out.println(deleteThumbFile + ": ????????? ?????? ?????? ??????");
				}
				// ?????? ?????????, ???????????????
				// ??? file??? ????????? ????????? ???????????????
				afilename = multi.getFilesystemName("afilename");
				BufferedImage inputImg = ImageIO.read(new File(realPath + afilename));

				int width = 160;
				int height = 160;

				String[] imgs = { "png", "gif", "jpg", "bmp" };
				for (String format : imgs) {
					BufferedImage outpugImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

					Graphics2D gr2d = outpugImg.createGraphics();
					gr2d.drawImage(inputImg, 0, 0, width, height, null);

					File thumb = new File(thumbnailPath + afilename);

					if (thumb.exists()) {
						thumb.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(thumb);
					ImageIO.write(outpugImg, format, thumb);
					fos.close();
					map.put("saveFile", afilename);
					map.put("thumbnail", afilename);
				}
			}
			afilename = multi.getFilesystemName("afilename");
			System.out.println("file : " + afilename);
			// ?????????????????? ????????????

			map.put("title", title);
			map.put("id", id);
			map.put("content", content);
			map.put("ano", ano);
			/*
			 * map.put("upFile", file);// ?????? ????????? ?????? ????????? ????????? ??????
			 */

			int result = 0;
			if (afilename == null) {
				result = dao.modify(map);
			} else {
				result = dao.modifyFile(map);
			}

			HashMap<String, Object> log = new HashMap<String, Object>();
			log.put("id", id);
			log.put("target", "ActionBoardModify");

			if (result == 1) {
				log.put("etc", "????????? ?????? ??????");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./actionBoardDetail?ano=" + ano);
			} else {
				log.put("etc", "????????? ?????? ??????");
				log.put("ip", Util.getIP(request));
				LogDAO.insertLog(log);
				response.sendRedirect("./error?code=updateError01");

			}
		}

	}

}
