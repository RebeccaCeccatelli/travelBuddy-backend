package dao.bookings;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingStatus;
import backend.bookings.instances.booking.*;
import dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class BookingDao extends Dao {

    public boolean saveServiceType(int id, String service) {
        boolean saved = false;
        String updateQuery = "UPDATE" + getTable() + " SET service = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, service);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            saved = rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saved;
    }


    public int saveGeneralBookingInformation(int userId, int providerId, Date date, Time arrivalTime,
                                             String optionalNotes, BookingStatus status) {
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertQuery = "INSERT INTO" + getTable() + " (userId, providerId, date, arrivalTime, optionalNotes, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, providerId);
            preparedStatement.setDate(3, date);
            preparedStatement.setTime(4, arrivalTime);
            preparedStatement.setString(5, optionalNotes);
            preparedStatement.setString(6, status.toString());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return id;
    }

    public ArrayList<Booking> loadAllBookings(int accountId, String accountType) {
        ArrayList<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM " + getTable() + " WHERE " + getColumn(accountType) + " = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                int providerId = resultSet.getInt("providerId");
                Date date = resultSet.getDate("date");
                Time arrivalTime = resultSet.getTime("arrivalTime");
                String optionalNotes = resultSet.getString("optionalNotes");

                String status = resultSet.getString("status");
                BookingStatus bookingStatus = BookingStatus.valueOf(status);

                String service = resultSet.getString("service");

                Booking booking = null;
                if (Objects.equals(service, "Toilet")) {
                    booking = new ToiletBooking();
                    Time endTime = new ToiletBookingDao().loadSpecificInformation(id);
                    booking.initialize(id, userId, providerId, date, arrivalTime,
                            optionalNotes, bookingStatus, endTime);
                } else if (Objects.equals(service, "Wifi hotspot")) {
                    booking = new WiFiHotspotBooking();
                } else if (Objects.equals(service, "Luggage deposit")) {
                    booking = new LuggageDepositBooking();
                } else if (Objects.equals(service, "Parking lot")) {
                    booking = new ParkingLotBooking();
                } else if (Objects.equals(service, "Touristic attraction")) {
                    booking = new TouristicAttractionBooking();
                } else if (Objects.equals(service, "Dining option")) {
                    booking = new DiningOptionBooking();
                } else if (Objects.equals(service, "Accomodation")) {
                    booking = new AccomodationBooking();
                }
                if (booking != null) {
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private String getTable() {
        return "\"Booking\".\"Booking\"";
    }

    private String getColumn(String accountType) {
        if (Objects.equals(accountType, "Provider")) {
            return "providerId";
        } else if (Objects.equals(accountType, "User")) {
            return "userId";
        }
        else return null;
    }

}
