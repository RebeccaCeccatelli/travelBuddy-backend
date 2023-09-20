package backend.reviews.instances.manager;

import backend.bookings.framework.BookingsManager;
import backend.reviews.framework.ReviewsManager;

public class UserReviewsManager extends ReviewsManager {
    public UserReviewsManager(int accountId, BookingsManager bookingsManager) {
        super(accountId, bookingsManager);
    }

    @Override
    protected void loadReviewsFromDatabase(int accountId) {

    }
}
