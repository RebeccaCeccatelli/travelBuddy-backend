package backend.accounts.providers.framework;

import dao.accounts.providers.ProviderDao;

import java.util.ArrayList;

public class ProvidersManager {
    ArrayList<Provider> providers = new ArrayList<>();

    public ProvidersManager() {
        loadAllProviders();
    }

    public ArrayList<Provider> getProvidersByService(String service) {
        ArrayList<Provider> serviceProviders = new ArrayList<>();
        if (providers != null) {
            for (Provider provider : providers) {
                if (provider.getService(service) != null) {
                    serviceProviders.add(provider);
                }
            }
        }
        return serviceProviders;
    }

    private void loadAllProviders() {
        providers = new ProviderDao().loadAllProviders();
    }


}
