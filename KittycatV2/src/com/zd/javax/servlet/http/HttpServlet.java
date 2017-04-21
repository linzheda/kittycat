package com.zd.javax.servlet.http;

public abstract class HttpServlet {
	public  void init(){};
	public void service(HttpServletRequest req,HttpServletResponse resp){
		String method=req.getMethod();
		if("GET".equals(method)){
			doGet(req,resp);
		}else if("POST".equals(method)){
			doPost(req,resp);
		}else if("HEAD".equals(method)){
			doHead(req,resp);
		}


	}

	public  void doPost(HttpServletRequest req, HttpServletResponse resp){};
	public abstract void doGet(HttpServletRequest req, HttpServletResponse resp);
	public  void doHead(HttpServletRequest req, HttpServletResponse resp){}


}
