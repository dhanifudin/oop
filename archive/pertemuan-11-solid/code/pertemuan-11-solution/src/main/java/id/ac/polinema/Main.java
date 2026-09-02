package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.registerPolicy("REGULAR", new RegularDiscountPolicy());
        discountCalculator.registerPolicy("VIP", new VipDiscountPolicy());
        discountCalculator.registerPolicy("WHOLESALE", new WholesaleDiscountPolicy());

        OrderRepository orderRepository = new InMemoryOrderRepository();
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();

        OrderProcessor orderProcessor = new OrderProcessor(discountCalculator, orderRepository, receiptPrinter);

        Customer budi = new Customer("Budi", "C001", "REGULAR");
        Order order = new Order("Laptop Gaming", 100000);
        orderProcessor.processOrder(budi, order);

        System.out.println("Repository contents: " + orderRepository.findAll());
    }
}
