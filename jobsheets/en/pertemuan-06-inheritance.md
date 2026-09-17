# Practicum Jobsheet: Meeting 6
## Inheritance

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 6 (Week 6) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Write a subclass that inherits attributes and methods from a superclass using `extends` and `super(...)`.
2. Add new attributes and methods to a subclass without changing its superclass.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 4.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: SavingsAccount, the First Subclass

The `bank-mini` project from Meeting 4 continues in this meeting. So far there is only one account type, `Account`. A real Bank Mini serves several account types, each with its own traits, without rewriting `accountNumber`, `owner`, `balance`, `deposit()`, or `withdraw()` from scratch. Add a class `SavingsAccount` that inherits all of this from `Account` through `extends`, plus its own new attribute and method:

![SavingsAccount.java](../assets/code/pertemuan-06/p06-01-savingsaccount.png){width=65%}

`SavingsAccount`'s constructor calls `super(accountNumber, owner, balance)` as its first line to build the part inherited from `Account`, then fills in its own `interestRate`. Update `Main.java` to test it:

![Main.java creating a SavingsAccount and calling both its inherited method and its new method](../assets/code/pertemuan-06/p06-01-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `A001 - Nadia - balance: 350000.0` (from `printInfo()`, an inherited method not rewritten) followed by `Account type: Savings, interest rate: 0.01` (from `printAccountType()`, a new method belonging to `SavingsAccount`).

> ⚠️ **If it fails:** if the error `constructor Account in class Account cannot be applied to given types` appears, check whether `super(...)` in `SavingsAccount` is called with the exact same order and number of arguments as the intended `Account` constructor.

### Step 2: CheckingAccount, the Second Subclass

Add a class `CheckingAccount`, a second subclass of `Account`, following the same pattern as `SavingsAccount`:

![CheckingAccount.java](../assets/code/pertemuan-06/p06-02-checkingaccount.png){width=65%}

Update `Main.java` to test both account types:

![Main.java testing SavingsAccount and CheckingAccount](../assets/code/pertemuan-06/p06-02-main.png){width=70%}

> ✅ **Checkpoint:** the program prints four lines. The first two lines are for `acc1` (same as Step 1), then `A002 - Sari - balance: 200000.0` and `Account type: Checking, overdraft limit: 50000.0`. Notice that `acc2`'s balance stays at 200000.0, a withdrawal of 230000 is rejected, since the `withdraw()` inherited from `Account` only allows a withdrawal up to the available balance, and does not yet know how to use `overdraftLimit`. This is not an error, but something deliberately observed; the reason is covered in Meeting 7.

> ⚠️ **If it fails:** if `overdraftLimit` never gets stored correctly, check whether the constructor parameter names got swapped with `balance` by mistake.

### Step 3: Bank Mini Manages Several Account Types

Without changing a single line of `Bank`'s code, this class can already store both `SavingsAccount` and `CheckingAccount` objects at once, since both remain a kind of `Account` (inherited through `extends`). Update `Main.java` to demonstrate this:

![Main.java placing a SavingsAccount and a CheckingAccount into the same Bank](../assets/code/pertemuan-06/p06-03-main.png){width=70%}

> ✅ **Checkpoint:** `printAllAccounts()` prints `A001 - Nadia - balance: 350000.0` followed by `A002 - Sari - balance: 200000.0`. Notice that both lines use the exact same format, since `printInfo()` has not been rewritten by either subclass yet; `Bank` cannot yet display each account type's interest rate or overdraft limit through `printAllAccounts()`.

> ⚠️ **If it fails:** if an `incompatible types` error appears when `addAccount(acc1)` is called, check whether `Bank`'s `addAccount` parameter is typed as `Account`, not a specific subclass type.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 3.
- **Independent assignment:**
  1. Bank Mini adds one more account type for corporate customers. Add a class `BusinessAccount extends Account`, with attribute `monthlyTransactionFee` and a new method `printAccountType()`, following the same pattern as `SavingsAccount` and `CheckingAccount`:

     ![BusinessAccount.java](../assets/code/pertemuan-06/p06-tugas-businessaccount.png){width=65%}

     Demonstrate this by creating one `BusinessAccount`, adding it to `Bank` alongside the other accounts, then calling its `printAllAccounts()` and `printAccountType()`.
  2. Answer briefly (2 to 3 sentences): `Bank.printAllAccounts()` cannot yet display each account type's specific information (interest rate, overdraft limit, or monthly fee), even though a `printAccountType()` method already exists in each subclass. Why is that, and what do you think needs to change so `printAllAccounts()` can display it automatically?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `BusinessAccount` correct and conceptual answers accurate | `BusinessAccount` present even though answers are incomplete |
