
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Booking {
    
    private static PreparedStatement addNewBooking;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement getSeatsOnFlight;
    private static java.sql.Date sqlDate;
    
    
    
    public static String addBooking(String flight, String date, String customer){
        java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        String returnMessage = "";
        boolean canAdd = false;
        canAdd = checkAvailableSeats(flight, date);
        if (canAdd){
            try 
            {
                Connection connection = DBConnection.getConnection();
                addNewBooking = connection.prepareStatement(
                        "INSERT INTO Booking" +
                        "(Flight, Day, Customer, timestamp)" +
                        "VALUES(?, ?, ?, ?)");

                addNewBooking.setString(1, flight);
                sqlDate = java.sql.Date.valueOf(date);
                addNewBooking.setDate(2, sqlDate);
                addNewBooking.setString(3, customer);
                addNewBooking.setTimestamp(4, timestamp);

                addNewBooking.executeUpdate();
                returnMessage = customer + " was successfully booked on flight " + flight + " on " + date;
            }
            catch(SQLException sqlException)
            {
                returnMessage = "Flight not successfully booked due to database error";
                sqlException.printStackTrace();
            }
        }
        else{
            returnMessage = WaitingList.addWaitList(flight, date, customer);
        }
        
        return returnMessage;
    }
    
    public static boolean checkAvailableSeats(String flight, String date){
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        int seatsBooked = 10000;
        int seatsOnFlight = 0;
        try
        {
            Connection connection = DBConnection.getConnection();
            getFlightSeats = connection.prepareStatement("select count(flight) from booking where flight = ? and day = ?"); 
            getFlightSeats.setString(1, flight); 
            sqlDate = java.sql.Date.valueOf(date);
            getFlightSeats.setDate(2, sqlDate); 
            resultSet = getFlightSeats.executeQuery(); 
            if (resultSet.next()){
                seatsBooked = resultSet.getInt(1);

            }
            getSeatsOnFlight = connection.prepareStatement("select seats from flight where name = ?");
            getSeatsOnFlight.setString(1, flight);
            resultSet2 = getSeatsOnFlight.executeQuery();
            if (resultSet2.next())
                seatsOnFlight = resultSet2.getInt("Seats");
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        if (seatsBooked < seatsOnFlight){
            return true;
        }
        else
            return false;
    }
    
    
}
