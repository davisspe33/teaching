
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBSpencersod3";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    public static Connection connection;

    public static Connection getConnection(){
        
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            // System.exit(1);
        }
        return connection;
    }
}
