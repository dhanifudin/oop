package id.ac.polinema;

public class DigitalOrder extends Order {
    public DigitalOrder(String description, int amount) {
        super(description, amount);
    }

    @Override
    public String ship() {
        throw new UnsupportedOperationException("Digital orders cannot be shipped.");
    }
}
