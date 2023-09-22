package backend.members.common.instances;

import backend.members.common.framework.Account;

import java.sql.Date;

public class UserAccount extends Account {
    protected String gender;
    protected Date dateOfBirth;
    protected String nationality;

    @Override
    protected boolean accountExists(String email) {
        return false;
    }

    @Override
    protected boolean checkPasswordMatch(String password) {
        return false;
    }

    @Override
    protected boolean loadFromDatabase() {
        return false;
    }

    @Override
    protected int saveInDatabase() {
        return 0;
    }

    @Override
    protected boolean setMemberSpecificInformation(Object... memberSpecificInformation) {
        return false;
    }

    private boolean isGenderValid(String gender) {
        return false;
    }

    private boolean isDateOfBirthValid(Date date) {
        return false;
    }


}
