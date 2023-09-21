package backend.reviews.instances.review;

import backend.reviews.framework.Review;

public class WiFiHotspotReview extends Review {
    @Override
    public String getServiceReviewed() {
        return "Wifi hotspot";
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
