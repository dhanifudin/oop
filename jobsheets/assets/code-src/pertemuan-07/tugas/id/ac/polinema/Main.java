package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Budi", "0812-0000-0003");
        BusinessAccount acc = new BusinessAccount("A005", customer, 2000000, 25000);

        boolean result = acc.withdraw(1500000);
        System.out.println("Withdraw 1500000 allowed? " + result);
        acc.printInfo();
    }
}
