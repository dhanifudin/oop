package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Rectangle[] shapes = new Rectangle[3];
        shapes[0] = new Rectangle(6, 4);
        shapes[1] = new Rectangle(3, 3);
        shapes[2] = new Rectangle(8, 2);

        for (Rectangle r : shapes) {
            System.out.println("Area: " + r.area() + ", Perimeter: " + r.perimeter());
        }

        Account acc = new Account();
        acc.ownerName = "Nadia";
        acc.deposit(500000);
        acc.withdraw(150000);
        acc.printInfo();
    }
}
