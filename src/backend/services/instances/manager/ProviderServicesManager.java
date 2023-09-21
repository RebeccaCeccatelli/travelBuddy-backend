package backend.services.instances.manager;

import backend.services.framework.ServicesManager;

public class ProviderServicesManager extends ServicesManager {
    public ProviderServicesManager(int accountId) {
        super(accountId);
    }

    @Override
    protected void loadServicesFromDatabase(int accountId) {

    }
}
