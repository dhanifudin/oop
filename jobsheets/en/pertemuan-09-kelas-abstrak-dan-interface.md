# Practicum Jobsheet: Meeting 9
## Abstract Classes and Interfaces

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 9 (Week 9) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Declare an abstract class with an abstract method that every subclass must fill in.
2. Declare an interface and apply it to a class that needs a certain capability.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this jobsheet continues the `bank-mini` project from the Overriding and Overloading topic. That project already contains `Account` with `canWithdraw()`, plus `SavingsAccount` and `CheckingAccount` overriding it.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Account Becomes an Abstract Class

> **Concept Brief: Abstract Class.** Picture a basic cake recipe with one step still blank: "fill to taste". That recipe cannot be cooked until the blank step is filled in. An abstract class (`abstract class`) works like that: a class that is not yet complete, so its objects may not be created through `new`. Its blank step is called an abstract method, a method with no body that every subclass must fill in.

**Goal of this step:** prevent a plain `Account` from being created, and require each account type to decide its own monthly fee.

In Bank Mini, an account is always a `SavingsAccount` or a `CheckingAccount`. There is never a "general" account. The diagram below is the result this step works toward:

![Abstract Account with the abstract method monthlyFee, filled in by SavingsAccount and CheckingAccount](../assets/uml/p09-account-monthlyfee.png){width=55%}

How to read the diagram: the name `Account` and the method `monthlyFee()` are written in italics, meaning abstract. `monthlyFee()` is written again in both subclasses, meaning both subclasses fill it in.

1. Open `Account.java`. Add the word `abstract` to the class declaration, then add the abstract method `monthlyFee()`:

![Account.java becoming an abstract class with the abstract method monthlyFee](../assets/code/pertemuan-09/p09-01-account.png){width=65%}

2. Open `Bank.java`. Add a method `printMonthlyFees()` to print every account's monthly fee:

![Bank.java with the method printMonthlyFees](../assets/code/pertemuan-09/p09-01-bank.png){width=65%}

3. Fill in `monthlyFee()` in `SavingsAccount` (no fee) and `CheckingAccount` (a fixed fee):

![SavingsAccount.java filling in monthlyFee](../assets/code/pertemuan-09/p09-01-savingsaccount.png){width=65%}

![CheckingAccount.java filling in monthlyFee](../assets/code/pertemuan-09/p09-01-checkingaccount.png){width=65%}

4. Update `Main.java`:

![Main.java calling printMonthlyFees](../assets/code/pertemuan-09/p09-01-main.png){width=70%}

**Expected output:**

```text
A001 - Nadia - balance: 350000.0
Account type: Savings, interest rate: 0.01
A002 - Sari - balance: 200000.0
Account type: Checking, overdraft limit: 50000.0
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
A004 - Dewi - balance: -150000.0
Account type: Checking, overdraft limit: 200000.0
A001 fee: 0.0
A002 fee: 15000.0
A003 fee: 0.0
A004 fee: 15000.0
```

**Why is that?**

- The first eight lines come from `printAllAccounts()`. The last four lines come from `printMonthlyFees()`.
- `Bank` calls the same `monthlyFee()` for every account. A savings account answers 0.0, a checking account answers 15000.0, since each fills in that abstract method in its own way.
- A line `new Account(...)` can no longer be compiled. That is exactly the point.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `SavingsAccount is not abstract and does not override abstract method monthlyFee()` appears, check whether `monthlyFee()` has been filled in in both subclasses, with exactly the same signature as in `Account`.

### Step 2: InterestBearing, an Interface for Interest-Bearing Accounts

> **Concept Brief: Interface.** Picture a USB-C port. Anything that has that port can be charged with the same charger. An interface works like that: a list of methods with no bodies. A class that declares `implements` promises to fill in all of those methods. A class may `extends` only one superclass, but may `implements` many interfaces.

**Goal of this step:** give the "receives interest" capability only to accounts that really bear interest.

Only `SavingsAccount` bears interest. `CheckingAccount` does not. If `applyInterest()` were put in `Account`, a checking account would also have to fill it in. So this capability becomes its own interface:

![Account, SavingsAccount, and the interface InterestBearing](../assets/uml/p09-account-abstract.png){width=75%}

How to read the diagram: the dashed arrow from `SavingsAccount` to `InterestBearing` means `implements`. `CheckingAccount` has no such arrow.

1. Create a new file `InterestBearing.java`:

![InterestBearing.java](../assets/code/pertemuan-09/p09-02-interestbearing.png){width=55%}

2. Open `SavingsAccount.java`. Add `implements InterestBearing` to the class declaration, then fill in `applyInterest()`:

![SavingsAccount.java implementing InterestBearing](../assets/code/pertemuan-09/p09-02-savingsaccount.png){width=65%}

3. Add a test at the end of `Main.java`:

![Main.java testing applyInterest](../assets/code/pertemuan-09/p09-02-main.png){width=70%}

**Expected output:** the twelve lines from Step 1 stay the same, followed by two new lines:

```text
Before interest: 100000.0
After interest: 102000.0
```

**Why is that?** Account A003's interest rate is 0.02. Its interest is 2% of 100000, which is 2000, then deposited into the balance. `CheckingAccount` has no `applyInterest()` at all, since it does not declare `implements InterestBearing`.

> ✅ **Checkpoint:** the last two lines of the output match the block above.

> ⚠️ **If it fails:** if the error `SavingsAccount is not abstract and does not override abstract method applyInterest()` appears, check whether `implements InterestBearing` and the body of `applyInterest()` were added together. A class that declares `implements` must fill in every method of that interface.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. The bank needs an audit trail for accounts whose balance can go negative (accounts with an overdraft). Add an interface `Auditable`, then apply it to `CheckingAccount`. The diagram below is only a sketch of the method to fill in, NOT finished code; the body of `auditLog()` is entirely up to you:

     ![Sketch of Auditable and CheckingAccount, the method to fill in](../assets/uml/p09-tugas-auditable.png){width=60%}

     Demonstrate it through `Main.java`: call `auditLog()` on both existing `CheckingAccount` objects, then print the results. Your result is correct if:
     - `Auditable` is an interface with one method `auditLog()`;
     - `CheckingAccount` declares `implements Auditable`, while `SavingsAccount` does not;
     - the printed text contains the account number and its balance.
  2. Answer briefly (1 to 2 sentences for each question):
     - (a) Why is `applyInterest()` made into the interface `InterestBearing`, rather than an abstract method in `Account`?
     - (b) `SavingsAccount` uses `extends Account` and `implements InterestBearing`. How do the meanings of those two keywords differ?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `Auditable` interface correct and conceptual answers accurate | Interface present even though answers are incomplete |
