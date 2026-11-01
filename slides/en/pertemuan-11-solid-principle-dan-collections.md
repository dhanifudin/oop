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

Five principles of good class design

---

## What You Will Learn

- The five SOLID principles, so classes stay easy to understand, extend, and test
- How to spot a class that breaks one of those principles
- How to store data whose amount keeps changing (`ArrayList` and `Map`)
- All five principles applied in one order-processing system

<div class="tip-box">
The programming exercises for today's material are in the Object-Oriented Programming Practicum jobsheet (RTI253008), Meeting 11.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Single Responsibility and Open/Closed
- **Session 2 (50')**: Liskov Substitution and Interface Segregation
- **Session 3 (50')**: Dependency Inversion, then `ArrayList` and `Map`
- **Session 4 (50')**: Case study, an order-processing system

---

<!-- _class: divider -->

# Part 1
## SOLID: Single Responsibility and Open/Closed

Session 1 of 4

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
A class should have one responsibility, which means one reason to change. <b>One class, one job.</b>
</div>

---

## Why Does This Matter?

Imagine a `Report` class that calculates totals, formats the display, and sends email all at once. The team changing the display format can accidentally break the calculation lines. Everything sits in one file.

<div class="term-box">
A class with one job is safer to change. Replacing how email is sent does not touch the calculation.
</div>

---

## Code Example: `Report` Split Into Three Classes

```java
class TotalCalculator { double calculate(Report r) { /* ... */ return 0; } }
class ReportFormatter { String format(Report r) { /* ... */ return ""; } }
class ReportMailer    { void send(Report r) { /* ... */ } }
```

1. Calculating totals lives in `TotalCalculator`.
2. Formatting the display lives in `ReportFormatter`.
3. Sending email lives in `ReportMailer`. Each class has only one reason to change.

---

## Common Mistake: The Do-Everything Class

<div class="warn-box">
<b>Wrong:</b> <code>class UserManager</code> holds <code>validate()</code>, <code>saveToDatabase()</code>, and <code>sendEmail()</code> all at once. One class does three jobs.
</div>

**Correct:** three small classes, `UserValidator`, `UserStore`, and `EmailSender`. Each class has one job. Replacing how email is sent no longer touches the validation code.

---

## Open/Closed Principle (OCP)

A power socket accepts any appliance. A new appliance just plugs in, and the house wiring is not torn open.

![h:270 PaymentMethod.pay() with an if/else per type, compared with a new GoPay subclass added without changing old code](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: a class is open for extension, closed for modification. New features are added through new classes. <b>New class, not old code changed.</b>
</div>

---

## Why Does This Matter?

Imagine an online shop's payment system with one big method full of `if`/`else` branches, one per payment type. Adding GoPay means inserting a new branch into that method. One slip, and the credit card branch that already worked breaks too.

<div class="term-box">
The more payment types, the bigger that method and the bigger the risk. With a new class, the old, tested code is never touched.
</div>

---

## Code Example: Adding GoPay Without Changing Old Code

```java
// Before: every new type changes this method
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }

// After: just a new class
class GoPay extends PaymentMethod {
    void pay(double amount) { /* GoPay logic */ }
}
```

`PaymentMethod`, `CreditCard`, and `BankTransfer` already exist. Adding `GoPay` does not change a single line of their code.

---

## Common Mistake: Adding an `if` Branch for Every New Type

<div class="warn-box">
<b>Wrong:</b> every new discount type adds one more branch to <code>calculateDiscount()</code>.
</div>

```java
if (type.equals("REGULAR")) { return amount * 5 / 100; }
else if (type.equals("WHOLESALE")) { return amount * 20 / 100; }
else if (type.equals("STUDENT")) { return amount * 10 / 100; }   // yet another branch
```

**Correct:** one `DiscountPolicy` interface with a `calculate(amount)` method. Each discount type becomes its own class, for example `StudentDiscountPolicy`. A new type never touches the old method.

---

## Exercise

Which principle is broken: Single Responsibility or Open/Closed?

1. `UserManager` validates input, saves to the database, and sends email.
2. A new payment type requires adding an `if` branch in the method `pay()`.
3. `InvoicePrinter` prints the invoice and also calculates its tax.

---

## Exercise Answer

1. **Single Responsibility.** Three jobs are mixed into one class.
2. **Open/Closed.** The old method must change for every new payment type.
3. **Single Responsibility.** Printing and calculating tax are two jobs. A change in tax rules would touch the printing code.

---

## Part 1 Summary

- Single Responsibility: one class, one job.
- Open/Closed: new class, not old code changed.
- Both keep a small change small. Only one class is touched.

Next: Part 2 covers two principles about subclasses and interfaces, Liskov Substitution and Interface Segregation.

---

<!-- _class: divider -->

# Part 2
## SOLID: Liskov Substitution and Interface Segregation

Session 2 of 4

---

## Liskov Substitution Principle (LSP)

You order a motorbike taxi, and any motorbike may arrive. The replacement must still be able to take you there.

![h:270 List<Bird> calls fly() on each element, Sparrow and Duck succeed, Penguin throws an exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: a subclass must be able to replace its superclass anywhere without making the program wrong. <code>Sparrow</code> and <code>Duck</code> in the picture satisfy it, <code>Penguin</code> does not. <b>The replacement must still work.</b>
</div>

---

## Why Does This Matter?

Code that calls a method through the superclass type trusts the superclass's promise. If one subclass quietly breaks that promise, the program fails far away from that subclass. The cause is hard to trace.

<div class="term-box">
The compiler does not reject this kind of error. It only appears when the program runs, perhaps in front of a user.
</div>

---

## Common Mistake: A Subclass Breaks the Promise

<div class="warn-box">
<b>Wrong:</b> <code>Penguin extends Bird</code> overrides <code>fly()</code> to throw an <code>UnsupportedOperationException</code>, because penguins cannot fly.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error as soon as b turns out to be a Penguin
}
```

**Correct:** `Penguin` has no `fly()` at all. The ability to fly moves into a `Flyable` interface (next slide). The reason: the code above assumes every `Bird` can `fly()`. `Penguin` cannot replace `Bird`.

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

The caller now checks the ability: `if (b instanceof Flyable f) f.fly();`. The form `instanceof Flyable f` checks the type and also gives a variable `f` of type `Flyable`, without a cast. This small-interface pattern returns in Part 4 (`Shippable`).

---

## Interface Segregation Principle (ISP)

A TV remote that only needs to turn the TV on and off does not need 50 channel buttons.

![h:230 Chargeable implemented by Phone and ElectricCar, two separate hierarchies](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: an interface should be small and focused, so classes are not forced to fill in methods that do not apply to them. An example is <code>Chargeable</code> above: one method <code>charge()</code>, implemented by <code>Phone</code> and <code>ElectricCar</code>, two unrelated classes. <b>Small interface, one ability.</b>
</div>

---

## Why Does This Matter?

Imagine `charge()` and `call()` merged into one big interface, `Device`. `ElectricCar` must also write `call()`, even though an electric car cannot make calls. There are two choices and both are bad: leave it empty, or throw an exception.

<div class="term-box">
Code holding a <code>Device</code> assumes <code>call()</code> works. On <code>ElectricCar</code>, that method does nothing or stops the program. A fat interface also breaks Liskov.
</div>

---

## Code Example: Splitting a Fat Interface

```java
// Before: one fat interface
interface Device { void charge(); void call(); }

// After: two small interfaces
interface Chargeable { void charge(); }
interface Caller { void call(); }
class ElectricCar implements Chargeable { /* only charge() */ }
```

1. Before: `ElectricCar` is forced to write a `call()` that makes no sense.
2. After: two small interfaces, one ability each.
3. `ElectricCar` only `implements Chargeable`.

---

## Exercise

Which principle is broken: Liskov Substitution or Interface Segregation?

1. `Penguin extends Bird`, but its `fly()` throws an exception.
2. `interface Printer` has `print()`, `scan()`, and `fax()`. `SimplePrinter implements Printer`, even though it can only `print()`.
3. `ReadOnlyList extends ArrayList`, but its `add()` throws an exception.

---

## Exercise Answer

1. **Liskov Substitution.** `Penguin` breaks the promise of `Bird`, so it cannot replace it.
2. **Interface Segregation.** `SimplePrinter` is forced to fill in `scan()` and `fax()`. Split it into small interfaces.
3. **Liskov Substitution.** Code holding an `ArrayList` assumes `add()` always works. `ReadOnlyList` breaks that.

---

## Part 2 Summary

- Liskov Substitution: the replacement must still work.
- Interface Segregation: small interface, one ability.
- A fat interface forces fake methods. Fake methods break Liskov.

Next: Part 3 covers the last principle, Dependency Inversion, then two data structures for storing many objects.

---

<!-- _class: divider -->

# Part 3
## SOLID: Dependency Inversion, and Collections

Session 3 of 4

---

## Dependency Inversion Principle (DIP)

A cashier only knows the card machine, not the issuing bank. A card from any bank can be used.

![h:270 OrderProcessor depending directly on MySqlDatabase, compared with depending on the Repository interface implemented by MySqlDatabase and MockRepository](../assets/illustrations/dip-invert-dependency.svg)

<div class="term-box">
<b>Dependency Inversion Principle</b>: the class that runs the flow, for example <code>OrderProcessor</code>, depends on an interface, not on a real class such as <code>MySqlDatabase</code>. <b>Depend on the interface, not the real class.</b>
</div>

---

## Why Does This Matter?

Imagine an `OrderProcessor` that uses the `MySqlDatabase` class directly. Moving to another database becomes hard. Testing with a fake database is hard too. `OrderProcessor` is tied to MySQL.

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

## Common Mistake: Creating the Real Class Yourself

<div class="warn-box">
<b>Wrong:</b> <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); }</code>. The class creates its own real object.
</div>

**Correct:** `OrderProcessor` receives a `Repository` (interface) through its constructor. The code that creates `OrderProcessor` decides which implementation is used.

---

## The Problem: Arrays Are Rigid

`InMemoryRepository` must store many `Order` objects. A plain array has two problems.

```java
Order[] orders = new Order[2];                      // size locked at 2
orders[0] = new Order("Laptop", 5000000);
orders[1] = new Order("Mouse", 150000);
orders[2] = new Order("Cable", 50000);              // error at run time
```

1. The size is fixed at the start. The third element causes an `ArrayIndexOutOfBoundsException`.
2. Finding one element means checking from the start, one by one.

---

## `ArrayList` and `Map`

| | Array | `ArrayList` | `Map` |
|---|---|---|---|
| Size | fixed when created | grows automatically | grows automatically |
| How to fetch | by index | by index, `get(1)` | by key, `get("WHOLESALE")` |
| Best for | a known count | an ordered list | direct lookup by key |

<div class="term-box">
<b>ArrayList</b> is a list whose size adjusts automatically. <b>Map</b> stores pairs of key and value, then finds a value directly by its key. <code>HashMap</code> does not guarantee order; <code>LinkedHashMap</code> keeps insertion order.
</div>

---

## Code Example: `InMemoryRepository` Uses an `ArrayList`

```java
class InMemoryRepository implements Repository {
    private List<Order> orders = new ArrayList<>();   // a list that can grow

    public void save(Order order, int total) {
        orders.add(order);                            // no size limit
    }
}
```

1. `List<Order>` means this list only holds `Order` objects.
2. `add(...)` appends at the end. No need to know the capacity first.
3. `OrderProcessor` only knows `Repository`. It does not know there is an `ArrayList` inside.

---

## Code Example: A `Map` to Pick a Policy

```java
Map<String, DiscountPolicy> policies = new HashMap<>();
policies.put("REGULAR", new RegularDiscountPolicy());
policies.put("WHOLESALE", new WholesaleDiscountPolicy());
DiscountPolicy policy = policies.get("WHOLESALE");   // direct, no loop
```

1. `<String, DiscountPolicy>`: the key is the customer type, the value is a policy (from Part 1).
2. `put(...)` stores one pair of key and value.
3. `get("WHOLESALE")` fetches `WholesaleDiscountPolicy` directly, without checking `"REGULAR"`. If the key is missing, the result is `null`.

---

## Exercise

1. `OrderProcessor` contains `private MySqlDatabase db = new MySqlDatabase();`. Which principle is broken?
2. Pick `ArrayList` or `Map`: a list of orders that is only printed in order from first to last.
3. Pick `ArrayList` or `Map`: customer data that is often looked up by customer number.

---

## Exercise Answer

1. **Dependency Inversion.** Create a `Repository` interface, then let `OrderProcessor` receive it through its constructor.
2. **`ArrayList`.** The data is only read in order, no lookup by key is needed.
3. **`Map`.** The customer number becomes the key, so the lookup is direct.

---

## Part 3 Summary

- Dependency Inversion: depend on the interface, not the real class.
- Creating a real object with `new` inside the class still breaks it, even when the interface exists.
- `ArrayList` for a list that grows. `Map` for direct lookup by key.

Next: Part 4 brings all five principles together in one case study.

---

<!-- _class: divider -->

# Part 4
## Case Study: All Five Principles in One System

Session 4 of 4

---

## One Order-Processing System

An online shop has physical orders (which must be shipped) and digital orders (which need no shipping). Discounts differ by customer type, and storage may one day move from memory to a file.

The players:

- `Order`, with subclasses `PhysicalOrder` and `DigitalOrder`
- `Customer`, which has a customer type (regular, wholesale)
- `OrderProcessor`, the coordinator of the order flow
- `DiscountCalculator` and `DiscountPolicy`, which compute the discount
- `Repository`, which saves orders; `ReceiptPrinter`, which prints the receipt

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
        return "Shipping " + describe() + "...";
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

`OrderProcessor` does not compute the discount, does not store data, and does not print anything. Those three jobs go to `DiscountCalculator`, `Repository`, and `ReceiptPrinter`. Changing how receipts are printed cannot break the discount calculation.

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

This method only calls three other classes in turn: compute the discount, save, print the receipt.

---

## `DiscountPolicy` and `Repository`: OCP and DIP

![h:270 DiscountPolicy implemented by RegularDiscount and WholesaleDiscount, Repository implemented by InMemoryRepository and FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

Both use the same pattern: one interface, many small implementations. A new discount type is just a new class (Open/Closed). `OrderProcessor` only knows the `Repository` interface, so storage can be swapped freely (Dependency Inversion).

---

## Code Example: Adding a New `DiscountPolicy`

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

Adding this class does not change a single line of `DiscountCalculator` or `OrderProcessor`. `DiscountCalculator` keeps the policies in a `Map<String, DiscountPolicy>` keyed by customer type, exactly the `Map` example from Part 3.

---

## Common Mistake: Forcing an Irrelevant Contract

<div class="warn-box">
<b>Wrong:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, to be "consistent" with <code>PhysicalOrder</code>.
</div>

**Correct:** `class DigitalOrder extends Order { }` without `Shippable`. The reason: the wrong version breaks Interface Segregation, because `DigitalOrder` is forced to fill in an irrelevant method, and breaks Liskov, because `"N/A"` is a fake promise.

---

## Exercise

The shop wants to add `StudentDiscountPolicy` (a 10% discount for students).

1. How many new classes need to be added?
2. Does `DiscountCalculator` or `OrderProcessor` need to change?
3. Which SOLID principle makes that possible?

---

## Exercise Answer

1. **One new class:** `StudentDiscountPolicy implements DiscountPolicy`.
2. **No.** The new class is registered when the system is assembled, for example in `main`, not inside `DiscountCalculator` or `OrderProcessor`.
3. **Open/Closed.** The system is open for extension, but existing code is not changed.

---

## Part 4 Summary

- The small `Shippable` applies Interface Segregation and preserves Liskov Substitution.
- `OrderProcessor` hands discounts, storage, and printing to other classes (Single Responsibility).
- `DiscountPolicy` applies Open/Closed. `Repository` applies Dependency Inversion.

---

## Meeting 11 Summary

- SOLID is five principles of class design: one class one job; new class, not old code changed; the replacement must still work; small interface; depend on the interface.
- `ArrayList` and `Map` replace arrays for data that grows or is looked up by key.
- The order-processing system shows all five principles working together in one design.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, Collections chapter; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

The programming exercises for this material are in the Object-Oriented Programming Practicum jobsheet (RTI253008), Meeting 11

---

## Discussion

`OrderProcessor` receives a `Repository` through its constructor, so storage can move from `InMemoryRepository` to `FileRepository`. Imagine `OrderProcessor` had used `InMemoryRepository` directly from the start.

1. Which code would have to change when storage moves to a file?
2. In how many places could that change appear?
