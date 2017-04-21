package com.zd.javax.servlet.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
 * 从输入流中取出请求头协议，解析，封装
 * @author linzd
 *
 */
public class HttpServletRequest {
	private InputStream iis;
	
	private String method;//请求方法  GET  POST  HEAD  DELETE  PUT
	private String protecol;//版本信息
	private int serverPort;
	private String serverName;
	private String requestURI;//资源地址
	private String requestURL;//全路径
	private String contextPath;
	
	private String realPath=System.getProperty("user.dir")+File.separator+"webapps"+File.separator;
	
	
	public HttpServletRequest(InputStream iis) throws IOException{
		this.iis=iis;
		parseRequest();
	}
	
	
	/**
	 * 解析请求操作
	 * @throws IOException 
	 */
	private void parseRequest() throws IOException {
		String requestInfoString=readFromInputStream();
		
		if(requestInfoString==null||requestInfoString.equals("")){
			return;
		}
		parseRequestInfoString(requestInfoString);
		
	}

	
	/**
	 * 将字符串中的数据jie取出来，存到各个属性中
	 * @return
	 * @throws IOException 
	 */
	
	private void parseRequestInfoString(String requestInfoString) {
		StringTokenizer st=new StringTokenizer(requestInfoString);//字符串分割类
		if(st.hasMoreElements()){
			this.method=st.nextToken();//GET
			this.requestURI=st.nextToken();//"/"
			this.protecol=st.nextToken();//"HTTP/1.1"
		}
	}




	/**
	 * 从流中取出byte 转化协议字符串
	 * @return
	 * @throws IOException
	 */
	
	private String readFromInputStream() throws IOException {
		StringBuffer sb=new StringBuffer();
		byte [] bs=new byte[100*1024];
		int length=0;
		length=this.iis.read(bs);
		//FIXME　　如果是文件上传？？？？
		for(int i=0;i<length;i++){
			sb.append((char)bs[i]);
		}
		return sb.toString();
	}






	public String getMethod() {
		return method;
	}

	public String getProtecol() {
		return protecol;
	}
	public String getContextPath() {
		return contextPath;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	public String getServerName() {
		return serverName;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getRequestURL() {
		return requestURL;
	}

	public String getRealPath() {
		return realPath;
	}
	
	
	

}
