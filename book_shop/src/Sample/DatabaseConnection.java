package Sample;
import java.sql.*;
public class DatabaseConnection {
	 public Connection connection;
	 public Connection getConnection() {
		 String databaseName="project_data";
		 String userName="root";
		 String password="123456";
		 String url="jdbc:mysql://localhost/"+databaseName;
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 connection = DriverManager.getConnection(url, userName, password);
		 }
		 catch(Exception e){
			 System.out.println(e);
			 }
		 return connection;
	 }
}
