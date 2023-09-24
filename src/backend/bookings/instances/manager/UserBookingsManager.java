package backend.bookings.instances.manager;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import dao.bookings.framework.BookingDao;

import java.util.ArrayList;

public class UserBookingsManager extends BookingsManager {
    public UserBookingsManager(int accountId) {
        super(accountId);
    }

    @Override
    protected ArrayList<Booking> loadAllBookings(int accountId) {
        return BookingDao.loadAllBookings(accountId, "User");
    }

}
