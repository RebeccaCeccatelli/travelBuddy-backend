package backend.members.common.framework;

import java.util.Objects;

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
        return false;
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

    //method created for testing purposes
    public static boolean equals(Address firstAddress, Address secondAddress) {
        if (Objects.equals(firstAddress.street, secondAddress.street)) {
            if (Objects.equals(firstAddress.civicNumber, secondAddress.civicNumber)) {
                if (Objects.equals(firstAddress.postCode, secondAddress.postCode)) {
                    if (Objects.equals(firstAddress.city, secondAddress.city)) {
                        if (Objects.equals(firstAddress.country, secondAddress.country)) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
