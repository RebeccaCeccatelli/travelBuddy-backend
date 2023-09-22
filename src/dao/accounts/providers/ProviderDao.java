package dao.accounts.providers;

import backend.accounts.common.features.framework.Address;
import backend.accounts.common.features.instances.ProviderAccount;
import backend.accounts.providers.framework.Provider;
import dao.accounts.AccountDao;
import dao.accounts.AddressDao;

import java.sql.*;
import java.util.ArrayList;

public class ProviderDao extends AccountDao {

    @Override
    public ProviderAccount loadAccount(String email) {
        ProviderAccount providerAccount = null;
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

                String licenseId = resultSet.getString("licenseId");
                String businessId = resultSet.getString("businessId");

                providerAccount = new ProviderAccount();
                providerAccount.initialize(id, storedEmail, password, name, address, phoneNumber, licenseId, businessId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providerAccount;
    }

    @Override
    public void saveSpecificAccountInformation(int id, Object... memberSpecificInformation) {
        String licenseId = (String) memberSpecificInformation[0];
        String businessId = (String) memberSpecificInformation[1];

        String updateQuery = "UPDATE " + getTable() +
                " SET licenseId = ?, businessId = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, licenseId);
            preparedStatement.setString(2, businessId);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Provider> loadAllProviders() {
        ArrayList<Provider> providers = new ArrayList<>();

        String query = "SELECT * FROM" + getTable();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int addressId = resultSet.getInt("addressId");
                Address address = new AddressDao("Provider").loadAddress(addressId);
                String phoneNumber = resultSet.getString("phoneNumber");
                String licenseId = resultSet.getString("licenseId");
                String businessId = resultSet.getString("businessId");


                Provider provider = new Provider();
                provider.initialize(id, email, password, name, address, phoneNumber, licenseId, businessId);

                providers.add(provider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return providers;
    }


    public String getAccountType() {
        return "Provider";
    }
}
