package com.zd.javax.servlet.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedWriter;
import java.io.PrintWriter;

public class HttpServletResponse {
	
	private HttpServletRequest request;
	private OutputStream oos;
	public PrintWriter out;
	
	public HttpServletResponse(HttpServletRequest request, OutputStream oos) {
		this.request = request;
		this.oos = oos;
		this.out=new PrintWriter(this.oos);
	}
	
	
	public PrintWriter getOut(){
		return this.out;
	}

	public void sendRedirect() {
		//以流的方式读取
		String uri=request.getRequestURI();
		File f=new File(request.getRealPath()+uri);
		
		String responseProtocol=null;
		byte[] fileContent=null;
		if(f.exists()){
			fileContent=readFile(f);
			responseProtocol=gen200(fileContent.length);
		
		}else{
			f=new File(request.getRealPath()+"404.html");
			fileContent=readFile(f);
			responseProtocol=gen404( fileContent.length);
		}
		try {
			this.oos.write(responseProtocol.getBytes());
			this.oos.write(fileContent);
			this.oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				this.oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}


	private String gen200(int length) {
		StringBuffer sb=new StringBuffer();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Server: hengyang yuancheng info Limited Kitty server\r\n");
		sb.append("Content-Language: en\r\n");
		sb.append("Content-Length: "+length+"\r\n");
		
		//判断文件类型
		String uri=this.request.getRequestURI();
		String extensionName=uri.substring( uri.lastIndexOf(".") +1);
		//写成xml解析（web.xml）
		if("jpg".equalsIgnoreCase(extensionName)||"jpeg".equalsIgnoreCase(extensionName)){
			sb.append("Content-Type: image/jpeg");
		}else if("png".equalsIgnoreCase(extensionName)){
			sb.append("Content-Type: image/png");
		}else if("json".equalsIgnoreCase(extensionName)){
			sb.append("Content-Type: application/json");
		}else{
			sb.append("Content-Type: text/html;charset=UTF-8");
		}
		sb.append("\r\n\r\n");
		return sb.toString();
	}


	private String gen404(int length) {
		StringBuffer sb=new StringBuffer();
		sb.append("HTTP/1.1 404 Not Found\r\n");
		sb.append("Server: hengyang yuancheng info Limited Kitty server\r\n");
		sb.append("Content-Type: text/html;charset=UTF-8\r\n");
		sb.append("Content-Language: en\r\n");
		sb.append("Content-Length: "+length+"\r\n");
		sb.append("\r\n");
		return sb.toString();
	}


	private byte[] readFile(File f) {
		FileInputStream fis=null;
		ByteArrayOutputStream boos=new ByteArrayOutputStream();
		
		try {
			fis=new FileInputStream(f);
			byte[] bs=new byte[1024];
			int length=0;
			while((length=fis.read(bs,0,bs.length))!=-1){
				boos.write(bs,0,length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		return boos.toByteArray();
	}
	

	
	
}
