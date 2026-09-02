package id.ac.polinema;

public class OrderProcessor {
    private DiscountCalculator discountCalculator;
    private OrderRepository orderRepository;
    private ReceiptPrinter receiptPrinter;

    public OrderProcessor(DiscountCalculator discountCalculator, OrderRepository orderRepository, ReceiptPrinter receiptPrinter) {
        this.discountCalculator = discountCalculator;
        this.orderRepository = orderRepository;
        this.receiptPrinter = receiptPrinter;
    }

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
