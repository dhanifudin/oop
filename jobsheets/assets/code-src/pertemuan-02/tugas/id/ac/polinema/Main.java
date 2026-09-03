package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];

        accounts[0] = new Account();
        accounts[0].ownerName = "Nadia";
        accounts[0].deposit(500000);
        accounts[0].withdraw(150000);

        accounts[1] = new Account();
        accounts[1].ownerName = "Budi";
        accounts[1].deposit(1000000);

        accounts[2] = new Account();
        accounts[2].ownerName = "Sari";
        accounts[2].deposit(750000);
        accounts[2].withdraw(250000);

        for (Account acc : accounts) {
            acc.printInfo();
        }

        Account from = new Account();
        from.ownerName = "Nadia";
        from.deposit(500000);

        Account to = new Account();
        to.ownerName = "Budi";
        to.deposit(200000);

        from.transferTo(to, 100000);

        from.printInfo();
        to.printInfo();
    }
}
