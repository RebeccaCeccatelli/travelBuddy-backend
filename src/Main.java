import backend.accounts.common.features.framework.Address;
import backend.accounts.users.framework.User;
import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import dao.bookings.BookingDao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        User user = new User();
        Address address = new Address("1", "1", "1", "1", "1");
        boolean registered = user.register("rebeccaceccatelli@gmail.com", "Pianoforte2000!", "Rebecca", address, "1233433333", "female", Date.valueOf("2000-06-21"), "italian");
    }
}
