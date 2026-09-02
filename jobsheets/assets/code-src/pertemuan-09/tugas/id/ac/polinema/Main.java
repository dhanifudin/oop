package id.ac.polinema;

public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Nadia", "0812-0000-0001");
        SavingsAccount acc1 = new SavingsAccount("A001", customer1, 500000, 0.01);
        acc1.withdraw(150000);

        Customer customer2 = new Customer("Sari", "0812-0000-0002");
        CheckingAccount acc2 = new CheckingAccount("A002", customer2, 200000, 50000);

        Customer customer3 = new Customer("Rian", "0812-0000-0003");
        SavingsAccount savings = new SavingsAccount("A003", customer3, 100000, 0.02);
        savings.withdraw(70000);

        Customer customer4 = new Customer("Dewi", "0812-0000-0004");
        CheckingAccount checking = new CheckingAccount("A004", customer4, 100000, 200000);
        checking.withdraw(250000);

        Bank bank = new Bank(10);
        bank.addAccount(acc1);
        bank.addAccount(acc2);
        bank.addAccount(savings);
        bank.addAccount(checking);
        bank.printAllAccounts();
        bank.printMonthlyFees();

        System.out.println("Before interest: " + savings.getBalance());
        savings.applyInterest();
        System.out.println("After interest: " + savings.getBalance());

        System.out.println(acc2.auditLog());
        System.out.println(checking.auditLog());
    }
}
