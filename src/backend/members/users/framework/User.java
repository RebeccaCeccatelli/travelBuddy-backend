package backend.members.users.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.UserBookingsManager;
import backend.localization.services.ProvidersMap;
import backend.members.common.framework.Account;
import backend.members.common.framework.Address;
import backend.members.common.instances.UserAccount;
import backend.payment.methods.framework.PaymentMethod;
import backend.payment.methods.framework.PaymentMethodsManager;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
import backend.reviews.instances.manager.UserReviewsManager;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

public class User {
    Account account = new UserAccount();
    protected BookingsManager bookingsManager;
    protected ReviewsManager reviewsManager;
    protected PaymentMethodsManager paymentMethodsManager;

    public ProvidersMap createMap(String service) {
        ProvidersMap map = new ProvidersMap();

        map.locateUser(this);
        map.populate(service);

        return map;
    }

    public User register(String email, String password, String name, Address address,
                            String phoneNumber, Object... memberSpecificInformation) {
        if (account.register(email, password, name, address, phoneNumber, memberSpecificInformation)) {
            setManagers();
        }
        return this;
    }

    public User login(String email, String password){
        if (account.login(email, password)) {
            setManagers();
        }
        return this;
    }

    private void setManagers() {
        bookingsManager = new UserBookingsManager(account.getId());
        reviewsManager = new UserReviewsManager(account.getId(), bookingsManager);
        paymentMethodsManager = new PaymentMethodsManager(account.getId());
    }

    public boolean addPaymentMethod(String type, String... information) {
        return paymentMethodsManager.addPaymentMethod(type, information);
    }

    public boolean modifyPaymentMethod(int paymentMethodId, Map<String, String> modifications) {
        return paymentMethodsManager.modifyPaymentMethod(paymentMethodId, modifications);
    }

    public boolean removePaymentMethod(int paymentMethodId) {
        return paymentMethodsManager.removePaymentMethod(paymentMethodId);
    }

    public boolean addBooking(String service, String providerName, String providerAddress, Date date, Time arrivalTime,
                              PaymentMethod paymentMethod, String optionalNotes,
                              Object... serviceSpecificInformation) {
        return bookingsManager.addBooking(service, account.getId(), providerName, providerAddress,
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

    public String getUserMessage() {
        return "Hi, " + account.getName() + "! You are here.";
    }

    public Address getAddress() {
        return account.getAddress();
    }

}