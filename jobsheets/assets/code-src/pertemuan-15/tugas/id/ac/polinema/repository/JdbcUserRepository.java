package id.ac.polinema.repository;

import id.ac.polinema.PasswordHasher;
import id.ac.polinema.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUserRepository implements UserRepository {
    private String url;

    public JdbcUserRepository(String databasePath) {
        this.url = "jdbc:sqlite:" + databasePath;
        createTableIfNotExists();
        seedDefaultUserIfEmpty();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "username TEXT PRIMARY KEY, "
                + "password_hash TEXT NOT NULL)";
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize users table", e);
        }
    }

    private void seedDefaultUserIfEmpty() {
        if (findByUsername("teller1") != null) {
            return;
        }
        save(new User("teller1", PasswordHasher.hash("teller123")));
        save(new User("teller2", PasswordHasher.hash("teller456")));
    }

    private void save(User user) {
        String sql = "INSERT OR REPLACE INTO users (username, password_hash) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save user " + user.getUsername(), e);
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new User(rs.getString("username"), rs.getString("password_hash"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find user " + username, e);
        }
    }
}
