# Practicum Jobsheet: Meeting 10
## Polymorphism and Exception Handling

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 10 (Week 10) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Create their own exception, then use `throw`, `try`, and `catch` to handle a failure without stopping the program.
2. Write a method that processes objects of various subclasses through one loop (polymorphism), including checking an object's capability with `instanceof`.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this jobsheet continues the `bank-mini` project from the Abstract Classes and Interfaces topic. That project already contains the abstract `Account` with `monthlyFee()`, plus the interface `InterestBearing` implemented by `SavingsAccount`.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: withdraw() Throws InsufficientBalanceException

> **Concept Brief: Exception.** Picture a fire alarm: when there is a problem, the alarm sounds and activity stops until someone deals with it. An exception works like that. A method that finds a problem throws (`throw`) an exception object, then stops at once. The calling code puts that call inside a `try` block, and prepares a backup plan in a `catch` block. An exception of your own is simply a class that `extends Exception`.

**Goal of this step:** make a failed withdrawal impossible to pass by silently.

Right now `withdraw()` returns `false` when a withdrawal fails. A caller can forget to check that value, then carry on as if the withdrawal succeeded. The diagram below is the result this step works toward:

![InsufficientBalanceException as a subclass of Exception, thrown by Account](../assets/uml/p10-insufficientbalance-exception.png){width=60%}

How to read the diagram: `InsufficientBalanceException` is a subclass of `Exception`. The dashed arrow labeled "throws" means `withdraw()` in `Account` can throw that exception. The return type of `withdraw()` is now `void`, not `boolean`.

1. Create a new file `InsufficientBalanceException.java`:

![InsufficientBalanceException.java](../assets/code/pertemuan-10/p10-01-insufficientbalanceexception.png){width=55%}

2. Open `Account.java`. Change `withdraw()` so it throws an exception when `canWithdraw()` is `false`:

![Account.java, withdraw throwing InsufficientBalanceException](../assets/code/pertemuan-10/p10-01-account.png){width=65%}

3. Update `Main.java`. Wrap each call to `withdraw()` in `try`/`catch`:

![Main.java testing a withdraw that throws an exception](../assets/code/pertemuan-10/p10-01-main.png){width=70%}

**Expected output:**

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
```

**Why is that?**

- A003's balance is 100000 with a minimum balance of 50000. A withdrawal of 70000 is rejected by `canWithdraw()`, so `withdraw()` throws an exception. The `catch` block prints its message through `e.getMessage()`.
- The exception is thrown before the line `balance -= amount` runs, so the balance stays 100000.
- The second withdrawal, 30000, is allowed. The balance becomes 70000, and the "succeeded" line inside `try` runs too.

> ✅ **Checkpoint:** the program's output matches the block above.

> ⚠️ **If it fails:** if the error `unreported exception InsufficientBalanceException; must be caught or declared to be thrown` appears, check whether every call to `withdraw()` in `Main.java` is inside a `try`/`catch` block.

### Step 2: processMonthEnd(), Polymorphism and instanceof

> **Concept Brief: Polymorphism.** A coach shouts "go!": the swimmer starts swimming, the runner starts running. There is one command, and each athlete carries it out in their own way. Polymorphism works like that: one and the same method call runs the version belonging to the object that receives it. When code needs to know whether an object has a certain capability, use `instanceof`. The form `if (obj instanceof Type name)` checks and also provides a variable `name` ready to use.

![One call to area() resolved differently while the program runs](../assets/uml/p10-polymorphic-dispatch.png){width=68%}

**Goal of this step:** process every account at month end through one loop, with no `if` branch for each account type.

Every account has a monthly fee (`monthlyFee()`). Only accounts that are `InterestBearing` receive interest:

![Account as an abstract class, SavingsAccount implementing the interface InterestBearing](../assets/uml/p10-account-abstract.png){width=72%}

1. Open `Bank.java`. Add the method `processMonthEnd()`:

![Bank.java with the method processMonthEnd](../assets/code/pertemuan-10/p10-02-bank.png){width=68%}

2. Add its call at the end of `Main.java`:

![Main.java calling processMonthEnd](../assets/code/pertemuan-10/p10-02-main.png){width=45%}

**Expected output:** the two lines from Step 1 stay the same, followed by five new lines:

```text
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
```

**Why is that?**

- `monthlyFee()` is called for all three accounts. A savings account answers 0.0, a checking account answers 15000.0. That is polymorphism: one call, and the answer follows the object.
- The "interest applied" line appears only for A001 and A003, since only those two are `InterestBearing`. A002 is a checking account, so it is skipped.
- A001's interest is 1% of 500000. A003's interest is 2% of 70000.

> ✅ **Checkpoint:** the last five lines of the output match the block above.

> ⚠️ **If it fails:** if no "interest applied" line appears at all, check whether the check uses `instanceof InterestBearing`, and whether `SavingsAccount` declares `implements InterestBearing`.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. The bank needs an audit report for accounts that are `Auditable` (the interface from the Abstract Classes and Interfaces topic). Add `Bank.printAuditLog()`: one loop over every account, printing `auditLog()` only for accounts that are `Auditable`. The diagram below is only a sketch of the method to add, NOT finished code; its body is entirely up to you:

     ![Sketch of Bank.printAuditLog(), checking Auditable through instanceof](../assets/uml/p10-tugas-auditlog.png){width=55%}

     Your result is correct if only checking accounts are printed, and the check uses `instanceof Auditable`, not a class name.
  2. `Bank.findAccount()` returns `null` when an account is not found. A caller can forget to check for `null`. Change it so the method throws an exception `AccountNotFoundException`. The diagram below is only a sketch of the structure and signature that change, NOT finished code:

     ![Sketch of AccountNotFoundException and the Bank.findAccount() that throws it](../assets/uml/p10-tugas-accountnotfound.png){width=60%}

     Demonstrate it through `Main.java`: call `findAccount()` for one account number that exists and one that does not, each inside `try`/`catch`. Your result is correct if the missing number produces a message from the `catch` block, and the program still runs to the end.
  3. Answer briefly (1 to 2 sentences for each question):
     - (a) Why is throwing an exception safer than returning `null` in `findAccount()`?
     - (b) Why does `processMonthEnd()` check `instanceof InterestBearing`, rather than `instanceof SavingsAccount`?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Both assignment methods correct, conceptual answers accurate | Part of the assignment completed even though answers are incomplete |
