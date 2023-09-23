package backend.accounts.providers.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.ProviderBookingsManager;
import backend.accounts.common.features.framework.Account;
import backend.accounts.common.features.framework.Address;
import backend.accounts.common.features.instances.ProviderAccount;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
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

    public void initialize(int id, String email, String password, String name, Address address,
                           String phoneNumber, Object... memberSpecificInformation) {
        account.initialize(id, email, password, name, address, phoneNumber, memberSpecificInformation);
        setManagers();
    }

    public boolean register(String email, String password, String name, Address address,
                            String phoneNumber, Object... memberSpecificInformation) {
        boolean registered = false;
        account = account.register(email, password, name, address, phoneNumber, memberSpecificInformation);
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

    private void setManagers() {
        servicesManager = new ProviderServicesManager(account.getId());
        bookingsManager = new ProviderBookingsManager(account.getId());
        reviewsManager = new ProviderReviewsManager(account.getId(), bookingsManager);
    }

    public boolean addService(String service, Time openingTime, Time closingTime, String paymentPolicy,
                              double price, String optionalNotes,
                              Object... serviceSpecificInformation) {
        return servicesManager.addService(service, openingTime, closingTime, paymentPolicy,
                price, optionalNotes, serviceSpecificInformation);
    }

    public boolean modifyServiceInformation(String service, Map<String, Object> modifications) {
        return servicesManager.modifyServiceInformation(service, modifications);
    }

    public boolean cancelService(String service) {
        return servicesManager.cancelService(service);
    }

    public boolean updateServiceStatus(String service, ServiceStatus newStatus) {
        return servicesManager.updateServiceStatus(service, newStatus);
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

    public ArrayList<Service> getServices() {
        return servicesManager.getServices();
    }

    public Service getService(String service) {
        return servicesManager.getServiceByType(service);
    }

    public double getProviderRating() {
        return reviewsManager.calculateAverageRating();
    }

    public double getServiceRating(String service) {
        return reviewsManager.calculateAverageRating(service);
    }


    public static int findProviderIdByNameAndAddress(String providerName, String address) {
        int providerId = 0;
        //TODO search providerId in database using name and address
        return providerId;
    }

    public Address getAddress() {
        return account.getAddress();
    }
}
