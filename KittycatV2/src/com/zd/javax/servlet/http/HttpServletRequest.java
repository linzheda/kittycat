package com.zd.javax.servlet.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

	private Map<String,String[]> parameterMap=new HashMap<String,String[]>();
	private String queryString;
	
	public ServletContext getServletContext(){
		return ServletContext.getInstance();
	}
	public String getParameter(String name){
		if(parameterMap.containsKey(name)){
			return parameterMap.get(name)[0];
		}else{
			return null;
		}
	}
	public Map<String,String[]> getParameterMap(){
		return this.parameterMap;
	}
	public Set<String> getParameterName(){
		return this.parameterMap.keySet();
	}



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


			parseParameter(requestInfoString);
		}
	}


	/**
	 * 解析请求的参数
	 * @param requestInfoString
	 */

	private void parseParameter(String requestInfoString) {
		//解析第一行中的参数
		if(this.requestURI.indexOf("?")>=0){
			this.queryString=this.requestURI.substring(this.requestURI.indexOf("?")+1);
			this.requestURI=requestURI.substring(0,this.requestURI.indexOf("?"));
			
			String[] ss=this.queryString.split("&");
			if(ss!=null&&ss.length>0){
				for(String s:ss){
					String[] pairs=s.split("=");
					if(pairs!=null&&pairs.length>0){
						String[] values=new String[1];
						values[0]=pairs[1];
						this.parameterMap.put(pairs[0], values);
					}
				}
			}
		}
		//判断是否为Post请求
		if(this.method.equalsIgnoreCase("post")){
			//取出"\r\n\r\n"后的字符串
			
			String ps=requestInfoString.substring(requestInfoString.indexOf("\r\n\r\n")+4);
			if(ps!=null&&ps.length()>0){
				String[] ss=ps.split("&");
				if(ss!=null&&ss.length>0){
					for(String s:ss){
						String[] pairs=s.split("=");
						if(pairs!=null&&pairs.length>0){
							String[] values=new String[1];
							values[0]=pairs[1];
							this.parameterMap.put(pairs[0], values);
						}
					}
				}
			}
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
	public String getQueryString() {
		return queryString;
	}




}
