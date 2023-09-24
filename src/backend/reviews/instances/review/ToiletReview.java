package backend.reviews.instances.review;

import backend.reviews.framework.Review;
import dao.reviews.instances.ToiletReviewDao;

public class ToiletReview extends Review {
    Double cleanliness;
    Double offeredProductsQuality;
    Double bathroomSize;
    Double quietness;
    Double accessibility;

    @Override
    protected boolean setServiceSpecificInfo(Object... serviceSpecificInformation) {
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
    protected int save() {
        return new ToiletReviewDao().save(bookingId, reviewText, rating, cleanliness,
                offeredProductsQuality, bathroomSize, quietness, accessibility);
    }

    @Override
    protected boolean delete() {
        return new ToiletReviewDao().remove(id);
    }

    @Override
    public String getServiceReviewed() {
        return "Toilet";
    }
}
