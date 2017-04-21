package kittyserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;
import com.zd.kittyserver.util.YcConstants;

public class TaskService  implements Runnable{
	private Socket s;
	private InputStream iis;
	private OutputStream oos;
	private boolean flag;
	
	
	public TaskService(Socket s) {
		this.s=s;
		try {
			this.iis=this.s.getInputStream();
			this.oos=this.s.getOutputStream();
			flag=true;
		} catch (IOException e) {
			e.printStackTrace();
			YcConstants.logger.error(e.getMessage());
			flag=false;
		}
	}

	@Override
	public void run() {
		///Http协议：基于请求，响应的超文本传输协议-》短连接
		
		try {
			if(flag){
				HttpServletRequest request=new HttpServletRequest(this.iis);
				HttpServletResponse response=new HttpServletResponse(request,this.oos);
				response.sendRedirect();
				
			}
			
			this.s.close();
		} catch (IOException e) {
			e.printStackTrace();
			YcConstants.logger.error("failed to close connection to client. the clause as following:"+ e.getMessage());
		}
		
		
		
	}

}
