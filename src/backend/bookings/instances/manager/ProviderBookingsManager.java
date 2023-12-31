package backend.bookings.instances.manager;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import data.access.bookings.framework.BookingDao;

import java.util.ArrayList;

public class ProviderBookingsManager extends BookingsManager {
    public ProviderBookingsManager(int accountId) {
        super(accountId);
    }

    @Override
    protected ArrayList<Booking> loadAllBookings(int accountId) {
        return BookingDao.loadAllBookings(accountId, "Provider");
    }
}
