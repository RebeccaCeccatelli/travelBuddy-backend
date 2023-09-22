import backend.accounts.providers.framework.Provider;
import backend.accounts.providers.framework.ProvidersManager;
import dao.accounts.providers.ProviderDao;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ProviderDao providerDao = new ProviderDao();

        ProvidersManager providersManager = new ProvidersManager();
    }
}
