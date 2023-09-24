package dao.bookings.instances;

import dao.bookings.framework.BookingDao;

import java.sql.*;

public class ToiletBookingDao extends BookingDao {

    public void saveServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo) {
        Time endTime = (Time) serviceSpecificInfo[0];

        String insertQuery = "INSERT INTO  \"Booking\".\"ToiletBooking\" (bookingId, endTime) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, bookingId);
            preparedStatement.setTime(2, endTime);

            preparedStatement.executeUpdate();

            saveServiceType(bookingId, "Toilet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Time loadServiceSpecificInfo(int bookingId) {
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

    @Override
    protected boolean removeServiceSpecificInfo(int bookingId) {
        boolean removed = false;
        //TODO delete row from ToiletBooking table
        return removed;
    }

    @Override
    protected boolean updateServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo) {
        boolean updated = false;
        //TODO update row corresponding to bookingId in ToiletBooking table
        return updated;
    }
}
