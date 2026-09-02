package id.ac.polinema;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private List<String> records = new ArrayList<>();

    public void save(Customer customer, Order order, int discount, int total) {
        records.add(customer.getName() + ";" + order.getDescription() + ";" + discount + ";" + total);
    }

    public List<String> findAll() {
        return records;
    }
}
