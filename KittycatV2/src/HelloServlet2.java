import com.zd.javax.servlet.http.HttpServlet;
import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;

public class HelloServlet2 extends HttpServlet{
	
	
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
		
		
	}
	
}
