package backend.payment.methods.framework;

import backend.payment.methods.instances.*;
import dao.payment.methods.framework.PaymentMethodDao;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PaymentMethodsManager {
    protected ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
    int accountId;

    public PaymentMethodsManager(int accountId) {
        this.accountId = accountId;
        paymentMethods = loadAllPaymentMethods();
    }

    protected ArrayList<PaymentMethod> loadAllPaymentMethods(){
        return PaymentMethodDao.loadAllPaymentMethod(accountId);
    }

    public boolean addPaymentMethod(String type, String... methodSpecificInfo) {
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
        created = paymentMethod.create(accountId, methodSpecificInfo);
        if (created) {
            paymentMethods.add(paymentMethod);
        }
        return created;
    }

    public boolean removePaymentMethod(int paymentMethodId) {
        boolean removed = false;
        PaymentMethod paymentMethod = findPaymentMethodById(paymentMethodId);
        if (paymentMethod != null) {
            if (paymentMethod.remove()) {
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

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
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
