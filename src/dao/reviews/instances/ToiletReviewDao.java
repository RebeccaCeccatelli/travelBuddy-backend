package dao.reviews.instances;

import dao.reviews.framework.ReviewDao;

import java.util.ArrayList;

public class ToiletReviewDao extends ReviewDao {
    @Override
    protected void saveServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo) {
        //TODO save in database
    }

    @Override
    protected boolean removeServiceSpecifInfo(int reviewId) {
        boolean removed = false;
        return removed;
    }

    @Override
    protected boolean updateServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo) {
        boolean updated = false;
        return updated;
    }

    @Override
    protected ArrayList<Double> loadServiceSpecificInfo(int reviewId) {
        ArrayList<Double> ratings = new ArrayList<>();
        //TODO get ratings from ToiletReview. To be used in loadAllReviews
        return ratings;
    }
}
