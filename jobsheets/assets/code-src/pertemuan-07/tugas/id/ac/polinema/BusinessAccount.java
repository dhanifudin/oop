package id.ac.polinema;

public class BusinessAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000000;
    private double monthlyTransactionFee;

    public BusinessAccount(String accountNumber, Customer owner, double balance, double monthlyTransactionFee) {
        super(accountNumber, owner, balance);
        this.monthlyTransactionFee = monthlyTransactionFee;
    }

    public double getMonthlyTransactionFee() {
        return monthlyTransactionFee;
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return amount > 0 && (getBalance() - amount) >= MINIMUM_BALANCE;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Account type: Business, monthly fee: " + monthlyTransactionFee);
    }
}
