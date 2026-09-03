# Practicum Jobsheet: Meeting 2
## Classes and Objects

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 2 (Week 2) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |
| **Starting Code** | `code/bank-mini/pertemuan-01/` (Meeting 1 checkpoint) |
| **Final Code** | the `bank-mini` project after Step 6, copied as the checkpoint `code/bank-mini/pertemuan-02/` |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Define a Java class with attributes and methods, and create objects from it using `new`.
2. Write a method that returns a value and apply it to simple logic on an object's data.
3. Explain reference behavior (aliasing and `null`) and create many objects at once using an array.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project created in Meeting 1, and directly applies the class and object theory already covered in the concept lecture to `Account`, the first class of the Bank Mini application. All classes remain inside the package `id.ac.polinema` (the standard Java package naming convention: the institution's domain name reversed, so "polinema.ac.id" becomes `id.ac.polinema`).
- **Quick verification** before starting:
  ```bash
  java -version
  javac -version
  ```
  If both display a version number with no errors, the process may proceed.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor, continuing the `bank-mini/` folder from Meeting 1:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Continuing the `bank-mini` Project

Open the `bank-mini` project created in Meeting 1 (or the same folder if following the "Without NetBeans?" alternative above). This meeting builds `Account`, the seed class of the Bank Mini application, step by step: starting with attributes alone, then methods, then reference behavior, until finally many `Account` objects are managed at once through an array.

> ✅ **Checkpoint:** the Projects panel still shows the `bank-mini` project with a package `id.ac.polinema` containing `Main.java` from Meeting 1.

### Step 2: A Minimal `Account` Class and the First Object

A **class** is a mold that describes the attributes and behavior that every **object** created from it will have. The `Account` class below currently has only attributes (state); it does not yet have any methods:

![Account.java: a class with two attributes](../assets/code/pertemuan-02/p02-02-account.png){width=55%}

Add a new class `Account` to the package `id.ac.polinema` (right-click the package > New > Java Class), then replace the contents of `Main.java` to create a single `Account` object and fill in its attributes one at a time:

![Main.java: creating an Account object and filling in attributes manually](../assets/code/pertemuan-02/p02-02-main.png){width=75%}

Run the project (right-click the project > Run, or press F6). If using a terminal: `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`.

> ✅ **Checkpoint:** the program displays `Nadia - balance: 500000.0`.

> ⚠️ **If it fails:** if `error: cannot find symbol` appears, check whether the file name matches the class name exactly (`Account.java` for `class Account`). Java is case-sensitive, so uppercase and lowercase letters must match precisely.

When the statement `new Account()` is executed, two things happen in memory. The variable `acc` is merely a small slot on the **stack** that holds an address. The actual `Account` object, complete with its `ownerName` and `balance`, resides separately on the **heap**. The illustration is as follows:

![A stack variable pointing to an Account object on the heap](../assets/uml/p02-memory-new.png){width=45%}

### Step 3: Adding Methods (Behavior)

Attributes only store data and have no behavior of their own. A **method** gives a class its behavior, that is, something an object can do with its own data. Add three methods to `Account` (the green lines mark the newly added code):

![Account.java with the deposit(), withdraw(), and printInfo() methods added](../assets/code/pertemuan-02/p02-03-account.png){width=55%}

Simplify `Main.java` to use these three methods:

![Main.java using deposit(), withdraw(), and printInfo()](../assets/code/pertemuan-02/p02-03-main.png){width=75%}

Run the program again.

> ✅ **Checkpoint:** the program displays `Nadia - balance: 350000.0`.

> ⚠️ **If it fails:** if the error `non-static method deposit(double) cannot be referenced from a static context` appears, a method was called without going through an object (for example `Account.deposit(500000)`). A non-static method must be called through its object, that is, `acc.deposit(500000)`.

The following UML class diagram summarizes `Account` so far:

![UML class diagram for Account](../assets/uml/p02-account.png){width=45%}

The `+` mark in front of an attribute or method indicates a public nature (directly accessible from outside the class), while the `-` mark indicates a private nature. The diagram above is entirely marked `+`, meaning `ownerName` and `balance` can still be changed directly from outside the class, without going through `deposit()`/`withdraw()`. This is intentional for this meeting; the risk of a design like this becomes the motivation for encapsulation, covered in full in Meeting 3.

### Step 4: Methods with Logic and Return Values

A method does not have to be `void`. A method that returns a value processes an object's data and hands the result back to the caller through `return`. Add the following two methods to `Account`:

![Account.java with the formatBalance() and isOverdrawn() methods added](../assets/code/pertemuan-02/p02-04-account.png){width=55%}

`formatBalance()` turns `balance` into text with a thousands separator and two decimal places using `String.format("%,.2f", balance)`, while `isOverdrawn()` returns `true` once the balance has gone negative. Update `Main.java` to try both, including deliberately withdrawing more than the available balance:

![Main.java printing the formatted balance, then testing isOverdrawn()](../assets/code/pertemuan-02/p02-04-main.png){width=75%}

> ✅ **Checkpoint:** the first line displays `Nadia - balance: 350,000.00`, the second line displays `Overdrawn: true`.

> ⚠️ **If it fails:** if the result of `formatBalance()` does not show a thousands separator, check the format string `"%,.2f"` again: the comma before `.2f` is what enables the thousands separator.

> **Note.** This version of `Account` has no validation at all: `withdraw()` allows the balance to go negative without any check, so `isOverdrawn()` is only useful as an after-the-fact check, not a prevention. This weakness is exactly what motivates Meeting 3: encapsulation closes this gap by validating a value before the balance actually changes.

### Step 5: References, Aliasing, and `null`

An object variable in Java is not the object itself, but rather a **reference** that points to an object in memory. Because of this, two variables can point to the exact same object. Replace the contents of `Main.java` with the following code:

![Main.java with an aliasing block and a null test added](../assets/code/pertemuan-02/p02-05-bug-main.png){width=75%}

Run the program.

> ✅ **Checkpoint:** the first two lines (`Via original:` and `Via copy:`) both display the value `600000.0`, even though only `copy` received the second deposit. This is not an error, but rather how references work: `original` and `copy` point to the exact same object, so a change made through either variable is automatically visible through the other. The next line produces a `NullPointerException`, and this is intentional.

The illustration on the stack and heap is as follows:

![Two stack variables pointing to the same single Account object on the heap](../assets/uml/p02-memory-alias.png){width=55%}

Fix this by removing the last line (`Account empty = null;` along with the `printInfo()` call above it) so that the program runs again without errors:

![Main.java after the null-test line has been removed](../assets/code/pertemuan-02/p02-05-fix-main.png){width=75%}

> ⚠️ **If it fails:** `NullPointerException` always occurs when a method is called on a reference that does not yet point to any object (`null`). The solution is always the same: make sure the object has actually been created with `new` before its methods are called.

### Step 6: Array of Objects, Many Objects from One Class

A single class can produce many objects at once. Replace the contents of `Main.java` with an `Account[]` array holding three accounts:

![Main.java final version: an Account[] array holding three accounts](../assets/code/pertemuan-02/p02-06-main.png){width=75%}

> ✅ **Checkpoint:** the program prints three `- balance:` lines (one per array element, each with a different value since every `Account` has its own data): `Nadia - balance: 350000.0`, `Budi - balance: 1000000.0`, `Sari - balance: 500000.0`.

> ⚠️ **If it fails:** `ArrayIndexOutOfBoundsException` indicates that the index used does not exist, for example `accounts[3]` when the array is only of size 3 (valid indices: 0, 1, 2).

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 6.
- **Independent assignment:**
  1. Bank Mini needs to move balance between accounts. Add a method `transferTo` to `Account` following the UML class diagram below:

     ![UML class diagram for Account with transferTo added](../assets/uml/p02-account-transfer.png){width=50%}

     `transferTo(target, amount)` deposits `amount` into the `target` account, then withdraws the same `amount` from its own caller's account:

     ![Account.java with transferTo added](../assets/code/pertemuan-02/p02-tugas-account.png){width=55%}

     Demonstrate this by creating two `Account` objects in `Main`, calling `transferTo` from one to the other, then printing both:

     ![Main.java testing transferTo between two Account objects](../assets/code/pertemuan-02/p02-tugas-main.png){width=75%}
  2. Answer briefly (2 to 3 sentences for each question): (a) what is the difference between an object and a reference to an object? (b) precisely when does a class's constructor run?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Transfer method between accounts correct and conceptual answers accurate | Transfer method between accounts present even though answers are incomplete |
