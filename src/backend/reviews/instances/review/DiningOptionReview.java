package backend.reviews.instances.review;

import backend.reviews.framework.Review;

public class DiningOptionReview extends Review {
    @Override
    public String getServiceReviewed() {
        return "Dining option";
    }

    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        return false;
    }

    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        return false;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        return false;
    }
}
