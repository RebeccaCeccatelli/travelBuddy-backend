package data.access.payment.methods.instances;

import data.access.payment.methods.framework.PaymentMethodDao;

public class DebitCardDao extends PaymentMethodDao {
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
