package rxjava.flow;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.SerializedSubscriber;


//订阅者再收到消息时，处理消息时必须捕获所有异常，不然就再也收不到后续消息。
public class FlowableHolder1<T> {
	private FlowableProcessor<T> mBus;
	private Object lock=new Object();
	public FlowableHolder1()
	{
		//this.mBus=PublishProcessor.<T>create();//.toSerialized();
		this.mBus=ReplayProcessor.<T>create();//.toSerialized();	
	}
	
	public Flowable<T> getSubscriber()
	{
		return this.mBus;//.subscribeOn(Schedulers.newThread());
	}

	public void publish(T data)
	{
//		java.util.concurrent.CompletableFuture.runAsync(()->{
//			try {
//				mBus.onNext(data);
//			}catch(Throwable t) {
//				System.out.println("发布消息error!onNext(data)");
//				t.printStackTrace();
//			}
//		});
		//new SerializedSubscriber<>(mBus).onNext(data);
		synchronized(lock) {
		   mBus.onNext(data);
		}
	}
	
	public static void main(String[] args)
	{
		FlowableHolder1<Integer>  flows=new FlowableHolder1<Integer>();
		
		
		new Thread(()-> {
			for(int i=0;i<100;i++) {
			   flows.publish(i);
			   try {
				Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		//FlowableHolder<Integer>  flows=new FlowableHolder<Integer>();
		
		new Thread(()-> {
				    Disposable handle=flows.getSubscriber().subscribe(t->{
				    	System.out.println(Thread.currentThread().getName()+":"+t);
				    });
				    
				    System.out.println("wait 25 second to end  订阅");
				    try {				    	
						Thread.sleep(25000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    //结束订阅
				    handle.dispose();
			
		}).start();
		
		
	}
	
}
