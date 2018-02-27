package com.admin.util;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 下载文件 创建人：FH 创建时间：2014年12月23日
 * @version
 */
public class FileDownload {

	/**
	 * @param response
	 * @param filePath //文件完整路径(包括文件名和扩展名)
	 * @param fileName //下载后看到的文件名
	 * @return 文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception {

		byte[] data = FileUtil.toByteArray2(filePath);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();

	}
	public static void fileURLDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		int connectTimeout = 30 * 1000; // 连接超时:30s
		int readTimeout = 1 * 1000 * 1000; // IO超时:1min
		byte[]  buffer = new byte[8 * 1024]; // IO缓冲区:8KB
		String fileNameUrl = fileName;
		fileNameUrl = new String(URLEncoder.encode(fileNameUrl,"utf-8").getBytes());
		filePath = filePath.substring(0,filePath.lastIndexOf("/"))+"/"+fileNameUrl;

		URL url = new URL(filePath);
		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		conn.connect();

		in = conn.getInputStream();

		byte[] data = FileUtil.input2byte(in);
		fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", ""+data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
	}

}
