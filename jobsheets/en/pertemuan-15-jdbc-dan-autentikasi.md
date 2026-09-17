# Practicum Jobsheet: Meeting 15
## Persistence with JDBC and an Authentication Mechanism

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 15 (Week 15) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Replace the `AccountRepository` implementation from in-memory storage to JDBC/SQLite storage, with no change to the `Bank` or `BankMiniFrame` code that uses it.
2. Explain why a method that changes data (`deposit()`, `withdraw()`, `processMonthEnd()`) must explicitly call storage again on a database-backed store, unlike in-memory storage.
3. Build a simple login mechanism (username and hashed password) that restricts access to `BankMiniFrame` to only the user whose credentials are stored in the database.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans.
- **Project**: this meeting continues the Maven `bank-mini` project from Meeting 14. Add TWO dependencies to `pom.xml` (right-click the project > **Properties > Libraries > Add Dependency**): SQLite JDBC Driver (`org.xerial:sqlite-jdbc:3.45.1.0`) and Apache Commons DbUtils (`commons-dbutils:commons-dbutils:1.8.1`), which simplify writing JDBC code in Steps 1 and 2.

> **Without NetBeans?** Add both dependencies below manually to `pom.xml`, inside the `<dependencies>` element:
> ```xml
> <dependency>
>   <groupId>org.xerial</groupId>
>   <artifactId>sqlite-jdbc</artifactId>
>   <version>3.45.1.0</version>
> </dependency>
> <dependency>
>   <groupId>commons-dbutils</groupId>
>   <artifactId>commons-dbutils</artifactId>
>   <version>1.8.1</version>
> </dependency>
> ```
> Every work step in this jobsheet needs no NetBeans (there is no new form design), so it can be followed entirely with `mvn -q compile exec:java`.

## C. Work Steps

### Step 1: JdbcAccountRepository, Accounts Stored in a Database

> **Concept Brief: JDBC.** JDBC (Java Database Connectivity) is Java's built-in API for communicating with a relational database through plain SQL commands. Its three main elements: `Connection` (a connection to one database file/server), `Statement`/`PreparedStatement` (a carrier of an SQL command, with `PreparedStatement` using a question mark `?` as a value placeholder to stay safe from SQL injection), and `ResultSet` (a query's result rows, read one at a time through `next()`). SQLite stores an entire database in a single ordinary file on disk (`bankmini.db`), so it needs no separate database server, fitting a practicum exercise well.

> **Concept Brief: Apache Commons DbUtils.** Writing `Connection`/`PreparedStatement`/`ResultSet` manually in every method is repetitive and easy to forget to close. `QueryRunner` from Apache Commons DbUtils wraps this pattern: `run.update(sql, params...)` for `INSERT`/`UPDATE`/`CREATE TABLE`, `run.query(sql, handler, params...)` for `SELECT`, both opening and closing their own connection. `QueryRunner` is built once from a `SQLiteDataSource` (not directly from a `Connection`), so every call to `run.update(...)`/`run.query(...)` automatically fetches and closes its own connection, removing the risk of forgetting `close()`.

Since Meeting 11, `Bank` has already depended on the `AccountRepository` interface, not directly on its concrete implementation (Dependency Inversion Principle). Thanks to that, in-memory storage can be replaced with JDBC storage just by writing a new implementation, with no change to `Bank` at all:

![JdbcAccountRepository.java, constructor and save](../assets/code/pertemuan-15/p15-01-jdbcaccountrepository-save.png){width=75%}

![JdbcAccountRepository.java, findByNumber, findAll, and mapRow](../assets/code/pertemuan-15/p15-01-jdbcaccountrepository-find.png){width=75%}

`createTableIfNotExists()` is called in the constructor, creating the `accounts` table automatically on first contact with the database, so this checkpoint needs no separate database setup step. Column `account_type` stores `"SAVINGS"` or `"CHECKING"` so `mapRow()` knows which `Account` subclass to recreate when reading a row.

Update `BankMiniFrame` to use `JdbcAccountRepository`, no longer `InMemoryAccountRepository`:

![BankMiniFrame.java, constructor using JdbcAccountRepository](../assets/code/pertemuan-15/p15-01-bankminiframe-constructor.png){width=75%}

Note `seedSampleAccountsIfEmpty()`: the two sample accounts are only added if the `accounts` table is still empty. Without this check, both sample accounts would be rewritten with their initial balance every time the application runs, overwriting any balance change already saved from a previous session, exactly the opposite of what persistence is for.

One important thing easy to miss: in-memory storage "saves" a change automatically, since the changed `Account` object is the SAME object stored in the map. JDBC storage does not work that way; changing an `Account` object in memory does not automatically rewrite its row in the database. `Bank` gets a new method to close this gap:

![Bank.java, method saveAccount](../assets/code/pertemuan-15/p15-01-bank-saveaccount.png){width=68%}

![Bank.java, processMonthEnd calling repository.save after interest is applied](../assets/code/pertemuan-15/p15-01-bank-processmonthend.png){width=68%}

`BankMiniFrame` calls `bank.saveAccount(account)` after every successful transaction:

![BankMiniFrame.java, depositButtonActionPerformed and withdrawButtonActionPerformed calling saveAccount](../assets/code/pertemuan-15/p15-01-bankminiframe-savecalls.png){width=75%}

> ✅ **Checkpoint:** run **Run Project** (F6), select one of the accounts in the table, click **Deposit...**, then fill in the amount input dialog. After the balance increases, **close the application completely** and run it again. The balance just changed still shows, not reverting to its original value, proof that the data is genuinely stored in the `bankmini.db` file, not merely in memory while the application runs.

> ⚠️ **If it fails:** if the balance reverts to its original value every time the application is run again, check two possibilities: (1) `bank.saveAccount(account)` is genuinely called after `account.deposit(amount)`/`account.withdraw(amount)`, not just `loadAccounts()`; (2) `seedSampleAccountsIfEmpty()` checks `bank.getAllAccounts().isEmpty()` before adding the sample accounts, rather than adding them unconditionally.

### Step 2: A Simple Login with a Hashed Password

> **Concept Brief: Password Hashing.** Storing a password as-is (plain text) in a database is highly risky: anyone who accesses the database file can read every user's password. Hashing turns a password into a string of random-looking characters (a hash) through a one-way function, unable to be reversed to recover the original password. At login, the password the user types is hashed again with the same function, then the result is compared with the stored hash; the original password is never stored nor compared directly.

Bank Mini uses SHA-256 (available directly through `java.security.MessageDigest`, with no extra dependency) for this exercise:

![PasswordHasher.java](../assets/code/pertemuan-15/p15-02-passwordhasher.png){width=68%}

> ⚠️ **Not for production.** Plain SHA-256 (no salt, a single hash pass) is easily attacked through a rainbow table on a genuine production system. A real application uses an algorithm specifically designed for passwords, such as bcrypt, Argon2, or PBKDF2, which add a random salt and are deliberately made slow to compute. `PasswordHasher` in this jobsheet is purely a simplification for practice, not a ready-to-use example.

Following the `AccountRepository` pattern already learned since Meeting 11 (an interface plus an in-memory implementation as a preview, followed by a JDBC version), user credentials use the exact same structure:

![InMemoryUserRepository.java](../assets/code/pertemuan-15/p15-02-inmemoryuserrepository.png){width=68%}

`InMemoryUserRepository` above shows that `UserRepository` could be implemented as simply as a `HashMap` for testing purposes. But since credentials must still exist even after the application closes, this checkpoint uses the JDBC version:

![JdbcUserRepository.java, constructor and seedDefaultUserIfEmpty](../assets/code/pertemuan-15/p15-02-jdbcuserrepository-seed.png){width=75%}

![JdbcUserRepository.java, save and findByUsername](../assets/code/pertemuan-15/p15-02-jdbcuserrepository-find.png){width=75%}

Build a class `LoginFrame` (an ordinary JFrame, a login form: `Username` as a `JTextField`, `Password` as a **`JPasswordField`**, not a plain `JTextField`, so the typed characters are hidden as dots) with one **Login** button. Fill in the button's handler:

![LoginFrame.java, loginButtonActionPerformed](../assets/code/pertemuan-15/p15-02-loginhandler.png){width=75%}

> **Concept Brief: Switching to Another Window.** Note the last two lines on a successful login: `dispose()` closes and releases the resources of the currently active `LoginFrame` window, then `new BankMiniFrame().setVisible(true)` creates and displays a new window. This `dispose()` followed by `new SomeFrame().setVisible(true)` pattern is Swing's standard way of switching from one window to another, already introduced as a concept in Meeting 13; `LoginFrame` here is where it is genuinely put into practice for the first time, since Meetings 13-14 only ever opened one window (`BankMiniFrame`) directly from `Main.java`, never closing one window to open another.

![An empty LoginFrame window before being filled in](../assets/screenshots/pertemuan-15/p15-login-screen.png){width=55%}

![An error dialog after attempting a login with the wrong password](../assets/screenshots/pertemuan-15/p15-login-failed.png){width=55%}

Finally, `Main.java` runs `LoginFrame` first, instead of opening `BankMiniFrame` directly:

![Main.java running LoginFrame](../assets/code/pertemuan-15/p15-02-main.png){width=68%}

<!-- TODO(screenshot): mock-up SVG, not a genuine screenshot. Replace with a genuine BankMiniFrame screenshot once display access is available; see conventions/bank-mini.md, section "Verifikasi visual GUI tanpa NetBeans/X server". -->
![Mock-up of the BankMiniFrame window after a successful login](../assets/uml/p14-window-selected.png){width=60%}

The following diagram summarizes the complete Bank Mini application flow up to this point, from `Main.java` to a transaction in `BankMiniFrame`:

![Bank Mini application flow: Main.java, LoginFrame, and BankMiniFrame](../assets/uml/p15-application-flow.png){width=75%}

> ✅ **Checkpoint:** run **Run Project** (F6). The `LoginFrame` window appears first. Try logging in with username `teller1` and a wrong password; an error dialog "Invalid username or password." appears and the login window stays open. Log in again with the correct password, `teller123`; the login window closes and `BankMiniFrame` opens, displaying the account list as usual.

> ⚠️ **If it fails:** if a login with correct credentials is still rejected, check whether the typed password is hashed first through `PasswordHasher.hash()` before being compared, rather than compared directly with `user.getPasswordHash()` (a hash will never match plain text).

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the `LoginFrame` window, the error dialog from a failed login attempt, and `BankMiniFrame` after a successful login.
- **Independent assignment:**
  1. Change `BankMiniFrame`'s constructor to accept a `username` parameter, then display `"Bank Mini - Logged in as: <username>"` as the window title. Adjust `LoginFrame` to pass the successfully logged-in username to that constructor:

     ![BankMiniFrame.java, constructor accepting a username parameter](../assets/code/pertemuan-15/p15-tugas-bankminiframe-constructor.png){width=75%}

     ![LoginFrame.java, passing the username to BankMiniFrame](../assets/code/pertemuan-15/p15-tugas-loginhandler.png){width=75%}

  2. Add a second user in `seedDefaultUserIfEmpty()` (e.g. `teller2` with password `teller456`):

     ![JdbcUserRepository.java, two sample users](../assets/code/pertemuan-15/p15-tugas-seconduser.png){width=68%}

     <!-- TODO(screenshot): mock-up SVG, not a genuine screenshot. Replace with a genuine BankMiniFrame screenshot once display access is available; see conventions/bank-mini.md, section "Verifikasi visual GUI tanpa NetBeans/X server". -->
     ![Mock-up of the BankMiniFrame window displaying the username in its title after logging in as teller2](../assets/uml/p15-window-logged-in.png){width=60%}

  3. Answer briefly (2 to 3 sentences for each question):
     - (a) why do `seedSampleAccountsIfEmpty()` and `seedDefaultUserIfEmpty()` both check for an empty condition before adding data, and what happens if that check is removed?
     - (b) `UserRepository` and `AccountRepository` are two different interfaces, but both follow the same design pattern. Name that pattern, and explain one concrete benefit of following it here.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot) | Some checkpoints demonstrated |
| Independent assignment | 25% | Window title and second user correct, conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
