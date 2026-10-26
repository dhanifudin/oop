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
4. Trace and prove the Open/Closed Principle, Liskov Substitution Principle, and Interface Segregation Principle through `Account`/`Bank` code already built since the Inheritance through Polymorphism topics.
5. Add a new `Account` subclass with no change to the existing `Account`/`Bank` code, as direct proof of all five SOLID principles working together.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from the Polymorphism and Exception Handling topic.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Bank Switches from an Array to a Map

> **Concept Brief: Collections.** A plain array has a fixed size from the moment it is created, and searching it checks elements one by one. The Java Collections Framework provides replacements. An `ArrayList` is a list whose size adjusts automatically. A `Map` stores pairs of a key and a value, so searching by key can be direct. Bank Mini uses `LinkedHashMap`, a `Map` that keeps elements in the order they were added.

> **Concept Brief: Generics.** The `<...>` in `Map<String, Account>` is called a *type parameter*. It states which types may be stored. In `Map<String, Account>`, the key must be a `String` and the value must be an `Account`. If a type is wrong, the compiler rejects the code at once. Without `<...>`, the mistake only appears as a `ClassCastException` when the program runs.

![A fixed-size array searched one by one, compared with a Map searched directly by key](../assets/uml/p11-collections-motivation.png){width=75%}

**Goal of this step:** replace the account storage, from an array to a `Map`, without changing the program's output.

So far, `Bank` stores accounts in a fixed-size `Account[] accounts`, and `findAccount()` checks elements one by one. With `Map<String, Account>`, the account number becomes the key, so lookup is direct.

1. Open `Bank.java`. Replace the array with a `LinkedHashMap`. Every method that walks through the accounts now uses `accounts.values()`:

![Bank.java using LinkedHashMap in place of the Account array](../assets/code/pertemuan-11/p11-01-bank.png){width=85%}

2. Update `Main.java`. The `Bank` constructor no longer needs a capacity:

![Main.java creating Bank without a capacity parameter](../assets/code/pertemuan-11/p11-01-main.png){width=70%}

**Expected output:**

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
```

**Why is that?**

- The output is the same as in Meeting 10, because only how accounts are stored changed, not what `Bank` does.
- `LinkedHashMap` keeps the order of insertion, so A001, A002, and A003 stay in order.
- `accounts.values()` returns all accounts. `findAccount()` uses `accounts.get(...)`, which looks up the account number directly.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `for-each not applicable to expression type` appears, check whether the `for` loop uses `accounts.values()` rather than `accounts` itself. A `Map` cannot be looped over directly like an array.

### Step 2: Transaction, Single Responsibility Principle

> **Concept Brief: Single Responsibility Principle.** The Single Responsibility Principle (SRP) says that a class should have one responsibility, which means one reason to change. A class that mixes many responsibilities is hard to understand. A change to one responsibility risks breaking another.

![One class with three responsibilities, split into three classes with one responsibility each](../assets/uml/p11-srp-split.png){width=72%}

> **Concept Brief: enum.** An `enum` declares a fixed set of named constants. The compiler rejects any value outside those constants. `TransactionType.DEPOSIT` is always valid. A typo such as `"WITHDRAWL"` in a `String` still compiles, and is only noticed when the transaction history no longer makes sense.

**Goal of this step:** record transaction history, with one dedicated class that represents a single transaction.

So far Bank Mini does not record transaction history. Add a `Transaction` class whose only responsibility is to represent one transaction:

1. Create a new file `TransactionType.java`:

![TransactionType.java](../assets/code/pertemuan-11/p11-02-transactiontype.png){width=45%}

2. Create a new file `Transaction.java`:

![Transaction.java](../assets/code/pertemuan-11/p11-02-transaction.png){width=55%}

![Account and Transaction, one Account holds many Transactions](../assets/uml/p11-transaction.png){width=68%}

3. Open `Account.java`. `Account` keeps a list of its own `Transaction` objects, and adds one each time a `deposit()` or `withdraw()` succeeds:

![Account.java recording a Transaction in deposit and withdraw](../assets/code/pertemuan-11/p11-02-account.png){width=68%}

4. Open `Bank.java`. Add a method to display the history of one account:

![Bank.java with the printHistory method](../assets/code/pertemuan-11/p11-02-bank.png){width=68%}

5. Update `Main.java`:

![Main.java calling printHistory](../assets/code/pertemuan-11/p11-02-main.png){width=70%}

**Expected output:** the seven lines from Step 1 stay the same, followed by two history lines:

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
A003 WITHDRAW 30000.0
A003 DEPOSIT 1400.0
```

**Why is that?**

- The 70000 withdrawal is rejected, so it is not recorded. `history.add(...)` only runs after validation succeeds.
- The 30000 withdrawal succeeds, so it is recorded as `WITHDRAW`.
- A003's interest is deposited through `deposit()`, so it is recorded as `DEPOSIT 1400.0` (2% of 70000).

> ✅ **Checkpoint:** the last two lines of the output match the block above.

> ⚠️ **If it fails:** if the transaction history is empty, check whether `history.add(...)` is called **after** validation succeeds (inside `deposit()` and `withdraw()`), not before the `canWithdraw()` or minimum amount check. If the error `cannot find symbol: variable DEPOSIT` appears, check whether the constant is written as `TransactionType.DEPOSIT`, not `Transaction.DEPOSIT`. The constant comes from the `TransactionType` class.

### Step 3: AccountRepository, Dependency Inversion Principle

> **Concept Brief: Dependency Inversion Principle.** The Dependency Inversion Principle (DIP) says that the class that controls the flow (a high-level class) should depend on an interface, not on a concrete class. That way, the concrete implementation can be replaced without changing the class that uses it.

**Goal of this step:** separate the storage responsibility from `Bank`, so `Bank` only knows an interface.

So far `Bank` keeps a `Map<String, Account>` inside itself. Separate the storage into its own interface:

1. Create a new file `AccountRepository.java`:

![AccountRepository.java](../assets/code/pertemuan-11/p11-03-accountrepository.png){width=55%}

2. Create a new file `InMemoryAccountRepository.java`:

![InMemoryAccountRepository.java](../assets/code/pertemuan-11/p11-03-inmemoryaccountrepository.png){width=65%}

![Bank depends on the AccountRepository interface, implemented by InMemoryAccountRepository](../assets/uml/p11-accountrepository.png){width=75%}

How to read the diagram: the label `«interface»` marks `AccountRepository` as an interface. The dashed arrow means `InMemoryAccountRepository` implements it. `Bank` only knows that interface.

3. Open `Bank.java`. `Bank` now depends on `AccountRepository`, not on a `Map`. Every method uses `repository.findAll()` and `repository.findByNumber(...)`:

![Bank.java depending on AccountRepository](../assets/code/pertemuan-11/p11-03-bank.png){width=85%}

4. Update `Main.java`:

![Main.java creating Bank with an InMemoryAccountRepository](../assets/code/pertemuan-11/p11-03-main.png){width=70%}

This interface is deliberately not made generic (`Repository<T>`), because Bank Mini only stores one kind of data so far, which is `Account`.

**Expected output:** exactly the same as Step 2:

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
A003 WITHDRAW 30000.0
A003 DEPOSIT 1400.0
```

**Why is that?**

- Only how accounts are stored changed. What `Bank` does did not change, so not a single line of output is different.
- `Bank` does not know its storage is a `Map`. It only calls methods of `AccountRepository`.
- The code that creates `Bank` (in `Main.java`) chooses which implementation is used.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `constructor Bank in class Bank cannot be applied to given types` appears, check whether `Main.java` calls `new Bank(new InMemoryAccountRepository())`, not `new Bank()` as in Steps 1 and 2.

### Step 4: Tracing OCP, LSP, and ISP in Code Already Built

> **Concept Brief: Open/Closed Principle.** The Open/Closed Principle (OCP) says that a class is open for extension (new subclasses) but closed for modification (old code is not touched). Bank Mini has applied it since the Overriding and Overloading topic.

> **Concept Brief: Liskov Substitution Principle.** The Liskov Substitution Principle (LSP) says that a subclass must be able to replace its superclass anywhere without making the program wrong. `Bank` processing `Account` objects polymorphically already shows it.

> **Concept Brief: Interface Segregation Principle.** The Interface Segregation Principle (ISP) says that an interface should be small and focused, so classes are not forced to fill in methods that do not apply to them. `InterestBearing` is an example.

**Goal of this step:** prove that all three principles already work in the code you built, without writing new code.

**4a. Open/Closed.** `SavingsAccount.canWithdraw()` (keeps a minimum balance) and `CheckingAccount.canWithdraw()` (allows an overdraft) override the same method with different rules. `Account` and `Bank` never know either rule:

![SavingsAccount.java overriding canWithdraw to keep a minimum balance](../assets/code/pertemuan-11/p11-04-savingsaccount.png){width=65%}

![CheckingAccount.java overriding canWithdraw for the overdraft](../assets/code/pertemuan-11/p11-04-checkingaccount.png){width=65%}

1. Open `Account.java` and `Bank.java`. Use the Find feature (Ctrl+F, or Find in NetBeans) to search both files for the words `SavingsAccount` and `CheckingAccount`.

> ✅ **Checkpoint:** the search finds no occurrence of `SavingsAccount` or `CheckingAccount` in `Account.java` or `Bank.java`. A new withdrawal rule only needs an override, with no change to `Account` or `Bank`. This is the **Open/Closed Principle**.

**4b. Liskov Substitution.** `Bank.processMonthEnd()` calls `acc.monthlyFee()` through the `Account` type. That method is trusted to be fulfilled by any subclass:

![Bank.java with the processMonthEnd method, polymorphic iteration through the repository](../assets/code/pertemuan-11/p11-04-bank.png){width=68%}

2. Look again at the output of Step 3. The `monthly fee` lines differ for each kind of account.

> ✅ **Checkpoint:** the output shows `A001 monthly fee: 0.0` and `A003 monthly fee: 0.0` (`SavingsAccount`), but `A002 monthly fee: 15000.0` (`CheckingAccount`). Yet `processMonthEnd()` uses one and the same line `acc.monthlyFee()`, with no `if`/`else` branch per kind. Both subclasses replace `Account` without making the program wrong. This is the **Liskov Substitution Principle**.

**4c. Interface Segregation.** `InterestBearing` holds only one method, `applyInterest()`, and only `SavingsAccount` implements it:

![InterestBearing.java, a small one-method interface](../assets/code/pertemuan-11/p11-04-interestbearing.png){width=45%}

3. Search for the word `InterestBearing` in `SavingsAccount.java` and in `CheckingAccount.java`.

> ✅ **Checkpoint:** `InterestBearing` appears in `SavingsAccount.java` (on the `implements` line), but does not appear at all in `CheckingAccount.java`. `CheckingAccount` is not forced to implement an `applyInterest()` that makes no sense for it. This is the **Interface Segregation Principle**.

**Why is that?**

- `Account` and `Bank` never mention a concrete class name, so a new subclass does not force either of them to change.
- The caller treats every `Account` the same way, and each subclass answers by its own rule.
- An ability that only some classes have becomes a small interface, not a method in `Account`.

> ⚠️ **If it fails:** if `SavingsAccount` or `CheckingAccount` does appear in `Account.java` or `Bank.java`, check whether either file writes a concrete class name, for example `if (acc instanceof SavingsAccount)`. The check should go through a polymorphic method or an `instanceof` check against an interface.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 3, and after the independent assignment.
- **Independent assignment:**
  1. **BusinessAccount: proving OCP, LSP, and ISP through new code.** Add `BusinessAccount extends Account` (a business account, minimum balance Rp 1,000,000, earning no interest). The diagram below is only a sketch of which methods need overriding, NOT finished code; the actual implementation of `canWithdraw()`/`monthlyFee()`/`printInfo()` (exactly the `SavingsAccount`/`CheckingAccount` pattern already traced in Step 4) is entirely up to you:

     ![Sketch of BusinessAccount, methods to override with no implementation](../assets/uml/p11-tugas-businessaccount.png){width=60%}

     Register it with `Bank`, then run `processMonthEnd()` and `printAllAccounts()`:

     ![Main.java adding BusinessAccount, calling processMonthEnd and printAllAccounts](../assets/code/pertemuan-11/p11-tugas-main.png){width=70%}

     Prove both of the following from the program's output, with no change to a single line of `Account.java` or `Bank.java`:
     - **(Open/Closed + Liskov Substitution)** the line `A004 monthly fee: 0.0` appears among `processMonthEnd()`'s other lines, and `A004 - Budi - balance: 2000000.0` followed by `Account type: Business` appears in `printAllAccounts()`, exactly as `SavingsAccount`/`CheckingAccount` were processed earlier.
     - **(Interface Segregation)** no `A004 interest applied` line appears, since `BusinessAccount` does not implement `InterestBearing`.
  2. Answer briefly (2 to 3 sentences for each question):
     - (a) name which SOLID principle is proven by the fact that `Account.java`/`Bank.java` did not change at all after `BusinessAccount` was added, and explain why.
     - (b) `processMonthEnd()` uses `instanceof InterestBearing`, not `instanceof SavingsAccount`. Explain why `BusinessAccount` is automatically handled correctly (not charged interest) with no need for `Bank.java` to know anything about its existence.
     - (c) Sometime later, `InMemoryAccountRepository` will be replaced with another class that stores data in a database. Explain why `Bank.java` does not need a single line changed for that swap, and which SOLID principle makes this possible.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `BusinessAccount` correct, OCP/LSP/ISP proven through output, conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
