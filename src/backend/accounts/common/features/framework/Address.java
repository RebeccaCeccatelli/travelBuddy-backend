package backend.accounts.common.features.framework;

public class Address{
    private String street;
    private String civicNumber;
    private String postCode;
    private String city;
    private String country;

    public Address(String street, String civicNumber, String postCode, String city, String country){
        this.street = street;
        this.civicNumber = civicNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public static boolean isAddressValid(Address address) {
        if (address == null) {
            return false;
        }

        return !isEmpty(address.getStreet()) && !isEmpty(address.getCivicNumber()) &&
                !isEmpty(address.getPostCode()) && !isEmpty(address.getCity()) &&
                !isEmpty(address.getCountry());
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    void setStreet(String street) {
        this.street = street;
    }

    void setCivicNumber(String civicNumber) {
        this.civicNumber = civicNumber;
    }

    void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getCivicNumber() {
        return civicNumber;
    }

    public String getPostCode(){
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressAsString() {
        return street + " " + civicNumber + ", " + postCode + " " + city + ", " + country;
    }
}
