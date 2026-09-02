package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Rian", "0812-0000-0003");
        SavingsAccount savings = new SavingsAccount("A003", customer1, 100000, 0.02);
        savings.withdraw(70000);

        Customer customer2 = new Customer("Dewi", "0812-0000-0004");
        CheckingAccount checking = new CheckingAccount("A004", customer2, 100000, 200000);
        boolean result = checking.withdraw(250000);
        System.out.println("Withdraw 250000 allowed? " + result);

        Bank bank = new Bank(10);
        bank.addAccount(savings);
        bank.addAccount(checking);
        bank.printAllAccounts();
    }
}
