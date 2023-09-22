package backend.payment.methods.instances;

import backend.payment.methods.framework.PaymentMethod;

import java.util.Map;

public class GooglePay extends PaymentMethod {

    @Override
    protected boolean setInformation(String... information) {
        return false;
    }

    @Override
    protected int saveInDB() {
        return 0;
    }

    @Override
    protected boolean modifyInfo(Map<String, String>... modifications) {
        return false;
    }

    @Override
    protected boolean updateDB() {
        return false;
    }

    @Override
    protected boolean deleteFromDB() {
        return false;
    }
}
