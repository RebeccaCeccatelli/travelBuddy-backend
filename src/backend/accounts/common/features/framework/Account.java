package backend.accounts.common.features.framework;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Account {
    protected int id;
    public String name;
    public String password;
    public String email;
    protected Address address;
    public String phoneNumber;

    public void initialize(int id, String email, String password, String name, Address address, String phoneNumber, Object... additionalAccountInfo) {
        setId(id);
        if (setGeneralInformation(email, password, name, address, phoneNumber)) {
            setAccountSpecificInformation(additionalAccountInfo);
        }
    }

    public Account register(String email, String password, String name, Address address, String phoneNumber, Object... memberSpecificInformation) {
        if (!checkAccountExists(email)) {
            if (setGeneralInformation(email, password, name, address, phoneNumber)) {
                if (setAccountSpecificInformation(memberSpecificInformation)) {
                    int id = save();
                    if (id != 0) {
                        setId(id);
                        return this;
                    }
                }
            }
        }
        return null;
    }

    public Account login(String email, String password) {
        if (checkAccountExists(email)) {
            this.email = email;
            if (checkPasswordsMatch(email, password)) {
                this.password = password;

                return load();
            }
        }
        return null;
    }

    protected boolean setGeneralInformation(String email, String password, String name,
                                     Address address, String phoneNumber) {
        boolean valid = false;
        this.name = name;

        if (isEmailValid(email)) {
            this.email = email;

            if (isPasswordValid(password)) {
                this.password = password;

                if(Address.isAddressValid(address)) {
                    this.address = address;

                    if (isPhoneNumberValid(phoneNumber)) {
                        this.phoneNumber = phoneNumber;

                        valid = true;
                    }
                }
            }
        }
        return valid;
    }


    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        String numericPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (numericPhoneNumber.length() < 10) {
            return false;
        }
        return true;
    }

    protected abstract boolean setAccountSpecificInformation(Object... memberSpecificInformation);

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    protected abstract boolean checkAccountExists(String email);

    protected abstract boolean checkPasswordsMatch(String email, String password);

    protected abstract Account load();

    protected abstract int save();
}
