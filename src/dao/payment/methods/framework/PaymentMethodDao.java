package dao.payment.methods.framework;

import backend.payment.methods.framework.PaymentMethod;
import backend.payment.methods.framework.PaymentMethodStatus;

import java.util.ArrayList;

public class PaymentMethodDao {

    public int savePaymentMethod(int accountId, PaymentMethodStatus status) {
        int id = 0;
        return id;
    }

    public ArrayList<PaymentMethod> loadAllPaymentMethods(int accountId) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        return paymentMethods;
    }
}
