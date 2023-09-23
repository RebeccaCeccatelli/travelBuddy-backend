package dao.bookings;

import dao.Dao;

import java.sql.*;

public class ToiletBookingDao extends Dao {

    public boolean saveSpecificInformation(int bookingId, Time endTime) {
        boolean saved = false;

        String insertQuery = "INSERT INTO  \"Booking\".\"ToiletBooking\" (bookingId, endTime) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, bookingId);
            preparedStatement.setTime(2, endTime);

            int rowsInserted = preparedStatement.executeUpdate();

            saved = rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saved;
    }

    public Time loadSpecificInformation(int bookingId) {
    Time endTime = null;

    String sql = "SELECT endTime FROM \"Booking\".\"ToiletBooking\" WHERE bookingId = ?";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, bookingId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            endTime = resultSet.getTime("endTime");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return endTime;
}
}
