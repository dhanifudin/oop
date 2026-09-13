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

Meeting 11: **SOLID Principle and Collections**

Five principles of good class design, and ready-made data structures

---

## What You Will Learn

- How to store and search data without guessing a size upfront or checking every element one by one
- Five principles that keep a class easy to understand, extend, and test as an application grows larger
- How to recognize when a class violates one of those principles
- A synthesis case study: an order processing system that applies all five SOLID principles at once in a single design

<div class="tip-box">
Programming exercises for today's material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 11.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Collections, `ArrayList` and `Map`
- **Session 2 (50')**: SOLID part 1, Single Responsibility, Open/Closed, Liskov Substitution
- **Session 3 (50')**: SOLID part 2, Interface Segregation, Dependency Inversion
- **Session 4 (50')**: Synthesis, all five principles in one order processing system

---

<!-- _class: divider -->

# Part 1
## Collections

Session 1 of 4

---

## The Limits of a Plain Array

A plain array must have its size fixed the moment it is created, and finding one element inside it means checking elements one by one until it is found.

<div class="warn-box">
Fixing an array's size upfront means guessing: too small runs out of room, too large wastes memory for nothing. Searching data by checking one by one also gets slower as more data is added.
</div>

---

## Why Does This Matter?

Imagine an application with millions of customer records stored in an array. Finding one customer means, in the worst case, checking millions of elements one by one before it is found (or confirmed absent). The larger the application grows, the slower every search operation feels, while the array size limit fixed upfront is sooner or later bound to be exceeded.

<div class="term-box">
This is why nearly every real application uses a data structure from the Java Collections Framework instead of a plain array: its size adjusts automatically, and a lookup by key can be done directly without checking any other data at all.
</div>

---

## ArrayList and Map

![h:300 A fixed-size array with one-by-one search, compared with a Map with a direct lookup by key](../assets/illustrations/collections-motivation.svg)

<div class="term-box">
<b>ArrayList</b> is a list whose size adjusts automatically. <b>Map</b> (most commonly <code>HashMap</code> or <code>LinkedHashMap</code>) stores key-value pairs, and a lookup by key is done directly without checking any other element.
</div>

---

## Code Example: `ArrayList` Adjusts Its Size Automatically

```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee("E001", "Nadia"));
employees.add(new Employee("E002", "Sari"));
System.out.println(employees.get(1).getName());
```

No size needs to be fixed upfront; `employees.add(...)` adds an element with no limit guessed in advance.

---

## Code Example: `Map` Looks Up Directly by Key

```java
Map<String, Employee> employees = new HashMap<>();
employees.put("E001", new Employee("E001", "Nadia"));
employees.put("E002", new Employee("E002", "Sari"));
Employee e = employees.get("E002");
```

`employees.get("E002")` returns the object directly, without checking `"E001"` first.

---

## Common Mistake: Searching a Map with a Manual Loop

<div class="warn-box">
<b>Wrong:</b> writing <code>for (Employee e : employees.values()) { if (e.getId().equals("E003")) return e; }</code>, even though <code>employees</code> is already a <code>Map</code>.
</div>

**Right:** `employees.get("E003")` returns the object directly (or `null` if absent), with no need to check other elements one by one. This is `Map`'s main advantage over an array or `ArrayList` for a lookup by key.

---

## Exercise

Application A stores a list of orders that are ONLY ever processed sequentially from start to end, never searched by a specific ID. Application B stores employee data that is FREQUENTLY searched by employee ID number.

Which data structure (`ArrayList` or `Map`) fits each one better? Explain.

---

## Exercise Answer

**Application A: `ArrayList`.** Data is only accessed sequentially, never needing a fast lookup by a specific key, so `ArrayList` is already enough.

**Application B: `Map<String, Employee>`.** Lookups are frequently done by ID number, so `Map` uses the ID number as the key, making the lookup direct and far faster than checking one by one.

---

## Part 1 Summary

- A plain array must have its size fixed upfront, and searching it checks elements one by one.
- `ArrayList` adjusts its size automatically; `Map` stores key-value pairs and searches directly by key.
- Using a manual loop to search a `Map` throws away its main advantage.

Next: Part 2 covers three of the five SOLID principles, how to design a class that stays easy to extend.

---

<!-- _class: divider -->

# Part 2
## SOLID: Single Responsibility, Open/Closed, Liskov Substitution

Session 2 of 4

---

## Five Principles of Good Class Design

<div class="term-box">
<b>SOLID</b> is a set of five principles that help a class stay easy to understand, extend, and test as an application grows larger: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

Some of these you have already practiced without naming them, since several meetings ago. This part and Part 3 give them their formal names, while also covering the two that have not yet been discussed.

---

## Single Responsibility Principle (SRP)

![h:280 One class with three responsibilities, split into three classes each with one responsibility](../assets/illustrations/srp-split.svg)

<div class="term-box">
A class should have one responsibility, one reason to change. A class that mixes many responsibilities becomes hard to understand, and a change to one responsibility risks affecting another responsibility that is actually unrelated to it. Like a restaurant: one person does not act as chef, cashier, and server all at once, so a mistake in the kitchen does not also throw off the payment records.
</div>

---

## Why Does This Matter?

Imagine a class `Report` that at once computes a total, formats a display, and sends an email, all mixed into a single class. A team working on a display format change (say, from text to PDF) could unintentionally change a line that affects the total computation, simply because both live in the same file, even though the two are entirely unrelated.

<div class="term-box">
A class with one responsibility is far safer to change: changing how an email is sent never touches the calculation logic at all, since the two now live in different classes. The larger an application grows, the higher the price paid when this principle is ignored from the start.
</div>

---

## Code Example: `Report` Split into Three Classes

```java
class TotalCalculator {
    double calculate(Report report) { /* ... */ return 0; }
}

class ReportFormatter {
    String format(Report report) { /* ... */ return ""; }
}
```

`ReportMailer` (not shown) completes the third responsibility; each class now has only one reason to change.

---

## Open/Closed Principle (Recap)

![h:260 PaymentMethod.pay() with if/else per type, compared with a new GoPay subclass added without changing existing code](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: a class should be open for extension, closed for modification. You have already practiced this since Meeting 7: adding a new <code>PaymentMethod</code> subclass never changes the existing superclass code; the new subclass simply overrides its own method. Compare this with an <code>if</code>/<code>else</code> branch per type: a bug in one payment type's branch risks breaking another type's branch, since everything is mixed into the same method. Like an electrical wall outlet: any new appliance just plugs in, with no need to tear open the house wiring to add it.
</div>

---

## Why Does This Matter?

Imagine an e-commerce payment system that handles every payment kind through one large method full of `if`/`else` branches: `if (type.equals("CREDIT_CARD")) {...} else if (type.equals("BANK_TRANSFER")) {...}`. When the store wants to add GoPay, the only option is to insert a new `else if` branch INSIDE that same method. A programmer in a hurry could easily misplace the new logic and unintentionally change the Credit Card branch's flow that was already working correctly, even though the two are entirely unrelated.

<div class="term-box">
The more payment kinds get added to the same method, the larger that method grows, and the greater the risk that one small change spills over into another branch that should never have been touched. This is why the Open/Closed Principle matters: adding a new capability should never require changing old code that is already tested and already working correctly.
</div>

---

## Code Example: Adding GoPay Without Changing Old Code

```java
// Before: one large method, must be changed for every new kind
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }
// adding GoPay means inserting a new branch here

// After: a new subclass, old code untouched
class GoPayMethod extends PaymentMethod {
    void pay(double amount) { /* GoPay logic */ }
}
```

`PaymentMethod`, `CreditCardMethod`, and `BankTransferMethod`, already in place since Meeting 7, do not need a single line changed; `GoPayMethod` simply gets added as a new class.

---

## Common Mistake: Inserting a New Branch into an Old Method

<div class="warn-box">
<b>Wrong:</b> adding GoPay support by inserting a new <code>else if (type.equals("GOPAY")) {...}</code> into the existing <code>pay()</code> method, instead of creating a new subclass.
</div>

**Right:** create a class `GoPayMethod extends PaymentMethod`, then override its own `pay()` method. The old method where `CreditCardMethod` and `BankTransferMethod` already work correctly is never touched at all, so there is no risk of regression to an existing, already-tested payment kind.

---

## Liskov Substitution Principle (Recap)

![h:260 List<Bird> calling fly() for each element, Sparrow and Duck succeed, Penguin throws an exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: a subclass must be able to stand in for its superclass anywhere, without changing the correctness of the program. <code>Sedan</code> and <code>Truck</code> have always been usable anywhere code expects a <code>Vehicle</code>, since Meeting 6-7, without ever making that code behave incorrectly. Code that calls a method through a superclass type places full trust in that contract; the moment one subclass silently violates it, the program can fail in a location far from the offending subclass, far harder to trace than an ordinary compile error. The next slide shows a subclass that FAILS to keep this promise.
</div>

---

## Common Mistake: A Subclass Rejects Its Superclass's Contract

<div class="warn-box">
<b>Wrong:</b> <code>Penguin extends Bird</code> overrides <code>fly()</code> to throw <code>UnsupportedOperationException</code>, since a penguin cannot fly.
</div>

```java
for (Bird b : birds) {
    b.fly();  // blows up the moment b turns out to be a Penguin
}
```

**Right:** the code above assumes EVERY `Bird` can `fly()` without a problem; that is the `Bird` type's contract. `Penguin` rejects that contract (throwing an exception instead of flying), so `b.fly()` suddenly fails the moment `birds` contains a `Penguin`. This is a Liskov Substitution Principle violation: `Penguin` fails to stand in for `Bird` in exactly the place that expects a `Bird`.

---

## Fix: Separate the Flying Capability into an Interface

<div class="term-box">
The correct fix: take <code>fly()</code> out of <code>Bird</code>, and declare it as its own small interface, say <code>Flyable</code>. Only a subclass that genuinely can fly (<code>Sparrow</code>, <code>Duck</code>) implements <code>Flyable</code>; <code>Penguin</code> does not implement it at all, rather than implementing then rejecting it.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* does not implement Flyable */ }
```

The calling code changes to `if (b instanceof Flyable f) f.fly();`, checking the capability through an interface instead of assuming every `Bird` can necessarily fly. This pattern of separating a capability into a small interface appears again in Part 4 (`Shippable`).

---

## Exercise

For each of the following scenarios, determine which principle (Single Responsibility, Open/Closed, or Liskov Substitution) is violated:

1. A class `UserManager` at once validates input, saves it to a database, and sends a welcome email.
2. Adding a new discount kind requires changing the existing `calculateDiscount()` method, adding a new `if` branch inside it.

---

## Exercise Answer

1. **The Single Responsibility Principle is violated.** Three responsibilities (validation, storage, sending email) are mixed into a single class; one change risks affecting the others.
2. **The Open/Closed Principle is violated.** The existing method must be changed every time a new discount kind appears, when instead a new subclass or implementation should be enough, with no change to old code.

---

## Part 2 Summary

- Single Responsibility Principle: one class, one responsibility, one reason to change.
- Open/Closed Principle: open for extension (a new subclass), closed for modification (old code untouched).
- Liskov Substitution Principle: a subclass must be able to stand in for its superclass without changing the correctness of the program, never rejecting an inherited contract.

Next: Part 3 covers the two remaining SOLID principles, Interface Segregation and Dependency Inversion.

---

<!-- _class: divider -->

# Part 3
## SOLID: Interface Segregation, Dependency Inversion

Session 3 of 4

---

## Interface Segregation Principle (Recap)

![h:260 Chargeable implemented by Phone and ElectricCar, two separate hierarchies](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: an interface should be small and focused, so a class is never forced to implement a method irrelevant to it. You already practiced this in Meeting 9: a capability such as "can be charged" was declared as its own small interface (<code>Chargeable</code>), rather than folded into one large interface that forces other classes to implement a method irrelevant to them. Like a TV remote: a device that only needs to turn on and off should not be forced to have 50 channel buttons it will never use.
</div>

---

## Why Does This Matter?

Imagine `charge()` and `call()` folded into one large interface, call it `Device`. `ElectricCar`, implementing `Device`, is FORCED to also write a `call()` method, even though an electric car can never be used to make a phone call. Only two options remain, both equally problematic: leave `call()` empty (doing nothing at all), or throw an exception such as `UnsupportedOperationException`.

<div class="term-box">
Both options equally undermine trust in the interface: calling code holding a <code>Device</code> reference and calling <code>call()</code> assumes that method genuinely works, when on <code>ElectricCar</code> it silently does nothing, or worse, halts the program. An interface this bloated silently drags in a Liskov Substitution Principle violation too, since the subclass does not genuinely fulfill the contract its interface promised.
</div>

---

## Code Example: Splitting Up a Bloated Interface

```java
// Before: one bloated interface forces an irrelevant method
interface Device { void charge(); void call(); }
class ElectricCar implements Device {
    public void charge() { /* charge the battery */ }
    public void call() { throw new UnsupportedOperationException(); }
}

// After: two small, focused interfaces
interface Chargeable { void charge(); }
interface Callable { void call(); }
class ElectricCar implements Chargeable { /* only charge() */ }
```

`ElectricCar` now only needs to implement the capability genuinely relevant to it.

---

## Dependency Inversion Principle

<div class="term-box">
<b>Dependency Inversion Principle</b>: a high-level class should depend on an interface (an abstraction), not on a concrete implementation. Like a USB-C charger: the same cable charges a laptop, a phone, or earphones of any brand, as long as the device follows the USB-C port standard; the charger never needs to know the specific brand of the device it is charging. Part 4 shows this principle working alongside all four other principles in a single system.
</div>

---

## Inverting the Direction of Dependency

![h:280 OrderProcessor depending directly on MySqlDatabase, compared with depending on a Repository interface implemented by MySqlDatabase and MockRepository](../assets/illustrations/dip-invert-dependency.svg)

The next slide explains why inverting the direction of this dependency matters, rather than being merely an extra layer of abstraction.

---

## Why Does This Matter?

Imagine a class `OrderProcessor` that depends directly on the concrete class `MySqlDatabase`. Migrating to a different database, or adding automated tests (which need a fake data store so they never touch real data), both become difficult without changing `OrderProcessor` itself, since it directly "knows" its storage must be MySQL.

<div class="term-box">
The Dependency Inversion Principle inverts this direction of dependency: <code>OrderProcessor</code> simply depends on a <code>Repository</code> interface, and the concrete implementation (MySQL, temporary storage, or a fake version for testing) is free to change without <code>OrderProcessor</code> ever knowing or caring. Part 4 applies this same principle as part of a larger system.
</div>

---

## Code Example: `OrderProcessor` Depends on an Interface

```java
interface Repository {
    void save(Order order);
}

class OrderProcessor {
    private Repository repository;
    OrderProcessor(Repository repository) {
        this.repository = repository;
    }
}
```

---

## Common Mistake: Creating Its Own Concrete Implementation

<div class="warn-box">
<b>Wrong:</b> writing <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); ... }</code>, with <code>OrderProcessor</code> creating its own concrete instance inside its own class.
</div>

**Right:** `OrderProcessor` receives a `Repository` (interface) through its constructor, never knowing or creating its own concrete implementation. The code that creates the `OrderProcessor` object is the one that decides which implementation gets used.

---

## Exercise

`OrderProcessor` currently writes `private MySqlDatabase db = new MySqlDatabase();` directly inside its class.

Explain the steps to fix it so it follows the Dependency Inversion Principle, then state one concrete benefit of that fix.

---

## Exercise Answer

Create an interface `Repository` with the needed method (e.g. `save(...)`), make `MySqlDatabase` implement that interface, then have `OrderProcessor` receive a `Repository` through its constructor instead of creating its own `MySqlDatabase`. **Benefit:** `OrderProcessor` can be tested with a mock implementation without touching a real database, and the storage implementation can be swapped without changing `OrderProcessor` at all.

---

## Part 3 Summary

- Interface Segregation Principle: an interface stays small and focused, never forcing a class to implement an irrelevant method.
- Dependency Inversion Principle: a high-level class depends on an interface, not a concrete implementation.
- A class that creates its own concrete instance of a dependency (`new MySqlDatabase()` inside itself) violates the Dependency Inversion Principle, even if the interface already exists.

Next: Part 4 brings all these principles together in one synthesis case study.

---

<!-- _class: divider -->

# Part 4
## Synthesis: All Five Principles in One System

Session 4 of 4

---

## One Order Processing System

Imagine an online store's order processing system: there are physical orders (needing shipment) and digital orders (needing none), discounts that differ by customer type, and a data store that may someday need to move from memory to a file. This one small system turns out to be enough to show all five SOLID principles working together at once, rather than five separate, standalone rules.

---

## Shippable: LSP and ISP Together

![h:230 Order, PhysicalOrder implementing Shippable, and DigitalOrder deliberately not](../assets/uml/p11-order-shippable.png)

`Shippable` is deliberately small and focused, containing only `ship()` (Interface Segregation). `PhysicalOrder` implements it; `DigitalOrder` deliberately does NOT, since a digital order can never be shipped. This respects the Liskov Substitution Principle: `DigitalOrder` is never forced to pretend it has a `ship()` that makes no sense for it.

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

---

## OrderProcessor: Single Responsibility Principle

![h:280 OrderProcessor delegating to DiscountCalculator, Repository, and ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

`OrderProcessor` itself does not compute a discount, does not save data, and does not print anything. All three responsibilities are delegated to `DiscountCalculator`, `Repository`, and `ReceiptPrinter` respectively, so changing how a receipt is printed, for instance, never risks breaking the discount calculation.

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

---

## DiscountPolicy and Repository: OCP and DIP Side by Side

![h:280 DiscountPolicy implemented by RegularDiscount and WholesaleDiscount, Repository implemented by InMemoryRepository and FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

Both sides use the same pattern: one interface, many small implementations that can be added or swapped. `DiscountPolicy` (Open/Closed): a new discount kind is simply a new class, `DiscountCalculator` is never changed. `Repository` (Dependency Inversion): `OrderProcessor` depends only on its interface, and the storage implementation is free to be swapped.

---

## Code Example: Adding a New `DiscountPolicy`

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

Adding this class does not change a single line of the existing `DiscountCalculator` or `OrderProcessor`.

---

## Common Mistake: Forcing an Irrelevant Contract

<div class="warn-box">
<b>Wrong:</b> writing <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, forcing <code>DigitalOrder</code> to also implement <code>Shippable</code> for "consistency" with <code>PhysicalOrder</code>.
</div>

**Right:** this violates the Interface Segregation Principle (`DigitalOrder` is forced to implement a method irrelevant to it) as well as the Liskov Substitution Principle (`ship()` returning `"N/A"` is a fake contract, not genuine `Shippable` behavior). A class that genuinely lacks a particular capability should not implement that interface at all.

---

## Exercise

The store wants to add `StudentDiscountPolicy` (a 10% discount for customers with student status).

Which classes need to be added or changed? Name the SOLID principle that makes this change avoid touching the existing `DiscountCalculator` or `OrderProcessor` code at all.

---

## Exercise Answer

**Just one new class is needed**, `StudentDiscountPolicy implements DiscountPolicy`, then registering it with `DiscountCalculator` for the matching customer type. No code in `DiscountCalculator` or `OrderProcessor` needs to change. This is the **Open/Closed Principle**: the system is open for extension (a new discount policy class) but closed for modification (existing code is untouched).

---

## Part 4 Summary

- `Shippable`, small and focused, implemented only by the classes genuinely relevant to it, applies Interface Segregation while also preserving Liskov Substitution.
- `OrderProcessor` delegates discounting, storage, and printing to separate classes, applying Single Responsibility.
- `DiscountPolicy` and `Repository` both follow the one-interface-many-implementations pattern: the first applies Open/Closed, the second applies Dependency Inversion.

---

## Meeting 11 Summary

- `ArrayList` and `Map` replace a plain array for data whose size changes, or that is frequently searched by key.
- SOLID is a set of five class design principles: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
- Part 4's order processing system shows all five principles working together in one design, rather than five separate standalone rules.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, chapter on Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Programming exercises for this material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 11

---

## Discussion

`OrderProcessor` receives a `Repository` through its constructor, so it can move from `InMemoryRepository` to `FileRepository` without changing `OrderProcessor` at all. Explain in your own words: what would happen (which code would need to change, and in how many places) if `OrderProcessor` had depended directly on `InMemoryRepository` from the start with no `Repository` interface, and its storage later had to be switched to a file?
