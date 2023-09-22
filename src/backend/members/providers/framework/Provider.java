package backend.members.providers.framework;

import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.manager.ProviderBookingsManager;
import backend.members.common.framework.Account;
import backend.members.common.framework.Address;
import backend.members.common.instances.ProviderAccount;
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
    Account account = new ProviderAccount();
    protected ServicesManager servicesManager;
    protected BookingsManager bookingsManager;
    protected ReviewsManager reviewsManager;

    public Provider register(String email, String password, String name, Address address,
                            String phoneNumber, Object... memberSpecificInformation) {
        if (account.register(email, password, name, address, phoneNumber, memberSpecificInformation)) {
            setManagers();
        }
        return this;
    }

    public Provider login(String email, String password){
        if (account.login(email, password)) {
            setManagers();
        }
        return this;
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
