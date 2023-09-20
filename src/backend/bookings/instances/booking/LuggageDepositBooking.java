package backend.bookings.instances.booking;

import backend.bookings.framework.Booking;

import java.util.Map;

public class LuggageDepositBooking extends Booking {
    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        return false;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        return false;
    }

    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        return false;
    }

    @Override
    protected boolean modifyServiceSpecificInformation(Map<String, Object> modifications) {
        return false;
    }

    @Override
    protected boolean updateServiceSpecificInformationInDB(Map<String, Object> modifications) {
        return false;
    }
}
