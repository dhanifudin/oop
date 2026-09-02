package id.ac.polinema;

import java.util.HashMap;
import java.util.Map;

public class DiscountCalculator {
    private Map<String, DiscountPolicy> policies = new HashMap<>();

    public void registerPolicy(String customerType, DiscountPolicy policy) {
        policies.put(customerType, policy);
    }

    public int calculate(Customer customer, Order order) {
        DiscountPolicy policy = policies.get(customer.getType());
        if (policy == null) {
            throw new IllegalStateException("No discount policy for type: " + customer.getType());
        }
        return policy.calculate(order.getAmount());
    }
}
