package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account from = new Account("Nadia", 500000);
        Account to = new Account("Budi", 200000);

        from.transferTo(to, 100000);

        from.printInfo();
        to.printInfo();
    }
}
