# Practicum Jobsheet: Meeting 10
## Polymorphism and Exception Handling

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 10 (Week 10) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Declare a custom exception and apply `throw`/`try`/`catch` to handle an error condition without forcibly terminating the program.
2. Write a method that exploits polymorphism, including an `instanceof` check with pattern matching, to process objects from various subclasses through one single point of code.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 9.
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

### Step 1: withdraw() Throws InsufficientBalanceException

> **Concept Brief: Exception Handling.** When a method encounters a condition it cannot handle reasonably (for instance, an insufficient balance for a withdrawal), it may throw (`throw`) an exception object, halting its own execution right there. The calling code wraps the call in a `try` block, then handles any exception that may be thrown through a `catch` block. A custom exception is built by declaring a class that `extends` `Exception`. A generic example: `Grade.setScore(150)` throws `InvalidScoreException` instead of silently clamping the score, so the calling code knows exactly that something went wrong and is required to handle it.

![Exception, InvalidScoreException, and the Grade that throws it](../assets/uml/p10-invalidscore-exception.png){width=60%}

So far, `withdraw()` has silently returned `false` when a withdrawal failed; the calling code could easily forget to check the return value and carry on as if the withdrawal had succeeded. Change `withdraw()`'s contract so it throws an exception instead of returning a boolean. Add a custom exception class:

![InsufficientBalanceException.java](../assets/code/pertemuan-10/p10-01-insufficientbalanceexception.png){width=55%}

![Account.java, withdraw throwing InsufficientBalanceException](../assets/code/pertemuan-10/p10-01-account.png){width=65%}

Update `Main.java` to wrap the call to `withdraw()` in `try`/`catch`:

![Main.java testing a withdraw that throws an exception](../assets/code/pertemuan-10/p10-01-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0`, followed by `Withdrawal succeeded, new balance: 70000.0`.

> ⚠️ **If it fails:** if the compile error `unreported exception InsufficientBalanceException; must be caught or declared to be thrown` appears, check whether the call to `withdraw()` in `Main.java` is wrapped in a `try`/`catch` block, rather than called directly as before.

### Step 2: processMonthEnd(), Polymorphism through instanceof

> **Concept Brief: Polymorphism.** When an array or collection typed as a superclass (or interface) holds objects from various subclasses, one identical method call, for instance `s.area()` on each element of `Shape[] shapes`, automatically runs the version belonging to the object's actual runtime type, not the version declared by the variable's type. This is polymorphism: one point of code, different behavior depending on the object receiving it. Sometimes code still needs to know an object's concrete type, for instance to call a capability that only some subclasses have; `instanceof` with pattern matching (`if (obj instanceof SpecificType variable)`) checks and safely downcasts in a single step.

![One call to area() resolved differently while the program runs](../assets/uml/p10-polymorphic-dispatch.png){width=68%}

Only an account that implements `InterestBearing` needs `applyInterest()`; `CheckingAccount` does not. Add `processMonthEnd()` to `Bank`, processing every account polymorphically through `monthlyFee()`, and using `instanceof` to apply interest only to the accounts for which it is relevant:

![Bank.java with method processMonthEnd](../assets/code/pertemuan-10/p10-02-bank.png){width=68%}

![Account as an abstract class, SavingsAccount implementing interface InterestBearing](../assets/uml/p09-account-abstract.png){width=72%}

Update `Main.java`:

![Main.java calling processMonthEnd](../assets/code/pertemuan-10/p10-02-main.png){width=70%}

> ✅ **Checkpoint:** the program adds the lines `A001 interest applied, new balance: 505000.0`, `A001 monthly fee: 0.0`, `A002 monthly fee: 15000.0`, `A003 interest applied, new balance: 71400.0`, `A003 monthly fee: 0.0` after the lines from Step 1.

> ⚠️ **If it fails:** if `applyInterest()` is never called for any account, check again whether the check uses `instanceof InterestBearing` (not `instanceof SavingsAccount`), since polymorphism here is deliberately independent of the concrete class name, relying only on the interface implemented.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. The bank needs an audit report that only prints accounts implementing `Auditable` (from Meeting 9). Add `Bank.printAuditLog()`, processing every account polymorphically and using `instanceof Auditable` to print `auditLog()` only for the accounts for which it is relevant:

     ![Bank.java with method printAuditLog](../assets/code/pertemuan-10/p10-tugas-bank.png){width=68%}

  2. `Bank.findAccount()` has so far returned `null` when an account is not found; the calling code could forget to check for `null` and trigger a `NullPointerException` on the next line. Change it to throw a custom exception `AccountNotFoundException` instead of returning `null`:

     ![AccountNotFoundException.java](../assets/code/pertemuan-10/p10-tugas-accountnotfoundexception.png){width=55%}

     ![Bank.java, findAccount throwing AccountNotFoundException](../assets/code/pertemuan-10/p10-tugas-bank-findaccount.png){width=65%}

     Prove it by calling `findAccount()` in `Main.java` for one account number that exists and one that does not, each wrapped in `try`/`catch`.
  3. Answer briefly (2 to 3 sentences for each question): (a) why does changing `findAccount()` to throw an exception, rather than still returning `null`, make the calling code safer? (b) `processMonthEnd()` uses `instanceof InterestBearing`, not `instanceof SavingsAccount`. Explain why this distinction matters if Bank Mini someday adds a new interest-bearing account kind besides `SavingsAccount`.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Both assignment methods correct, conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
