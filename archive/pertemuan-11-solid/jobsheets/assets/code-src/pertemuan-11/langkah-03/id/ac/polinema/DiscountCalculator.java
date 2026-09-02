package id.ac.polinema;

public class DiscountCalculator {
    public int calculate(Customer customer, Order order) {
        if (customer.getType().equals("REGULAR")) {
            return order.getAmount() * 5 / 100;
        } else if (customer.getType().equals("VIP")) {
            return order.getAmount() * 15 / 100;
        } else {
            return 0;
        }
    }
}
