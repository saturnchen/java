package chapter2;
import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class QueryDemo1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Class.forName("com.mysql.jdbc.Driver");
		
		String jdbcurl="jdbc:mysql://localhost:3318/bookstore";
		
		Database db = Database.from(new ConnectionProviderFromUrl(jdbcurl,
				"root","root"));
		Observable<String> result = db
			    .select("select name from users where id >= :min and id <=:max")
			    .parameter("min", 1)
			    .parameter("max", 6)
			    .getAs(String.class);
		
		result.subscribe(name->{
			System.out.println(name);
		});
		
		
		Thread.currentThread().sleep(1000*10);
		
	}
	

}
