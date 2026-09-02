package id.ac.polinema;

public class Order {
    private String description;
    private int amount;

    public Order(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public int getAmount() { return amount; }

    public String describe() {
        return description + " (Rp" + amount + ")";
    }

    public String ship() {
        return "Shipping \"" + description + "\"...";
    }
}
