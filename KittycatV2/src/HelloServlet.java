import java.io.PrintWriter;
import java.util.Date;

import com.zd.javax.servlet.http.HttpServlet;
import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet{
	
	
	@Override
	public void init() {
		super.init();
		System.out.println("初始化.....");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("doPost.....");
		
		
		
		
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("doGet.....");
		String content="欢迎您：当前是"+(new Date().toString());
		
		StringBuffer sb=new StringBuffer();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Content-Type: text/html;charset=UTF-8");
		sb.append("Content-Length: "+content.getBytes().length+"\r\n\r\n");
		PrintWriter out=resp.getOut();
		out.println(sb.toString());
		out.print(content);
		out.flush();
		out.close();
		
	}
	
}
