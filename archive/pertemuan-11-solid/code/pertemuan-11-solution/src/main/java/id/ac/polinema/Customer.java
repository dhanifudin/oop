package id.ac.polinema;

public class Customer {
    private String name;
    private String customerId;
    private String type; // "REGULAR" or "VIP"

    public Customer(String name, String customerId, String type) {
        this.name = name;
        this.customerId = customerId;
        this.type = type;
    }

    public String getName() { return name; }
    public String getCustomerId() { return customerId; }
    public String getType() { return type; }
}
