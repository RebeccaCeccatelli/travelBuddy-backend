package backend.bookings.framework;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

public abstract class Booking {
    protected int id;
    protected int userId;
    protected int providerId;
    protected Date date;
    protected Time arrivalTime;
    protected String optionalNotes;
    protected BookingStatus status = BookingStatus.INCOMPLETE;

    public void initialize(int id, int userId, int providerId, Date date, Time arrivalTime, String optionalNotes,
                           BookingStatus status, Object... serviceSpecificInfo) {
        if (id != 0) {
            setId(id);
        }
        if (setInfo(userId, providerId, date, arrivalTime, optionalNotes, serviceSpecificInfo)) {
            setStatus(status);
        }
    }

    public boolean create(int userId, int providerId, Date date, Time arrivalTime, String optionalNotes,
                          Object... serviceSpecificInformation) {
        boolean created = false;

        if (setInfo(userId, providerId, date, arrivalTime, optionalNotes, serviceSpecificInformation)) {
            setStatus(BookingStatus.FUTURE);
            int id = save();
            if (id != 0) {
                    setId(id);
                    created = true;
            }
        }
        return created;
    }

    public boolean remove() {
        boolean removed = false;
        if (delete()) {
            setStatus(BookingStatus.CANCELLED);
            removed = true;
        }
        return removed;
    }

    public boolean modify(Map<String, Object> modifications) {
        boolean modified = false;
        if (modifyGeneralInfo(modifications)) {
                modified = true;
        }
        if (modifyServiceSpecificInfo(modifications)) {
                modified = true;
        }
        if (!update()) {
            modified = false;
        }
        return modified;
    }

    protected abstract int save();

    protected abstract boolean delete();

    public abstract boolean update();

    private boolean setInfo(int userId, int providerId, Date date, Time arrivalTime, String optionalNotes,
                            Object... serviceSecificInfo) {
        boolean set = false;
        if (setGeneralInfo(userId, providerId, date, arrivalTime, optionalNotes)) {
            if (setServiceSpecificInfo(serviceSecificInfo)) {
                set = true;
            }
        }
        return set;
    }

    private boolean setGeneralInfo(int userId, int providerId, Date date, Time arrivalTime,
                                   String optionalNotes) {
        boolean valid = false;
        if (userId != 0) {
            this. userId = userId;

            if (providerId != 0) {
                this.providerId = providerId;

                if (isDateValid(date)) {
                    this.date = date;

                    if (isArrivalTimeValid(arrivalTime)) {
                        this.arrivalTime = arrivalTime;

                        this.optionalNotes = optionalNotes;}
                        valid = true;
                    }
                }
        }
        return valid;
    }

    protected abstract boolean setServiceSpecificInfo(Object... serviceSpecificInformation);

    private boolean modifyGeneralInfo(Map<String, Object> modifications) {
        boolean modified = false;

        if (modifications.containsKey("date")) {
            Date date = (Date) modifications.get("date");
            if (isDateValid(date)) {
                this.date = date;
                modified = true;
            }
        }

        if (modifications.containsKey("arrivalTime")) {
            Time time = (Time) modifications.get("arrivalTime");
            if (isArrivalTimeValid(time)) {
                this.arrivalTime = time;
                modified = true;
            }
        }

        if (modifications.containsKey("optionalNotes")) {
            this.optionalNotes = (String) modifications.get("optionalNotes");
            modified = true;
        }
        return modified;
    }

    protected abstract boolean modifyServiceSpecificInfo(Map<String, Object> modifications);

    private void setId(int id) {
        this.id = id;
    }

    public void setStatus(BookingStatus newStatus) {
        this.status = newStatus;
    }

    protected boolean isSuccessful() {
        return this.status == BookingStatus.SUCCESSFUL;
    }

    private boolean isDateValid(Date date) {
        boolean valid = true;
        //TODO check date validity
        return valid;
    }

    private boolean isArrivalTimeValid(Time time) {
        boolean valid = true;
        //TODO check time validity
        return valid;
    }
}


