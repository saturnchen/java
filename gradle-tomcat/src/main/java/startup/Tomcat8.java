package startup;

public class Tomcat8 {

	public static void main(String[] args) throws Exception
	{
		 new tomcat8.TomcatServer().start("/demo",  //context 即主机端口后面的路径，http://localhost:8080/demo 中的demo.
				 "./src/main/webapp",   //webapp所在路径
				 8080   //web访问端口
				 );
	}
}
