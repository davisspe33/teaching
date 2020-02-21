
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class Day {
    
    private static PreparedStatement getDays;
    private static java.sql.Date sqlDate;
    private static PreparedStatement addDate;

    
    
    
    public static ArrayList<String> getDayList(){
        
        ArrayList<String> results = new ArrayList<String>();
        ResultSet resultSet = null;
        String columnName = "Date";
        
        try
        {
            Connection connection = DBConnection.getConnection();
            getDays = connection.prepareStatement(
                    "SELECT * FROM Day");
            resultSet = getDays.executeQuery();
            if (resultSet.next()){
                Date first = resultSet.getDate(columnName);
                String strVers = first.toString();
                results.add(strVers);

                while (resultSet.next()){
                    results.add(resultSet.getDate(columnName).toString());
                }
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                   sqlException.printStackTrace();
            }
        }
        return results;
    }
    
    public static String addDate(String date){
        String returnMessage = "";
        try 
        {
            Connection connection = DBConnection.getConnection();
            addDate = connection.prepareStatement(
                    "INSERT INTO Day" +
                    "(Date)" +
                    "VALUES(?)");
            
            sqlDate = java.sql.Date.valueOf(date);
            addDate.setDate(1, sqlDate);
            
            addDate.executeUpdate();
            returnMessage = "Successfully added " + date + " to the Day Database";
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Date was not successfully added to database";
            sqlException.printStackTrace();
        }
        
        return returnMessage;
    }
        
    
}
