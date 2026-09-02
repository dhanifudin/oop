package id.ac.polinema;

import java.io.FileWriter;
import java.io.IOException;

public class OrderRepository {
    public void save(Customer customer, Order order, int discount, int total) {
        try (FileWriter writer = new FileWriter("orders.txt", true)) {
            writer.write(customer.getName() + ";" + order.getDescription() + ";" + discount + ";" + total + "\n");
        } catch (IOException e) {
            System.out.println("Failed to save order record: " + e.getMessage());
        }
    }
}
