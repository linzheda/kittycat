package kittyserver;

import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;

public class StaticProcessor implements Processor{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) {
		response.sendRedirect();
	}

}
