package dao.bookings.instances;

import dao.bookings.framework.BookingDao;

public class LuggageDepositBookingDao extends BookingDao {
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
}
