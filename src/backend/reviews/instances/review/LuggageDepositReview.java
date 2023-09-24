package backend.reviews.instances.review;

import backend.reviews.framework.Review;

public class LuggageDepositReview extends Review {
    @Override
    public String getServiceReviewed() {
        return "Luggage deposit";
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
