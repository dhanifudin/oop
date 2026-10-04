# Practicum Jobsheet: Meeting 7
## Overriding and Overloading

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 7 (Week 7) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Override an inherited method inside a subclass, marked with the `@Override` annotation, including calling the superclass's version through `super.method(...)`.
2. Tell overriding apart from overloading by writing methods with the same name but different parameter lists.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this jobsheet continues the `bank-mini` project from the Inheritance topic. That project already contains `Account`, `SavingsAccount`, `CheckingAccount`, `Customer`, `Bank`, and `Main`.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

The diagram below is the end result this jobsheet works toward:

![Class diagram of Account, SavingsAccount, and CheckingAccount after overriding](../assets/uml/p07-account-hierarchy.png){width=70%}

How to read the diagram:

- The `#` mark means `protected`: it may be used and rewritten by a subclass.
- `canWithdraw()` and `printInfo()` are written in `Account`, then written again in both subclasses. A method written again in a subclass is overridden.

> **Concept Brief: Overriding.** Picture a family recipe: a child cooks a dish with the same name, but uses their own recipe. Overriding works like that. A subclass rewrites an inherited method with the same name and parameters; only its body is different. The method name plus its parameter list is called the **signature**.

### Step 1: Account Provides a Point to Override

**Goal of this step:** move the "may withdraw or not" rule into its own method, so that each account type can later have its own rule.

Right now `withdraw()` in `Account` knows only one rule: a withdrawal may not exceed the balance. The `interestRate` and `overdraftLimit` attributes in the subclasses have no effect yet.

1. Open `Account.java`.
2. Add a new method `canWithdraw(double amount)` marked `protected`. Its body is the old rule: the amount must be positive and must not exceed the balance.
3. Change `withdraw()` so it calls `canWithdraw(amount)`.

![Account.java, withdraw() calling the new canWithdraw()](../assets/code/pertemuan-07/p07-01-account.png){width=65%}

Run the program without changing `Main.java`.

**Expected output:**

```text
A001 - Nadia - balance: 350000.0
A002 - Sari - balance: 200000.0
```

**Why is that?** The output is the same as before this step. `canWithdraw()` still holds the old rule, so the program's behavior has not changed. This step only reorganizes the code (refactoring) so the next steps become possible.

> ✅ **Checkpoint:** the program runs and its output matches the block above.

> ⚠️ **If it fails:** if the error `cannot find symbol: method canWithdraw` appears, check whether the method name and parameter inside `withdraw()` exactly match its declaration.

### Step 2: SavingsAccount Overrides canWithdraw and printInfo

**Goal of this step:** make a savings account keep a minimum balance of 50000, and print more complete information.

1. Open `SavingsAccount.java`.
2. Rewrite `canWithdraw(double amount)` with the `@Override` annotation. The new rule: the balance after a withdrawal may not drop below 50000.
3. Rewrite `printInfo()` with the `@Override` annotation. Call `super.printInfo()` first, then print the account type and its interest rate.

![SavingsAccount.java overriding canWithdraw and printInfo](../assets/code/pertemuan-07/p07-02-savingsaccount.png){width=65%}

4. Update `Main.java`:

![Main.java testing a withdrawal that breaks the minimum balance](../assets/code/pertemuan-07/p07-02-main.png){width=70%}

**Expected output:**

```text
Withdraw 70000 allowed? false
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
```

**Why is that?**

- Withdrawing 70000 from a balance of 100000 would leave 30000. That is below the minimum balance of 50000, so `SavingsAccount`'s `canWithdraw()` returns `false`.
- The second line is printed by `super.printInfo()`, which is `Account`'s version. The third line is the addition from `SavingsAccount`.
- `@Override` asks the compiler to check that the method really exists in the superclass with the same signature. A typo in the method name is caught right away.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `method does not override a method from its superclass` appears, check whether the method's signature in the subclass exactly matches the one in `Account`.

### Step 3: CheckingAccount Overrides canWithdraw and printInfo

**Goal of this step:** let a checking account be withdrawn from beyond its balance, up to `overdraftLimit`.

1. Open `CheckingAccount.java`.
2. Rewrite `canWithdraw(double amount)` and `printInfo()` following the same pattern as Step 2. The new rule: a withdrawal may go up to the balance plus `overdraftLimit`.

![CheckingAccount.java overriding canWithdraw and printInfo](../assets/code/pertemuan-07/p07-03-checkingaccount.png){width=65%}

3. Update `Main.java`:

![Main.java testing a withdrawal beyond the balance through overdraft](../assets/code/pertemuan-07/p07-03-main.png){width=70%}

**Expected output:**

```text
Withdraw 250000 allowed? true
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
A004 - Dewi - balance: -150000.0
Account type: Checking, overdraft limit: 200000.0
```

**Why is that?**

- A004's balance is 100000 and its overdraft limit is 200000. A withdrawal of 250000 is still within the limit, so it is allowed and the balance becomes -150000.
- `Bank.printAllAccounts()` was not changed at all. It still calls `printInfo()` for each account. Java runs the version that belongs to the object: `SavingsAccount`'s version for A003, `CheckingAccount`'s version for A004.

> ✅ **Checkpoint:** the program's output matches the block above, including the negative balance on A004.

> ⚠️ **If it fails:** if A004's balance never becomes negative, check whether `canWithdraw()` in `CheckingAccount` compares `amount` against `getBalance() + overdraftLimit`, not `getBalance()` alone.

### Step 4: Overloading, deposit() with a Note

> **Concept Brief: Overloading.** At a cashier, the single word "pay" works for cash, card, and QR. The cashier picks the way from what is handed over. Overloading works like that: several methods use the same name, but their parameter lists differ. The compiler picks the version from the arguments given when the method is called.

**Goal of this step:** add a second way to deposit, namely a deposit that comes with a note.

1. Open `Account.java`.
2. Add a method `deposit(double amount, String note)`. It prints the note, then calls the existing `deposit(amount)`.

![Account.java, deposit(double, String) as an overload of deposit(double)](../assets/code/pertemuan-07/p07-04-account.png){width=65%}

3. Update `Main.java`:

![Main.java calling deposit with a note](../assets/code/pertemuan-07/p07-04-main.png){width=70%}

**Expected output:**

```text
A003 deposit note: Initial top-up
A003 - Rian - balance: 150000.0
Account type: Savings, interest rate: 0.02
```

**Why is that?** The call `deposit(50000, "Initial top-up")` carries two arguments, so the compiler picks the two-parameter version. That version prints the note, then hands the balance increase over to `deposit(double)`. Both versions remain usable.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `reference to deposit is ambiguous` appears, check whether the two `deposit` methods genuinely differ in their parameter list (count or type), not merely in parameter names.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 4.
- **Independent assignment:**
  1. Apply the same overriding pattern to the `BusinessAccount` you built in the Inheritance topic. Override `canWithdraw(double)` so a withdrawal must leave a minimum balance of 1000000, and override `printInfo()` so it also prints the account type. The diagram below is only a sketch of the methods to rewrite, NOT finished code; their bodies are entirely up to you:

     ![Sketch of BusinessAccount overriding canWithdraw and printInfo](../assets/uml/p07-tugas-businessaccount.png){width=85%}

     Demonstrate it through `Main.java`: create one `BusinessAccount` with a balance of 2000000, try to withdraw 1500000, then print its information. Your result is correct if:
     - the withdrawal of 1500000 is rejected;
     - `printInfo()` prints `Account`'s line, followed by the account type line;
     - both methods use `@Override`.
  2. Answer briefly (1 to 2 sentences for each question):
     - (a) How do the signatures differ between the overridden methods (`canWithdraw()`, `printInfo()`) and the overloaded method (`deposit()`)?
     - (b) What decides which method version runs in overriding, and what decides it in overloading?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `BusinessAccount` override correct and conceptual answers accurate | Override present even though answers are incomplete |
