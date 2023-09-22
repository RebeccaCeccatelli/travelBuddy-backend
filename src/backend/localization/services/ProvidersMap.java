package backend.localization.services;

import backend.accounts.providers.framework.Provider;
import backend.accounts.providers.framework.ProvidersManager;
import backend.accounts.users.framework.User;
import backend.services.framework.Service;

import java.util.ArrayList;

public class ProvidersMap {
    ArrayList<Marker> markers;
    Marker user;

    public void locateUser(User user) {
        Coordinates coordinates = new Coordinates(user.getAddress());
        String popupMessage = user.getUserMessage();

        this.user = new Marker(coordinates, popupMessage);
    }

    public void populate(String serviceType) {

        ProvidersManager providersManager = new ProvidersManager();
        ArrayList<Provider> providers = providersManager.getProvidersByService(serviceType);
        for (Provider provider : providers) {
            Coordinates coordinates = new Coordinates(provider.getAddress());

            Service service = provider.getService(serviceType);
            String popupMessage = service.getServiceInfoMessage();

            Marker marker = new Marker(coordinates, popupMessage);
            markers.add(marker);
        }
    }
}
