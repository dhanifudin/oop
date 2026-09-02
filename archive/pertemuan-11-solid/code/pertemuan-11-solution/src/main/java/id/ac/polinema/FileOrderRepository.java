package id.ac.polinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository implements OrderRepository {
    public void save(Customer customer, Order order, int discount, int total) {
        try (FileWriter writer = new FileWriter("orders.txt", true)) {
            writer.write(customer.getName() + ";" + order.getDescription() + ";" + discount + ";" + total + "\n");
        } catch (IOException e) {
            System.out.println("Failed to save order record: " + e.getMessage());
        }
    }

    public List<String> findAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read order record: " + e.getMessage());
        }
        return lines;
    }
}
