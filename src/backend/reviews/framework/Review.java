package backend.reviews.framework;

public abstract class Review {
    protected int bookingId;
    protected int userId;
    protected String reviewText;
    protected double rating;

    public boolean create(int bookingId, String reviewText, double rating, Object... serviceSpecificInformation) {
        boolean created = false;
        if (setGeneralInformation(bookingId, reviewText, rating)) {
            if (setServiceSpecificInformation(serviceSpecificInformation)) {
               int id = saveGeneralInformationInDB();
               if (id != 0) {
                   if (saveServiceSpecificInformationInDB()) {
                       created = true;
                   }
               }
            }
        }
        return created;
    }

    public boolean cancel() {
        boolean cancelled = false;
        if (cancelServiceSpecificInformationFromDB()) {
            if (cancelGeneralInformationFromDB()) {
                cancelled = true;
            }
        }
        return cancelled;
    }

    protected abstract boolean cancelServiceSpecificInformationFromDB();

    private boolean cancelGeneralInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel basic information from database (using id)
        return cancelled;
    }

    private boolean setGeneralInformation(int bookingId, String reviewText, double rating) {
        boolean valid = false;
        this.bookingId = bookingId;
        this.reviewText = reviewText;
        if (isRatingValid(rating)) {
            this.rating = rating;
            valid = true;
        }
        return valid;
    }

    protected abstract boolean setServiceSpecificInformation(Object... serviceSpecificInformation);

    protected boolean isRatingValid(double rating) {
        boolean valid = false;
        if (rating >= 1.0 && rating <= 10.0) {
            double remainder = rating % 0.5;
            if (remainder == 0.0) {
                valid = true;
            }
        }
        return valid;
    }

    private int saveGeneralInformationInDB() {
        int id = 0;
        //TODO add to database (using id)
        return id;
    }

    protected abstract boolean saveServiceSpecificInformationInDB();


}
