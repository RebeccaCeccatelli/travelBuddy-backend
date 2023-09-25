package backend.accounts.providers.framework;

import data.access.accounts.instances.ProviderAccountDao;

import java.util.ArrayList;

public class ProvidersManager {
    ArrayList<Provider> providers = new ArrayList<>();

    public ProvidersManager() {
        providers = loadAllProviders();
    }

    private ArrayList<Provider> loadAllProviders() {
        return new ProviderAccountDao().loadAllProviders();
    }

    public ArrayList<Provider> getServiceProviders(String service) {
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




}
