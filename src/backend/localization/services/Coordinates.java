package backend.localization.services;

import backend.members.common.framework.Address;

import java.util.ArrayList;

public class Coordinates {
    protected Double latitude;
    protected Double longitude;

    public Coordinates(Address address) {
        ArrayList<Double> coordinates = getCoordinatesFromAddress(address);
        if (!coordinates.isEmpty()) {
            this.longitude = coordinates.get(0);
            this.longitude = coordinates.get(1);
        }
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    private ArrayList<Double> getCoordinatesFromAddress(Address address) {
        ArrayList<Double> coordinates = new ArrayList<>();
        //TODO get coordinates using address
        return coordinates;
    }
}
