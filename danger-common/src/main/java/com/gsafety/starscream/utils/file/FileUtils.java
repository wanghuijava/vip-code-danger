package com.gsafety.starscream.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class FileUtils {

	
	/**
	 * 下载文件
	 * 
	 * @param response
	 *            响应请求
	 * @param fileName
	 *            文件名
	 * @param filePath
	 *            文件地址
	 * @return
	 */
	public static String downloadFile(HttpServletResponse response, String fileName,
			String filePath) {
		// 下载附件
		// 设置编码格式
		response.setCharacterEncoding("utf-8");
		OutputStream os = null;
		try {
			response.setContentType("text/html");
			if (fileName != null) {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode(
										URLDecoder.decode(fileName, "utf-8"),
										"utf-8"));
			}
			os = response.getOutputStream();

			FileInputStream fis = new FileInputStream(new File(filePath));
			byte[] b = new byte[2048];
			while ((fis.read(b)) != -1) {
				os.write(b);
			}
			fis.close();

		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e) {
				return null;
			}
		}

		return null;
	}
}
