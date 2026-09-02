package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer budi = new Customer("Budi", "C001", "REGULAR");
        Order order = new Order("Laptop Gaming", 100000);

        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.processOrder(budi, order);
    }
}
