package dao.reviews.instances;

import dao.reviews.framework.ReviewDao;

public class ParkingLotReviewDao extends ReviewDao {
    @Override
    protected void saveServiceSpecificInfo(int reviewId, Object... serviceSpecificInfo) {

    }

    @Override
    protected boolean removeServiceSpecifInfo(int reviewId) {
        return false;
    }

    @Override
    protected Object loadServiceSpecificInfo(int reviewId) {
        return null;
    }
}
