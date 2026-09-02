package id.ac.polinema;

public class PhysicalOrder extends Order implements Shippable {
    public PhysicalOrder(String description, int amount) {
        super(description, amount);
    }

    public String ship() {
        return "Shipping \"" + getDescription() + "\"...";
    }
}
