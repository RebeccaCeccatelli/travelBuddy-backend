package backend.bookings.instances.manager;

import backend.bookings.framework.BookingsManager;

public class UserBookingsManager extends BookingsManager {
    public UserBookingsManager(int accountId) {
        super(accountId);
    }

    @Override
    public void loadBookingsFromDatabase(int accountId) {
        //TODO get bookings from database (clientId here is a user id)
    }
}
