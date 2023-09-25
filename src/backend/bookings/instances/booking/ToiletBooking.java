package backend.bookings.instances.booking;

import backend.bookings.framework.Booking;
import data.access.bookings.instances.ToiletBookingDao;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Map;

public class ToiletBooking extends Booking {
    Time endTime;

    public int save() {
        return new ToiletBookingDao().save(userId, providerId, date, arrivalTime, optionalNotes, status, endTime);
    }

    public boolean delete() {
        return new ToiletBookingDao().remove(id);
    }

    @Override
    public boolean update() {
        return new ToiletBookingDao().update(id, userId, providerId, date, arrivalTime, optionalNotes, status, endTime);
    }

    @Override
    protected boolean setServiceSpecificInfo(Object... serviceSpecificInformation) {
        boolean valid = false;
        if (serviceSpecificInformation != null && serviceSpecificInformation.length > 0) {
            Time time = (Time) serviceSpecificInformation[0];
            if (isEndTimeValid(time)) {
                this.endTime = time;
                valid = true;
            }
        }
        return valid;
    }

    @Override
    protected boolean modifyServiceSpecificInfo(Map<String, Object> modifications) {
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

    private boolean isEndTimeValid(Time endTime) {
        boolean valid = true;

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
