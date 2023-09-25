package data.access.reviews.framework;

import backend.reviews.framework.Review;
import backend.reviews.instances.review.*;
import data.access.Dao;
import data.access.reviews.instances.ToiletReviewDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class ReviewDao extends Dao {

    public int save(int bookingId, String reviewText, Double rating, Object... serviceSpecificInfo) {
        int id = saveGeneralInfo(bookingId, reviewText, rating);
        if (id != 0) {
            saveServiceSpecificInfo(id, serviceSpecificInfo);
        }
        return id;
    }

    public boolean remove(int reviewId) {
        boolean removed = false;
        if (removeServiceSpecifInfo(reviewId)) {
            if (removeGeneralInfo(reviewId)) {
                removed = true;
            }
        }
        return removed;
    }

    public static ArrayList<Review> loadAllReviews(int accountId, String accountType) {
        ArrayList<Review> reviews = new ArrayList<>();

        ArrayList<Integer> bookingIds = getAllBookingIds(accountId, accountType);

        String sql = "SELECT * FROM " + getTableName() + " WHERE bookingId IN (";
        for (int i = 0; i < bookingIds.size(); i++) {
            if (i > 0) {
                sql += ",";
            }
            sql += "?";
        }
        sql += ")";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < bookingIds.size(); i++) {
                preparedStatement.setInt(i + 1, bookingIds.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookingId = resultSet.getInt("bookingId");
                String reviewText = resultSet.getString("reviewText");
                Double rating = resultSet.getDouble("rating");
                String service = resultSet.getString("service");

                Review review = null;
                if (Objects.equals(service, "Toilet")) {
                    review = new ToiletReview();
                    ArrayList<Double> toiletRatings = new ToiletReviewDao().loadServiceSpecificInfo(id);
                    review.initialize(id, bookingId, reviewText, rating, toiletRatings.get(0), toiletRatings.get(1), toiletRatings.get(2), toiletRatings.get(3), toiletRatings.get(4));
                }
                else if (Objects.equals(service, "Wifi hotspot")) {
                    review = new WiFiHotspotReview();
                }
                else if (Objects.equals(service, "Luggage deposit")) {
                    review = new LuggageDepositReview();
                }
                else if (Objects.equals(service, "Parking lot")) {
                    review = new ParkingLotReview();
                }
                else if (Objects.equals(service, "Touristic attraction")) {
                    review = new TouristicAttractionReview();
                }
                else if (Objects.equals(service, "Dining option")) {
                    review = new DiningOptionReview();
                }
                else if (Objects.equals(service, "Accomodation")) {
                    review = new AccomodationReview();
                }

                if (review != null) {
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private int saveGeneralInfo(int bookingId, String reviewText, Double rating) {
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String insertQuery = "INSERT INTO  " + getTableName() + " (bookingId, reviewText, rating) " +
                    "VALUES (?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.setString(2, reviewText);
            preparedStatement.setDouble(3, rating);

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

    protected abstract void saveServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo);

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

    private boolean removeGeneralInfo(int reviewId) {
        boolean removed = false;
        //TODO remove from database
        return removed;
    }

    protected abstract boolean removeServiceSpecifInfo(int reviewId);

    private static String getTableName() {
        return "\"Review\".\"Review\"";
    }

    private static ArrayList<Integer> getAllBookingIds(int accountId, String accountType) {
        ArrayList<Integer> bookingIds = new ArrayList<>();

        String sql = "SELECT id FROM \"Booking\".\"Booking\" WHERE "
                + selectColumn(accountType) + " = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("id");
                bookingIds.add(bookingId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingIds;
    }

    protected abstract Object loadServiceSpecificInfo(int reviewId);

    private static String selectColumn(String accountType) {
        return accountType.equals("Provider") ? "providerId" : "userId";
    }
}
