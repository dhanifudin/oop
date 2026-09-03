package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account original = new Account();
        original.ownerName = "Nadia";
        original.deposit(500000);

        Account copy = original;
        copy.deposit(100000);

        System.out.println("Via original: " + original.balance);
        System.out.println("Via copy: " + copy.balance);

        Account empty = null;
        empty.printInfo();
    }
}
