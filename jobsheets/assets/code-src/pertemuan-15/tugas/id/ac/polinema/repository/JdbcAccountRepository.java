package id.ac.polinema.repository;

import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.SavingsAccount;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.sqlite.SQLiteDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcAccountRepository implements AccountRepository {
    private QueryRunner run;

    public JdbcAccountRepository(String databasePath) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + databasePath);
        this.run = new QueryRunner(dataSource);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts ("
                + "account_number TEXT PRIMARY KEY, "
                + "owner_name TEXT NOT NULL, "
                + "owner_phone TEXT, "
                + "account_type TEXT NOT NULL, "
                + "balance REAL NOT NULL, "
                + "extra_param REAL NOT NULL)";
        try {
            run.update(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize accounts table", e);
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT OR REPLACE INTO accounts "
                + "(account_number, owner_name, owner_phone, account_type, balance, extra_param) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String type;
        double extraParam;
        if (account instanceof SavingsAccount savings) {
            type = "SAVINGS";
            extraParam = savings.getInterestRate();
        } else {
            CheckingAccount checking = (CheckingAccount) account;
            type = "CHECKING";
            extraParam = checking.getOverdraftLimit();
        }
        try {
            run.update(sql, account.getAccountNumber(), account.getOwner().getName(),
                    account.getOwner().getPhone(), type, account.getBalance(), extraParam);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save account " + account.getAccountNumber(), e);
        }
    }

    @Override
    public Account findByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try {
            return run.query(sql, new AccountHandler(), accountNumber);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find account " + accountNumber, e);
        }
    }

    @Override
    public Collection<Account> findAll() {
        String sql = "SELECT * FROM accounts";
        try {
            return run.query(sql, new AccountListHandler());
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load accounts", e);
        }
    }

    private static Account mapRow(ResultSet rs) throws SQLException {
        Customer owner = new Customer(rs.getString("owner_name"), rs.getString("owner_phone"));
        String accountNumber = rs.getString("account_number");
        double balance = rs.getDouble("balance");
        double extraParam = rs.getDouble("extra_param");
        if ("SAVINGS".equals(rs.getString("account_type"))) {
            return new SavingsAccount(accountNumber, owner, balance, extraParam);
        }
        return new CheckingAccount(accountNumber, owner, balance, extraParam);
    }

    private static class AccountHandler implements ResultSetHandler<Account> {
        @Override
        public Account handle(ResultSet rs) throws SQLException {
            return rs.next() ? mapRow(rs) : null;
        }
    }

    private static class AccountListHandler implements ResultSetHandler<Collection<Account>> {
        @Override
        public Collection<Account> handle(ResultSet rs) throws SQLException {
            Collection<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
            return accounts;
        }
    }
}
