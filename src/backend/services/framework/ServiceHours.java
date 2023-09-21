package backend.services.framework;

import java.sql.Time;

class ServiceHours {
    Time openingTime;
    Time closingTime;

    ServiceHours(Time openingTime, Time closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }
}
