package com.zd.javax.servlet.http;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
	private static ServletContext instance=new ServletContext() ;
	private ServletContext(){
		
	}
	
	public static Map<String,HttpServlet> servletMap=new HashMap<String,HttpServlet>() ;
	
	public Map<String,Object> attributes=new HashMap<String,Object>();
	
	public synchronized static ServletContext getInstance(){
		if(instance==null){
			instance=new ServletContext();
		}
		return instance;
	}
	
	public void removeAttribute(String name){
		if(this.attributes.get(name)!=null){
			this.attributes.remove(name);
		}
		
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(String name,String object) {
		this.attributes.put(name, object);
	}

	public	HttpServlet getServlet(String name){
		return servletMap.get(name);
	}
	public Map<String, HttpServlet> getServlets() {
		return this.servletMap;
	}
	public void putServlet(String name,HttpServlet value){
		this.servletMap.put(name, value);
	}
	
	
}
