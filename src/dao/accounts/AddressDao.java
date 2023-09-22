package dao.accounts;

import backend.accounts.common.features.framework.Address;
import dao.Dao;

import java.sql.*;

public class AddressDao extends Dao {

    String accountType;

    public AddressDao(String accountType) {
        this.accountType = accountType;
    }

    public int saveAddress(Address address) {
        int id = 0;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO " + getTable() +
                    "(street, civicNumber, postCode, city, country) VALUES (?, ?, ?, ?, ?)" +
                    "RETURNING id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, address.getStreet());
                preparedStatement.setString(2, address.getCivicNumber());
                preparedStatement.setString(3, address.getPostCode());
                preparedStatement.setString(4, address.getCity());
                preparedStatement.setString(5, address.getCountry());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public Address loadAddress(int addressId) {
        Address address = null;
        String query = "SELECT * FROM" + getTable() + " WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String street = resultSet.getString("street");
                String civicNumber = resultSet.getString("civicNumber");
                String postCode = resultSet.getString("postCode");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");

                address = new Address(street, civicNumber, postCode, city, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

    private String getTable() {
        return "\"" + accountType + "\".\"" + accountType + "Address\"";
    }

}
