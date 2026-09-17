# Practicum Jobsheet: Meeting 7
## Overriding and Overloading

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 7 (Week 7) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Override an inherited superclass method inside a subclass, marked with the `@Override` annotation, including calling the superclass version through `super.method(...)`.
2. Tell overriding apart from overloading by writing a method with the same name but a different parameter list.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 6.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Account Provides a Point to Override

Last week, `SavingsAccount` and `CheckingAccount` each got an attribute (`interestRate`, `overdraftLimit`) that did not yet actually affect the withdrawal rule, since the inherited `withdraw()` only knew one generic rule: a withdrawal must not exceed the balance. So each subclass can have its own rule, move that check into a separate `protected` method named `canWithdraw(double)`, then call that method from inside `withdraw()`:

![Account.java, withdraw() calling the new canWithdraw()](../assets/code/pertemuan-07/p07-01-account.png){width=65%}

`protected` means this method can be called and rewritten by a subclass, but still stays invisible from outside the package the way `public` would not. `withdraw()` itself does not change its behavior at all for now, since `canWithdraw(double)` still returns the same generic rule.

> ✅ **Checkpoint:** the program still runs and displays exactly the same output as before; this change is purely a reorganization (refactoring), not yet a behavior change.

> ⚠️ **If it fails:** if the error `cannot find symbol: method canWithdraw` appears, check whether the method name and parameter order in the call inside `withdraw()` exactly match its declaration.

### Step 2: SavingsAccount Overrides canWithdraw and printInfo

Now `SavingsAccount` can override `canWithdraw(double)` so a withdrawal must leave a minimum balance, and override `printInfo()` so it also prints its account type and interest rate:

![SavingsAccount.java overriding canWithdraw and printInfo](../assets/code/pertemuan-07/p07-02-savingsaccount.png){width=65%}

The `@Override` annotation tells the compiler to check that this method genuinely rewrites a superclass method with the exact same signature (name and parameters); if there is a typo in the method name, the compiler raises an error instead of silently creating a new method that is never called. `printInfo()` calls `super.printInfo()` first to print the inherited part, then adds its own line, so it does not need to rewrite the entire contents of `printInfo()` from scratch. Update `Main.java`:

![Main.java testing a withdrawal that violates the minimum balance](../assets/code/pertemuan-07/p07-02-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `Withdraw 70000 allowed? false`, followed by `A003 - Rian - balance: 100000.0` and `Account type: Savings, interest rate: 0.02`. The withdrawal is rejected since it would leave a balance of 30000, below the minimum balance of 50000.

> ⚠️ **If it fails:** if `@Override` raises the compile error `method does not override a method from its superclass`, check again whether the subclass method's signature exactly matches the one declared in `Account`.

### Step 3: CheckingAccount Overrides canWithdraw and printInfo

Apply the same pattern to `CheckingAccount`, this time with a rule that allows a withdrawal beyond the balance, up to its overdraft limit:

![CheckingAccount.java overriding canWithdraw and printInfo](../assets/code/pertemuan-07/p07-03-checkingaccount.png){width=65%}

Update `Main.java` to demonstrate that a withdrawal rejected last week is now allowed:

![Main.java testing a withdrawal beyond the balance through overdraft](../assets/code/pertemuan-07/p07-03-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `Withdraw 250000 allowed? true`, then `printAllAccounts()` displays `A003 - Rian - balance: 100000.0` followed by `Account type: Savings, interest rate: 0.02`, and `A004 - Dewi - balance: -150000.0` followed by `Account type: Checking, overdraft limit: 200000.0`. A withdrawal of 250000 from a balance of 100000 is now allowed since it still stays within the overdraft limit of 200000, something rejected last week before `canWithdraw()` was overridden.

> ⚠️ **If it fails:** if the checking account's balance never goes negative even though overdraft should allow it, check whether `CheckingAccount`'s `canWithdraw()` compares `amount` against `getBalance() + overdraftLimit`, not `getBalance()` alone.

### Step 4: Overloading, deposit() with a Note

Overriding rewrites a method that already exists in a superclass, with the same signature. Overloading is different: it adds a method with the same name but a different parameter list, and the compiler chooses which version gets called based on the arguments given, not based on the object's actual type at runtime. Add a second version of `deposit()` to `Account` that accepts an extra note:

![Account.java, deposit(double, String) as an overload of deposit(double)](../assets/code/pertemuan-07/p07-04-account.png){width=65%}

Update `Main.java` to call this new version:

![Main.java calling deposit with a note](../assets/code/pertemuan-07/p07-04-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `A003 deposit note: Initial top-up`, followed by `A003 - Rian - balance: 150000.0` and `Account type: Savings, interest rate: 0.02`.

> ⚠️ **If it fails:** if the error `reference to deposit is ambiguous` appears, check whether the two `deposit` methods genuinely differ in their parameter list (count or type), not merely in parameter names.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 4.
- **Independent assignment:**
  1. Apply the same overriding pattern to `BusinessAccount` from Meeting 6: override `canWithdraw(double)` so a withdrawal must leave a minimum balance of 1000000, and override `printInfo()` so it also prints the account type:

     ![BusinessAccount.java overriding canWithdraw and printInfo](../assets/code/pertemuan-07/p07-tugas-businessaccount.png){width=65%}

     Demonstrate this by creating one `BusinessAccount` with a balance of 2000000, attempting to withdraw 1500000 (must be rejected), then printing its full info.
  2. Answer briefly (2 to 3 sentences): based on `canWithdraw()`/`printInfo()` (overriding) and `deposit()` (overloading) that you just built, what tells the two apart in terms of a method's signature, and in terms of when Java decides which method actually gets called?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `BusinessAccount` override correct and conceptual answers accurate | Override present even though answers are incomplete |
