import backend.bookings.instances.manager.UserBookingsManager;
import backend.reviews.instances.manager.UserReviewsManager;

public class Main {
    public static void main(String[] args) {

        UserBookingsManager ubm = new UserBookingsManager(16);
        UserReviewsManager urm = new UserReviewsManager(16, ubm);
        urm.addReview(8, "Tutto buono", 5.0, 1.0,2.0,3.0,4.0,5.0);
    }
}
