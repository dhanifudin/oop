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

> **Concept Brief: Generics.** The `<...>` written in `Map<String, Account>` is called a *type parameter*: this part states what type the data structure is allowed to hold. With `Map<String, Account>`, the compiler ensures only a `String` can be used as a key and only an `Account` can be stored as a value; a wrong type is caught at compile time, instead of causing a `ClassCastException` later once the program is already running.

![A fixed-size array with one-by-one search, compared with a Map with a direct lookup by key](../assets/uml/p11-collections-motivation.png){width=75%}

So far, `Bank` has stored accounts in a fixed-size `Account[] accounts`, with `findAccount()` checking elements one by one. Replace it with a `Map<String, Account>`, using the account number as the key:

![Bank.java using LinkedHashMap in place of an Account array](../assets/code/pertemuan-11/p11-01-bank.png){width=65%}

Apply the same `accounts.values()` change to `printMonthlyFees()` and `processMonthEnd()` (not shown here, the pattern is identical to `printAllAccounts()` above).

Update `Main.java`; the `Bank` constructor no longer needs a capacity:

![Main.java creating Bank with no capacity parameter](../assets/code/pertemuan-11/p11-01-main.png){width=70%}

> ✅ **Checkpoint:** the program's output remains identical to before, from the Polymorphism and Exception Handling topic (the `Withdrawal failed`, `Withdrawal succeeded`, `interest applied`, and `monthly fee` lines for A001, A002, A003).

> ⚠️ **If it fails:** if the error `incompatible types: Account cannot be converted to ...` appears in a loop, check whether the `for` loop uses `accounts.values()` (not `accounts` directly), since a `Map` cannot be iterated over like an array.

### Step 2: Transaction, Single Responsibility Principle

> **Concept Brief: Single Responsibility Principle.** One of the five SOLID principles, the Single Responsibility Principle, states that a class should have one responsibility, one reason to change. A class mixing many responsibilities at once (computing, formatting, sending, and so on) becomes hard to understand, and a change to one responsibility risks affecting another responsibility that is actually unrelated to it.

![One class with three responsibilities, split into three classes each with one responsibility](../assets/uml/p11-srp-split.png){width=72%}

Bank Mini has so far kept no transaction history at all. Add a class `Transaction`, whose sole responsibility is representing one transaction.

> **Concept Brief: enum.** An `enum` declares a fixed set of named constants. Unlike a `String`, the compiler rejects any value outside the declared constants: `TransactionType.DEPOSIT` is always valid, while a typo such as `"WITHDRAWL"` on a `String` still compiles successfully and only surfaces much later, once a transaction history line stops making sense.

![TransactionType.java](../assets/code/pertemuan-11/p11-02-transactiontype.png){width=45%}

![Transaction.java](../assets/code/pertemuan-11/p11-02-transaction.png){width=55%}

![Account and Transaction, one Account holding many Transactions](../assets/uml/p11-transaction.png){width=68%}

`Account` keeps a list of its own `Transaction` entries, adding one every time `deposit()` or `withdraw()` succeeds:

![Account.java recording a Transaction on deposit and withdraw](../assets/code/pertemuan-11/p11-02-account.png){width=68%}

`Bank` gets a method to display one account's history:

![Bank.java with method printHistory](../assets/code/pertemuan-11/p11-02-bank.png){width=68%}

Update `Main.java`:

![Main.java calling printHistory](../assets/code/pertemuan-11/p11-02-main.png){width=70%}

> ✅ **Checkpoint:** the program adds the lines `A003 WITHDRAW 30000.0` and `A003 DEPOSIT 1400.0` after the lines from Step 1.

> ⚠️ **If it fails:** if the transaction history is empty, check whether `history.add(...)` is called AFTER validation succeeds (inside `deposit()` and `withdraw()`), not before the `canWithdraw()`/minimum amount check. If the error `cannot find symbol: variable DEPOSIT` or similar appears, check whether the constant is called as `TransactionType.DEPOSIT` (not `Transaction.DEPOSIT`), since the enum constant comes from the `TransactionType` class, not `Transaction`.

### Step 3: AccountRepository, Dependency Inversion Principle

> **Concept Brief: Dependency Inversion Principle.** One of the five SOLID principles, the Dependency Inversion Principle, states that a high-level class (governing business flow) should depend on an interface (an abstraction), rather than directly on a concrete implementation class. This way, the concrete implementation can be swapped at any time without changing a single line of code that depends on it.

> **Concept Brief: Declaring a Generic Interface.** Besides being used (as in `Map<String, Account>` in Step 1), an interface or class can also BE declared generic, by writing `<T>` right after its own name:
> ```java
> public interface Repository<T> {
>     void save(T item);
>     T findByNumber(String id);
>     Collection<T> findAll();
> }
> ```
> `T` here is a *type parameter*, filled in later by whoever implements this interface (e.g. `Repository<Account>`). The snippet above is NOT something to add to your project, purely an illustration: `AccountRepository` could in principle have been written following this same `Repository<T>` pattern, but Bank Mini deliberately keeps it as its own concrete interface, since there is so far only one entity type (`Account`) that needs storing; generalizing through `<T>` only genuinely pays off once a second entity type also needs storing the same way.

`Bank` has so far stored `Map<String, Account>` directly inside itself. Separate the storage responsibility into its own interface:

![AccountRepository.java](../assets/code/pertemuan-11/p11-03-accountrepository.png){width=55%}

![InMemoryAccountRepository.java](../assets/code/pertemuan-11/p11-03-inmemoryaccountrepository.png){width=65%}

`Bank` now depends on the `AccountRepository` interface, not directly on `Map`:

![Bank.java depending on AccountRepository](../assets/code/pertemuan-11/p11-03-bank.png){width=65%}

The other methods (`printMonthlyFees()`, `processMonthEnd()`, `printHistory()`) follow the same pattern: `accounts.values()` becomes `repository.findAll()`, and `accounts.get(...)` becomes `repository.findByNumber(...)`.

![Bank depending on interface AccountRepository, implemented by InMemoryAccountRepository](../assets/uml/p11-accountrepository.png){width=75%}

Update `Main.java`:

![Main.java creating Bank with InMemoryAccountRepository](../assets/code/pertemuan-11/p11-03-main.png){width=70%}

> ✅ **Checkpoint:** the program's output remains identical to Step 2, not one line changing even though the way accounts are stored has changed completely.

> ⚠️ **If it fails:** if the error `constructor Bank in class Bank cannot be applied to given types` appears, check whether `Main.java` now calls `new Bank(new InMemoryAccountRepository())`, not `new Bank()` as in Steps 1-2.

### Step 4: Tracing OCP, LSP, and ISP in Code Already Built

> **Concept Brief: Open/Closed Principle.** One of the five SOLID principles, the Open/Closed Principle, states that a class should be open for extension (a new subclass) but closed for modification (existing code untouched). Bank Mini has actually applied this principle since the Overriding and Overloading topic already; no new code is needed to prove it, only tracing.

> **Concept Brief: Liskov Substitution Principle.** One of the five SOLID principles, the Liskov Substitution Principle, states that a subclass must be able to stand in for its superclass anywhere without changing the correctness of the program. `Bank` processing `Account` polymorphically since the Abstract Class/Interface and Polymorphism topics already shows this principle at work.

> **Concept Brief: Interface Segregation Principle.** One of the five SOLID principles, the Interface Segregation Principle, states that an interface should be small and focused, never forcing a class to implement a method irrelevant to it. `InterestBearing`, already built since the Abstract Classes and Interfaces topic, is a genuine example of it.

`SavingsAccount.canWithdraw()` (enforcing a minimum balance) and `CheckingAccount.canWithdraw()` (allowing overdraft) override the same method with different rules, with neither `Account` nor `Bank` ever knowing either subclass's specific rule:

![SavingsAccount.java overriding canWithdraw to enforce a minimum balance](../assets/code/pertemuan-11/p11-04-savingsaccount.png){width=65%}

![CheckingAccount.java overriding canWithdraw for overdraft](../assets/code/pertemuan-11/p11-04-checkingaccount.png){width=65%}

Prove it directly from the command line, not just by reading the code:

```bash
grep -c "SavingsAccount\|CheckingAccount" src/id/ac/polinema/Account.java src/id/ac/polinema/Bank.java
```

> ✅ **Checkpoint:** both `grep` results above show `0`. `Account.java` and `Bank.java` never name either concrete class at all, even though both must process `SavingsAccount` and `CheckingAccount` differently. This is the **Open/Closed Principle**: a new withdrawal rule is simply written through an override, with no change to `Account`/`Bank`.

`Bank.processMonthEnd()` and `printAllAccounts()` (the Abstract Class/Interface and Polymorphism topics) call `monthlyFee()` and `printInfo()` polymorphically through the `Account` type, trusting that method's contract is always fulfilled by whatever subclass it is:

![Bank.java with method processMonthEnd, iterating polymorphically through the repository](../assets/code/pertemuan-11/p11-04-bank.png){width=68%}

> ✅ **Checkpoint:** explain in your own words why `processMonthEnd()` can process both `SavingsAccount` and `CheckingAccount` through the same single line `acc.monthlyFee()`, with no `if`/`else` branch per account kind. This is the **Liskov Substitution Principle**: both subclasses can stand in for `Account` at this point without changing the correctness of the program.

`InterestBearing` (the Abstract Classes and Interfaces topic) contains only one method, `applyInterest()`, and ONLY `SavingsAccount` implements it; `CheckingAccount` is never forced to have a method irrelevant to it:

![InterestBearing.java, a small one-method interface](../assets/code/pertemuan-11/p11-04-interestbearing.png){width=45%}

Prove it again from the command line:

```bash
grep -c "InterestBearing" src/id/ac/polinema/SavingsAccount.java src/id/ac/polinema/CheckingAccount.java
```

> ✅ **Checkpoint:** the `grep` result for `SavingsAccount.java` shows `1` (it implements `InterestBearing`), the result for `CheckingAccount.java` shows `0`. This is the **Interface Segregation Principle**: a small, focused interface, with `CheckingAccount` never forced to implement `applyInterest()`, which would make no sense for it.

> ⚠️ **If it fails:** if a `grep` result expected to be `0` instead shows another number, check again whether `Account.java`/`Bank.java` ever wrote a concrete class name (e.g. `if (acc instanceof SavingsAccount)`) instead of using a polymorphic method/an `instanceof` check through an interface as it should have since the Inheritance through Polymorphism topics.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 3, and after the independent assignment.
- **Independent assignment:**
  1. **BusinessAccount: proving OCP, LSP, and ISP through new code.** Add `BusinessAccount extends Account` (a business account, minimum balance Rp 1,000,000, earning no interest). The diagram below is only a sketch of which methods need overriding, NOT finished code; the actual implementation of `canWithdraw()`/`monthlyFee()`/`printInfo()` (exactly the `SavingsAccount`/`CheckingAccount` pattern already traced in Step 4) is entirely up to you:

     ![Sketch of BusinessAccount, methods to override with no implementation](../assets/uml/p11-tugas-businessaccount.png){width=60%}

     Register it with `Bank`, then run `processMonthEnd()` and `printAllAccounts()`:

     ![Main.java adding BusinessAccount, calling processMonthEnd and printAllAccounts](../assets/code/pertemuan-11/p11-tugas-main.png){width=70%}

     Prove both of the following from the program's output, with NO change to a single line of `Account.java` or `Bank.java`:
     - **(Open/Closed + Liskov Substitution)** the line `A004 monthly fee: 0.0` appears among `processMonthEnd()`'s other lines, and `A004 - Budi - balance: 2000000.0` followed by `Account type: Business` appears in `printAllAccounts()`, exactly as `SavingsAccount`/`CheckingAccount` were processed earlier.
     - **(Interface Segregation)** NO `A004 interest applied` line appears, since `BusinessAccount` does not implement `InterestBearing`.
  2. Answer briefly (2 to 3 sentences for each question): (a) name which SOLID principle is proven by the fact that `Account.java`/`Bank.java` did not change at all after `BusinessAccount` was added, and explain why. (b) `processMonthEnd()` uses `instanceof InterestBearing`, not `instanceof SavingsAccount`. Explain why `BusinessAccount` is automatically handled correctly (not charged interest) with no need for `Bank.java` to know anything about its existence. (c) Sometime later (the Persistence with JDBC and an Authentication Mechanism topic), `InMemoryAccountRepository` will be replaced with `JdbcAccountRepository`, storing data to a database. Explain why `Bank.java` does not need a single line changed for that swap, and which SOLID principle makes this possible.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `BusinessAccount` correct, OCP/LSP/ISP proven through output, conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
