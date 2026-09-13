# Practicum Jobsheet: Meeting 9
## Abstract Classes and Interfaces

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 9 (Week 9) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Declare an abstract class with an abstract method that every subclass must implement.
2. Declare and apply an interface on a class that needs a specific behavior contract.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from the Overriding and Overloading topic.
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

### Step 1: Account Becomes an Abstract Class

> **Concept Brief: Abstract Class.** An abstract class (`abstract class`) must not be instantiated directly through `new`; it may only serve as a superclass. An abstract class may contain an abstract method, one whose signature is only declared (with no body), and every concrete subclass must provide its own body. A generic example: abstract class `Shape` declares `abstract double area()` with no idea how to compute it, while `Circle` and `Square` each implement their own area formula.

![Shape as an abstract class, Circle and Square implementing area()](../assets/uml/p09-shape-abstract.png){width=70%}

Not a single plain `Account` has ever been created directly in Bank Mini so far, every instantiation has always been a `SavingsAccount` or a `CheckingAccount`. This is a good sign that `Account` should become an abstract class. Add an abstract method `monthlyFee()`:

![Account.java becoming an abstract class with abstract method monthlyFee](../assets/code/pertemuan-09/p09-01-account.png){width=65%}

`Bank` also gets a new method to display every account's monthly fee:

![Bank.java with method printMonthlyFees](../assets/code/pertemuan-09/p09-01-bank.png){width=65%}

Since `Account` now declares `monthlyFee()` as abstract, `SavingsAccount` and `CheckingAccount` must implement it:

![SavingsAccount.java implementing monthlyFee](../assets/code/pertemuan-09/p09-01-savingsaccount.png){width=65%}

![CheckingAccount.java implementing monthlyFee](../assets/code/pertemuan-09/p09-01-checkingaccount.png){width=65%}

Update `Main.java`:

![Main.java calling printMonthlyFees](../assets/code/pertemuan-09/p09-01-main.png){width=70%}

> ✅ **Checkpoint:** the program prints `A001 fee: 0.0`, `A002 fee: 15000.0`, `A003 fee: 0.0`, `A004 fee: 15000.0`, matching each account's type.

> ⚠️ **If it fails:** if the error `SavingsAccount is not abstract and does not override abstract method monthlyFee()` appears, check whether `monthlyFee()` is genuinely implemented in both subclasses, with a signature exactly matching the one declared in `Account`.

### Step 2: InterestBearing, an Interface for Interest-Bearing Accounts

> **Concept Brief: Interface.** An `interface` declares a method contract (a signature with no body) that any class stating `implements` against it must fulfill, without requiring an `extends` relationship at all. Unlike an abstract class, a class may implement many interfaces at once, so an interface is well suited to a capability across a variety of different class hierarchies.

Only an account that earns interest needs the `applyInterest()` capability, `CheckingAccount` does not need it. Rather than adding that method to `Account` (which would mean every subclass inherits it, including ones for which it is irrelevant), declare it as its own interface:

![InterestBearing.java](../assets/code/pertemuan-09/p09-02-interestbearing.png){width=55%}

![Account, SavingsAccount, and the InterestBearing interface](../assets/uml/p09-account-abstract.png){width=75%}

`SavingsAccount` states `implements InterestBearing` and implements `applyInterest()`:

![SavingsAccount.java implementing InterestBearing](../assets/code/pertemuan-09/p09-02-savingsaccount.png){width=65%}

Update `Main.java`:

![Main.java testing applyInterest](../assets/code/pertemuan-09/p09-02-main.png){width=70%}

> ✅ **Checkpoint:** the program displays `Before interest: 100000.0` followed by `After interest: 102000.0` (2% interest on a balance of 100000).

> ⚠️ **If it fails:** if the error `SavingsAccount is not abstract and does not override abstract method applyInterest()` appears, check whether `implements InterestBearing` and the body of `applyInterest()` were both added together; a class that states `implements` must still implement every method from that interface.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. The bank needs an audit trail for accounts at risk of going negative (accounts with overdraft). Add an interface `Auditable`, then apply it to `CheckingAccount`. The diagram below is only a sketch of the method to implement, NOT finished code; the body of `auditLog()` is entirely up to you:

     ![Sketch of Auditable and CheckingAccount, methods to implement with no body](../assets/uml/p09-tugas-auditable.png){width=60%}

     Demonstrate this by calling `auditLog()` on both `CheckingAccount` objects already in `Main.java` and printing the result.
  2. Answer briefly (2 to 3 sentences for each question):
     - (a) why does `applyInterest()` fit better as interface `InterestBearing`, compared to an abstract method placed directly in `Account`?
     - (b) `SavingsAccount` now has two "contracts" at once, inheriting `Account` (an abstract class) and implementing `InterestBearing` (an interface). What is the fundamental difference between these two kinds of contract?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Interface `Auditable` correct and conceptual answers accurate | Interface present even though answers are incomplete |
