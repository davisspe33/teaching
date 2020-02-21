
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Flight {
    
    private static PreparedStatement getFlights;
    private static PreparedStatement getFlightStatus;
    private static PreparedStatement addFlight;
    private static PreparedStatement removeWaitList;
    private static PreparedStatement removeFlightBookings;
    private static PreparedStatement removeBooking;
    private static PreparedStatement checkForFlights;
    private static PreparedStatement deleteFlight;
    private static PreparedStatement getAllFromWaitList;
    
    private static java.sql.Date sqlDate;

    
    
    
    public static ArrayList<String> getFlightList(){
        
        ArrayList<String> results = new ArrayList<String>();
        ResultSet resultSet = null;
        String columnName = "Name";
        
        try
        {
            Connection connection = DBConnection.getConnection();
            getFlights = connection.prepareStatement(
                    "SELECT Name FROM Flight");
            resultSet = getFlights.executeQuery();
            if (resultSet.next()){
                String first = resultSet.getString(columnName);
                results.add(first);
                
                while (resultSet.next()){
                    results.add(resultSet.getString(columnName));
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
    
    public static String getFlightStatus(String flight, String day){
        ResultSet resultSet = null;
        String results = "";
        try
        {
            Connection connection = DBConnection.getConnection();
            getFlightStatus = connection.prepareStatement(
                    "SELECT Customer FROM Booking WHERE flight = ? and day = ?");
            getFlightStatus.setString(1, flight);
            java.sql.Date sqlDate = java.sql.Date.valueOf(day);
            getFlightStatus.setDate(2, sqlDate);
            resultSet = getFlightStatus.executeQuery();
            if (resultSet.next()){
                String customer = resultSet.getString("Customer");
                results = results.concat(customer + "\n");
                while (resultSet.next()){
                    results = results.concat(resultSet.getString("Customer") + "\n");
                }
            }
            
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }
    
        public static String addFlight(String flight, int seats){
            String returnMessage = "";
            try 
            {
                Connection connection = DBConnection.getConnection();
                addFlight = connection.prepareStatement(
                        "INSERT INTO Flight" +
                        "(Name, Seats)" +
                        "VALUES(?, ?)");

                addFlight.setString(1, flight);
                addFlight.setInt(2, seats);
                addFlight.executeUpdate();
                returnMessage = "Successfully added " + flight + " to the Flight Database";
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Flight was not successfully added to database";
                sqlException.printStackTrace();
            }

            return returnMessage;
        }
        public static String dropFlight(String flight){
            String returnMessage = "";
            ResultSet resultSet = null;
            
            
            String waitListMessage = removeFromWaitList(flight);
            returnMessage = returnMessage + waitListMessage;
            
           
            deleteFlight(flight);
            
            
            
            try 
            {
                Connection connection = DBConnection.getConnection();
                removeFlightBookings = connection.prepareStatement(
                        "select * from booking where flight = ? order by day, timestamp asc");

                removeFlightBookings.setString(1, flight);
                resultSet = removeFlightBookings.executeQuery();
                
                if (resultSet.next()){
                    String customer = resultSet.getString("Customer");
                    Date day = resultSet.getDate("Day");
                    String dayString = String.valueOf(day);
                    String removeMessage = removeBooking(customer, dayString);
                    
                    
                    String moveToNextAvailable = moveToAvailableFlight(customer, dayString);
                    returnMessage = returnMessage + "\n" + removeMessage + "\n" + moveToNextAvailable;
                    
                    while(resultSet.next()){
                        customer = resultSet.getString("Customer");
                        day = resultSet.getDate("Day");
                        dayString = String.valueOf(day);
                        removeMessage = removeBooking(customer, dayString);

                        moveToNextAvailable = moveToAvailableFlight(customer, dayString);
                        returnMessage = returnMessage + "\n" + removeMessage + "\n" + moveToNextAvailable;
                    }
                }
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Flight was not successfully dropped from database";
                sqlException.printStackTrace();
            }
            return returnMessage;
        }
        
        public static String removeFromWaitList(String flight){ 
            String returnMessage = "";
            ResultSet resultSet = null;
            
            try 
            {
                Connection connection = DBConnection.getConnection();
                getAllFromWaitList = connection.prepareStatement(
                        "select * from waitinglist where flight = ?");
                
                getAllFromWaitList.setString(1, flight);
                resultSet = getAllFromWaitList.executeQuery();
                if (resultSet.next()){
                    String customer = resultSet.getString("Customer");
                    Date date = resultSet.getDate("Day");
                    returnMessage = returnMessage + "Removed " + customer + " from wait list on " + date;
                    while (resultSet.next()){
                        customer = resultSet.getString("Customer");
                        date = resultSet.getDate("Day");
                        returnMessage = returnMessage + "Removed " + customer + " from wait list on " + date;
                    }
                }
                
                removeWaitList = connection.prepareStatement(
                        "delete from waitinglist where flight = ?");

                removeWaitList.setString(1, flight);
                removeWaitList.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Flight was not successfully removed from WaitingList Database";
                sqlException.printStackTrace();
            }
            return returnMessage;
        }
        
        public static String removeBooking(String customer, String day){
            String returnMessage = "";
            
            try 
            {
                Connection connection = DBConnection.getConnection();
                removeBooking = connection.prepareStatement(
                        "delete from booking where customer = ? and day = ?");

                removeBooking.setString(1, customer);
                sqlDate = java.sql.Date.valueOf(day);
                removeBooking.setDate(2, sqlDate);
                removeBooking.executeUpdate();
                returnMessage = "Removed booking for  " + customer + " on " + day;
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Did not successfully remove " + customer + " from booking on " + day;
                sqlException.printStackTrace();
            }
            
            return returnMessage;
        }
        
        public static void deleteFlight(String flight){
            try 
            {
                Connection connection = DBConnection.getConnection();
                deleteFlight = connection.prepareStatement(
                        "delete from flight where name = ?");

                deleteFlight.setString(1, flight);
                deleteFlight.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        public static String moveToAvailableFlight(String customer, String day){
            String returnMessage = "";
            ResultSet resultSet = null;
            boolean booked = false;
            
            
            try 
            {
                Connection connection = DBConnection.getConnection();
                checkForFlights = connection.prepareStatement(
                        "select * from flight ");

                resultSet = checkForFlights.executeQuery();
                if (resultSet.next()){
                    // there is another flight. Need to check for availabe seats
                    String flight = resultSet.getString("Name");
                    if (Booking.checkAvailableSeats(flight, day)){
                        Booking.addBooking(flight, day, customer);
                        returnMessage = "Customer " + customer + " re-booked to flight " + flight;
                        booked = true;
                    }
                    while (resultSet.next() && !booked){
                        flight = resultSet.getString("Name");
                        if (Booking.checkAvailableSeats(flight, day)){
                            Booking.addBooking(flight, day, customer);
                            returnMessage = "Customer " + customer + " re-booked to flight " + flight;
                            booked = true;
                        }
                    }
                }
                
                if (!booked)
                    returnMessage = "No available flights for " + customer + " to be booked on for " + day;
                
                
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Did not successfully check for other available flights";
                sqlException.printStackTrace();
            }
            
            return returnMessage;
        }
    
}
