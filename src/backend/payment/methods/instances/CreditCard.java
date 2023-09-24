package backend.payment.methods.instances;

import backend.payment.methods.framework.PaymentMethod;

import java.util.Map;

public class CreditCard extends PaymentMethod {

    @Override
    protected boolean setMethodSpecificInfo(Object... methodSpecificInfo) {
        return false;
    }

    @Override
    protected int save() {
        return 0;
    }

    @SafeVarargs
    @Override
    protected final boolean modifyMethodSpecificInfo(Map<String, String>... modifications) {
        return false;
    }

    @Override
    protected boolean update() {
        return false;
    }

    @Override
    protected boolean delete() {
        return false;
    }
}
