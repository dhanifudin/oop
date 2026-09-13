package id.ac.polinema.model;

public class Transaction {
    private TransactionType type;
    private double amount;

    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + " " + amount;
    }
}
