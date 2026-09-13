# Practicum Jobsheet: Meeting 11
## SOLID Principle and Collections

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 11 (Week 11) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Replace a plain array with `ArrayList`/`Map` from the Java Collections Framework, and explain its advantages over an array.
2. Apply the Single Responsibility Principle by separating the responsibility of recording transactions into its own class.
3. Apply the Dependency Inversion Principle by making a class depend on an interface, not on a concrete implementation.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 10.
- **Quick verification** before starting:
  ```bash
  java -version
  javac -version
  ```
  If both display a version number with no errors, the process may proceed.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Bank Switches from Array to Map

> **Concept Brief: Collections.** The Java Collections Framework provides ready-made data structures such as `ArrayList` (a list whose size adjusts automatically, needing no upfront size) and `HashMap`/`LinkedHashMap` (storing key-value pairs, with a lookup by key done directly without checking elements one by one). Both replace a plain array, whose size stays fixed since creation and whose search must check elements one by one.

![A fixed-size array with one-by-one search, compared with a Map with a direct lookup by key](../assets/uml/p11-collections-motivation.png){width=75%}

So far, `Bank` has stored accounts in a fixed-size `Account[] accounts`, with `findAccount()` checking elements one by one. Replace it with a `Map<String, Account>`, using the account number as the key:

![Bank.java using LinkedHashMap in place of an Account array](../assets/code/pertemuan-11/p11-01-bank.png){width=70%}

Update `Main.java`; the `Bank` constructor no longer needs a capacity:

![Main.java creating Bank with no capacity parameter](../assets/code/pertemuan-11/p11-01-main.png){width=70%}

> ✅ **Checkpoint:** the program's output remains identical to Meeting 10 (the `Withdrawal failed`, `Withdrawal succeeded`, `interest applied`, and `monthly fee` lines for A001, A002, A003).

> ⚠️ **If it fails:** if the error `incompatible types: Account cannot be converted to ...` appears in a loop, check whether the `for` loop uses `accounts.values()` (not `accounts` directly), since a `Map` cannot be iterated over like an array.

### Step 2: Transaction, Single Responsibility Principle

> **Concept Brief: Single Responsibility Principle.** One of the five SOLID principles, the Single Responsibility Principle, states that a class should have one responsibility, one reason to change. A class mixing many responsibilities at once (computing, formatting, sending, and so on) becomes hard to understand, and a change to one responsibility risks affecting another responsibility that is actually unrelated to it.

![One class with three responsibilities, split into three classes each with one responsibility](../assets/uml/p11-srp-split.png){width=72%}

Bank Mini has so far kept no transaction history at all. Add a class `Transaction`, whose sole responsibility is representing one transaction:

![Transaction.java](../assets/code/pertemuan-11/p11-02-transaction.png){width=55%}

![Account and Transaction, one Account holding many Transactions](../assets/uml/p11-transaction.png){width=68%}

`Account` keeps a list of its own `Transaction` entries, adding one every time `deposit()` or `withdraw()` succeeds:

![Account.java recording a Transaction on deposit and withdraw](../assets/code/pertemuan-11/p11-02-account.png){width=68%}

`Bank` gets a method to display one account's history:

![Bank.java with method printHistory](../assets/code/pertemuan-11/p11-02-bank.png){width=68%}

Update `Main.java`:

![Main.java calling printHistory](../assets/code/pertemuan-11/p11-02-main.png){width=70%}

> ✅ **Checkpoint:** the program adds the lines `A003 WITHDRAW 30000.0` and `A003 DEPOSIT 1400.0` after the lines from Step 1.

> ⚠️ **If it fails:** if the transaction history is empty, check whether `history.add(...)` is called AFTER validation succeeds (inside `deposit()` and `withdraw()`), not before the `canWithdraw()`/minimum amount check.

### Step 3: AccountRepository, Dependency Inversion Principle

> **Concept Brief: Dependency Inversion Principle.** One of the five SOLID principles, the Dependency Inversion Principle, states that a high-level class (governing business flow) should depend on an interface (an abstraction), rather than directly on a concrete implementation class. This way, the concrete implementation can be swapped at any time without changing a single line of code that depends on it.

`Bank` has so far stored `Map<String, Account>` directly inside itself. Separate the storage responsibility into its own interface:

![AccountRepository.java](../assets/code/pertemuan-11/p11-03-accountrepository.png){width=55%}

![InMemoryAccountRepository.java](../assets/code/pertemuan-11/p11-03-inmemoryaccountrepository.png){width=65%}

`Bank` now depends on the `AccountRepository` interface, not directly on `Map`:

![Bank.java depending on AccountRepository](../assets/code/pertemuan-11/p11-03-bank.png){width=70%}

![Bank depending on interface AccountRepository, implemented by InMemoryAccountRepository](../assets/uml/p11-accountrepository.png){width=75%}

Update `Main.java`:

![Main.java creating Bank with InMemoryAccountRepository](../assets/code/pertemuan-11/p11-03-main.png){width=70%}

> ✅ **Checkpoint:** the program's output remains identical to Step 2, not one line changing even though the way accounts are stored has changed completely.

> ⚠️ **If it fails:** if the error `constructor Bank in class Bank cannot be applied to given types` appears, check whether `Main.java` now calls `new Bank(new InMemoryAccountRepository())`, not `new Bank()` as in Steps 1-2.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 3.
- **Independent assignment:**
  1. Add `Bank.totalAssets()`, returning a `double` total balance across every account (using `repository.findAll()`), then print the result in `Main.java`:

     ![Bank.java with method totalAssets](../assets/code/pertemuan-11/p11-tugas-bank.png){width=68%}

  2. Answer briefly (2 to 3 sentences for each question): (a) `findAccount()` now searches through a `Map`, no longer checking an array one by one. Explain the difference in search speed conceptually. (b) Meeting 15 will replace `InMemoryAccountRepository` with `JdbcAccountRepository`, storing data to a database. Explain why `Bank.java` does not need a single line changed for that swap, and which SOLID principle makes this possible.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `totalAssets()` correct, conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
