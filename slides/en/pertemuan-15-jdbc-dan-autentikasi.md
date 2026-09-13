---
marp: true
theme: default
paginate: true
size: 16:9
style: |
  section {
    font-family: 'Helvetica Neue', Arial, sans-serif;
    padding: 56px 72px;
    justify-content: center;
  }
  section.lead {
    background: linear-gradient(135deg, #1e3a8a 0%, #1d4ed8 55%, #2563eb 100%);
    color: #fff;
    justify-content: center;
  }
  section.lead h1, section.lead h2, section.lead p {
    color: #fff;
  }
  section.lead a {
    color: #bfdbfe;
  }
  section.divider {
    background: #1d4ed8;
    color: #fff;
  }
  section.divider h1 {
    color: #fff;
    font-size: 2.2em;
  }
  section.divider h2 {
    color: #bfdbfe;
  }
  section.divider p {
    color: #bfdbfe;
  }
  h1 {
    color: #1d4ed8;
    font-size: 1.6em;
  }
  h2 {
    color: #1d4ed8;
  }
  table {
    font-size: 0.72em;
    width: 100%;
  }
  code {
    background: #f1f5f9;
    color: #0f172a;
  }
  .term-box {
    border-left: 6px solid #1d4ed8;
    background: #eff6ff;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.82em;
  }
  .term-box b {
    color: #1d4ed8;
  }
  .tip-box {
    border-left: 6px solid #16a34a;
    background: #f0fdf4;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .warn-box {
    border-left: 6px solid #dc2626;
    background: #fef2f2;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .cols {
    display: flex;
    gap: 28px;
    align-items: center;
  }
  .cols > div {
    flex: 1;
  }
  .cols img {
    display: block;
    margin: 0 auto;
    max-width: 100%;
    max-height: 460px;
  }
  .footnote {
    font-size: 0.55em;
    color: #64748b;
    margin-top: 8px;
  }
  img {
    display: block;
    margin: 0 auto 12px auto;
    max-width: 90%;
    max-height: 420px;
  }
---

<!-- _class: lead -->

# Object-Oriented Programming
## RTI253007 &nbsp;|&nbsp; D-IV Informatics Engineering

Meeting 15: **Persistence with JDBC and an Authentication Mechanism**

From data that evaporates when the application closes to data that genuinely stays stored

---

## What You Will Learn

- Why in-memory storage loses all of its data every time the application closes, and how JDBC solves this
- How the Dependency Inversion Principle acts again: swapping a storage implementation without changing the code that uses it
- Why an application with no login mechanism is a genuine risk, and how a password should actually be stored (hashed, not as-is)
- Application to Bank Mini: `JdbcAccountRepository`, `JdbcUserRepository`, and `LoginFrame`

<div class="tip-box">
Programming exercises for today's material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 15.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Persistence, data that endures
- **Session 2 (50')**: Authentication, who is allowed in
- **Session 3 (50')**: Applying JDBC to Bank Mini
- **Session 4 (50')**: Applying authentication to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Persistence: Data That Endures

Session 1 of 4

---

## An Application That Forgets Everything

A simple note-taking application stores all of its data in variables, in a structure such as `ArrayList` or `HashMap`, while the application runs. The moment the application closes, the entire contents of memory (RAM) are cleared by the operating system, including data the user just entered.

<div class="warn-box">
Closing an application and reopening it makes all the data the user entered vanish without a trace, as if it had never existed.
</div>

---

## Why Does This Matter?

Nearly every real application, from a small point-of-sale app to a large-scale banking system, must remember its data across sessions: a user closes their laptop, restarts their computer, or an application server gets redeployed, all without expecting customer data to disappear along with it. An application whose data evaporates on every restart is not fit for real work, no matter how good the quality of its business logic.

<div class="term-box">
Persistence (data that endures beyond one application process's lifecycle) is one of the most fundamental reasons nearly every real application is connected to a database, rather than merely storing data in a variable.
</div>

---

## RAM Is Cleared, Disk Is Not

![h:260 Contrast between in-memory storage lost on restart and database storage that endures](../assets/illustrations/persistence-restart.svg)

<div class="term-box">
JDBC (Java Database Connectivity) is Java's built-in API for connecting to a relational database through SQL commands. SQLite stores an entire database in a single ordinary file on disk, so it needs no separate database server, fitting a small to medium application.
</div>

---

## Managing a Dependency Through Maven

SQLite is not part of Java itself; it is a third-party library. The question is: how can `import org.sqlite...` be used, when its code was never typed out by hand?

<div class="term-box">
A Maven project declares the library it needs as a <b>dependency</b> inside <code>pom.xml</code>, just its name and version number. Maven downloads that library (and every other library it needs) from a central server called <i>Maven Central</i>, then places it on the project's classpath automatically. No <code>.jar</code> file needs to be downloaded or copied manually.
</div>

---

## Example: Declaring a Dependency

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.1.0</version>
</dependency>
```

These three coordinates (`groupId`, `artifactId`, `version`) are already enough for Maven to find, download, and install the SQLite JDBC Driver used this meeting.

---

## Apache Commons DbUtils: Simplifying JDBC

Writing `Connection`/`Statement`/`ResultSet` manually in every method is repetitive and easy to forget to close, as discussed on the previous slide. Maven makes it easy to use a library that already solves this problem.

<div class="term-box">
<b>Apache Commons DbUtils</b> is a third-party library (this meeting's second Maven dependency) that wraps the repetitive JDBC pattern through the <code>QueryRunner</code> class: one method call replaces an entire <code>Connection</code>/<code>PreparedStatement</code>/<code>try</code>-with-resources block.
</div>

---

## Code Example: JDBC Connection, Raw vs Through DbUtils

```java
// Raw JDBC
try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement stmt = conn.prepareStatement(sql)) {
    stmt.setString(1, accountNumber);
    stmt.executeUpdate();
}

// Through QueryRunner (Apache Commons DbUtils)
run.update(sql, accountNumber);
```

The second line does EXACTLY the same thing as the first block: opening a connection, preparing a `PreparedStatement`, filling in a parameter, executing it, then closing everything, just done inside `run.update(...)`.

---

## Common Mistake: Forgetting to Close a Connection

<div class="warn-box">
<b>Wrong:</b> opening <code>Connection conn = DriverManager.getConnection(url);</code> with neither <code>try</code>-with-resources nor a manual <code>conn.close()</code> afterward.
</div>

**Right:** every `Connection`, `Statement`, and `ResultSet` must be closed after use, since each one holds a system resource (a file, memory) that is not released automatically. The pattern `try (Connection conn = ...; PreparedStatement stmt = ...) { ... }` closes both automatically once the block finishes. A `QueryRunner` built from a `DataSource` (not directly from a `Connection`) removes this entire class of bug: every `run.update(...)`/`run.query(...)` fetches and closes its own connection.

---

## Exercise

A method opens a `Connection` on its first line, then runs several queries, with NEITHER `try`-with-resources nor a `close()` at the end of the method.

What is the risk if this method is called thousands of times in a long-running application? Explain.

---

## Exercise Answer

**A resource leak.** Every call opens a new connection that is never closed, and the system resource it holds (a socket, a database file) keeps piling up. After thousands of calls, the application can run out of available connections, or even cause the whole application to stop responding. Fix: wrap the `Connection` in `try`-with-resources so it always closes automatically, whatever happens inside the block.

---

## Part 1 Summary

- Data stored only in a variable (RAM) is lost every time the application closes; persistence means data endures beyond the application's lifecycle.
- JDBC connects Java to a database through SQL; SQLite stores an entire database in a single file on disk.
- `Connection`, `Statement`, and `ResultSet` must be closed after use; `try`-with-resources does this automatically.

Next: Part 2 covers authentication, how to ensure only a legitimate user can access data that is now genuinely stored.

---

<!-- _class: divider -->

# Part 2
## Authentication: Who Is Allowed In

Session 2 of 4

---

## A Door With No Lock

A desktop application that immediately displays all of its data the moment it runs, never asking who its user is, is like an office whose door is left open for anyone. Whoever can run the program automatically gets full access to all the data inside it.

<div class="warn-box">
Without a login mechanism, there is no way to tell a legitimate user apart from anyone who merely happens to be able to run the application.
</div>

---

## Why Does This Matter?

A real business application, from a point-of-sale system to banking, stores data that should not be accessible to just anyone: account balances, transaction history, customer data. A data breach incident that repeatedly makes the news nearly always involves a system that failed to verify its user's identity before granting access. A login is not an optional feature that can be postponed, but a basic requirement for an application to be called secure at all.

<div class="term-box">
Authentication (verifying who a user is) differs from authorization (deciding what that user is allowed to do). This jobsheet only builds authentication; tiered authorization (e.g. teller vs. admin) is a follow-on topic that can be developed as an independent project in Meeting 16.
</div>

---

## A Gate Before the Data

![h:260 Contrast between an application with no login and an application with a login gate](../assets/illustrations/login-gate.svg)

<div class="term-box">
A <b>login gate</b> checks credentials (username and password) BEFORE allowing access to the real data. Without this gate, all the data is equally open to whoever runs the application.
</div>

---

## Hashing: A One-Way Function

<div class="warn-box">
A password must never be stored as-is (plain text). A password is hashed (transformed through a one-way function that cannot be reversed) before being stored; at login, the typed password is hashed again and compared with the stored hash.
</div>

<div class="term-box">
A hash function turns any input into a fixed-length string of characters, and it is IMPOSSIBLE to reverse to recover the original input. Even if the database leaks, an attacker only gets the hash, not the user's actual password.
</div>

---

## Code Example: Hashing a Password with SHA-256

```java
MessageDigest digest = MessageDigest.getInstance("SHA-256");
byte[] hashBytes = digest.digest(plainPassword.getBytes("UTF-8"));
StringBuilder hex = new StringBuilder();
for (byte b : hashBytes) {
    hex.append(String.format("%02x", b));
}
return hex.toString();
```

The same password always produces the exact same hash, but the hash itself cannot be reversed back into the original password.

---

## Common Mistake: Comparing a Plain Password to a Hash

<div class="warn-box">
<b>Wrong:</b> writing <code>if (user.getPasswordHash().equals(password))</code>, comparing a stored hash directly with the plain password the user just typed.
</div>

**Right:** the newly typed password must be HASHED FIRST with the same algorithm, then compared with the stored hash: `user.getPasswordHash().equals(PasswordHasher.hash(password))`. Comparing a hash with plain text is almost always `false`, even for a genuinely correct password.

---

## Exercise

The `users` table stores `passwordHash` for `"nadia"` as the result of `PasswordHasher.hash("rahasia123")`.

A user logs in with username `"nadia"` and password `"rahasia123"`. Explain the steps the `LoginFrame` code must take to decide whether this login succeeds.

---

## Exercise Answer

The code MUST hash the newly typed password again (`PasswordHasher.hash("rahasia123")`), then compare the result with the `passwordHash` stored in the database for `"nadia"`. Since a hash function produces the exact same output for the same input, these two hashes will match and the login is declared successful, WITHOUT ever comparing the plain passwords directly.

---

## Part 2 Summary

- An application with no login gate cannot tell a legitimate user apart from anyone who runs the program.
- A password is hashed (a one-way function) before being stored; the original password is never stored at all.
- Login verification compares hash with hash, not hash with plain password.

Next: Part 3 applies JDBC to Bank Mini's account data storage.

---

<!-- _class: divider -->

# Part 3
## Applying JDBC to Bank Mini

Session 3 of 4

---

## Swapping Storage Without Changing Its User

![h:300 AccountRepository now implemented by JdbcAccountRepository, replacing the in-memory version](../assets/uml/p15-accountrepository-jdbc.png)

<div class="tip-box">
This is the Dependency Inversion Principle (Meeting 11) acting again: <code>Bank</code> only depends on the <code>AccountRepository</code> interface, so in-memory storage can be replaced with database storage just by writing a new implementation, without touching <code>Bank</code> at all.
</div>

---

## Code Example: JdbcAccountRepository.save()

```java
public JdbcAccountRepository(String databasePath) {
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl("jdbc:sqlite:" + databasePath);
    this.run = new QueryRunner(dataSource);
    createTableIfNotExists();
}

public void save(Account account) {
    String sql = "INSERT OR REPLACE INTO accounts "
            + "(account_number, owner_name, balance) VALUES (?, ?, ?)";
    run.update(sql, account.getAccountNumber(), account.getOwner().getName(), account.getBalance());
}
```

`QueryRunner` is built once in the constructor from `SQLiteDataSource`. `INSERT OR REPLACE` saves a new row, or overwrites an old row if that account number already exists; `run.update(...)` handles the connection and parameter binding.

---

## Bank.saveAccount(): Saving Again After a Change

In-memory storage "saves" a change automatically: the object changed in memory is the exact same object stored in the `HashMap`. JDBC storage does NOT work this way; changing an `Account` object in memory never automatically changes its row in the database.

```java
public void saveAccount(Account account) {
    repository.save(account);
}
```

---

## Code Example: Calling saveAccount() After a Change

```java
account.deposit(amount);
bank.saveAccount(account);
loadAccounts();
```

`saveAccount(account)` must be called again after every `deposit()`, `withdraw()`, or `processMonthEnd()`, at the exact same point where the balance change happens.

---

## Common Mistake: Forgetting to Call saveAccount()

<div class="warn-box">
<b>Wrong:</b> calling <code>account.deposit(amount);</code> then immediately <code>loadAccounts();</code>, with no <code>bank.saveAccount(account);</code> in between.
</div>

**Right:** without `saveAccount(...)`, the balance change only happens to the `Account` object in memory, and is NEVER saved to the database. `loadAccounts()` happens to still display the correct balance (since it reads from that same object in memory), so this bug easily slips by unnoticed, until the application closes and reopens, and the balance reverts to its value before the deposit.

---

## Exercise

A user deposits Rp 50,000 into `SavingsAccount` A001, and the table immediately shows the correct new balance. However, the line `bank.saveAccount(account);` turns out to have been left out of the `depositButtonActionPerformed` code.

What happens to A001's balance after the application is closed and reopened? Explain.

---

## Exercise Answer

**The balance reverts to its value BEFORE the Rp 50,000 deposit.** The deposit only changes the `Account` object in memory, and `loadAccounts()` reads from that same object, so the table briefly shows the "correct" balance. But without `saveAccount(...)`, the database row is never updated; once the application closes, the object in memory is lost, and when reopened, the data is reloaded from the database, which still holds the old balance.

---

## Part 3 Summary

- `AccountRepository` is now implemented by `JdbcAccountRepository`, saving data to the file `bankmini.db`, with no change to `Bank` at all (Dependency Inversion Principle).
- JDBC storage is not automatically synced with the object in memory; `Bank.saveAccount(...)` must be called again after every change.
- Forgetting to call `saveAccount(...)` is a bug that easily slips by, since the display stays correct until the application is genuinely closed and reopened.

Next: Part 4 applies the same pattern to user authentication.

---

<!-- _class: divider -->

# Part 4
## Applying Authentication to Bank Mini

Session 4 of 4

---

## The Same Pattern, Applied Again

![h:300 UserRepository implemented by InMemoryUserRepository and JdbcUserRepository, exactly the AccountRepository pattern](../assets/uml/p15-userrepository-auth.png)

<div class="term-box">
<code>UserRepository</code> follows the exact same shape as <code>AccountRepository</code>: one interface, one in-memory implementation as a preview, one JDBC implementation for real storage. Once a design pattern is mastered, it can be reused for a different need.
</div>

---

## Code Example: JdbcUserRepository.findByUsername()

```java
public User findByUsername(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";
    return run.query(sql, new UserHandler(), username);
}
```

The same `QueryRunner` from Part 3 is used again here; `UserHandler` is a small `ResultSetHandler` that turns one query result row into a `User` object, EXACTLY the same pattern as `AccountHandler` in `JdbcAccountRepository`.

---

## LoginFrame: A Gate Before BankMiniFrame

![h:220 An empty LoginFrame window before being filled in](../assets/screenshots/pertemuan-15/p15-login-screen.png)

`Main.java` now runs `LoginFrame` first, instead of opening `BankMiniFrame` directly, using the `dispose()` + `new SomeFrame().setVisible(true)` pattern from Meeting 13. `BankMiniFrame` only opens once the entered credentials match data stored in the `users` table.

---

## Code Example: loginButtonActionPerformed

```java
User user = userRepository.findByUsername(username);
if (user == null || !user.getPasswordHash().equals(PasswordHasher.hash(password))) {
    JOptionPane.showMessageDialog(this,
            "Invalid username or password.",
            "Login failed", JOptionPane.ERROR_MESSAGE);
    passwordField.setText("");
    return;
}
dispose();
new BankMiniFrame().setVisible(true);
```

---

## Login Fails, Password Cleared

![h:200 An error dialog after attempting a login with the wrong password](../assets/screenshots/pertemuan-15/p15-login-failed.png)

`user == null` (username not found) and a wrong password are handled through the SAME check, both displaying the generic message "Invalid username or password.", never revealing which one was actually wrong.

<div class="warn-box">
Plain SHA-256 in this jobsheet is purely a simplification for practice. A production system uses an algorithm specifically designed for passwords, such as bcrypt, Argon2, or PBKDF2.
</div>

---

## Common Mistake: An Error Message That Leaks Information

<div class="warn-box">
<b>Wrong:</b> displaying a different message for "username not found" and "wrong password", e.g. <code>"Username not found"</code> vs <code>"Wrong password"</code>.
</div>

**Right:** the actual `LoginFrame` code deliberately displays the SAME generic message for both, `"Invalid username or password."`. A different message leaks information to an attacker: they would then know which username is valid, needing only to guess the password.

---

## The Complete Bank Mini Application Flow

![h:340 Main.java runs LoginFrame, invalid credentials show a dialog and stay on LoginFrame, valid credentials open BankMiniFrame with its forms and action dialogs](../assets/illustrations/application-flow.svg)

This is the complete shape of the window navigation flow built since Meeting 13: one active window at a time, switching through `dispose()` + `new SomeFrame().setVisible(true)`, with a login gate as the check before the main window may be accessed.

---

## Exercise

A user tries to log in with username `"admin"`, which does NOT EXIST in the `users` table.

What does `userRepository.findByUsername("admin")` return, and what message is displayed to the user? Explain why the message is designed that way.

---

## Exercise Answer

`findByUsername("admin")` returns `null`, since there is no row with that username. The check `user == null || ...` evaluates to `true` (short-circuit, the part after `||` does not need to be evaluated), so the "Invalid username or password." dialog is displayed, the EXACT SAME message as if the username existed but the password were wrong. This generic message is deliberately used so an attacker cannot tell "wrong username" apart from "wrong password".

---

## Part 4 Summary

- `UserRepository` follows the exact `AccountRepository` pattern: one interface, an in-memory and a JDBC implementation.
- `LoginFrame` checks credentials through `PasswordHasher.hash(...)` before opening `BankMiniFrame`.
- The login error message is deliberately made generic, not distinguishing "wrong username" from "wrong password", so no information leaks to an attacker.

---

## Meeting 15 Summary

- Persistence means data endures beyond the application's lifecycle; JDBC connects Java to a database, and `Connection`/`Statement`/`ResultSet` must be closed after use.
- Authentication verifies a user's identity before granting access; a password is stored as a one-way hash, never as-is.
- Bank Mini applies both: `JdbcAccountRepository` replaces in-memory storage with no change to `Bank`, and `LoginFrame` becomes the gate before `BankMiniFrame` opens.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, chapter on JDBC, Security

Oracle Java Tutorials: ["JDBC Basics"](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html), ["MessageDigest Class"](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/MessageDigest.html)

Apache Commons: ["DbUtils: JDBC Utility Component"](https://commons.apache.org/proper/commons-dbutils/)

Programming exercises for this material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 15

---

## Discussion

`seedSampleAccountsIfEmpty()` and `seedDefaultUserIfEmpty()` both check first whether their table is still empty before adding sample data. Explain in your own words: what would happen to account data and user credentials if that check were removed and sample data were added unconditionally every time the application runs? Relate your answer to the concept of persistence just learned.
