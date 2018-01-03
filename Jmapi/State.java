
import java.sql.*;
public class State {

Statement stmt;
Connection con;
String url;

    public State() {
    }
    public Statement getStatement()
    {
    	try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url="jdbc:mysql://localhost:3306/mail?user=root&password=root";
			con=DriverManager.getConnection(url);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
		}
		catch(SQLException e)
		{}
		return stmt;
    }
    
}