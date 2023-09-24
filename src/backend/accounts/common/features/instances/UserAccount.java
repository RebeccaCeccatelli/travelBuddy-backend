package backend.accounts.common.features.instances;

import backend.accounts.common.features.framework.Account;
import dao.accounts.instances.UserAccountDao;

import java.sql.Date;
import java.util.Objects;

public class UserAccount extends Account {
    public String gender;
    public Date dateOfBirth;
    public String nationality;

    @Override
    public int save() {
        return new UserAccountDao().save(name, email, password, address, phoneNumber,
                gender, dateOfBirth, nationality);
    }

    @Override
    protected UserAccount load() {
        return new UserAccountDao().load(email);
    }

    @Override
    protected boolean checkAccountExists(String email) {
        return new UserAccountDao().checkAccountExists(email);
    }

    @Override
    protected boolean checkPasswordsMatch(String email, String password) {
        return new UserAccountDao().checkPasswordsMatch(email, password);
    }

    @Override
    protected boolean setAccountSpecificInfo(Object... memberSpecificInformation) {
        boolean valid = false;
        String gender = (String) memberSpecificInformation[0];
        if (isGenderValid(gender)) {
            this.gender = gender;

            Date date = (Date) memberSpecificInformation[1];
            if (isDateOfBirthValid(date)) {
                this.dateOfBirth = date;

                this.nationality = (String) memberSpecificInformation[2];
                valid = true;
            }
        }
        return valid;
    }

    private boolean isGenderValid(String gender) {
        return Objects.equals(gender, "Female") || Objects.equals(gender, "Male");
    }

    private boolean isDateOfBirthValid(Date date) {
        if (date == null) {
            return false;
        }

        java.util.Date currentDate = new java.util.Date();

        if (date.after(currentDate)) {
            return false;
        }

        long ageInMillis = currentDate.getTime() - date.getTime();
        long ageInYears = ageInMillis / (365 * 24 * 60 * 60 * 1000L);

        return ageInYears >= 18 && ageInYears <= 100;
    }
}
