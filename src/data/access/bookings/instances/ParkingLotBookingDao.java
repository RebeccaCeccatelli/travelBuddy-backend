package data.access.bookings.instances;

import data.access.bookings.framework.BookingDao;

public class ParkingLotBookingDao extends BookingDao {
    @Override
    protected void saveServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo) {

    }

    @Override
    protected boolean removeServiceSpecificInfo(int bookingId) {
        return false;
    }

    @Override
    protected boolean updateServiceSpecificInfo(int bookingId, Object... serviceSpecificInfo) {
        return false;
    }

    @Override
    public Object loadServiceSpecificInfo(int bookingId) {
        return null;
    }
}
