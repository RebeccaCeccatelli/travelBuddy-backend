package backend.payment.methods.framework;

import backend.payment.methods.instances.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PaymentMethodsManager {
    protected ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();

    public PaymentMethodsManager(int accountId) {
        loadPaymentMethodsFromDatabase(accountId);
    }

    protected void loadPaymentMethodsFromDatabase(int accountId){
        //TODO to implement
    }

    public boolean addPaymentMethod(String type, String... information) {
        boolean created = false;

        PaymentMethod paymentMethod;
        if (Objects.equals(type, "Banking account")) {
            paymentMethod = new BankingAccount();
        } else if (Objects.equals(type, "Credit card")) {
            paymentMethod = new CreditCard();
        } else if (Objects.equals(type, "Debit card")) {
            paymentMethod = new DebitCard();
        } else if (Objects.equals(type, "Gift card")) {
            paymentMethod = new GiftCard();
        } else if (Objects.equals(type, "Google pay")) {
            paymentMethod = new GooglePay();
        }
        else {
            return created;
        }
        created = paymentMethod.create(information);
        if (created) {
            paymentMethods.add(paymentMethod);
        }
        return created;
    }

    public boolean removePaymentMethod(int paymentMethodId) {
        boolean removed = false;
        PaymentMethod paymentMethod = findPaymentMethodById(paymentMethodId);
        if (paymentMethod != null) {
            if (paymentMethod.delete()) {
                paymentMethods.remove(paymentMethod);
                removed = true;
            }
        }
        return removed;
    }

    public boolean modifyPaymentMethod(int paymentMethodId, Map<String, String> modifications) {
        boolean modified = false;
        PaymentMethod paymentMethod = findPaymentMethodById(paymentMethodId);
        if (paymentMethod != null) {
            if (paymentMethod.modify(modifications)) {
                modified = true;
            }
        }
        return modified;
    }

    private PaymentMethod findPaymentMethodById(int id) {
        for (PaymentMethod paymentMethod : paymentMethods) {
            if (paymentMethod.getId() == id) {
                return paymentMethod;
            }
        }
        return null;
    }
}
