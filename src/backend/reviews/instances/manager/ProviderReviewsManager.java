package backend.reviews.instances.manager;

import backend.bookings.framework.BookingsManager;
import backend.reviews.framework.ReviewsManager;

public class ProviderReviewsManager extends ReviewsManager {
    public ProviderReviewsManager(int accountId, BookingsManager bookingsManager) {
        super(accountId, bookingsManager);
    }

    @Override
    protected void loadReviewsFromDatabase(int accountId) {

    }
}
