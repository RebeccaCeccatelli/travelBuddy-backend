package dao.bookings.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingStatus;
import backend.bookings.instances.booking.*;
import dao.Dao;
import dao.bookings.instances.ToiletBookingDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class BookingDao extends Dao {

    public int save(int userId, int providerId, Date date, Time arrivalTime, String optionalNotes, BookingStatus status, Object... serviceSpecificInfo) {
        int id = saveGeneralInfo(userId, providerId, date, arrivalTime, optionalNotes, status);
        if (id != 0) {
            saveServiceSpecificInfo(id, serviceSpecificInfo);
        }
        return id;
    }

    public boolean remove(int bookingId) {
        boolean removed = false;
        if (removeServiceSpecificInfo(bookingId)) {
            if (removeGeneralInfo(bookingId)) {
                removed = true;
            }
        }
        return removed;
    }

    public boolean update(int bookingId, int userId, int providerId, Date date, Time arrivalTime, String optionalnotes, BookingStatus status, Object... serviceSpecificInfo) {
        boolean updated = false;
        if (updateGeneralInfo(bookingId, userId, providerId, date, arrivalTime, optionalnotes, status)) {
            if (updateServiceSpecificInfo(bookingId, serviceSpecificInfo)) {
                updated = true;
            }
        }
        return updated;
    }

    public static ArrayList<Booking> loadAllBookings(int accountId, String accountType) {
        ArrayList<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM " + getTableName() + " WHERE " + selectAccountColumn(accountType) + " = ?";

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
                    Time endTime = new ToiletBookingDao().loadServiceSpecificInfo(id);
                    booking.initialize(id, userId, providerId, date, arrivalTime,
                            optionalNotes, bookingStatus, endTime);
                }
                else if (Objects.equals(service, "Wifi hotspot")) {
                    booking = new WiFiHotspotBooking();
                }
                else if (Objects.equals(service, "Luggage deposit")) {
                    booking = new LuggageDepositBooking();
                }
                else if (Objects.equals(service, "Parking lot")) {
                    booking = new ParkingLotBooking();
                }
                else if (Objects.equals(service, "Touristic attraction")) {
                    booking = new TouristicAttractionBooking();
                }
                else if (Objects.equals(service, "Dining option")) {
                    booking = new DiningOptionBooking();
                }
                else if (Objects.equals(service, "Accomodation")) {
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

    private int saveGeneralInfo(int userId, int providerId, Date date, Time arrivalTime,
                                String optionalNotes, BookingStatus status) {
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertQuery = "INSERT INTO" + getTableName() + " (userId, providerId, date, arrivalTime, optionalNotes, status) " +
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

    protected abstract void saveServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo);

    protected void saveServiceType(int id, String service) {
        String updateQuery = "UPDATE" + getTableName() + " SET service = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, service);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean removeGeneralInfo(int bookingId) {
        boolean removed = false;
        //TODO delete row from Booking table in database
        return removed;
    }

    protected abstract boolean removeServiceSpecificInfo(int bookingId);

    private boolean updateGeneralInfo(int bookingId, int userId, int providerId, Date date, Time arrivalTime, String optionalNotes, BookingStatus bookingStatus) {
        boolean updated = false;
        //TODO update row corresponding to bookingId
        return updated;
    }

    protected abstract boolean updateServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo);

    private static String getTableName() {
        return "\"Booking\".\"Booking\"";
    }

    private static String selectAccountColumn(String accountType) {
        if (Objects.equals(accountType, "Provider")) {
            return "providerId";
        } else if (Objects.equals(accountType, "User")) {
            return "userId";
        }
        else return null;
    }

}
