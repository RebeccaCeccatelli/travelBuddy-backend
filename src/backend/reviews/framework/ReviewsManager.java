package backend.reviews.framework;
import backend.bookings.framework.Booking;
import backend.bookings.framework.BookingsManager;
import backend.bookings.instances.booking.*;
import backend.reviews.instances.review.*;

import java.util.ArrayList;
import java.util.Objects;

public abstract class ReviewsManager {
    protected ArrayList<Review> reviews = new ArrayList<>();
    BookingsManager bookingsManager;

    public ReviewsManager(int accountId, BookingsManager bookingsManager) {
        loadReviewsFromDatabase(accountId);
        this.bookingsManager = bookingsManager;
    }

    protected abstract void loadReviewsFromDatabase(int accountId);

    public boolean addReview(int bookingId, String reviewText, double rating,
                             Object... serviceSpecificInformation) {
        boolean created = false;

        Review review;
        Booking booking = bookingsManager.findBookingById(bookingId);
        if (booking != null) {
            if (!reviewAlreadyExists(bookingId)) {
                if (bookingsManager.isBookingSuccessful(bookingId)) {
                    if (booking instanceof ToiletBooking) {
                        review = new ToiletReview();
                    } else if (booking instanceof WiFiHotspotBooking) {
                        review = new WiFiHotspotReview();
                    } else if (booking instanceof LuggageDepositBooking) {
                        review = new LuggageDepositReview();
                    } else if (booking instanceof ParkingLotBooking) {
                        review = new ParkingLotReview();
                    } else if (booking instanceof TouristicAttractionBooking) {
                        review = new TouristicAttractionReview();
                    } else if (booking instanceof DiningOptionBooking) {
                        review = new DiningOptionReview();
                    } else if (booking instanceof AccomodationBooking) {
                        review = new AccomodationReview();
                    }
                    else {
                        return created;
                    }
                    created = review.create(bookingId, reviewText, rating, serviceSpecificInformation);
                    if (created) {
                        reviews.add(review);
                    }
                }
            }
        }
        return created;
    }

    public boolean cancelReview(int bookingId) {
        boolean cancelled = false;
        Review review = findReviewByBookingId(bookingId);
        if (review != null) {
            if (review.cancel()) {
                reviews.remove(review);
                cancelled = true;
            }
        }
        return cancelled;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    private boolean reviewAlreadyExists(int bookingId) {
        boolean reviewed = false;
        Review review = findReviewByBookingId(bookingId);
        if (review != null) {
            reviewed = true;
        }
        return reviewed;
    }

    private Review findReviewByBookingId(int bookingId) {
        for (Review review : reviews) {
            if (review.bookingId == bookingId) {
                return review;
            }
        }
        return null;
    }

    public double calculateAverageRating() {
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            for (Review review : reviews) {
                averageRating += review.rating;
            }
            averageRating = averageRating / reviews.size();
        }
        return averageRating;
    }

    public double calculateAverageRating(String service) {
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            int serviceReviews = 0;
            for (Review review : reviews) {
                if (Objects.equals(review.getServiceReviewed(), service)) {
                    averageRating += review.rating;
                    serviceReviews++;
                }
            }

            if (serviceReviews > 0) {
                averageRating = averageRating / serviceReviews;
            } else {
                averageRating = 0;
            }
        }
        return averageRating;
    }
}

