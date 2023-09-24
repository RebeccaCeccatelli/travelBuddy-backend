package backend.bookings.instances.booking;

import backend.bookings.framework.Booking;

import java.util.Map;

public class WiFiHotspotBooking extends Booking {
    @Override
    public int save() {
        return 0;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    protected boolean setServiceSpecificInfo(Object... serviceSpecificInformation) {
        return false;
    }

    @Override
    protected boolean modifyServiceSpecificInfo(Map<String, Object> modifications) {
        return false;
    }
}
