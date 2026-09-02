package id.ac.polinema;

public class ShippingDemo {
    public static void main(String[] args) {
        Order[] orders = {
            new PhysicalOrder("Laptop Gaming", 100000),
            new DigitalOrder("Software License", 50000)
        };

        for (Order o : orders) {
            if (o instanceof Shippable shippable) {
                System.out.println(shippable.ship());
            } else {
                System.out.println(o.describe() + " (digital delivery, no shipping)");
            }
        }
    }
}
