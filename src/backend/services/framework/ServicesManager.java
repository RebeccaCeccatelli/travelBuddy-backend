package backend.services.framework;

import backend.services.instances.service.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public abstract class ServicesManager {
    protected int accountId;
    protected ArrayList<Service> services = new ArrayList<>();

    public ServicesManager(int accountId) {
        this.accountId = accountId;
        loadServicesFromDatabase(accountId);
    }

    protected abstract void loadServicesFromDatabase(int accountId);

    public boolean addService(String type, Time openingTime, Time closingTime,
                              String paymentPolicy, double price, String optionalNotes,
                              Object... serviceSpecificInformation) {
        boolean created = false;
        if (serviceAlreadyExists(type)) {
            return created;
        }

        Service service;
        if (Objects.equals(type, "Toilet")) {
            service = new Toilet();
        } else if (Objects.equals(type, "WiFi hotspot")) {
            service = new WiFiHotspot();
        } else if (Objects.equals(type, "Luggage deposit")) {
            service = new LuggageDeposit();
        } else if (Objects.equals(type, "Parking lot")) {
            service = new ParkingLot();
        } else if (Objects.equals(type, "Touristic attraction")) {
            service = new TouristicAttraction();
        } else if (Objects.equals(type, "Dining option")) {
            service = new DiningOption();
        } else if (Objects.equals(type, "Accomodation")) {
            service = new Accomodation();
        }
        else {
            return created;
        }

        created = service.create(accountId, openingTime, closingTime, paymentPolicy,
                price, optionalNotes, serviceSpecificInformation);
        if (created) {
            services.add(service);
        }
        return created;
    }

    public boolean modifyServiceInformation(String type, Map<String, Object> modifications) {
        boolean modified = false;
        Service service = getServiceByType(type);
        if (service != null) {
            if (service.modify(modifications)) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean cancelService(String type) {
        boolean cancelled = false;
        Service service = getServiceByType(type);
        if (service != null) {
            if (service.cancel()) {
                services.remove(service);
                cancelled = true;
            }
        }
        return cancelled;
    }

    public boolean updateServiceStatus(String type, ServiceStatus newStatus) {
        boolean updated = false;
        Service service = getServiceByType(type);
        if (service != null) {
            if (service.setStatus(newStatus)){
                updated = true;
            }
        }
        return updated;
    }

    private boolean serviceAlreadyExists(String type) {
        boolean existing = false;
        Service service = getServiceByType(type);
        if (service != null) {
            existing = true;
        }
        return existing;
    }

    private Service getServiceByType(String type) {
        Service service = null;
        for (Service s : services) {
            if (Objects.equals(s.getServiceType(), type)) {
                service = s;
                break;
            }
        }
        return service;
    }

    public ArrayList<Service> getServices() {
        return services;
    }
}
