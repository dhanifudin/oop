package id.ac.polinema;

import java.util.List;

public interface OrderRepository {
    void save(Customer customer, Order order, int discount, int total);
    List<String> findAll();
}
