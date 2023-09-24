package backend.reviews.instances.review;

import backend.reviews.framework.Review;

public class AccomodationReview extends Review {
    @Override
    public String getServiceReviewed() {
        return "Accomodation";
    }

    @Override
    protected boolean setServiceSpecificInfo(Object... serviceSpecificInformation) {
        return false;
    }

    @Override
    protected int save() {
        return 0;
    }

    @Override
    protected boolean delete() {
        return false;
    }
}
