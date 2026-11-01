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
  .cols.wide > div:first-child {
    flex: 2;
  }
  .cols.wide > div:last-child {
    flex: 1;
  }
  .cols.wide img {
    max-height: 480px;
  }
  .cols.wide .term-box {
    margin: 0;
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
- All five principles in one order-processing system

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
<b>SOLID</b> is five principles that keep classes easy to understand, extend, and test: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

You have already used some without realizing it. Today we give them their official names.

---

## Single Responsibility Principle (SRP)

<div class="cols wide">
<div>

![One class with three responsibilities, split into three classes with one responsibility each](../assets/illustrations/srp-split.svg)

</div>
<div>

In a restaurant, the cook, the cashier, and the waiter each have their own job. A mistake in the kitchen does not mess up the payments.

<div class="term-box">
A class has one responsibility, which means one reason to change. <b>One class, one job.</b>
</div>

</div>
</div>

---

## Why Does This Matter?

A `Report` class calculates totals, formats the display, and sends email all at once. Changing the display format can accidentally break the calculation.

<div class="term-box">
A class with one job is safe to change. Replacing how email is sent does not touch the calculation.
</div>

---

## Code Example: `Report` Split Into Three Classes

```java
class TotalCalculator { double calculate(Report r) { /* ... */ return 0; } }
class ReportFormatter { String format(Report r) { /* ... */ return ""; } }
class ReportMailer    { void send(Report r) { /* ... */ } }
```

1. Calculating totals: `TotalCalculator`.
2. Formatting the display: `ReportFormatter`.
3. Sending email: `ReportMailer`. Each class has one reason to change.

---

## Open/Closed Principle (OCP)

<div class="cols wide">
<div>

![PaymentMethod.pay() with an if/else per type, compared with a new GoPay subclass added without changing old code](../assets/illustrations/ocp-extend-not-modify.svg)

</div>
<div>

A power socket accepts any appliance. A new appliance just plugs in, and the house wiring is not torn open.

<div class="term-box">
<b>Open/Closed Principle</b>: open for extension, closed for modification. <b>New class, not old code changed.</b>
</div>

</div>
</div>

---

## Why Does This Matter?

One big method holds an `if`/`else` for every payment type. Adding GoPay means inserting a new branch. One slip, and the credit card branch breaks too.

<div class="term-box">
With a new class, the old, tested code is never touched.
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

`PaymentMethod`, `CreditCard`, and `BankTransfer` do not change by a single line.

---

## Common Mistake: An `if` Branch for Every New Type

<div class="warn-box">
<b>Wrong:</b> every new discount type adds one more branch to <code>calculateDiscount()</code>.
</div>

```java
if (type.equals("REGULAR")) { return amount * 5 / 100; }
else if (type.equals("WHOLESALE")) { return amount * 20 / 100; }
else if (type.equals("STUDENT")) { return amount * 10 / 100; }   // yet another branch
```

**Correct:** a `DiscountPolicy` interface with `calculate(amount)`, one class per discount type. A new type never touches the old method.

---

## Exercise

Which principle is broken: Single Responsibility or Open/Closed?

1. `UserManager` validates input, saves to the database, and sends email.
2. A new payment type requires adding an `if` branch in `pay()`.
3. `InvoicePrinter` prints the invoice and also calculates its tax.

---

## Exercise Answer

1. **Single Responsibility.** Three jobs in one class.
2. **Open/Closed.** The old method changes for every new type.
3. **Single Responsibility.** Printing and calculating tax are two jobs.

---

## Part 1 Summary

- Single Responsibility: one class, one job.
- Open/Closed: new class, not old code changed.
- A small change stays small: only one class is touched.

Next: Liskov Substitution and Interface Segregation.

---

<!-- _class: divider -->

# Part 2
## SOLID: Liskov Substitution and Interface Segregation

Session 2 of 4

---

## Liskov Substitution Principle (LSP)

<div class="cols wide">
<div>

![List<Bird> calls fly() on each element, Sparrow and Duck succeed, Penguin throws an exception](../assets/illustrations/lsp-substitution.svg)

</div>
<div>

You order a motorbike taxi, and any motorbike may arrive. The replacement must still be able to take you there.

<div class="term-box">
<b>Liskov Substitution Principle</b>: a subclass must be able to replace its superclass without making the program wrong. <code>Sparrow</code> and <code>Duck</code> can, <code>Penguin</code> cannot. <b>The replacement must still work.</b>
</div>

</div>
</div>

---

## Why Does This Matter?

Code that calls through the superclass type trusts the superclass's promise. If one subclass breaks that promise, the program fails far away from that subclass.

<div class="term-box">
The compiler does not reject it. The error only appears when the program runs, perhaps in front of a user.
</div>

---

## Common Mistake: A Subclass Breaks the Promise

<div class="warn-box">
<b>Wrong:</b> <code>Penguin extends Bird</code> overrides <code>fly()</code> to throw an <code>UnsupportedOperationException</code>.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error as soon as b turns out to be a Penguin
}
```

**Correct:** `Penguin` has no `fly()` at all (next slide). The code above assumes every `Bird` can `fly()`.

---

## The Fix: Separate the Ability to Fly

<div class="term-box">
Move <code>fly()</code> into a small interface <code>Flyable</code>. Only <code>Sparrow</code> and <code>Duck</code> implement it.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* does not implement Flyable */ }
```

The caller checks first: `if (b instanceof Flyable f) f.fly();`. This form gives a variable `f` of type `Flyable` without a cast. The pattern returns in Part 4 (`Shippable`).

---

## Interface Segregation Principle (ISP)

<div class="cols wide">
<div>

![Chargeable implemented by Phone and ElectricCar, two separate hierarchies](../assets/uml/p09-chargeable.png)

</div>
<div>

A TV remote that only needs to turn the TV on and off does not need 50 channel buttons.

<div class="term-box">
<b>Interface Segregation Principle</b>: small, focused interfaces, so classes are not forced to fill in methods that do not apply. <code>Chargeable</code> above: one method, two unrelated classes. <b>Small interface, one ability.</b>
</div>

</div>
</div>

---

## Why Does This Matter?

If `charge()` and `call()` are merged into one `Device` interface, `ElectricCar` must write `call()`. Two choices, both bad: leave it empty, or throw an exception.

<div class="term-box">
Code holding a <code>Device</code> assumes <code>call()</code> works. A fat interface also breaks Liskov.
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

1. Before: `ElectricCar` is forced to write `call()`.
2. After: two interfaces, one ability each.
3. `ElectricCar` only `implements Chargeable`.

---

## Exercise

Which principle is broken: Liskov Substitution or Interface Segregation?

1. `Penguin extends Bird`, but its `fly()` throws an exception.
2. `interface Printer` has `print()`, `scan()`, `fax()`. `SimplePrinter` can only `print()`.
3. `ReadOnlyList extends ArrayList`, but its `add()` throws an exception.

---

## Exercise Answer

1. **Liskov Substitution.** `Penguin` breaks the promise of `Bird`.
2. **Interface Segregation.** `SimplePrinter` is forced to fill in `scan()` and `fax()`.
3. **Liskov Substitution.** Users of `ArrayList` assume `add()` always works.

---

## Part 2 Summary

- Liskov Substitution: the replacement must still work.
- Interface Segregation: small interface, one ability.
- A fat interface forces fake methods, and that breaks Liskov.

Next: Dependency Inversion, then `ArrayList` and `Map`.

---

<!-- _class: divider -->

# Part 3
## SOLID: Dependency Inversion, and Collections

Session 3 of 4

---

## Dependency Inversion Principle (DIP)

<div class="cols wide">
<div>

![OrderProcessor depending directly on MySqlDatabase, compared with depending on the Repository interface implemented by MySqlDatabase and MockRepository](../assets/illustrations/dip-invert-dependency.svg)

</div>
<div>

A cashier only knows the card machine, not the issuing bank. A card from any bank can be used.

<div class="term-box">
<b>Dependency Inversion Principle</b>: the class that runs the flow (<code>OrderProcessor</code>) depends on an interface, not on a real class (<code>MySqlDatabase</code>). <b>Depend on the interface, not the real class.</b>
</div>

</div>
</div>

---

## Why Does This Matter?

An `OrderProcessor` that uses `MySqlDatabase` directly is tied to MySQL. Switching databases or testing with a fake becomes hard.

<div class="term-box">
With a <code>Repository</code> interface, storage can change without changing <code>OrderProcessor</code>.
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
3. The code that creates `OrderProcessor` chooses the implementation.

---

## `ArrayList` and `Map`

`InMemoryRepository` must store many `Order` objects. A plain array is too rigid.

| | Array | `ArrayList` | `Map` |
|---|---|---|---|
| Size | fixed when created | grows automatically | grows automatically |
| How to fetch | by index | by index, `get(1)` | by key, `get("WHOLESALE")` |
| Best for | a known count | an ordered list | direct lookup by key |

<div class="term-box">
An element beyond the array size: <code>ArrayIndexOutOfBoundsException</code>. <b>ArrayList</b> grows automatically. <b>Map</b> looks up directly by key; <code>LinkedHashMap</code> keeps insertion order.
</div>

---

## Code Example: `ArrayList` and `Map`

```java
List<Order> orders = new ArrayList<>();
orders.add(order);                                   // no size limit

Map<String, DiscountPolicy> policies = new HashMap<>();
policies.put("WHOLESALE", new WholesaleDiscountPolicy());
DiscountPolicy policy = policies.get("WHOLESALE");   // direct, no loop
```

1. `List<Order>`: a list that only holds `Order`, grows through `add(...)`.
2. `Map<String, DiscountPolicy>`: key is the customer type, value is a policy (Part 1).
3. `get("WHOLESALE")` is direct. Missing key: the result is `null`.

---

## Exercise

1. `OrderProcessor` contains `private MySqlDatabase db = new MySqlDatabase();`. Which principle is broken?
2. Pick `ArrayList` or `Map`: a list of orders that is only printed in order.
3. Pick `ArrayList` or `Map`: customer data that is often looked up by customer number.

---

## Exercise Answer

1. **Dependency Inversion.** Receive a `Repository` through the constructor.
2. **`ArrayList`.** Only read in order.
3. **`Map`.** The customer number becomes the key.

---

## Part 3 Summary

- Dependency Inversion: depend on the interface, not the real class.
- `new` of a real class inside the class still breaks it.
- `ArrayList` for a list that grows, `Map` for lookup by key.

Next: all five principles in one case study.

---

<!-- _class: divider -->

# Part 4
## Case Study: All Five Principles in One System

Session 4 of 4

---

## One Order-Processing System

An online shop has physical orders (shipped) and digital orders (not shipped). Discounts differ by customer type.

- `Order`, with subclasses `PhysicalOrder` and `DigitalOrder`
- `Customer`, which has a customer type (regular, wholesale)
- `OrderProcessor`, the coordinator of the order flow
- `DiscountCalculator` and `DiscountPolicy`, which compute the discount
- `Repository`, which saves orders; `ReceiptPrinter`, which prints the receipt

---

## `Shippable`: LSP and ISP at Once

<div class="cols wide">
<div>

![Order, PhysicalOrder implementing Shippable, and DigitalOrder deliberately not implementing it](../assets/uml/p11-order-shippable.png)

</div>
<div>

`Shippable` only has `ship()` (Interface Segregation). `PhysicalOrder implements Shippable`; `DigitalOrder` deliberately does not, since a digital order is never shipped. No fake `ship()` (Liskov Substitution).

</div>
</div>

---

## `OrderProcessor`: Single Responsibility

<div class="cols wide">
<div>

![OrderProcessor delegating to DiscountCalculator, Repository, and ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

</div>
<div>

`OrderProcessor` does not compute, does not store, does not print. Those three jobs go to `DiscountCalculator`, `Repository`, and `ReceiptPrinter`.

</div>
</div>

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

Compute the discount, save, print the receipt. Each by another class.

---

## `DiscountPolicy` and `Repository`: OCP and DIP

<div class="cols wide">
<div>

![DiscountPolicy implemented by RegularDiscount and WholesaleDiscount, Repository implemented by InMemoryRepository and FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

</div>
<div>

The same pattern: one interface, many small implementations. A new discount type is just a new class (Open/Closed). `OrderProcessor` only knows `Repository`, so storage can be swapped freely (Dependency Inversion).

</div>
</div>

---

## Code Example: Adding a New `DiscountPolicy`

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

`DiscountCalculator` and `OrderProcessor` do not change. `DiscountCalculator` holds a `Map<String, DiscountPolicy>`, exactly the example from Part 3.

---

## Common Mistake: Forcing an Irrelevant Contract

<div class="warn-box">
<b>Wrong:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, to be "consistent".
</div>

**Correct:** `class DigitalOrder extends Order { }` without `Shippable`. `"N/A"` is a fake promise (Liskov), and an irrelevant method is forced in (Interface Segregation).

---

## Exercise

The shop wants to add `StudentDiscountPolicy` (a 10% discount for students).

1. How many new classes need to be added?
2. Does `DiscountCalculator` or `OrderProcessor` need to change?
3. Which SOLID principle makes that possible?

---

## Exercise Answer

1. **One new class:** `StudentDiscountPolicy implements DiscountPolicy`.
2. **No.** The new class is registered in `main`, not inside either of them.
3. **Open/Closed.** Extended through a new class, old code unchanged.

---

## Part 4 Summary

- Small `Shippable`: Interface Segregation, and it preserves Liskov.
- `OrderProcessor` only coordinates: Single Responsibility.
- `DiscountPolicy`: Open/Closed. `Repository`: Dependency Inversion.

---

## Meeting 11 Summary

- SOLID: one class one job; new class, not old code changed; the replacement must still work; small interface; depend on the interface.
- `ArrayList` for data that grows, `Map` for lookup by key.
- The order-processing system shows all five principles working together.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, Collections chapter; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

The programming exercises for this material are in the Object-Oriented Programming Practicum jobsheet (RTI253008), Meeting 11

---

## Assignment 1: Single Responsibility and Open/Closed

**SRP.** `OrderService` validates orders, calculates shipping cost, saves to a file, and sends email.

1. Write four replacement classes, one job each, with one main method apiece.
2. The shipping-cost rule changes. Which class is touched after the split?

**OCP.** `ShippingCalculator.cost(order)` holds an `if` for `"JNE"`, `"POS"`, `"GOJEK"`. `"SICEPAT"` will be added.

3. Write `interface ShippingMethod { int cost(Order order); }` and the class `SicepatShipping`.
4. Write the line in `main` that picks `SicepatShipping` without changing `ShippingCalculator`.

---

## Assignment 2: Liskov Substitution and Interface Segregation

**LSP.** `Square extends Rectangle`, and its `setWidth(w)` also changes the height.

1. `Rectangle r = new Square(); r.setWidth(4); r.setHeight(5);` then print `r.area()`. What is expected, what is printed?
2. Fix it: both `implements Shape { double area(); }`. Draw the diagram on paper.

**ISP.** `interface Worker` has `work()`, `eat()`, `sleep()`. A robot neither eats nor sleeps.

3. Split `Worker` into small interfaces. Write each interface declaration.
4. Write the `implements` lines for `RobotWorker` and `HumanWorker`.

---

## Assignment 3: Dependency Inversion and Collections

**DIP.** `NotificationService` holds `private EmailSender sender = new EmailSender();`. The shop wants to send through WhatsApp as well.

1. Write `interface MessageSender { void send(String to, String text); }`.
2. Rewrite `NotificationService` so it receives a `MessageSender` through its constructor.

**Collections.** `NotificationService` records every sent message and finds them by recipient number.

3. Pick `ArrayList` or `Map`, then write the field declaration.
