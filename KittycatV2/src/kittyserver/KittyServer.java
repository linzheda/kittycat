package kittyserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.zd.kittyserver.util.YcConstants;
import com.zd.utils.threadpool.ThreadPoolManager;

public class KittyServer {
	public static void main(String[] args) throws Exception {
		ThreadPoolManager tpm=new ThreadPoolManager(10);//线程池
		//解析server.xml协议，读取配置启动端口
		int port=paseServer();
		YcConstants.logger.info("System is running ,and listening port is "+port);
		
		ServerSocket ss=new ServerSocket(port);
		while(true){
			Socket s=ss.accept();
			YcConstants.logger.info(s.getInetAddress()+" is connecting to this server.");
			
			TaskService ts=new TaskService(s);
			if(tpm!=null){
				tpm.process(ts);
			}else{
				Thread t=new Thread(ts);
				t.start();
			}
			
		}
		
		
	}
	/**
	 * xml解析
	 * @return
	 * @throws Exception
	 */
	private static int paseServer() throws Exception{//解析启动端口
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		
		File f=new File(System.getProperty("user.dir")+File.separator+"conf"+File.separator+"server.xml");
		Document doc=builder.parse(new FileInputStream(f));
		
		NodeList nodelist=doc.getElementsByTagName("Connector");
		for(int i=0;i<nodelist.getLength();i++){
			Element node=(Element) nodelist.item(i);
			return Integer.parseInt(node.getAttribute("port"));
		}
		return 80;
	}
	
	

}
