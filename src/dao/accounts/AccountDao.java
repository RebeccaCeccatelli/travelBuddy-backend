package dao.accounts;

import backend.accounts.common.features.framework.Account;
import backend.accounts.common.features.framework.Address;
import dao.Dao;

import java.sql.*;

public abstract class AccountDao extends Dao {

    public boolean checkPasswordsMatch(String email, String password) {
        boolean valid = false;
        String query = "SELECT password FROM " + getTable() + " WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                valid = password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valid;
    }

    public boolean checkAccountExists(String email) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM  \"" + getTable() +
                "\" WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                exists = rowCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public abstract Account loadAccount(String email);

    public int saveAccount(String name, String email, String password, Address address,
                           String phoneNumber, Object... memberSpecificInformation) {
        int id = 0;

        int addressId = new AddressDao(getAccountType()).saveAddress(address);

        String insertCommonQuery = "INSERT INTO " + getTable() +
                " (name, email, password, addressId, phoneNumber) VALUES (?, ?, ?, ?, ?)" +
                "RETURNING id";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertCommonQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, addressId);
            preparedStatement.setString(5, phoneNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");

                saveSpecificAccountInformation(id, memberSpecificInformation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public abstract void saveSpecificAccountInformation(int id, Object... memberSpecificInformation);

    protected abstract String getAccountType();

    protected String getTable() {
        return "\"" + getAccountType() + "\".\"" + getAccountType() + "\"";
    }

}
