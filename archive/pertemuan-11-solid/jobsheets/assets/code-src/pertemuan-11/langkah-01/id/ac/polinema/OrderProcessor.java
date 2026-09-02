package id.ac.polinema;

import java.io.FileWriter;
import java.io.IOException;

public class OrderProcessor {

    public void processOrder(Customer customer, Order order) {
        if (!validate(customer, order)) {
            System.out.println("Invalid order.");
            return;
        }

        int discount = calculateDiscount(customer, order);
        int total = order.getAmount() - discount;
        saveToFile(customer, order, discount, total);
        printReceipt(customer, order, discount, total);
    }

    private boolean validate(Customer customer, Order order) {
        return customer != null && order != null;
    }

    private int calculateDiscount(Customer customer, Order order) {
        if (customer.getType().equals("REGULAR")) {
            return order.getAmount() * 5 / 100;
        } else if (customer.getType().equals("VIP")) {
            return order.getAmount() * 15 / 100;
        } else {
            return 0;
        }
    }

    private void saveToFile(Customer customer, Order order, int discount, int total) {
        try (FileWriter writer = new FileWriter("orders.txt", true)) {
            writer.write(customer.getName() + ";" + order.getDescription() + ";" + discount + ";" + total + "\n");
        } catch (IOException e) {
            System.out.println("Failed to save order record: " + e.getMessage());
        }
    }

    private void printReceipt(Customer customer, Order order, int discount, int total) {
        System.out.println("=== Order Receipt ===");
        System.out.println("Customer : " + customer.getName() + " (" + customer.getType() + ")");
        System.out.println("Order    : " + order.describe());
        System.out.println("Discount : Rp" + discount);
        System.out.println("Total    : Rp" + total);
    }
}
