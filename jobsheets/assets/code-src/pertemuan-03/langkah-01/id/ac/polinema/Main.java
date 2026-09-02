package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();
        acc.ownerName = "Nadia";
        acc.deposit(500000);
        acc.withdraw(150000);
        acc.printInfo();
    }
}
