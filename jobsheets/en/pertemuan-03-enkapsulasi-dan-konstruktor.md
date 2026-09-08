# Practicum Jobsheet: Meeting 3
## Encapsulation and Constructor

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 3 (Week 3) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Explain the risk of public attributes, then apply encapsulation and information hiding (`private` attributes, access through methods).
2. Write getters and setters, including a method that validates input values.
3. Write a constructor that requires complete data and establishes a read-only attribute (getter without setter).

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 2.
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

### Step 1: Continuing the `bank-mini` Project

Open the `bank-mini` project from Meeting 2 again. Run the project to confirm its condition still matches the end of Meeting 2 before `Account` is changed in the next step.

> ✅ **Checkpoint:** the program still compiles and runs, displaying the same three `- balance:` lines as at the end of Meeting 2 (`Nadia`, `Budi`, `Sari`).

### Step 2: Applying Encapsulation to Account

`Account` from Meeting 2 still has public attributes `ownerName` and `balance`. Other code can write `acc.balance = -999999;` directly, without going through `deposit()`/`withdraw()`. An account's most basic invariant, a balance that is always validated, is therefore not guaranteed at all. Replace the contents of `Account.java` according to the class diagram already covered in the concept slide: every attribute is made `private` (information hiding through the strictest access modifier that still lets the class work), a new `accountNumber` attribute is added, a single constructor requires complete data (account number, owner name, initial balance), a getter is provided for every attribute, and `deposit()`/`withdraw()` validate their input and return `boolean`:

![Account.java after encapsulation is applied](../assets/code/pertemuan-03/p03-02-account.png){width=65%}

Since the constructor now requires complete data, update the test in `Main.java`:

![Main.java using the new Account constructor](../assets/code/pertemuan-03/p03-02-main.png){width=70%}

> ✅ **Checkpoint:** the program recompiles successfully and displays `A001 - Nadia - balance: 350000.0`.

> ⚠️ **If it fails:** if the error `constructor Account in class Account cannot be applied to given types` appears, check whether the number and order of arguments in `new Account(...)` match the available constructor.

> **Note.** `accountNumber` is deliberately given only a getter, no setter, following the read-only attribute pattern from the concept slide. Its value is set once by the constructor and never changes again.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. The `Account` you built does not yet limit how much can be withdrawn per day. Add a private attribute `dailyWithdrawalLimit` (set through the constructor), then modify `withdraw()` to also reject a withdrawal that exceeds this limit, in addition to the existing balance rule:

     ![Account.java with dailyWithdrawalLimit added](../assets/code/pertemuan-03/p03-tugas-account.png){width=65%}

     Demonstrate this by creating one `Account` with a balance of 1000000 and a daily limit of 200000 in `Main`, then try withdrawing 300000 (must be rejected) and 150000 (must succeed):

     ![Main.java testing dailyWithdrawalLimit](../assets/code/pertemuan-03/p03-tugas-main.png){width=70%}
  2. Answer briefly (2 to 3 sentences for each question): (a) why is returning a `boolean` value from `deposit()`/`withdraw()` safer than not informing the caller at all when a value is rejected? (b) name one attribute on `Account` that you think should be made read-only (getter without setter), and explain your reasoning.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Daily withdrawal limit correct and conceptual answers accurate | Daily withdrawal limit present even though answers are incomplete |
