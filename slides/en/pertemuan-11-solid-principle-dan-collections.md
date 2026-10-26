---
marp: true
theme: default
paginate: true
size: 16:9
style: |
  section {
    font-family: 'Helvetica Neue', Arial, sans-serif;
    padding: 56px 72px;
    justify-content: center;
  }
  section.lead {
    background: linear-gradient(135deg, #1e3a8a 0%, #1d4ed8 55%, #2563eb 100%);
    color: #fff;
    justify-content: center;
  }
  section.lead h1, section.lead h2, section.lead p {
    color: #fff;
  }
  section.divider {
    background: #1d4ed8;
    color: #fff;
  }
  section.divider h1 {
    color: #fff;
    font-size: 2.2em;
  }
  section.divider h2 {
    color: #bfdbfe;
  }
  section.divider p {
    color: #bfdbfe;
  }
  h1 {
    color: #1d4ed8;
    font-size: 1.6em;
  }
  h2 {
    color: #1d4ed8;
  }
  table {
    font-size: 0.72em;
    width: 100%;
  }
  code {
    background: #f1f5f9;
    color: #0f172a;
  }
  .term-box {
    border-left: 6px solid #1d4ed8;
    background: #eff6ff;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.82em;
  }
  .term-box b {
    color: #1d4ed8;
  }
  .tip-box {
    border-left: 6px solid #16a34a;
    background: #f0fdf4;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .warn-box {
    border-left: 6px solid #dc2626;
    background: #fef2f2;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .cols {
    display: flex;
    gap: 28px;
    align-items: center;
  }
  .cols > div {
    flex: 1;
  }
  .cols img {
    display: block;
    margin: 0 auto;
    max-width: 100%;
    max-height: 460px;
  }
  .footnote {
    font-size: 0.55em;
    color: #64748b;
    margin-top: 8px;
  }
  img {
    display: block;
    margin: 0 auto 12px auto;
    max-width: 90%;
    max-height: 420px;
  }
---

<!-- _class: lead -->

# Object-Oriented Programming
## RTI253007 &nbsp;|&nbsp; D-IV Informatics Engineering

Meeting 11: **SOLID Principles and Collections**

Five principles of good class design, and ready-made data structures

---

## What You Will Learn

- How to store data whose amount keeps changing (`ArrayList`)
- How to find data directly by a key (`Map`)
- The five SOLID principles that keep classes easy to understand, extend, and test
- How to recognize a class that breaks one of those principles
- Applying all five principles in one order-processing system

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 11.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Collections, `ArrayList` and `Map`
- **Session 2 (50')**: SOLID part 1, Single Responsibility, Open/Closed, Liskov Substitution
- **Session 3 (50')**: SOLID part 2, Interface Segregation, Dependency Inversion
- **Session 4 (50')**: Case study, an order-processing system

---

<!-- _class: divider -->

# Part 1
## Collections

Session 1 of 4

---

## The Problem: Arrays Are Rigid

```java
Employee[] employees = new Employee[2];   // size locked at 2
employees[0] = new Employee("E001", "Nadia");
employees[1] = new Employee("E002", "Sari");
employees[2] = new Employee("E003", "Rian");   // error at run time
```

An array only holds as many elements as the size set at the start. The third element causes an `ArrayIndexOutOfBoundsException`.

---

## The Problem: Searching Is One by One

```java
for (Employee e : employees) {
    if (e.getId().equals("E002")) { System.out.println(e.getName()); }
}
```

To find one employee, the program checks elements from the start until it finds a match. The more data there is, the longer the search takes.

---

## Why Does This Matter?

Imagine an online shop with millions of customer records in an array. Finding one customer can mean checking millions of elements. Meanwhile, the array's size limit is sooner or later exceeded.

<div class="term-box">
That is why real applications use the Java Collections Framework. Its size adjusts automatically, and searching by key can be direct.
</div>

---

## `ArrayList` and `Map`

An `ArrayList` is like a queue that may grow longer. A `Map` is like a phone book: look up a name, and get the number right away.

![h:270 A fixed-size array searched one by one, compared with a Map searched directly by key](../assets/illustrations/collections-motivation.svg)

<div class="term-box">
An <b>ArrayList</b> is a list whose size adjusts automatically. A <b>Map</b> stores key and value pairs, then finds a value directly through its key.
</div>

---

## Code Example: `ArrayList`

```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee("E001", "Nadia"));
employees.add(new Employee("E002", "Sari"));
System.out.println(employees.get(1).getName());
```

1. `<Employee>` means this list only holds `Employee` objects.
2. The two `add(...)` calls add elements at index 0 and 1.
3. `get(1)` takes index 1, which is the second element. Output: `Sari`

---

## Code Example: `Map`

```java
Map<String, Employee> employees = new HashMap<>();
employees.put("E001", new Employee("E001", "Nadia"));
employees.put("E002", new Employee("E002", "Sari"));
Employee e = employees.get("E002");
System.out.println(e.getName());
```

1. `<String, Employee>`: the key is a `String`, the value is an `Employee`.
2. `put(...)` stores one pair of key and value.
3. `get("E002")` fetches the object directly, without checking `"E001"`. Output: `Sari`

---

## Common Mistake: A Manual Loop on a `Map`

<div class="warn-box">
<b>Wrong:</b> searching a <code>Map</code> with a manual loop, even though <code>employees</code> is already a <code>Map</code>.
</div>

```java
for (Employee e : employees.values()) {
    if (e.getId().equals("E003")) { return e; }
}
```

**Correct:** simply `employees.get("E003")`. The result is `null` if the key does not exist. A manual loop throws away the main advantage of a `Map`.

---

## Exercise

Choose `ArrayList` or `Map` for each application:

1. A list of orders that is only printed in order, from start to end.
2. Employee data that is often looked up by employee ID number.
3. A glossary: the user types a word, then the program shows its meaning.

---

## Exercise Answer

1. **`ArrayList`.** The data is only read in order, with no need to search by key.
2. **`Map`.** The ID number becomes the key, so the lookup is direct.
3. **`Map`.** The word becomes the key, and the meaning becomes the value.

---

## Part 1 Summary

- An array has a fixed size and is searched one by one.
- An `ArrayList` adjusts its size automatically. A `Map` searches directly by key.
- A manual loop on a `Map` throws away its advantage.

Next: Part 2 covers three of the five SOLID principles, how to design classes that stay easy to extend.

---

<!-- _class: divider -->

# Part 2
## SOLID: Single Responsibility, Open/Closed, Liskov Substitution

Session 2 of 4

---

## Five Principles of Good Class Design

<div class="term-box">
<b>SOLID</b> is five principles that keep classes easy to understand, extend, and test. Each letter stands for one principle: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

You have already used some of them without realizing it. This meeting gives them their official names.

---

## Single Responsibility Principle (SRP)

In a restaurant, the cook, the cashier, and the waiter each have their own job. A mistake in the kitchen does not mess up the payments.

![h:270 One class with three responsibilities, split into three classes with one responsibility each](../assets/illustrations/srp-split.svg)

<div class="term-box">
A class should have one responsibility, which means one reason to change.
</div>

---

## Why Does This Matter?

Imagine a `Report` class that calculates totals, formats the display, and sends email all at once. The team changing the display format can accidentally break the calculation lines, because everything sits in one file.

<div class="term-box">
A class with one responsibility is safer to change. Changing how email is sent does not touch the calculation logic.
</div>

---

## Code Example: `Report` Split Into Three Classes

```java
class TotalCalculator { double calculate(Report r) { /* ... */ return 0; } }
class ReportFormatter { String format(Report r) { /* ... */ return ""; } }
class ReportMailer    { void send(Report r) { /* ... */ } }
```

1. Calculating the total lives in `TotalCalculator`.
2. Formatting the display lives in `ReportFormatter`.
3. Sending email lives in `ReportMailer`. Each class has only one reason to change.

---

## Open/Closed Principle (OCP)

A wall socket accepts any appliance. A new appliance is simply plugged in, and the house wiring is not torn open.

![h:270 PaymentMethod.pay() with an if/else per type, compared with a new GoPay subclass added without changing old code](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: a class is open for extension, closed for modification. New features are added through new classes, not by changing old code.
</div>

---

## Why Does This Matter?

Imagine an online shop's payment system with one big method containing an `if`/`else` for every payment type. Adding GoPay means inserting a new branch into that method. Put it in slightly the wrong place, and the credit card branch that already worked breaks too.

<div class="term-box">
The more payment types, the bigger that method and the bigger the risk. With OCP, a new feature does not change old code that is already tested.
</div>

---

## Code Example: Adding GoPay Without Changing Old Code

```java
// Before: every new type changes this method
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }

// After: just a new class
class GoPayMethod extends PaymentMethod {
    void pay(double amount) { /* GoPay logic */ }
}
```

`PaymentMethod`, `CreditCardMethod`, and `BankTransferMethod` already exist. Adding `GoPayMethod` does not change a single line of their code.

---

## Liskov Substitution Principle (LSP)

You order a motorbike taxi, and any motorbike may arrive. The replacement must still be able to take you there.

![h:270 List<Bird> calls fly() on each element, Sparrow and Duck succeed, Penguin throws an exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: a subclass must be able to replace its superclass anywhere without making the program wrong. <code>Sedan</code> and <code>Truck</code> from Meetings 6 and 7 already satisfy it.
</div>

---

## Why Does This Matter?

Code that calls a method through the superclass type trusts that type's contract. If one subclass quietly breaks the contract, the program fails far from the problem subclass. The cause is hard to trace.

<div class="term-box">
The compiler does not reject this kind of error. It only appears when the program runs, perhaps in front of a user.
</div>

---

## Common Mistake: A Subclass Rejects the Contract

<div class="warn-box">
<b>Wrong:</b> <code>Penguin extends Bird</code> overrides <code>fly()</code> to throw an <code>UnsupportedOperationException</code>, because penguins cannot fly.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error as soon as b turns out to be a Penguin
}
```

**Correct:** the code above assumes every `Bird` can `fly()`. As soon as `birds` holds a `Penguin`, `b.fly()` fails. `Penguin` cannot replace `Bird`, so Liskov is broken.

---

## The Fix: Separate the Ability to Fly

<div class="term-box">
Move <code>fly()</code> into a small interface, for example <code>Flyable</code>. Only <code>Sparrow</code> and <code>Duck</code> <code>implements Flyable</code>. <code>Penguin</code> does not.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* does not implement Flyable */ }
```

The caller now checks the ability: `if (b instanceof Flyable f) f.fly();`. This small-interface pattern returns in Part 4 (`Shippable`).

---

## Exercise

Which principle is broken: Single Responsibility, Open/Closed, or Liskov Substitution?

1. `UserManager` validates input, saves to the database, and sends email.
2. A new discount type requires adding an `if` branch in the method `calculateDiscount()`.
3. `Penguin extends Bird`, but its `fly()` throws an exception.

---

## Exercise Answer

1. **Single Responsibility.** Three responsibilities are mixed in one class.
2. **Open/Closed.** The old method must change for every new discount type.
3. **Liskov Substitution.** `Penguin` rejects the `Bird` contract, so it cannot replace `Bird`.

---

## Part 2 Summary

- Single Responsibility: one class, one reason to change.
- Open/Closed: open for extension, closed for modification.
- Liskov Substitution: a subclass must be able to replace its superclass without making the program wrong.

Next: Part 3 covers the two remaining principles, Interface Segregation and Dependency Inversion.

---

<!-- _class: divider -->

# Part 3
## SOLID: Interface Segregation, Dependency Inversion

Session 3 of 4

---

## Interface Segregation Principle (ISP)

A TV remote that only needs to turn the TV on and off does not need 50 channel buttons.

![h:230 Chargeable implemented by Phone and ElectricCar, two separate hierarchies](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: an interface should be small and focused, so classes are not forced to fill in methods that do not apply to them. An example is <code>Chargeable</code> from Meeting 9.
</div>

---

## Why Does This Matter?

Imagine `charge()` and `dial()` merged into one big interface, `Device`. `ElectricCar` must also write `dial()`, even though an electric car cannot make calls. There are two choices and both are bad: leave it empty, or throw an exception.

<div class="term-box">
Code holding a <code>Device</code> assumes <code>dial()</code> works. On <code>ElectricCar</code>, that method does nothing or stops the program. A fat interface also breaks Liskov.
</div>

---

## Code Example: Splitting a Fat Interface

```java
// Before: one fat interface
interface Device { void charge(); void dial(); }

// After: two small interfaces
interface Chargeable { void charge(); }
interface Dialable { void dial(); }
class ElectricCar implements Chargeable { /* only charge() */ }
```

1. Before: `ElectricCar` is forced to write a `dial()` that makes no sense.
2. After: two small interfaces, one ability each.
3. `ElectricCar` only `implements Chargeable`.

---

## Dependency Inversion Principle (DIP)

A cashier only knows the card machine, not the bank that issued the card. A card from any bank can be used.

![h:270 OrderProcessor depending directly on MySqlDatabase, compared with depending on the Repository interface implemented by MySqlDatabase and MockRepository](../assets/illustrations/dip-invert-dependency.svg)

<div class="term-box">
<b>Dependency Inversion Principle</b>: the class that controls the flow (a high-level class) depends on an interface, not on a concrete class.
</div>

---

## Why Does This Matter?

Imagine an `OrderProcessor` that uses the class `MySqlDatabase` directly. Moving to another database, or testing with a fake database, becomes hard because `OrderProcessor` is tied to MySQL.

<div class="term-box">
With a <code>Repository</code> interface, storage can change (MySQL, memory, or a fake for testing) without changing <code>OrderProcessor</code>.
</div>

---

## Code Example: `OrderProcessor` Depends on an Interface

```java
interface Repository { void save(Order order, int total); }

class OrderProcessor {
    private Repository repository;
    OrderProcessor(Repository repository) { this.repository = repository; }
}
```

1. `Repository` only states the ability `save(...)`.
2. `OrderProcessor` receives a `Repository` through its constructor.
3. The code that creates `OrderProcessor` chooses which implementation is used.

---

## Common Mistake: Creating the Concrete Implementation Yourself

<div class="warn-box">
<b>Wrong:</b> <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); }</code>. This class creates its own concrete object.
</div>

**Correct:** `OrderProcessor` receives a `Repository` (an interface) through its constructor. The code that creates `OrderProcessor` decides which implementation is used.

---

## Exercise

Which principle is broken: Interface Segregation or Dependency Inversion?

1. `interface Printer` has `print()`, `scan()`, and `fax()`. `SimplePrinter implements Printer`, even though it can only `print()`.
2. `OrderProcessor` contains `private MySqlDatabase db = new MySqlDatabase();`. What one change fixes it?

---

## Exercise Answer

1. **Interface Segregation.** `SimplePrinter` is forced to fill in `scan()` and `fax()`. Split it into small interfaces.
2. **Dependency Inversion.** Create an interface `Repository`, then let `OrderProcessor` receive it through its constructor. Storage becomes easy to swap and easy to test with a fake.

---

## Part 3 Summary

- Interface Segregation: small, focused interfaces.
- Dependency Inversion: depend on an interface, not a concrete class.
- Creating a concrete object with `new` inside a class breaks Dependency Inversion, even when the interface exists.

Next: Part 4 brings all five principles together in one case study.

---

<!-- _class: divider -->

# Part 4
## Case Study: All Five Principles in One System

Session 4 of 4

---

## One Order-Processing System

An online shop has physical orders (which must be shipped) and digital orders (which need no shipping). Discounts differ by customer type, and storage may one day move from memory to a file.

This small system is enough to show all five principles working together.

---

## `Shippable`: LSP and ISP at Once

![h:230 Order, PhysicalOrder implementing Shippable, and DigitalOrder deliberately not implementing it](../assets/uml/p11-order-shippable.png)

`Shippable` is small and focused, only `ship()` (Interface Segregation). `PhysicalOrder` implements it. `DigitalOrder` deliberately does not, because a digital order is never shipped, so it is not forced to have a fake `ship()` (Liskov Substitution).

---

## Code Example: `PhysicalOrder` and `DigitalOrder`

```java
public class PhysicalOrder extends Order implements Shippable {
    public String ship() {
        return "Shipping \"" + getDescription() + "\"...";
    }
}

public class DigitalOrder extends Order {
    // deliberately does not implement Shippable
}
```

Only `PhysicalOrder` can be shipped.

---

## `OrderProcessor`: Single Responsibility

![h:270 OrderProcessor delegating to DiscountCalculator, Repository, and ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

`OrderProcessor` does not calculate discounts, does not save data, and does not print anything. Those three jobs are handed to `DiscountCalculator`, `Repository`, and `ReceiptPrinter`. Changing how receipts are printed cannot break the discount calculation.

---

## Code Example: `OrderProcessor` Only Coordinates

```java
public void processOrder(Customer customer, Order order) {
    int discount = discountCalculator.calculate(customer, order);
    int total = order.getAmount() - discount;
    repository.save(order, total);
    receiptPrinter.print(customer, order, discount, total);
}
```

This method only calls three other classes in order: calculate the discount, save, print the receipt.

---

## `DiscountPolicy` and `Repository`: OCP and DIP

![h:270 DiscountPolicy implemented by RegularDiscount and WholesaleDiscount, Repository implemented by InMemoryRepository and FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

Both use the same pattern: one interface, many small implementations. A new discount type is simply a new class (Open/Closed). `OrderProcessor` only knows the `Repository` interface, so storage can be swapped freely (Dependency Inversion).

---

## Code Example: Adding a New `DiscountPolicy`

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

Adding this class does not change a single line of `DiscountCalculator` or `OrderProcessor`.

---

## Common Mistake: Forcing an Irrelevant Contract

<div class="warn-box">
<b>Wrong:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, to be "consistent" with <code>PhysicalOrder</code>.
</div>

**Correct:** this breaks Interface Segregation, because `DigitalOrder` is forced to fill in an irrelevant method. It also breaks Liskov, because `"N/A"` is a fake contract. A class without that ability should not implement the interface.

---

## Exercise

The shop wants to add `StudentDiscountPolicy` (a 10% discount for students).

1. How many new classes must be added?
2. Must `DiscountCalculator` or `OrderProcessor` be changed?
3. Which SOLID principle makes that possible?

---

## Exercise Answer

1. **One new class:** `StudentDiscountPolicy implements DiscountPolicy`.
2. **No.** The new class is registered when the system is assembled, for example in `main`, not inside `DiscountCalculator` or `OrderProcessor`.
3. **Open/Closed.** The system is open for extension, but existing code is not changed.

---

## Part 4 Summary

- A small `Shippable` applies Interface Segregation and keeps Liskov Substitution.
- `OrderProcessor` hands discounts, storage, and printing to other classes (Single Responsibility).
- `DiscountPolicy` applies Open/Closed. `Repository` applies Dependency Inversion.

---

## Meeting 11 Summary

- `ArrayList` and `Map` replace arrays for data that changes or is often looked up by key.
- SOLID is five class-design principles: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
- The order-processing system shows all five principles working together in one design.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Collections chapter; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 11

---

## Discussion

`OrderProcessor` receives a `Repository` through its constructor, so storage can move from `InMemoryRepository` to `FileRepository`. Imagine `OrderProcessor` had used `InMemoryRepository` directly from the start.

1. What code would have to change when storage moves to a file?
2. In how many places might that change appear?
