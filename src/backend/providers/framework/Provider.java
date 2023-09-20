package backend.providers.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.ProviderBookingsManager;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
import backend.reviews.instances.manager.ProviderReviewsManager;

import java.util.ArrayList;

public class Provider {
    protected int id;
    protected String email;
    protected String password;
    protected BookingsManager bookingsManager;
    protected ReviewsManager reviewsManager;

    public Provider(String email) {
        this.email = email;

        bookingsManager = new ProviderBookingsManager(id);
        reviewsManager = new ProviderReviewsManager(id, bookingsManager);
    }

    public boolean cancelBooking(int bookingId) {
        return bookingsManager.cancelBooking(bookingId);
    }

    public boolean confirmBookingAttendance(int bookingId) {
        return bookingsManager.confirmBookingAttendance(bookingId);
    }

    public ArrayList<Booking> getBookings() {
        return bookingsManager.getBookings();
    }

    public ArrayList<Review> getReviews() {
        return reviewsManager.getReviews();
    }

    public static int findProviderIdByNameAndAddress(String providerName, String address) {
        int providerId = 0;
        //TODO search providerId in database using name and address
        return providerId;
    }
}
