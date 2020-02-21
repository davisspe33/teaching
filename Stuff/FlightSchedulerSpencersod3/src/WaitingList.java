
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WaitingList {
    
    private static String flight;
    private static Date date;
    private static String customer;
    private static Timestamp timestamp;
    private static PreparedStatement addToWaitList;
    private static PreparedStatement getStatus;
    

    public static String statusDay(String f, String day){
        
        ResultSet resultSet = null;
        String results = "";
        //flight = ? and
        try
        {
            Connection connection = DBConnection.getConnection();
            getStatus = connection.prepareStatement(
                    "SELECT Customer, Flight FROM Waitinglist WHERE day = ? order by timestamp ASC");
            //getStatus.setString(1, flight);
            java.sql.Date sqlDate = java.sql.Date.valueOf(day);
            getStatus.setDate(1, sqlDate);
            resultSet = getStatus.executeQuery();
            if (resultSet.next()){
                flight= resultSet.getString("Flight");
                customer = resultSet.getString("Customer");
                results = results.concat(customer+" on flight: "+flight + "\n");
                while (resultSet.next()){
                    results = results.concat(resultSet.getString("Customer")+" on flight: "+ resultSet.getString("Flight")+ "\n");
                }
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
        
    }
    
    public static String addWaitList(String flight, String day, String customerName){
        java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        String returnMessage = "";
        try 
        {
            Connection connection = DBConnection.getConnection();
            addToWaitList = connection.prepareStatement("insert into waitinglist (flight, day, customer, timestamp) values (?, ?, ?, ?)");
            addToWaitList.setString(1, flight);
            addToWaitList.setString(2, day);
            addToWaitList.setString(3, customerName);
            addToWaitList.setTimestamp(4, timestamp);
            addToWaitList.executeUpdate();
            returnMessage = customerName + " added to waitlist for flight " + flight + " on " + day;
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Customer not successfully added to WaitList database";
            sqlException.printStackTrace();
        }
        return returnMessage;
    }
}
