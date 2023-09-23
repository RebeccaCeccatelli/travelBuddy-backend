package backend.bookings.framework;

import dao.bookings.BookingDao;

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
    BookingStatus status = BookingStatus.INCOMPLETE;

    public void initialize(int id, int userId, int providerId, Date date, Time arrivalTime, String optionalNotes, BookingStatus status, Object... serviceSpecificInformation) {
        setId(id);
        if (setGeneralInformation(userId, providerId, date, arrivalTime, optionalNotes)) {
            setStatus(status);
            setServiceSpecificInformation(serviceSpecificInformation);
        }
    }

    public boolean create(int userId, int providerId, Date date, Time arrivalTime,
                          String optionalNotes,
                          Object... serviceSpecificInformation) {
        boolean created = false;
        if (setGeneralInformation(userId, providerId, date, arrivalTime, optionalNotes)) {
            if(setServiceSpecificInformation(serviceSpecificInformation)) {
                int id = saveGeneralInformationInDB();
                if(id != 0) {
                    setId(id);
                    if (saveServiceSpecificInformationInDB()) {
                        setId(id);
                        created = true;
                        setStatus(BookingStatus.FUTURE);
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
                setStatus(BookingStatus.CANCELLED);
                cancelled = true;
            }
        }
        return cancelled;
    }

    public boolean modify(Map<String, Object> modifications) {
        boolean modified = false;
        if (modifyGeneralInformation(modifications)) {
            if (updateGeneralInformationInDB()) {
                modified = true;
            }
        }
        if (modifyServiceSpecificInformation(modifications)) {
            if (updateServiceSpecificInformationInDB()) {
                modified = true;
            }
        }
        return modified;
    }

    private boolean setGeneralInformation(int userId, int providerId, Date date, Time arrivalTime,
                                          String optionalNotes) {
        boolean valid = false;
        this.userId = userId;
        this.providerId = providerId;
        if (isDateValid(date)) {
            this.date = date;
            if (isArrivalTimeValid(arrivalTime)) {
                this.arrivalTime = arrivalTime;
                valid = true;
            }
        }

        this.optionalNotes = optionalNotes;
        return valid;
    }

    private int saveGeneralInformationInDB() {
        return new BookingDao().saveGeneralBookingInformation(userId, providerId, date, arrivalTime, optionalNotes, status);
    }

    protected abstract boolean setServiceSpecificInformation(Object... serviceSpecificInformation);


    protected abstract boolean saveServiceSpecificInformationInDB();

    private void setId(int id) {
        this.id = id;
    }

    private boolean cancelGeneralInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel basic information from database (using id)
        return cancelled;
    }

    protected abstract boolean cancelServiceSpecificInformationFromDB();

    private boolean modifyGeneralInformation(Map<String, Object> modifications) {
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

    protected abstract boolean modifyServiceSpecificInformation(Map<String, Object> modifications);

    private boolean updateGeneralInformationInDB() {
        boolean updated = false;
        //TODO modify information in database
        return updated;
    }

    protected  abstract  boolean updateServiceSpecificInformationInDB();

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

    public void setStatus(BookingStatus newStatus) {
        this.status = newStatus;
    }

    boolean isSuccessful() {
        return this.status == BookingStatus.SUCCESSFUL;
    }
}


