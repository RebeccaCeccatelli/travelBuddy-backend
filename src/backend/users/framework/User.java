package backend.users.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.UserBookingsManager;
import backend.payment.methods.framework.PaymentMethod;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
import backend.reviews.instances.manager.UserReviewsManager;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

public class User {
    protected int id;
    protected String email;
    protected String password;
    protected String name;
    protected String surname;

    protected BookingsManager bookingsManager;
    protected ReviewsManager reviewsManager;

    public User(String email) {
        this.email = email;

        loadIdFromDatabase();
        loadNameFromDatabase();
        loadSurnameFromDatabase();

        bookingsManager = new UserBookingsManager(id);
        reviewsManager = new UserReviewsManager(id, bookingsManager);
    }

    private void loadIdFromDatabase() {

    }
    private void loadNameFromDatabase() {

    }

    private void loadSurnameFromDatabase() {

    }

    public boolean addBooking(String service, String providerName, String providerAddress, Date date, Time arrivalTime,
                              PaymentMethod paymentMethod, String optionalNotes,
                              Object... serviceSpecificInformation) {
        return bookingsManager.addBooking(service, id, providerName, providerAddress,
                date, arrivalTime, paymentMethod, optionalNotes, serviceSpecificInformation);
    }

    public boolean modifyBooking(int bookingId, Map<String, Object> modifications) {
        return bookingsManager.modifyBooking(bookingId, modifications);
    }

    public boolean cancelBooking(int bookingId) {
        return bookingsManager.cancelBooking(bookingId);
    }

    public ArrayList<Booking> getBookings() {
        return bookingsManager.getBookings();
    }

    public boolean addReview(int bookingId, String reviewText, double rating,
                             Object... serviceSpecificInformation) {
        return reviewsManager.addReview(bookingId, reviewText, rating, serviceSpecificInformation);
    }

    public boolean cancelReview(int bookingId) {
        return reviewsManager.cancelReview(bookingId);
    }

    public ArrayList<Review> getReviews() {
        return reviewsManager.getReviews();
    }

    private boolean isNameValid(String name) {
        boolean valid = false;
        //TODO check name validity
        return valid;
    }

    private boolean isEmailValid(String email) {
        boolean valid = false;
        //TODO check email validity
        return valid;
    }
}
