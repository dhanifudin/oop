package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];

        accounts[0] = new Account("Nadia", 500000);
        accounts[0].withdraw(150000);

        accounts[1] = new Account("Budi", 1000000);

        accounts[2] = new Account("Sari", 750000);
        accounts[2].withdraw(250000);

        for (Account acc : accounts) {
            acc.printInfo();
        }
    }
}
