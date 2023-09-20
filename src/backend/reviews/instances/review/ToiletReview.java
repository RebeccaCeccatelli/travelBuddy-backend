package backend.reviews.instances.review;

import backend.reviews.framework.Review;

public class ToiletReview extends Review {
    double cleanliness;
    double offeredProductsQuality;
    double bathroomSize;
    double quietness;
    double accessibility;

    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        boolean valid = false;
        if (serviceSpecificInformation != null && serviceSpecificInformation.length > 0) {
            rating = (Double) serviceSpecificInformation[0];
            if (isRatingValid(rating)) {
                this.cleanliness = rating;
            }
            rating = (Double) serviceSpecificInformation[1];
            if (isRatingValid(rating)) {
                this.offeredProductsQuality = rating;
            }
            rating = (Double) serviceSpecificInformation[2];
            if (isRatingValid(rating)) {
                this.bathroomSize = rating;
            }
            rating = (Double) serviceSpecificInformation[3];
            if (isRatingValid(rating)) {
                this.quietness = rating;
            }
            rating = (Double) serviceSpecificInformation[4];
            if(isRatingValid(rating)) {
                this.accessibility = rating;
            }
        }
        return valid;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        boolean saved = false;
        //TODO add to database
        return saved;
    }


    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel service specific information from database
        return cancelled;
    }

}
