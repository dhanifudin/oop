package id.ac.polinema.repository;

import id.ac.polinema.PasswordHasher;
import id.ac.polinema.model.User;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.sqlite.SQLiteDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserRepository implements UserRepository {
    private QueryRunner run;

    public JdbcUserRepository(String databasePath) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + databasePath);
        this.run = new QueryRunner(dataSource);
        createTableIfNotExists();
        seedDefaultUserIfEmpty();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "username TEXT PRIMARY KEY, "
                + "password_hash TEXT NOT NULL)";
        try {
            run.update(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize users table", e);
        }
    }

    private void seedDefaultUserIfEmpty() {
        if (findByUsername("teller1") != null) {
            return;
        }
        save(new User("teller1", PasswordHasher.hash("teller123")));
    }

    private void save(User user) {
        String sql = "INSERT OR REPLACE INTO users (username, password_hash) VALUES (?, ?)";
        try {
            run.update(sql, user.getUsername(), user.getPasswordHash());
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save user " + user.getUsername(), e);
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return run.query(sql, new UserHandler(), username);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find user " + username, e);
        }
    }

    private static class UserHandler implements ResultSetHandler<User> {
        @Override
        public User handle(ResultSet rs) throws SQLException {
            return rs.next() ? new User(rs.getString("username"), rs.getString("password_hash")) : null;
        }
    }
}
