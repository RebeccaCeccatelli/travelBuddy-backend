package backend.services.instances.service;

import backend.services.framework.Service;

import java.util.Map;

public class ParkingLot extends Service {

    @Override
    protected String getServiceType() {
        return "Parking lot";
    }

    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        return false;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        return false;
    }

    @Override
    protected boolean modifyServiceSpecificInformation(Map<String, Object> modifications) {
        return false;
    }

    @Override
    protected boolean updateServiceSpecificInformationInDB() {
        return false;
    }

    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        return false;
    }
}
