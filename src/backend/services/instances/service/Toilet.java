package backend.services.instances.service;

import backend.services.framework.Service;

import java.util.Map;

public class Toilet extends Service {
    boolean disabilityFriendly;
    boolean changingTable;
    boolean shower;

    @Override
    public String getServiceType() {
        return "Toilet";
    }

    @Override
    protected boolean setServiceSpecificInformation(Object... serviceSpecificInformation) {
        boolean valid = false;
        if (serviceSpecificInformation != null && serviceSpecificInformation.length > 0) {
            this.disabilityFriendly = (Boolean) serviceSpecificInformation[0];
            this.changingTable = (Boolean) serviceSpecificInformation[1];
            this.shower = (Boolean) serviceSpecificInformation[2];
            valid = true;
        }
        return  valid;
    }

    @Override
    protected boolean saveServiceSpecificInformationInDB() {
        boolean saved = false;
        //TODO save in database
        return saved;
    }

    @Override
    protected boolean modifyServiceSpecificInformation(Map<String, Object> modifications) {
        boolean modified = false;
        if (modifications.containsKey("disabilityFriendly")) {
            this.disabilityFriendly = (Boolean) modifications.get("disabilityFriendly");
            modified = true;
        }
        if (modifications.containsKey("changingTable")) {
            this.changingTable = (Boolean) modifications.get("changingTable");
            modified = true;
        }
        if (modifications.containsKey("shower")) {
            this.shower = (Boolean) modifications.get("shower");
            modified = true;
        }
        return modified;
    }

    @Override
    protected boolean updateServiceSpecificInformationInDB() {
        boolean updated = false;
        //TODO update in database
        return updated;
    }

    @Override
    protected boolean cancelServiceSpecificInformationFromDB() {
        boolean cancelled = false;
        //TODO cancel from database
        return cancelled;
    }

    @Override
    protected String setAdditionalInfoInMessage(String message) {
        message += "Disability friendly: " + (disabilityFriendly ? "Yes" : "No") + ".\n";
        message += "ChangingTable: " + (changingTable ? "Yes" : "No") + ".\n";
        message += "Shower: " + (shower ? "Yes" : "No") + ".\n";
        return message;
    }
}
