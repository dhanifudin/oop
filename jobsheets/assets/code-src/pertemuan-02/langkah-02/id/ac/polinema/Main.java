package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();
        acc.ownerName = "Nadia";
        acc.balance = 500000;
        System.out.println(acc.ownerName + " - balance: " + acc.balance);
    }
}
