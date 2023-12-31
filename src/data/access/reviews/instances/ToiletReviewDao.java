package data.access.reviews.instances;

import data.access.Dao;
import data.access.reviews.framework.ReviewDao;

import java.sql.*;
import java.util.ArrayList;

public class ToiletReviewDao extends ReviewDao {
    @Override
    protected void saveServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo) {
        Double cleanliness = (Double) serviceSpecificInfo[0];
        Double productsQuality = (Double) serviceSpecificInfo[1];
        Double bathroomSize = (Double) serviceSpecificInfo[2];
        Double quietness = (Double) serviceSpecificInfo[3];
        Double accessibility = (Double) serviceSpecificInfo[4];

        String insertQuery = "INSERT INTO  " + getTableName() + " (reviewId, cleanliness, productsQuality," +
                "bathroomSize, quietness, accessibility) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Dao.DB_URL, Dao.DB_USER, Dao.DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, reviewId);
            preparedStatement.setDouble(2, cleanliness);
            preparedStatement.setDouble(3, productsQuality);
            preparedStatement.setDouble(4, bathroomSize);
            preparedStatement.setDouble(5, quietness);
            preparedStatement.setDouble(6, accessibility);

            preparedStatement.executeUpdate();

            saveServiceType(reviewId, "Toilet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean removeServiceSpecifInfo(int reviewId) {
        boolean removed = false;
        //TODO remove from database
        return removed;
    }

    @Override
    public ArrayList<Double> loadServiceSpecificInfo(int reviewId) {
        ArrayList<Double> ratings = new ArrayList<>();

        String sql = "SELECT cleanliness, productsQuality, bathroomSize, quietness, accessibility " +
                "FROM " + getTableName() + " WHERE reviewId = ?";

        try (Connection connection = DriverManager.getConnection(Dao.DB_URL, Dao.DB_USER, Dao.DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, reviewId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Double cleanliness = resultSet.getDouble("cleanliness");
                Double productsQuality = resultSet.getDouble("productsQuality");
                Double bathroomSize = resultSet.getDouble("bathroomSize");
                Double quietness = resultSet.getDouble("quietness");
                Double accessibility = resultSet.getDouble("accessibility");

                ratings.add(cleanliness);
                ratings.add(productsQuality);
                ratings.add(bathroomSize);
                ratings.add(quietness);
                ratings.add(accessibility);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
    }

    private String getTableName() {
        return "\"Review\".\"ToiletReview\"";
    }
}
