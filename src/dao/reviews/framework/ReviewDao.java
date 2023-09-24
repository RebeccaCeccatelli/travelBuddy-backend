package dao.reviews.framework;

import backend.reviews.framework.Review;

import java.util.ArrayList;

public abstract class ReviewDao {

    public int save(int bookingId, String reviewText, Double rating, Object... serviceSpecificInfo) {
        int id = saveGeneralInfo(bookingId, reviewText, rating);
        if (id != 0) {
            saveServiceSpecificInfo(id, serviceSpecificInfo);
        }
        return id;
    }

    public boolean remove(int reviewId) {
        boolean removed = false;
        if (removeServiceSpecifInfo(reviewId)) {
            if (removeGeneralInfo(reviewId)) {
                removed = true;
            }
        }
        return removed;
    }

    public boolean update(int reviewId, String reviewText, Double rating, Object... specificServiceInfo) {
        boolean updated = false;
        if (updateGeneralInfo(reviewId, reviewText, rating)) {
            if (updateServiceSpecificInfo(reviewId, specificServiceInfo)) {
                updated = true;
            }
        }
        return updated;
    }

    public static ArrayList<Review> loadAllReviews(int accountId, String accountType) {
        ArrayList<Review> reviews = new ArrayList<>();
        //TODO load from database
        return reviews;
    }

    private int saveGeneralInfo(int bookingId, String reviewText, Double rating) {
        int id = 0;
        //TODO implement saving in the database
        return id;
    }

    protected abstract void saveServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo);

    protected void saveServiceType(int id, String service) {
        //TODO to be implemented, to be called in saveServiceSpecificInfo
    }
    private boolean removeGeneralInfo(int reviewId) {
        boolean removed = false;
        //TODO remove from database
        return removed;
    }

    protected abstract boolean removeServiceSpecifInfo(int reviewId);

    private boolean updateGeneralInfo(int reviewId, String reviewText, Double rating) {
        boolean updated = false;
        //TODO update in database
        return updated;
    }

    protected abstract boolean updateServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo);

    private static String getTableName() {
        return "\"Review\".\"Review\"";
    }

    private static ArrayList<Integer> getAllBookingIds(int accountId, String accountType) {
        ArrayList<Integer> bookingIds = new ArrayList<>();
        //TODO cross info with Booking table and return ids related to that accountId and type
        // (e.g. 3, User). To be used in loadAllReviews()
        return bookingIds;
    }

    protected abstract Object loadServiceSpecificInfo(int reviewId);
}
