package backend.payment.methods.instances;

import backend.payment.methods.framework.PaymentMethod;

import java.util.Map;
import java.util.regex.Pattern;

public class BankingAccount extends PaymentMethod {
    protected String accountHolderName;
    protected String accountHolderSurname;
    protected String bankName;
    protected String bankAddress;
    protected String IBAN;
    protected String SWIFTCode;

    protected boolean setInformation(String... information) {
        boolean valid = false;
        this.accountHolderName = information[0];
        this.accountHolderSurname = information[1];
        this.bankName = information[2];
        this.bankAddress = information[3];

        String iban = information[4];
        if (isValidIBAN(iban)) {
            this.IBAN = iban;

            String swift = information[5];
            if (isValidSWIFT(swift)) {
                this.SWIFTCode = swift;
                valid = true;
            }
        }
        return valid;
    }

    @Override
    protected int saveInDB() {
        return 0;
    }

    @SafeVarargs
    protected final boolean modifyInfo(Map<String, String>... modifications) {
        boolean modified = false;
        return modified;
    }

    protected boolean updateDB(){
        boolean updated = false;
        return updated;
    }

    protected boolean deleteFromDB() {
        boolean deleted = false;
        //TODO delete from DB
        return deleted;
    }

    private boolean isValidIBAN(String iban) {
        iban = iban.replaceAll("\\s", "").replaceAll("-", "");

        if (iban.length() < 5) {
            return false;
        }

        if (!Pattern.matches("^[0-9A-Z]*$", iban)) {
            return false;
        }

        iban = iban.substring(4) + iban.substring(0, 4);

        StringBuilder digitsIBAN = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            char c = iban.charAt(i);
            if (Character.isLetter(c)) {
                digitsIBAN.append(Character.getNumericValue(c));
            } else {
                digitsIBAN.append(c);
            }
        }

        long ibanValue = Long.parseLong(digitsIBAN.toString());
        return ibanValue % 97 == 1;
    }


    private boolean isValidSWIFT(String swift) {
        swift = swift.replaceAll("\\s", "").replaceAll("-", "");

        if (swift.length() != 8 && swift.length() != 11) {
            return false;
        }

        for (char c : swift.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
