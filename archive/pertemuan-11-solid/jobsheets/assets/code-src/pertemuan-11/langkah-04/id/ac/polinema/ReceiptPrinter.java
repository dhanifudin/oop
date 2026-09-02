package id.ac.polinema;

public class ReceiptPrinter {
    public void print(Customer customer, Order order, int discount, int total) {
        System.out.println("=== Order Receipt ===");
        System.out.println("Customer : " + customer.getName() + " (" + customer.getType() + ")");
        System.out.println("Order    : " + order.describe());
        System.out.println("Discount : Rp" + discount);
        System.out.println("Total    : Rp" + total);
    }
}
