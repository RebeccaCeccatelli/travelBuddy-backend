package data.access.payment.methods.framework;

import backend.payment.methods.framework.PaymentMethod;
import backend.payment.methods.framework.PaymentMethodStatus;

import java.util.ArrayList;

public abstract class PaymentMethodDao {

    public int save(int accountId, PaymentMethodStatus status, Object... methodSpecificInfo) {
        int id = saveGeneralInfo(accountId, status);
        if (id != 0) {
            saveMethodSpecificInfo(id, methodSpecificInfo);
        }
        return id;
    }

    public boolean remove(int paymentMethodId) {
        boolean removed = false;
        if (removeMethodSpecificInfo(paymentMethodId)) {
            if (removeGeneralInfo(paymentMethodId)) {
                removed = true;
            }
        }
        return removed;
    }

    public boolean update(PaymentMethodStatus status, Object... methodSpecificInfo) {
        boolean updated = false;
        if (updateGeneralInfo(status)) {
            if (updateMethodSpecificInfo(methodSpecificInfo)) {
                updated = true;
            }
        }
         return updated;
    }

    public static ArrayList<PaymentMethod> loadAllPaymentMethod(int accountId) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        //TODO to be implemented
        return paymentMethods;
    }

    private int saveGeneralInfo(int accountId, PaymentMethodStatus status) {
        int id = 0;
        //TODO save in PaymentMethod table
        return id;
    }

    protected abstract void saveMethodSpecificInfo(int paymentMethodId, Object... methodSpecificInfo);

    protected void savePaymentMethodType(String type) {
        //TODO to be implemented, to be used in saveMethodSpecificInfo
    }

    private boolean removeGeneralInfo(int paymentMethodId) {
        boolean removed = false;
        //TODO remove from PaymentMethod table
        return removed;
    }

    protected abstract boolean removeMethodSpecificInfo(int paymentMethodId);

    private boolean updateGeneralInfo(PaymentMethodStatus status) {
        boolean updated = false;
        //TODO to be implemented
        return updated;
    }

    protected abstract boolean updateMethodSpecificInfo(Object... methodSpecificInfo);

    private static String getTableName() {
        return "\"PaymentMethod\".\"PaymentMethod\"";
    }

 }
