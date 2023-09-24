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
}
