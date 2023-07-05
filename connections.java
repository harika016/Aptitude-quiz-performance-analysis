import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class connections {
	public static void main(String args[])
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","harika","harika");
			Statement smt=con.createStatement();
			smt.executeUpdate("create table employee(name varchar2(20),empid number)");
			System.out.println("yes");
			con.close();
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			
		}
	}

}
