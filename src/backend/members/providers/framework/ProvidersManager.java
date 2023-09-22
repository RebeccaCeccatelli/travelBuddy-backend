package backend.members.providers.framework;

import backend.members.common.framework.Address;

import java.util.ArrayList;

public class ProvidersManager {
    ArrayList<Provider> providers = new ArrayList<>();

    public ProvidersManager() {
        loadProvidersFromDatabase();
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

    private void loadProvidersFromDatabase() {
        //TODO load from database by service
    }

    public Provider register(String email, String password, String name, Address address,
                    String phoneNumber, Object... memberSpecificInformation) {
        Provider provider = new Provider().register(
                email, password, name, address, phoneNumber, memberSpecificInformation);
        if (provider != null) {
            providers.add(provider);
        }
        return provider;
    }

    public Provider login(String email, String password) {
        return new Provider().login(email, password);
    }

}
