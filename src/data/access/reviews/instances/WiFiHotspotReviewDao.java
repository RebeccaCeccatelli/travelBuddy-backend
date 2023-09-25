package data.access.reviews.instances;

import data.access.reviews.framework.ReviewDao;

public class WiFiHotspotReviewDao extends ReviewDao {
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
