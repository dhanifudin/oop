package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer budi = new Customer("Budi", "C001", "REGULAR");
        Order order = new Order("Laptop Gaming", 100000);

        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.getDiscountCalculator().registerPolicy("REGULAR", new RegularDiscountPolicy());
        orderProcessor.getDiscountCalculator().registerPolicy("VIP", new VipDiscountPolicy());

        orderProcessor.processOrder(budi, order);

        Customer sari = new Customer("Sari", "C002", "WHOLESALE");
        orderProcessor.getDiscountCalculator().registerPolicy("WHOLESALE", new WholesaleDiscountPolicy());
        orderProcessor.processOrder(sari, order);
    }
}
