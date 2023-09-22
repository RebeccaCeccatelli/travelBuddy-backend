package backend.members.common.framework;

public abstract class Account {
    protected int id;
    protected String name;
    protected String password;
    protected String email;
    protected Address address;
    protected String phoneNumber;

    public boolean register(String email, String password, String name, Address address, String phoneNumber, Object... memberSpecificInformation) {
        boolean registered = false;
        if (!accountExists(email)) {
            if (setGeneralInformation(email, password, name, address, phoneNumber)) {
                if (setMemberSpecificInformation(memberSpecificInformation)) {
                    int id = saveInDatabase();
                    if (id != 0) {
                        setId(id);
                        registered = true;
                    }
                }
            }
        }
        return registered;
    }

    public boolean login(String email, String password) {
        boolean loggedIn = false;
        if (accountExists(email)) {
            if (checkPasswordMatch(password)) {
                if (loadFromDatabase()) {
                    loggedIn = true;
                }
            }
        }
        return loggedIn;
    }

    protected abstract boolean accountExists(String email);

    protected abstract boolean checkPasswordMatch(String password);

    protected abstract boolean loadFromDatabase();
    protected boolean setGeneralInformation(String email, String password, String name,
                                     Address address, String phoneNumber) {
        boolean valid = false;
        if (isEmailValid(email)) {
            this.email = email;

            if (isPasswordValid(password)) {
                this.password = password;

                this.name = name;
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

    protected abstract int saveInDatabase();

    private boolean isEmailValid(String email) {
        return false;
    }

    private boolean isPasswordValid(String password) {
        return false;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return false;
    }

    protected abstract boolean setMemberSpecificInformation(Object... memberSpecificInformation);

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
}
