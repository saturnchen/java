package rxjava.demo;

import java.util.Timer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import rxjava.Utils;
import rxjava.flow.FlowableHolder1;


//订阅者再收到消息时，处理消息时必须捕获所有异常，不然就再也收不到后续消息。
public class FlowWithDispose<T> {	
	
	public static void main(String[] args)
	{
		FlowableHolder1<Integer>  flows=new FlowableHolder1<Integer>();

		//FlowableHolder<Integer>  flows=new FlowableHolder<Integer>();
		
		//消费者线程1
		new Thread(()-> {
			        int count[]=new int[]{0};
				    Disposable handle=flows.getSubscriber()
				    		.subscribeOn(Schedulers.io())
				    		.subscribe(t->{
				    	System.out.println(Thread.currentThread().getName()+":"+t);
				    	count[0]++;
				    	Utils.sleep(1000);
				    });
				    
				    System.out.println(Thread.currentThread().getName()+":"+"wait 5 count to end  订阅");//消费5条消息后取消订阅
				    while(count[0]<5) {				        
				    	Utils.sleep(100);
				    }
				    handle.dispose();  //取消订阅
		}).start();
		
		//消费者线程2
		new Thread(()-> {			
			flows.getSubscriber()
			    //.subscribeOn(Schedulers.io())
			    .subscribe(t->{
		    	  System.out.println(Thread.currentThread().getName()+":"+t);
		    	  Utils.sleep(1000);
		    });
	
		}).start();
	
		//生产者线程，发布多个数据
		new Thread(()-> {
			Thread.currentThread().setName("publisher1");
			System.out.println(Thread.currentThread().getName()+"启动.....");
			for(int i=0;i<20;i++) {
			   flows.publish(i);
			   Utils.sleep(10);
			}
			System.out.println(Thread.currentThread().getName()+" 发布数据finished.");
			Utils.sleep(20*1000);//等待20秒
		}).start();
		//等待其他线程结束
		Utils.sleep(10*1000);
	}
	
}
