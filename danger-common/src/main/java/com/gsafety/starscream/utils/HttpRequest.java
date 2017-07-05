package com.gsafety.starscream.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;

/**
 * 用于请求网络url
 *  @author Zzj
 * @date 2015-10-14 上午11:11:31
 * @history V1.0
 */
public class HttpRequest {
	
	
	 /**
	  * @param url  发送请求的 URL
      * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
      * @return 所代表远程资源的响应结果
	  */
	 public static String sendGet(String url, String param,boolean switchOnOff) {
		 if(!switchOnOff) return "";
		return sendRequest(url, param, "GET");
	 }
	 /**
	  * @param url  发送请求的 URL
      * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
      * @return 所代表远程资源的响应结果
	  */
	 public static String sendPost(String url, String param,boolean switchOnOff) {
		 if(!switchOnOff) return "";
		 return sendRequest(url, param, "POST");
	 }
	 
	 /**
	  * @param url  发送请求的 URL
      * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
      * @param method 请求方式 GET,POST
      * @return 所代表远程资源的响应结果
	  */
	 private static String sendRequest(String url, String param,String method) {
		 method = StringUtils.isEmpty(method)?"GET":method.toUpperCase();
		 BufferedReader in = null;
		 PrintWriter out = null;
		 String result = "";
		 try {
			 URL realUrl = new URL(url);
			 URLConnection connection = realUrl.openConnection();
			 System.out.println(url+"?"+param);
			 // 设置通用的请求属性
			 connection.setRequestProperty("accept", "*/*");
			 connection.setRequestProperty("connection", "Keep-Alive");
			 // 设置 HttpURLConnection的字符编码
			 connection.setRequestProperty("Accept-Charset", "UTF-8");
//			 connection.setRequestProperty("Accept-Charset", "GBK");
			 connection.setRequestProperty("user-agent",
					 "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			 // 发送POST请求必须设置如下两行
			 if(method.equals("POST")){
				 connection.setDoOutput(true);
				 connection.setDoInput(true);
			 }
			 // 获取URLConnection对象对应的输出流
			 out = new PrintWriter(connection.getOutputStream());
			 // 发送请求参数
			 out.print(param);
			 // flush输出流的缓冲
			 out.flush();
			 
			 in = new BufferedReader(new InputStreamReader(
					 connection.getInputStream()));
			 String line;
			 while ((line = in.readLine()) != null) {
				 result += line;
			 }
		 } catch (MalformedURLException ex) {
			 ex.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }finally{
//			 try {
//				 if(out!=null){
//					 out.close();
//				 }
//				 if(in != null){
//					 in.close();
//				 }
//			 } catch (IOException e) {
//				 e.printStackTrace();
//			 }
		 }
		 return result;
	 }

}

