package rxjava.future;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class TestService {
	public static CompletableFuture<String>  echo2(String s,int sleepsecs)
	{
		CompletableFuture<String> fe=new CompletableFuture<String>();
	    try {
			Thread.sleep(sleepsecs*1000);
			if(s.contains("error"))
				throw new Exception("has error!");
			fe.complete(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fe.completeExceptionally(e);//设置结果为异常
		}	
	     
	   return fe;
	}
	
	//测试超时
	public static CompletableFuture<String>  echo_timeout(String s,int sleepsecs,int timeout)
	{
		CompletableFuture<String> fe=new CompletableFuture<String>();
	    try {
	    	new Timer().schedule(new TimerTask() {//启动超时启动机制
				@Override
				public void run() {
					//设置返回超时
					boolean r=fe.completeExceptionally(new RuntimeException("request timeout:"+timeout));
					if(r==true)
						System.out.println("service:has timeout!");
				}	    		
	    	}, timeout);
	    	
			Thread.sleep(sleepsecs*1000);//模拟真正服务的执行时间
			
			fe.complete(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	     
	   return fe;
	}
	
	
}
