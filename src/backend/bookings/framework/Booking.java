package backend.bookings.framework;

import backend.payment.methods.framework.PaymentMethod;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

public abstract class Booking {
    protected int id;
    protected int userId;
    protected int providerId;
    protected Date date;
    protected Time arrivalTime;
    PaymentMethod paymentMethod;
    protected String optionalNotes;
    BookingStatus status = BookingStatus.INCOMPLETE;

    public boolean create(int userId, int providerId, Date date, Time arrivalTime,
                          PaymentMethod paymentMethod, String optionalNotes,
                          Object... serviceSpecificInformation) {
        boolean created = false;
        if (setGeneralInformation(userId, providerId, date, arrivalTime, paymentMethod, optionalNotes)) {
            if(setServiceSpecificInformation(serviceSpecificInformation)) {
                int id = saveGeneralInformationInDB();
                if(id != 0) {
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
            if (updateGeneralInformationInDB(modifications)) {
                modified = true;
            }
        }
        else if (modifyServiceSpecificInformation(modifications)) {
            if (updateServiceSpecificInformationInDB(modifications)) {
                modified = true;
            }
        }
        return modified;
    }

    private boolean setGeneralInformation(int userId, int providerId, Date date, Time arrivalTime,
                                          PaymentMethod paymentMethod, String optionalNotes) {
        boolean valid = false;
        this.userId = userId;
        this.providerId = providerId;
        if (isDateValid(date)) {
            this.date = date;
            if (isArrivalTimeValid(arrivalTime)) {
                this.arrivalTime = arrivalTime;
            }
            if(isPaymentMethodValid(paymentMethod)) {
                this.paymentMethod = paymentMethod;
                valid = true;
            }
        }

        this.optionalNotes = optionalNotes;
        return valid;
    }

    private int saveGeneralInformationInDB() {
        int id = 0;
        //TODO add to database (using id)
        return id;
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

        if (modifications.containsKey("paymentMethod")) {
            PaymentMethod paymentMethod = (PaymentMethod) modifications.get("paymentMethod");
            if (isPaymentMethodValid(paymentMethod)) {
                this.paymentMethod = paymentMethod;
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

    private boolean updateGeneralInformationInDB(Map<String, Object> modifications) {
        boolean modified = false;
        //TODO modify information in database
        return modified;
    }

    protected  abstract  boolean updateServiceSpecificInformationInDB(Map<String, Object> modifications);

    private boolean isDateValid(Date date) {
        boolean valid = false;
        //TODO check date validity
        return valid;
    }

    private boolean isArrivalTimeValid(Time time) {
        boolean valid = false;
        //TODO check time validity
        return valid;
    }

    private boolean isPaymentMethodValid(PaymentMethod paymentMethod) {
        boolean valid = false;
        //TODO check payment method validity
        return valid;
    }

    void setStatus(BookingStatus newStatus) {
        this.status = newStatus;
    }

    boolean isSuccessful() {
        return this.status == BookingStatus.SUCCESSFUL;
    }
}


