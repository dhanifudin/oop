package id.ac.polinema;

public class ShippingDemo {
    public static void main(String[] args) {
        Order[] orders = {
            new Order("Laptop Gaming", 100000),
            new DigitalOrder("Software License", 50000)
        };

        for (Order o : orders) {
            System.out.println(o.ship());
        }
    }
}
