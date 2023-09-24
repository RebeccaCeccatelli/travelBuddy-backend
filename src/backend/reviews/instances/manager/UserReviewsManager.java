package backend.reviews.instances.manager;

import backend.bookings.framework.BookingsManager;
import backend.reviews.framework.Review;
import backend.reviews.framework.ReviewsManager;
import dao.reviews.framework.ReviewDao;

import java.util.ArrayList;

public class UserReviewsManager extends ReviewsManager {
    public UserReviewsManager(int accountId, BookingsManager bookingsManager) {
        super(accountId, bookingsManager);
    }

    @Override
    protected ArrayList<Review> loadAllReviews(int accountId) {
        return ReviewDao.loadAllReviews(accountId, "User");
    }
}
