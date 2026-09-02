package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Nadia", "0812-0000-0001");
        Account acc1 = new Account("A001", customer1, 500000);
        acc1.withdraw(150000);
        acc1.printInfo();
    }
}
