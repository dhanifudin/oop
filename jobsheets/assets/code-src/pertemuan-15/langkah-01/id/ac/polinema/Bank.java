package id.ac.polinema;

import id.ac.polinema.model.Account;
import id.ac.polinema.model.InterestBearing;
import id.ac.polinema.model.Transaction;
import id.ac.polinema.repository.AccountRepository;
import java.util.Collection;

public class Bank {
    private AccountRepository repository;

    public Bank(AccountRepository repository) {
        this.repository = repository;
    }

    public boolean addAccount(Account account) {
        if (repository.findByNumber(account.getAccountNumber()) != null) {
            return false;
        }
        repository.save(account);
        return true;
    }

    public Account findAccount(String accountNumber) {
        return repository.findByNumber(accountNumber);
    }

    public Collection<Account> getAllAccounts() {
        return repository.findAll();
    }

    public void saveAccount(Account account) {
        repository.save(account);
    }

    public void printAllAccounts() {
        for (Account acc : repository.findAll()) {
            acc.printInfo();
        }
    }

    public void printMonthlyFees() {
        for (Account acc : repository.findAll()) {
            System.out.println(acc.getAccountNumber() + " fee: " + acc.monthlyFee());
        }
    }

    public void processMonthEnd() {
        for (Account acc : repository.findAll()) {
            if (acc instanceof InterestBearing bearing) {
                bearing.applyInterest();
                System.out.println(acc.getAccountNumber() + " interest applied, new balance: " + acc.getBalance());
            }
            System.out.println(acc.getAccountNumber() + " monthly fee: " + acc.monthlyFee());
            repository.save(acc);
        }
    }

    public void printHistory(String accountNumber) {
        Account acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Account not found: " + accountNumber);
            return;
        }
        for (Transaction t : acc.getHistory()) {
            System.out.println(accountNumber + " " + t);
        }
    }
}
