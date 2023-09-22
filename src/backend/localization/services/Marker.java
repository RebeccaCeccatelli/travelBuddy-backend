package backend.localization.services;

public class Marker {
    Coordinates coordinates;
    String popupMessage;

    public Marker(Coordinates coordinates, String popupMessage) {
        this.coordinates = coordinates;
        this.popupMessage = popupMessage;
    }
}
