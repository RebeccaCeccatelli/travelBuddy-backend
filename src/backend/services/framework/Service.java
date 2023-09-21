package backend.services.framework;

import backend.payment.methods.framework.PaymentMethod;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

public abstract class Service {

    protected int providerId;
    ServiceHours hours;
    ServicePricing pricing;
    protected String optionalNotes;
    ServiceStatus status = ServiceStatus.INACTIVE;


    public boolean create(int providerId, Time openingTime, Time closingTime, String paymentPolicy,
                          double price, String optionalNotes, Object... serviceSpecificInformation) {
        boolean created = false;
        if (setGeneralInformation(providerId, openingTime, closingTime, paymentPolicy, price, optionalNotes)) {
            if (setServiceSpecificInformation(serviceSpecificInformation)) {
                int id = saveGeneralInformationInDB();
                if (id != 0) {
                    if (saveServiceSpecificInformationInDB()) {
                        created = true;
                        setStatus(ServiceStatus.ACTIVE);
                    }
                }
            }
        }
        return created;
    }

    private boolean setGeneralInformation(int providerId, Time openingTime, Time closingTime, String paymentPolicy,
                                            double price, String optionalNotes) {
        boolean valid = false;
        this.providerId = providerId;
        if (areServiceHoursValid(openingTime, closingTime)) {
            this.hours = new ServiceHours(openingTime, closingTime);

            this.pricing = new ServicePricing(paymentPolicy, price);
            this.optionalNotes = optionalNotes;
            valid = true;
        }
        return valid;
    }

    protected abstract String getServiceType();

    protected abstract boolean setServiceSpecificInformation(Object... serviceSpecificInformation);

    private int saveGeneralInformationInDB() {
        int id = 0;
        //TODO save in database
        return id;
    }

    protected abstract boolean saveServiceSpecificInformationInDB();

    public boolean cancel() {
        boolean cancelled = false;
        if (cancelServiceSpecificInformationFromDB()) {
            if (cancelGeneralInformationFromDB()) {
                setStatus(ServiceStatus.CANCELLED);
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

    private boolean modifyGeneralInformation(Map<String, Object> modifications) {
        boolean modified = false;

        if (modifications.containsKey("openingTime")) {
            Time time = (Time) modifications.get("openingTime");
            if (areServiceHoursValid(time, this.hours.closingTime)) {
                this.hours.openingTime = time;
                modified = true;
            }
        }

        if (modifications.containsKey("closingTime")) {
            Time time = (Time) modifications.get("closingTime");
            if (areServiceHoursValid(this.hours.openingTime, time)) {
                this.hours.closingTime = time;
                modified = true;
            }
        }

        if (modifications.containsKey("paymentPolicy")) {
            this.pricing.paymentPolicy = (String) modifications.get("paymentPolicy");
            modified = true;
        }

        if (modifications.containsKey("price")) {
            this.pricing.price = (Double) modifications.get("price");
            modified = true;
        }

        if (modifications.containsKey("optionalNotes")) {
            this.optionalNotes = (String) modifications.get("optionalNotes");
            modified = true;
        }

        if (modifications.containsKey("serviceStatus")) {
            setStatus((ServiceStatus) modifications.get("serviceStatus"));
            modified = true;
        }
        return modified;
    }

    private boolean updateGeneralInformationInDB() {
        boolean updated = false;
        //TODO save updates in database
        return updated;
    }

    protected abstract boolean modifyServiceSpecificInformation(Map<String, Object> modifications);

    protected abstract boolean updateServiceSpecificInformationInDB();
    private boolean cancelGeneralInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel from db
        return cancelled;
    }

    protected abstract boolean cancelServiceSpecificInformationFromDB();

    public boolean setStatus(ServiceStatus newStatus) {
        boolean modified = false;
        this.status = newStatus;
        if(updateGeneralInformationInDB()) {
            modified = true;
        }
        return modified;
    }

    private boolean areServiceHoursValid(Time openingTime, Time closingTime) {
        boolean valid = false;
        //TODO check time validity
        return valid;
    }
}
