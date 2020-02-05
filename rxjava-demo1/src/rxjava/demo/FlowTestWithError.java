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
public class FlowTestWithError<T> {	
	
	public static void main(String[] args)
	{
		FlowableHolder1<Integer>  flows=new FlowableHolder1<Integer>();
		//生产者线程，发布多个数据
		new Thread(()-> {
			Thread.currentThread().setName("publisher1");
			System.out.println(Thread.currentThread().getName()+"启动.....");
			for(int i=0;i<20;i++) {
			   flows.publish(i);
			}
			System.out.println(Thread.currentThread().getName()+"finished.");
		}).start();
		//FlowableHolder<Integer>  flows=new FlowableHolder<Integer>();
		
		
		//消费者线程2
		new Thread(()-> {			
			flows.getSubscriber().subscribe(t->{
		    	System.out.println(Thread.currentThread().getName()+":"+t);
		    	if(t.intValue()%5==2)//可以看到，订阅消息的处理代码不能抛出异常，否则就收不到后续消息了。
	    		   throw new RuntimeException("模拟出错！，看是否可以继续订阅");
		    });
	
		}).start();

		//消费者线程3
		new Thread(()-> {			
			flows.getSubscriber().subscribeOn(Schedulers.newThread())
			   .subscribe(t->{
		    	System.out.println(Thread.currentThread().getName()+":"+t);
		    });
	
		}).start();
	
		//等待其他线程结束
		Utils.sleep(10*1000);
	}
	
}
