package backend.payment.methods.instances;

import backend.payment.methods.framework.PaymentMethod;
import data.access.payment.methods.instances.BankingAccountDao;

import java.util.Map;
import java.util.regex.Pattern;

public class BankingAccount extends PaymentMethod {
    protected String accountHolderName;
    protected String accountHolderSurname;
    protected String bankName;
    protected String bankAddress;
    protected String IBAN;
    protected String SWIFTCode;

    protected boolean setMethodSpecificInfo(Object... methodSpecificInfo) {
        boolean valid = false;
        this.accountHolderName = (String) methodSpecificInfo[0];
        this.accountHolderSurname = (String) methodSpecificInfo[1];
        this.bankName = (String) methodSpecificInfo[2];
        this.bankAddress = (String) methodSpecificInfo[3];

        String iban = (String) methodSpecificInfo[4];
        if (isValidIBAN(iban)) {
            this.IBAN = iban;

            String swift = (String) methodSpecificInfo[5];
            if (isValidSWIFT(swift)) {
                this.SWIFTCode = swift;
                valid = true;
            }
        }
        return valid;
    }

    @SafeVarargs
    protected final boolean modifyMethodSpecificInfo(Map<String, String>... modifications) {
        boolean modified = false;
        //TODO
        return modified;
    }

    @Override
    protected int save() {
        return new BankingAccountDao().save(accountId, status, accountHolderName, accountHolderSurname,
                bankName, bankAddress, IBAN, SWIFTCode);
    }


    protected boolean update(){
        return new BankingAccountDao().update(status, accountHolderName, accountHolderSurname,
                bankName, bankAddress, IBAN, SWIFTCode);
    }

    protected boolean delete() {
        return new BankingAccountDao().remove(id);
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
