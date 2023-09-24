package backend.payment.methods.framework;

import java.util.Map;

public abstract class PaymentMethod {
    protected int id;
    protected int accountId;
    protected PaymentMethodStatus status = PaymentMethodStatus.INVALID;

    public void initialize(int id, int accountId, PaymentMethodStatus status, Object... methodSpecificInfo) {
        if (id != 0) {
            setId(id);
        }
        if (setInfo(accountId, methodSpecificInfo)) {
            setStatus(status);
        }
    }

    public boolean create(int accountId, String... methodSpecificInfo) {
        boolean created = false;
        if (setInfo(accountId, methodSpecificInfo)) {
            setStatus(PaymentMethodStatus.VALID);
            int id = save();
            if (id != 0) {
                setId(id);
                created = true;
            }
        }
        return created;
    }

    public boolean modify(Map<String, String>... modifications) {
        boolean modified = false;
        if (modifyMethodSpecificInfo(modifications)) {
            if (update()) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean remove() {
        boolean removed = false;
        if (delete()) {
            removed = true;
        }
        return removed;
    }

    protected abstract int save();

    protected abstract boolean update();

    protected abstract boolean delete();

    private boolean setInfo(int accountId, Object... methodSpecificInfo) {
        boolean set = false;
        if (accountId != 0) {
            this.accountId = accountId;

            if (setMethodSpecificInfo(methodSpecificInfo)) {
                set = true;
            }
        }
        return set;
    }

    protected abstract boolean setMethodSpecificInfo(Object... methodSpecificInfo);

    protected abstract boolean modifyMethodSpecificInfo(Map<String, String>... modifications);

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
