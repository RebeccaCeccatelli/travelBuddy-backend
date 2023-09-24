package dao.payment.methods.instances;

import dao.payment.methods.framework.PaymentMethodDao;

public class CreditCardDao extends PaymentMethodDao {
    @Override
    protected void saveMethodSpecificInfo(int paymentMethodId, Object... methodSpecificInfo) {

    }

    @Override
    protected boolean removeMethodSpecificInfo(int paymentMethodId) {
        return false;
    }

    @Override
    protected boolean updateMethodSpecificInfo(Object... methodSpecificInfo) {
        return false;
    }
}
