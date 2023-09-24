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

    public boolean initialize(int id, String email, String password, String name, Address address, String phoneNumber, Object... accountSpecificInfo) {
        boolean initialized = false;
        if (id != 0) {
            setId(id);

            if (setInfo(email, password, name, address, phoneNumber, accountSpecificInfo)) {
                initialized = true;
            }
        }
        return initialized;
    }

    public Account login(String email, String password) {
        Account account = null;
        if (checkAccountExists(email)) {
            this.email = email;
            if (checkPasswordsMatch(email, password)) {
                this.password = password;
                account = load();
            }
        }
        return account;
    }

    public Account register(String email, String password, String name, Address address, String phoneNumber,
                            Object... accountSpecificInfo) {
        Account account = null;
        if (!checkAccountExists(email)) {
            if (setInfo(email, password, name, address, phoneNumber, accountSpecificInfo)) {
                int id = save();
                if (id != 0) {
                    setId(id);
                    account = this;
                }
            }
        }
        return account;
    }

    protected abstract int save();

    protected abstract Account load();

    protected abstract boolean checkAccountExists(String email);

    protected abstract boolean checkPasswordsMatch(String email, String password);


    protected boolean setInfo(String email, String password, String name, Address address, String phoneNumber, Object... accountSpecificInfo) {
        boolean set = false;
        if (setGeneralInfo(email, password, name, address, phoneNumber)) {
            if (setAccountSpecificInfo(accountSpecificInfo)) {
                set = true;
            }
        }
        return set;
    }

    private boolean setGeneralInfo(String email, String password,String name, Address address, String phoneNumber) {
        boolean set = false;

        this.name = name;
        if (isEmailValid(email)) {
            this.email = email;

            if (isPasswordValid(password)) {
                this.password = password;

                if(Address.isAddressValid(address)) {
                    this.address = address;

                    if (isPhoneNumberValid(phoneNumber)) {
                        this.phoneNumber = phoneNumber;

                        set = true;
                    }
                }
            }
        }
        return set;
    }

    protected abstract boolean setAccountSpecificInfo(Object... memberSpecificInformation);

    private void setId(int id) {
        this.id = id;
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

        return numericPhoneNumber.length() >= 10;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }
}
