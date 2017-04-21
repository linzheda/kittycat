package kittyserver;

import com.zd.javax.servlet.http.HttpServletRequest;
import com.zd.javax.servlet.http.HttpServletResponse;

/**
 * 处理请求并作出响应
 * @author linzd
 *
 */
public interface Processor {
	public void process(HttpServletRequest request,HttpServletResponse response);
}
