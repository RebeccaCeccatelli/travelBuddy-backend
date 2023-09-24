package backend.reviews.instances.review;

import backend.reviews.framework.Review;
import dao.reviews.instances.ToiletReviewDao;

public class ToiletReview extends Review {
    Double cleanliness;
    Double productsQuality;
    Double bathroomSize;
    Double quietness;
    Double accessibility;

    @Override
    protected boolean setServiceSpecificInfo(Object... serviceSpecificInformation) {
        boolean valid = false;
        if (serviceSpecificInformation != null && serviceSpecificInformation.length > 0) {
            Double cleanliness = (Double) serviceSpecificInformation[0];
            if (isRatingValid(cleanliness)) {
                this.cleanliness = cleanliness;

                Double productsQuality = (Double) serviceSpecificInformation[1];
                if (isRatingValid(productsQuality)) {
                    this.productsQuality = productsQuality;

                    Double bathroomSize = (Double) serviceSpecificInformation[2];
                    if (isRatingValid(bathroomSize)) {
                        this.bathroomSize = bathroomSize;

                        Double quietness = (Double) serviceSpecificInformation[3];
                        if (isRatingValid(quietness)) {
                            this.quietness = quietness;

                            Double accessibility = (Double) serviceSpecificInformation[4];
                            if(isRatingValid(accessibility)) {
                                this.accessibility = accessibility;

                                valid = true;
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }

    @Override
    protected int save() {
        return new ToiletReviewDao().save(bookingId, reviewText, rating, cleanliness,
                productsQuality, bathroomSize, quietness, accessibility);
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
