package rxjava;

public class Utils {

	public static void sleep(long milsecs)
	{
		   try {
			  Thread.sleep(milsecs);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		   
	}
}
