# Practicum Jobsheet: Meeting 4
## Class Relationships and Object Arrays

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 4 (Week 4) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Write a class that holds another class as its attribute (an association relationship).
2. Manage a collection of objects through an array that is a class's attribute (an aggregation relationship), including searching within it.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: this meeting continues the `bank-mini` project from Meeting 3.
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

### Step 1: Account Relates to Customer

The `bank-mini` project from Meeting 3 continues in this meeting. So far, `Account` has only stored its owner's name as plain text. Add a class `Customer`:

![Customer.java](../assets/code/pertemuan-04/p04-01-customer.png){width=60%}

Replace the contents of `Account.java` so it stores a reference to a `Customer` (association), instead of an owner name as plain text:

![Account.java, the ownerName attribute replaced with an owner of type Customer](../assets/code/pertemuan-04/p04-01-account.png){width=65%}

Since `Account`'s constructor now accepts a `Customer` object, update the test in `Main.java`:

![Main.java using Customer and the new Account constructor](../assets/code/pertemuan-04/p04-01-main.png){width=70%}

> ✅ **Checkpoint:** the program recompiles successfully and displays `A001 - Nadia - balance: 350000.0`, this time obtained through a `Customer` object.

> ⚠️ **If it fails:** if the error `incompatible types: String cannot be converted to Customer` appears, check whether the second argument to `new Account(...)` is now a `Customer` object, not an owner name as text.

### Step 2: Bank Manages Many Account Objects

Add a class `Bank`, which stores many `Account` objects in an array (aggregation):

![Bank.java](../assets/code/pertemuan-04/p04-02-bank.png){width=65%}

Update `Main.java` to create two accounts and manage them through `Bank`:

![Main.java using Bank to manage two Account objects](../assets/code/pertemuan-04/p04-02-main.png){width=70%}

> ✅ **Checkpoint:** the program prints two lines from `printAllAccounts()` (`A001 - Nadia - balance: 350000.0` and `A002 - Sari - balance: 200000.0`), followed by one more line from `findAccount("A002")` displaying the same data for `A002`.

> ⚠️ **If it fails:** if `findAccount(...)` always returns `null` even though the account number exists, check whether the comparison uses `.equals(...)`, not `==`, since `==` on a `String` compares references, not the text content.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 2.
- **Independent assignment:**
  1. A customer could hold more than one account. Add a method `findAccountsByOwnerName(String name)` to `Bank`, returning an array containing every `Account` that belongs to a customer with that name:

     ![Bank.java with findAccountsByOwnerName added](../assets/code/pertemuan-04/p04-tugas-bank.png){width=65%}

     Demonstrate this by creating one customer named "Nadia" who holds two `Account` objects, another customer with one `Account`, adding all three to `Bank`, then calling `findAccountsByOwnerName("Nadia")` and printing the count and contents of the result.
  2. Answer briefly (2 to 3 sentences for each question):
     - (a) why is the `Account`-`Customer` relationship called an association, rather than an aggregation or composition?
     - (b) imagine a `Bank` object is removed from memory. In your view, should the `Account` objects that were added to it be removed along with it, or should they still be able to stand on their own? What does your answer mean for the kind of relationship between `Bank` and `Account`?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Search-by-customer-name method correct and conceptual answers accurate | Search method present even though answers are incomplete |
