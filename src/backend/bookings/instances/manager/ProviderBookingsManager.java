package backend.bookings.instances.manager;

import backend.bookings.framework.BookingsManager;

public class ProviderBookingsManager extends BookingsManager {
    public ProviderBookingsManager(int accountId) {
        super(accountId);
    }

    @Override
    public void loadBookingsFromDatabase(int accountId) {
        //TODO get bookings from database (clientId here is a business id)
    }
}
