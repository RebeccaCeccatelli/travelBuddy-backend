package backend.services.instances.service;

import backend.services.framework.Service;

import java.util.Map;

public class Accomodation extends Service {

    @Override
    protected String getServiceType() {
        return "Accomodation";
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
