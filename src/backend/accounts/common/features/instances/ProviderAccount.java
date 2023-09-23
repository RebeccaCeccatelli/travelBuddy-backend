package backend.accounts.common.features.instances;

import backend.accounts.common.features.framework.Account;
import dao.accounts.providers.ProviderDao;

public class ProviderAccount extends Account {
    public String licenseId;
    public String businessId;

    @Override
    protected boolean checkAccountExists(String email) {
        return new ProviderDao().checkAccountExists(email);
    }

    @Override
    protected boolean checkPasswordsMatch(String email, String password) {
        return new ProviderDao().checkPasswordsMatch(email, password);
    }

    @Override
    protected ProviderAccount load() {
        return new ProviderDao().loadAccount(email);
    }

    @Override
    public int save() {
        return new ProviderDao().saveAccount(name, email, password, address, phoneNumber,
                licenseId, businessId);
    }

    @Override
    protected boolean setAccountSpecificInformation(Object... memberSpecificInformation) {
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
        return true;
    }

    private boolean isBusinessIdValid(String businessId) {
        return true;
    }
}
