package backend.payment.methods.framework;

import java.util.Map;

public abstract class PaymentMethod {
    int id;
    PaymentMethodStatus status = PaymentMethodStatus.INVALID;

    public boolean create(String... information) {
        boolean created = false;
        if (setInformation(information)) {
            int id = saveInDB();
            if (id != 0) {
                setId(id);
                created = true;
                setStatus(PaymentMethodStatus.VALID);
            }
        }
        return created;
    }

    protected abstract boolean setInformation(String... information);

    protected abstract int saveInDB();

    public boolean modify(Map<String, String>... modifications) {
        boolean modified = false;
        if (modifyInfo(modifications)) {
            if (updateDB()) {
                modified = true;
            }
        }
        return modified;
    }

    protected abstract boolean modifyInfo(Map<String, String>... modifications);

    protected abstract boolean updateDB();

    public boolean delete() {
        boolean deleted = false;
        if (deleteFromDB()) {
            deleted = true;
        }
        return deleted;
    }

    protected abstract boolean deleteFromDB();

    private void setId(int id) {
        this.id = id;
    }

    private void setStatus(PaymentMethodStatus newStatus) {
        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

}
