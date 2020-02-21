
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer {
    
    private static PreparedStatement selectAllCust;
    private static PreparedStatement addNewCust;
    private static PreparedStatement findIfBooked;
    private static PreparedStatement findIfWaitListed;
    private static PreparedStatement removeBooking;
    private static PreparedStatement checkWaitList;
    private static PreparedStatement getFlightName;
    private static PreparedStatement statusDay;
    private static PreparedStatement statusDayWait;
    private static java.sql.Date sqlDate;

    
    public static String addCustomer(String name){
        String returnMessage = "";
        
        try 
        {
            Connection connection = DBConnection.getConnection();
            addNewCust = connection.prepareStatement(
                    "INSERT INTO Customer" +
                    "(Name)" +
                    "VALUES(?)");
            
            addNewCust.setString(1, name);
            
            addNewCust.executeUpdate();
            returnMessage = "Successfully added " + name + " to the Customer Database";
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Customer was not successfully added to database";
            sqlException.printStackTrace();
        }
        
        return returnMessage;
    }
    
    public static ArrayList<String> getCustomerList(){
        ArrayList<String> results = new ArrayList<String>();
        ResultSet resultSet = null;
        try
        {
            Connection connection = DBConnection.getConnection();
            selectAllCust = connection.prepareStatement(
                    "SELECT * FROM Customer");
            resultSet = selectAllCust.executeQuery();
            ResultSetMetaData metadata = resultSet.getMetaData();
            String columnName = metadata.getColumnName(1);  // This prints out the first column name.
            if (resultSet.next()){
                String row = resultSet.getString("Name");
                results.add(row);
                while (resultSet.next()){
                    results.add(resultSet.getString("Name"));
                }
            }
           
            
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }
    
    public static String checkWaitList(String day, String flight){
        ResultSet resultSet = null;
        String returnMessage = "";
        String moveToBooking = "";
        try 
        {
            Connection connection = DBConnection.getConnection();
            checkWaitList = connection.prepareStatement(
                    "SELECT * FROM WaitingList where day = ? and flight = ? order by timestamp asc");
            
            sqlDate = java.sql.Date.valueOf(day);
            checkWaitList.setDate(1, sqlDate);
            checkWaitList.setString(2, flight);
            resultSet = checkWaitList.executeQuery();
            if (resultSet.next()){
                moveToBooking = resultSet.getString("Customer");
                Booking.addBooking(flight, day, moveToBooking);
                removeFromWaitList(moveToBooking, day);
                returnMessage = "Customer " + moveToBooking + " was moved from waiting list to booked for flight " + flight + " on " + day;
            }
            else
                returnMessage = "";
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Error checking Waiting List for flight " + flight + " on " + day;
            sqlException.printStackTrace();
        }
        return returnMessage;
    }
    
    public static String statusDay(String customer){
        String returnMessage = "";
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        boolean sameCust = false;
        
        try 
        {
            Connection connection = DBConnection.getConnection();
            statusDay = connection.prepareStatement(
                    "select * from booking where customer = ?");
            
            statusDay.setString(1, customer);
            resultSet = statusDay.executeQuery();
            if (resultSet.next()){
                sameCust = true;
                String flight = resultSet.getString("Flight");
                Date date = resultSet.getDate("Day");
                returnMessage = returnMessage + customer + " booked for flight " + flight + " on " + date + "\n";
                while(resultSet.next()){
                    flight = resultSet.getString("Flight");
                    date = resultSet.getDate("Day");
                    returnMessage = returnMessage + "     " + "booked for flight "+ flight + " on " + date + "\n";
                }
            }
            
            statusDayWait = connection.prepareStatement(
                    "select * from waitinglist where customer = ?");
            
            statusDayWait.setString(1, customer);
            resultSet2 = statusDayWait.executeQuery();
            if (resultSet2.next()){
                String waitFlight = resultSet2.getString("Flight");
                Date waitDate = resultSet2.getDate("Day");
                if (sameCust)
                    returnMessage = returnMessage + "     " + "waitlisted for flight " + waitFlight + " on " + waitDate + "\n";
                else
                    returnMessage = returnMessage + customer + " waitlisted for flight " + waitFlight + " on " + waitDate + "\n";
                
                while(resultSet2.next()){
                    waitFlight = resultSet2.getString("Flight");
                    waitDate = resultSet2.getDate("Day");
                    returnMessage = returnMessage + "     " + "waitlisted for flight " + waitFlight + " on " + waitDate + "\n";
                }
            }
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Customer status check not successful";
            sqlException.printStackTrace();
        }
        
        return returnMessage;
    }
    
    
    public static String cancelDay(String customer, String day){
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        String returnMessage = "";
        String flightName = "";
        boolean isBooked = false;
        boolean isWaitListed = false;
        
        try 
        {
            Connection connection = DBConnection.getConnection();
            findIfBooked = connection.prepareStatement(
                    "SELECT * FROM booking where customer = ? and day = ?");
            
            findIfBooked.setString(1, customer);
            sqlDate = java.sql.Date.valueOf(day);
            findIfBooked.setDate(2, sqlDate);
            resultSet1 = findIfBooked.executeQuery();
            if (resultSet1.next()){
                isBooked = true;
                flightName = resultSet1.getString("Flight");
                removeFromBooking(customer, day);
                returnMessage = "<html>Customer " + customer + " booking cancelled for day " + day + "<br>";
                String waitListCheck = checkWaitList(day, flightName);
                returnMessage = returnMessage + waitListCheck + "</html>";
            }
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Error while checking customer booking";
            sqlException.printStackTrace();
        }
        
        if (!isBooked){
            try
            {
                Connection connection = DBConnection.getConnection();
                findIfWaitListed = connection.prepareStatement(
                        "SELECT * FROM WaitingList where customer = ? and day = ?");

                findIfWaitListed.setString(1, customer);
                sqlDate = java.sql.Date.valueOf(day);
                findIfWaitListed.setDate(2, sqlDate);
                resultSet2 = findIfWaitListed.executeQuery();
                if (resultSet2.next())
                    isWaitListed = true;
            }
            catch(SQLException sqlException){
                returnMessage = "Error while checking if customer exists on the Waiting List";
                sqlException.printStackTrace();
            }
            
            if (isWaitListed)
                returnMessage = removeFromWaitList(customer, day);
        }
        
        return returnMessage;
    }
    
    public static String removeFromBooking(String customer, String day){
        String returnMessage = "";
        boolean nextInLine = false;
        try 
        {
            Connection connection = DBConnection.getConnection();
            removeBooking = connection.prepareStatement(
                    "DELETE from Booking WHERE customer = ? and day = ?");
            
            removeBooking.setString(1, customer);
            sqlDate = java.sql.Date.valueOf(day);
            removeBooking.setDate(2, sqlDate);
            removeBooking.executeUpdate();
            returnMessage = "Customer " + customer + " cancelled for day: " + day;
            
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Customer was not successfully cancelled for day: " + day;
            sqlException.printStackTrace();
        }
        
        return returnMessage;
    }
    
    public static String removeFromWaitList(String customer, String day){
        String returnMessage = "";
        try 
        {
            Connection connection = DBConnection.getConnection();
            removeBooking = connection.prepareStatement(
                    "DELETE from WaitingList WHERE customer = ? and day = ?");
            
            removeBooking.setString(1, customer);
            sqlDate = java.sql.Date.valueOf(day);
            removeBooking.setDate(2, sqlDate);
            removeBooking.executeUpdate();
            returnMessage = "Customer " + customer + " cancelled from waiting list for day: " + day;
        }
        catch(SQLException sqlException)
        {
            returnMessage = "Customer not successfully cancelled from waiting list for day: " + day;
            sqlException.printStackTrace();
        }
        
        return returnMessage;
    }
}
