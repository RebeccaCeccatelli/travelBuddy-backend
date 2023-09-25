package backend.accounts.providers.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.ProviderBookingsManager;
import backend.accounts.common.features.framework.Account;
import backend.accounts.common.features.framework.Address;
import backend.accounts.common.features.instances.ProviderAccount;
import backend.reviews.framework.ReviewsManager;
import backend.reviews.framework.Review;
import backend.reviews.instances.manager.ProviderReviewsManager;
import backend.services.framework.Service;
import backend.services.framework.ServiceStatus;
import backend.services.framework.ServicesManager;
import backend.services.instances.manager.ProviderServicesManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

public class Provider {
    public Account account = new ProviderAccount();
    protected ServicesManager servicesManager;
    protected BookingsManager bookingsManager;
    protected ReviewsManager reviewsManager;

    public boolean initialize(int id, String email, String password, String name, Address address,
                           String phoneNumber, Object... accountSpecificInfo) {
        boolean initialized = false;
        if (account.initialize(id, email, password, name, address, phoneNumber, accountSpecificInfo)) {
            setManagers();
            initialized = true;
        }
        return initialized;
    }

    public boolean register(String email, String password, String name, Address address,
                            String phoneNumber, Object... accountSpecificInfo) {
        boolean registered = false;
        account = account.register(email, password, name, address, phoneNumber, accountSpecificInfo);
        if (account != null) {
            setManagers();
            registered = true;
        }
        return registered;
    }

    public boolean login(String email, String password){
        boolean loggedIn = false;
        account = account.login(email, password);
        if (account != null) {
            setManagers();
            loggedIn = true;
        }
        return loggedIn;
    }

    public boolean addService(String service, Time openingTime, Time closingTime, String paymentPolicy,
                              double price, String optionalNotes,
                              Object... serviceSpecificInformation) {
        return servicesManager.addService(service, openingTime, closingTime, paymentPolicy,
                price, optionalNotes, serviceSpecificInformation);
    }

    public boolean cancelService(String service) {
        return servicesManager.cancelService(service);
    }

    public boolean modifyServiceInformation(String service, Map<String, Object> modifications) {
        return servicesManager.modifyServiceInformation(service, modifications);
    }

    public boolean updateServiceStatus(String service, ServiceStatus newStatus) {
        return servicesManager.updateServiceStatus(service, newStatus);
    }

    public boolean confirmBookingAttendance(int bookingId) {
        return bookingsManager.confirmBookingAttendance(bookingId);
    }

    public boolean cancelBooking(int bookingId) {
        return bookingsManager.removeBooking(bookingId);
    }

    public ArrayList<Booking> getBookings() {
        return bookingsManager.getBookings();
    }

    public ArrayList<Review> getReviews() {
        return reviewsManager.getReviews();
    }

    public ArrayList<Service> getServices() {
        return servicesManager.getServices();
    }

    public Service getService(String service) {
        return servicesManager.getServiceByType(service);
    }

    public double getOverallRating() {
        return reviewsManager.calculateAverageRating();
    }

    public double getServiceRating(String service) {
        return reviewsManager.calculateAverageRating(service);
    }

    public Address getAddress() {
        return account.getAddress();
    }

    private void setManagers() {
        servicesManager = new ProviderServicesManager(account.getId());
        bookingsManager = new ProviderBookingsManager(account.getId());
        reviewsManager = new ProviderReviewsManager(account.getId(), bookingsManager);
    }
}
