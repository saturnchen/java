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
public class FlowTest<T> {	
	
	public static void main(String[] args)
	{
		FlowableHolder1<Integer>  flows=new FlowableHolder1<Integer>();		
		//消费者线程1
		new Thread(()-> {
			System.out.println("current threadname:"+Thread.currentThread().getName());
				    Disposable handle=flows.getSubscriber().subscribe(t->{
				    	System.out.println(Thread.currentThread().getName()+":"+t);
				    	Utils.sleep(1000);
				    });

		}).start();
		
		//消费者线程2
		new Thread(()-> {	
			System.out.println("current threadname:"+Thread.currentThread().getName());
			flows.getSubscriber()//.subscribeOn(Schedulers.computation())
			   .subscribe(t->{
		    	System.out.println(Thread.currentThread().getName()+":"+t);
		    	Utils.sleep(1000);
		    });
	
		}).start();
	
		
		//生产者线程，发布多个数据
		new Thread(()-> {
			Thread.currentThread().setName("publisher1");
			System.out.println(Thread.currentThread().getName()+"启动.....");
			for(int i=1;i<20;i++) {
			   flows.publish(i);
			   Utils.sleep(5);
			}
			System.out.println(Thread.currentThread().getName()+"   finished.");
			Utils.sleep(10*1000);//等待20秒
		}).start();
		//等待其他线程结束
		Utils.sleep(10*1000);
	}
	
}
