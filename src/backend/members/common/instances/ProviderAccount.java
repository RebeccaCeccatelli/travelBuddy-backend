package backend.members.common.instances;

import backend.members.common.framework.Account;

public class ProviderAccount extends Account {
    protected String licenseId;
    protected String businessId;

    @Override
    protected boolean accountExists(String email) {
        return false;
    }

    @Override
    protected boolean checkPasswordMatch(String password) {
        return false;
    }

    @Override
    protected boolean loadFromDatabase() {
        return false;
    }

    @Override
    protected int saveInDatabase() {
        return 0;
    }

    @Override
    protected boolean setMemberSpecificInformation(Object... memberSpecificInformation) {
        return false;
    }

    private boolean isLicenseIdValid(String licenseId) {
        return false;
    }

    private boolean isBusinessIdValid(String businessId) {
        return false;
    }
}
