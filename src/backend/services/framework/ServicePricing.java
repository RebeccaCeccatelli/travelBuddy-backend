package backend.services.framework;

class ServicePricing {
    String paymentPolicy;
    double price;

    public ServicePricing(String paymentPolicy, double price) {
        this.paymentPolicy = paymentPolicy;
        this.price = price;
    }

}
