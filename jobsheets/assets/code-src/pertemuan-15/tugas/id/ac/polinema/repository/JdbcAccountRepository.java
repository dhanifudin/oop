package id.ac.polinema.repository;

import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.SavingsAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcAccountRepository implements AccountRepository {
    private String url;

    public JdbcAccountRepository(String databasePath) {
        this.url = "jdbc:sqlite:" + databasePath;
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
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize accounts table", e);
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT OR REPLACE INTO accounts "
                + "(account_number, owner_name, owner_phone, account_type, balance, extra_param) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getOwner().getName());
            stmt.setString(3, account.getOwner().getPhone());
            if (account instanceof SavingsAccount savings) {
                stmt.setString(4, "SAVINGS");
                stmt.setDouble(6, savings.getInterestRate());
            } else {
                CheckingAccount checking = (CheckingAccount) account;
                stmt.setString(4, "CHECKING");
                stmt.setDouble(6, checking.getOverdraftLimit());
            }
            stmt.setDouble(5, account.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save account " + account.getAccountNumber(), e);
        }
    }

    @Override
    public Account findByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find account " + accountNumber, e);
        }
    }

    @Override
    public Collection<Account> findAll() {
        Collection<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load accounts", e);
        }
        return accounts;
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Customer owner = new Customer(rs.getString("owner_name"), rs.getString("owner_phone"));
        String accountNumber = rs.getString("account_number");
        double balance = rs.getDouble("balance");
        double extraParam = rs.getDouble("extra_param");
        if ("SAVINGS".equals(rs.getString("account_type"))) {
            return new SavingsAccount(accountNumber, owner, balance, extraParam);
        }
        return new CheckingAccount(accountNumber, owner, balance, extraParam);
    }
}
