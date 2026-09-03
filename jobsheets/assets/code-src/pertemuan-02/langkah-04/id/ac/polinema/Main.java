package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();
        acc.ownerName = "Nadia";
        acc.deposit(500000);
        acc.withdraw(150000);
        System.out.println(acc.ownerName + " - balance: " + acc.formatBalance());

        acc.withdraw(1000000);
        System.out.println("Overdrawn: " + acc.isOverdrawn());
    }
}
