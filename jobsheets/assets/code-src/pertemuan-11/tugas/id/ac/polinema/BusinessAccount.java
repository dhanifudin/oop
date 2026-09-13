package id.ac.polinema;

public class BusinessAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000000;

    public BusinessAccount(String accountNumber, Customer owner, double balance) {
        super(accountNumber, owner, balance);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return amount > 0 && (getBalance() - amount) >= MINIMUM_BALANCE;
    }

    @Override
    public double monthlyFee() {
        return 0;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Account type: Business");
    }
}
