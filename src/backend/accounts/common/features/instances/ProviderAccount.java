package backend.accounts.common.features.instances;

import backend.accounts.common.features.framework.Account;
import data.access.accounts.instances.ProviderAccountDao;

public class ProviderAccount extends Account {
    public String licenseId;
    public String businessId;

    @Override
    public int save() {
        return new ProviderAccountDao().save(name, email, password, address, phoneNumber,
                licenseId, businessId);
    }

    @Override
    protected ProviderAccount load() {
        return new ProviderAccountDao().load(email);
    }

    @Override
    protected boolean checkAccountExists(String email) {
        return new ProviderAccountDao().checkAccountExists(email);
    }

    @Override
    protected boolean checkPasswordsMatch(String email, String password) {
        return new ProviderAccountDao().checkPasswordsMatch(email, password);
    }

    @Override
    protected boolean setAccountSpecificInfo(Object... memberSpecificInformation) {
        boolean valid = false;
        String licenseId = (String) memberSpecificInformation[0];
        if (isLicenseIdValid(licenseId)) {
            this.licenseId = licenseId;

            String businessId = (String) memberSpecificInformation[1];
            if (isBusinessIdValid(businessId)) {
                this.businessId = businessId;
                valid = true;
            }
        }

        return valid;
    }

    private boolean isLicenseIdValid(String licenseId) {
        boolean valid = true;
        //TODO to be implemented
        return valid;
    }

    private boolean isBusinessIdValid(String businessId) {
        boolean valid = true;
        //TODO to be implemented
        return valid;
    }
}
