package backend.reviews.instances.manager;

import backend.bookings.framework.BookingsManager;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
import data.access.reviews.framework.ReviewDao;

import java.util.ArrayList;

public class ProviderReviewsManager extends ReviewsManager {
    public ProviderReviewsManager(int accountId, BookingsManager bookingsManager) {
        super(accountId, bookingsManager);
    }

    @Override
    protected ArrayList<Review> loadAllReviews(int accountId) {
        return ReviewDao.loadAllReviews(accountId, "Provider");
    }
}
