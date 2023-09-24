package backend.bookings.framework;

import backend.bookings.instances.booking.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public abstract class BookingsManager {
    protected ArrayList<Booking> bookings = new ArrayList<>();

    public BookingsManager(int accountId) {
        bookings = loadAllBookings(accountId);
    }

    protected abstract ArrayList<Booking> loadAllBookings(int accountId);

    public boolean addBooking(String service, int userId, int providerId, Date date, Time arrivalTime,
                                       String optionalNotes,
                                       Object... serviceSpecificInformation) {
        boolean created = false;
        if (providerId == 0) {
            return created;
        }

        Booking booking;
        if (Objects.equals(service, "Toilet")) {
            booking = new ToiletBooking();
        } else if (Objects.equals(service, "WiFi hotspot")) {
            booking = new WiFiHotspotBooking();
        } else if (Objects.equals(service, "Luggage deposit")) {
            booking = new LuggageDepositBooking();
        } else if (Objects.equals(service, "Parking lot")) {
            booking = new ParkingLotBooking();
        } else if (Objects.equals(service, "Touristic attraction")) {
            booking = new TouristicAttractionBooking();
        } else if (Objects.equals(service, "Dining option")) {
            booking = new DiningOptionBooking();
        } else if (Objects.equals(service, "Accomodation")) {
            booking = new AccomodationBooking();
        }
        else {
            return created;
        }
        created = booking.create(userId, providerId, date, arrivalTime,
                optionalNotes, serviceSpecificInformation);
        if (created) {
            bookings.add(booking);
        }
        return created;
    }

    public boolean modifyBooking(int bookingId, Map<String, Object> modifications) {
        boolean modified = false;
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            if (booking.modify(modifications)) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean removeBooking(int bookingId) {
        boolean removed = false;
        Booking booking  = findBookingById(bookingId);
        if (booking != null) {
            if (booking.remove()) {
                bookings.remove(booking);
                removed = true;
            }
        }
        return removed;
    }

    public boolean confirmBookingAttendance(int bookingId) {
        boolean confirmed = false;
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.setStatus(BookingStatus.SUCCESSFUL);
            if (booking.update()) {
                confirmed = true;
            }
        }
        return confirmed;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Booking findBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.id == bookingId) {
                return booking;
            }
        }
        return null;
    }

    public boolean isBookingSuccessful(int bookingId) {
        boolean successful = false;
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
           if (booking.isSuccessful()) {
               successful = true;
           }
        }
        return successful;
    }

}

