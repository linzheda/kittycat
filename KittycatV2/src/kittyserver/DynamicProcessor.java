package kittyserver;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.zd.javax.servlet.http.HttpServlet;
import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;
import com.zd.javax.servlet.http.ServletContext;

public class DynamicProcessor implements Processor{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) {
		//1.取得资源的名字
		String uri=request.getRequestURI();
		//2.取得类名
		String servletName=uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
		
		HttpServlet servlet=null;
		try {
			if(ServletContext.getInstance().getServlets().containsKey(servletName)){
				//判断map中是否存在这个实例，若存在则取出
				servlet=ServletContext.getInstance().getServlet(servletName);
			}else{

				
				//3.动态加载字节码，并让字节码 URLClassLoader->反射
				URL[] url=new URL[1];
				
				url[0]=new URL("file",null,request.getRealPath()+request.getContextPath());
				URLClassLoader loader=new URLClassLoader(url);
				Class c=loader.loadClass(servletName);
				//4.反射运行HelloServlet -》newInstance() ->  调用HelloServlet的构造方法
				Object obj=c.newInstance();
				if(obj instanceof HttpServlet){
					servlet=(HttpServlet) obj;
					//讲对象存到一个map中
					ServletContext.servletMap.put(servletName, servlet);
					//5.servlet生命周期的调用：init() -> service -> doget()/doPost()
					servlet.init();
				}
			}
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			String bodyyentity=e.getMessage()+"<br/>";
			for(StackTraceElement ste: e.getStackTrace()){
				bodyyentity+=ste.toString()+"<br/>";
			}
			String protocal=gen500(bodyyentity.length());
			PrintWriter out=response.getOut();
			out.println(protocal);
			out.println(bodyyentity);
			out.flush();
			out.close();
		}
		
	}
	
	
	private String gen500(int length) {
		StringBuffer sb=new StringBuffer();
		sb.append("HTTP/1.1 500 Not Found\r\n");
		sb.append("Server: hengyang yuancheng info Limited Kitty server\r\n");
		sb.append("Content-Type: text/html;charset=UTF-8\r\n");
		sb.append("Content-Language: en\r\n");
		sb.append("Content-Length: "+length+"\r\n");
		sb.append("\r\n");
		return sb.toString();
	}

}
