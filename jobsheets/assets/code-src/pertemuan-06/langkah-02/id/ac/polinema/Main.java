package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Nadia", "0812-0000-0001");
        SavingsAccount acc1 = new SavingsAccount("A001", customer1, 500000, 0.01);
        acc1.withdraw(150000);
        acc1.printInfo();
        acc1.printAccountType();

        Customer customer2 = new Customer("Sari", "0812-0000-0002");
        CheckingAccount acc2 = new CheckingAccount("A002", customer2, 200000, 50000);
        acc2.withdraw(230000);
        acc2.printInfo();
        acc2.printAccountType();
    }
}
