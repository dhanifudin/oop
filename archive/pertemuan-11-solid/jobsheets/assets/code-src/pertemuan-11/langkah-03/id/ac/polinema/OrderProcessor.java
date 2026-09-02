package id.ac.polinema;

public class OrderProcessor {
    private DiscountCalculator discountCalculator = new DiscountCalculator();
    private OrderRepository orderRepository = new OrderRepository();
    private ReceiptPrinter receiptPrinter = new ReceiptPrinter();

    public void processOrder(Customer customer, Order order) {
        if (!validate(customer, order)) {
            System.out.println("Invalid order.");
            return;
        }
        int discount = discountCalculator.calculate(customer, order);
        int total = order.getAmount() - discount;
        orderRepository.save(customer, order, discount, total);
        receiptPrinter.print(customer, order, discount, total);
    }

    private boolean validate(Customer customer, Order order) {
        return customer != null && order != null;
    }
}
