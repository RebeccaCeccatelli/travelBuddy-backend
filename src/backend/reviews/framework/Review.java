package backend.reviews.framework;

import java.util.Map;

public abstract class Review {
    protected int id;

    protected int bookingId;
    protected String reviewText;
    protected Double rating;

    public void initialize(int id, int bookingId, String reviewText, Double rating, Object... serviceSpecificInfo) {
        if (id != 0) {
            setId(id);
            setInfo(bookingId, reviewText, rating, serviceSpecificInfo);
        }
    }

    public boolean create(int bookingId, String reviewText, Double rating, Object... serviceSpecificInfo) {
        boolean created = false;
        if (setInfo(bookingId, reviewText, rating, serviceSpecificInfo)) {
           int id = save();
           if (id != 0) {
               setId(id);
               created = true;
           }
        }
        return created;
    }

    public boolean remove() {
        boolean removed = false;
        if (delete()) {
            removed = true;
        }
        return removed;
    }

    private void setId(int id) {
        this.id = id;
    }

    private boolean setInfo(int bookingId, String reviewText, Double rating, Object... serviceSpecificInfo) {
        boolean set = false;
        if (setGeneralInfo(bookingId, reviewText, rating)) {
            if (setServiceSpecificInfo(serviceSpecificInfo)) {
                set = true;
            }
        }
        return set;
    }

    private boolean setGeneralInfo(int bookingId, String reviewText, double rating) {
        boolean valid = false;
        this.bookingId = bookingId;
        this.reviewText = reviewText;
        if (isRatingValid(rating)) {
            this.rating = rating;
            valid = true;
        }
        return valid;
    }

    protected boolean isRatingValid(double rating) {
        boolean valid = false;
        if (rating >= 1.0 && rating <= 10.0) {
            double remainder = rating % 0.5;
            if (remainder == 0.0) {
                valid = true;
            }
        }
        return valid;
    }

    protected abstract boolean setServiceSpecificInfo(Object... serviceSpecificInformation);

    protected abstract int save();

    protected abstract boolean delete();

    public abstract String getServiceReviewed();
}
