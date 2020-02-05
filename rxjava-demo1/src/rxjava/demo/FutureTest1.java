package rxjava.demo;

import java.util.concurrent.CompletableFuture;

import rxjava.future.TestService;

public class FutureTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CompletableFuture<String> fs=TestService.echo2("test1", 2);
		
		fs.thenAccept(s->{
			System.out.println("echo:"+s);
		});
		

		TestService.echo2("test error", 2)
		    .whenComplete((s,t)->{
		    	if(t!=null)
		    		System.out.println("error:"+t.getMessage());
		    	else
		    		System.out.println("result="+s);
		    });
	}

}
