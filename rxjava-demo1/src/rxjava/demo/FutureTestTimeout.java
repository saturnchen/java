package rxjava.demo;

import java.util.concurrent.CompletableFuture;

import rxjava.future.TestService;

//测试超时异常是否会抛出
public class FutureTestTimeout {

	public static void main(String[] args) {
		//运行10秒， 5秒超时
		CompletableFuture<String> fs1=TestService.echo_timeout("test2", 10,5000);
		System.out.println("timeout in 5 secs");
		fs1.whenComplete((s,e)->{
			if(e!=null)
				e.printStackTrace();
			else
			   System.out.println("echo2:"+s);
		});

		//运行3秒， 5秒超时
		CompletableFuture<String> fs2=TestService.echo_timeout("test3", 3,5000);
		System.out.println("这次调用不会超时");
		fs2.whenComplete((s,e)->{
			if(e!=null)
				e.printStackTrace();
			else
			   System.out.println("echo3:"+s);
		});		
	}

}
