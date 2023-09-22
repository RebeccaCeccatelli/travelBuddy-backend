package dao.accounts.users;

import backend.accounts.common.features.framework.Address;
import backend.accounts.common.features.instances.UserAccount;
import backend.accounts.users.framework.User;
import dao.accounts.AccountDao;
import dao.accounts.AddressDao;

import java.sql.*;
import java.util.ArrayList;

public class UserDao extends AccountDao {

    @Override
    public UserAccount loadAccount(String email) {
        UserAccount userAccount = null;
        String query = "SELECT * FROM " + getTable() + " WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String storedEmail = resultSet.getString("email");
                String password = resultSet.getString("password");

                int addressId = resultSet.getInt("addressId");
                Address address = new AddressDao("User").loadAddress(addressId);

                String phoneNumber = resultSet.getString("phoneNumber");

                String gender = resultSet.getString("gender");
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                String nationality = resultSet.getString("nationality");

                userAccount = new UserAccount();
                userAccount.initialize(id, storedEmail, password, name, address, phoneNumber, gender, dateOfBirth, nationality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userAccount;
    }

    @Override
    public void saveSpecificAccountInformation(int id, Object... memberSpecificInformation) {
        String gender = (String) memberSpecificInformation[0];
        Date dateOfBirth = (Date) memberSpecificInformation[1];
        String nationality = (String) memberSpecificInformation[2];

        String updateQuery = "UPDATE " + getTable() +
                " SET gender = ?, dateOfBirth = ?, nationality = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, gender);
            preparedStatement.setDate(2, dateOfBirth);
            preparedStatement.setString(3, nationality);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected String getAccountType() {
        return "User";
    }
}
