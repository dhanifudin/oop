package id.ac.polinema;

public class DigitalOrder extends Order {
    public DigitalOrder(String description, int amount) {
        super(description, amount);
    }
    // Deliberately does not implement Shippable: digital orders cannot be shipped.
}
