package backend.bookings.instances.manager;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import dao.bookings.BookingDao;

import java.util.ArrayList;

public class UserBookingsManager extends BookingsManager {
    public UserBookingsManager(int accountId) {
        super(accountId);
    }

    @Override
    protected ArrayList<Booking> loadBookingsFromDatabase(int accountId) {
        return new BookingDao().loadAllBookings(accountId, "User");
    }

}
