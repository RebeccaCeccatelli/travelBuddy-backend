package backend.members.users.framework;

import backend.members.common.framework.Address;

import java.util.ArrayList;

public class UsersManager {
    ArrayList<User> users = new ArrayList<>();

    public UsersManager() {
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {

    }

    public User register(String email, String password, String name, Address address,
                         String phoneNumber, Object... memberSpecificInformation) {
        User user = new User().register(email, password, name, address, phoneNumber,
                memberSpecificInformation);
        if (user != null) {
            users.add(user);
        }
        return user;
    }

    public User login(String email, String password) {
        return new User().login(email, password);
    }
}
