package backend.bookings.instances.booking;

import backend.bookings.framework.Booking;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Map;

public class ToiletBooking extends Booking {
    Time endTime;


    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        boolean valid = false;
        if (serviceSpecificInformation != null && serviceSpecificInformation.length > 0) {
            if (isEndTimeValid(endTime)) {
                this.endTime = (Time) serviceSpecificInformation[0];
            }
        }
        return valid;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        boolean saved = false;
        //TODO add to database (using id)
        return saved;
    }

    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel service specific information from database (using id)
        return cancelled;
    }

    @Override
    protected boolean modifyServiceSpecificInformation(Map<String, Object> modifications) {
        boolean modified = false;
        if (modifications.containsKey("endTime")) {
            Time time = (Time) modifications.get("endTime");
            if (isEndTimeValid(time)) {
                this.endTime = time;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    protected boolean updateServiceSpecificInformationInDB(Map<String, Object> modifications) {
        boolean modified = false;
        //TODO modify information in database
        return modified;
    }

    private boolean isEndTimeValid(Time endTime) {
        boolean valid = false;

        Date arrivalDateTime = new Date(arrivalTime.getTime());
        Date endDateTime = new Date(endTime.getTime());

        Calendar calendarArrival = Calendar.getInstance();
        calendarArrival.setTime(arrivalDateTime);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDateTime);

        long diffInMillis = calendarEnd.getTimeInMillis() - calendarArrival.getTimeInMillis();

        long diffInMinutes = diffInMillis / (60 * 1000);

        if (diffInMinutes <= 30) {
            valid = true;
        }

        return valid;
    }
}
