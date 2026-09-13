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

Meeting 6: **Inheritance**

Passing a class's traits down to another class

---

## What You Will Learn

- Why similar code across several classes should be combined into one, rather than copied repeatedly
- How a new class inherits the capabilities of an existing class, and the step-by-step order in which it gets constructed
- The levels of access between classes, and how inheritance can stack in layers all the way up to the root of every class in Java
- When a class should genuinely become a specific kind of another class, and when it should not
- Applying all of these concepts to distinguish between account types in Bank Mini

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 6.
</div>

---

## Today's Session Map

- **Session 1 (50')**: The concept of inheritance, superclass and subclass
- **Session 2 (50')**: Constructors, `super(...)`, `protected`, and multilevel inheritance
- **Session 3 (50')**: When inheritance should be used
- **Session 4 (50')**: Applying inheritance to Bank Mini

---

<!-- _class: divider -->

# Part 1
## The Concept of Inheritance

Session 1 of 4

---

## Starting From Similar Classes

Imagine classes `Sedan` and `Truck` written separately, even though both share an attribute for a name and a method to get that name. Copying the same code into both classes makes a program hard to maintain: a change to one class has to be manually repeated in the other.

<div class="warn-box">
The same code, copied into many places, is one sign of a design that needs improving.
</div>

---

## Superclass and Subclass

<div class="term-box">
<b>Inheritance</b> lets a class (called a <b>subclass</b>) inherit attributes and methods from another class (called a <b>superclass</b>), so the same code only needs to be written once in the superclass, then shared by all of its subclasses.
</div>

---

## Why Does This Matter?

Imagine `Sedan` and `Truck` written separately for years, then a bug is found in their `getName()` method. A programmer fixes the bug in `Sedan`, but forgets to do the same in `Truck`, since the two are separate copies of the same code. Code that should be identical but slowly "drifts apart" because only some copies get updated is one of the most common sources of bugs in real projects.

<div class="term-box">
Inheritance eliminates this source of bugs by making sure the same code exists in only one place, the superclass. However, inheritance is a powerful tool that is also easy to misuse: forcing an "is-a" relationship that is not actually natural creates a rigid dependency between classes. Meeting 11 (SOLID) covers further discipline on when inheritance should be avoided.
</div>

---

## The Structure of Inheritance

![h:280 Sedan and Truck each inherit from Vehicle](../assets/illustrations/inheritance-tree.svg)

The keyword `extends` states this relationship in Java: `class Sedan extends Vehicle` means `Sedan` is a subclass of `Vehicle`, its superclass.

---

## Code Example: Superclass and Subclass

```java
class Vehicle {
    private String name;
    public String getName() { return name; }
}

class Sedan extends Vehicle {
    // automatically has getName(), without rewriting it
}
```

---

## What Gets Inherited?

![h:280 The Sedan subclass inherits every member of Vehicle, plus its own](../assets/illustrations/inherited-members.svg)

A subclass automatically has every attribute and method (that is not `private`) belonging to its superclass, plus any new attributes and methods written in the subclass itself.

---

## Common Mistake: Thinking Every Member Must Be Rewritten

<div class="warn-box">
<b>Wrong:</b> rewriting <code>getName()</code> inside <code>Sedan</code> even though its contents are exactly the same as <code>Vehicle</code>'s, thinking a subclass does not "really have" that method until it is written again itself.
</div>

**Correct:** `Sedan` automatically inherits `getName()` as-is the moment `extends Vehicle` is written. Rewriting it with no change at all only creates the very duplication inheritance was meant to solve.

---

## Exercise

Class `Bus` inherits from `Vehicle` (with attribute `name` and method `getName()`). `Bus` adds a new attribute `passengerCapacity` and a new method `boardPassenger()`.

State everything `Bus` automatically has without needing to rewrite it, and everything that must be written inside `Bus` itself.

---

## Exercise Answer

Automatically has (from `Vehicle`): attribute `name` and method `getName()`. Must be written inside `Bus` itself: attribute `passengerCapacity` and method `boardPassenger()`, since both are new and do not exist in `Vehicle`.

---

## Part 1 Summary

- Inheritance makes a subclass inherit its superclass's attributes and methods, avoiding code duplication between similar classes.
- The keyword `extends` states the subclass-superclass relationship in Java.
- Inherited members are automatically available in a subclass; only new members, or ones deliberately changed, need to be written.

Next: Part 2 covers how a constructor works when a subclass is created.

---

<!-- _class: divider -->

# Part 2
## Constructors, super(...), and Visibility

Session 2 of 4

---

## The Superclass Constructor: `super(...)`

![h:260 Class diagram for Vehicle, Sedan, and Truck](../assets/uml/p06-vehicle.png)

<div class="term-box">
A subclass's constructor must call its superclass's constructor, either explicitly through <code>super(...)</code> as its first line, or implicitly (Java calls the superclass's no-argument constructor if <code>super(...)</code> is not written).
</div>

<div class="tip-box">
<code>Sedan</code> and <code>Truck</code> override <code>honk()</code> so each subclass has its own horn sound, marked with the <code>@Override</code> annotation. The full rules for overriding are covered in Meeting 7.
</div>

---

## Code Example: Calling `super(...)`

```java
class Vehicle {
    public Vehicle(String name) { this.name = name; }
}

class Sedan extends Vehicle {
    public Sedan(String name) {
        super(name);  // must be the first line
    }
}
```

---

## Execution Order When super(...) Chains

![h:260 The order in which super(...) calls happen, and the order in which constructor bodies actually run](../assets/illustrations/constructor-chain.svg)

When `new Director(...)` is called, `super(...)` propagates upward first, all the way to `Employee`. Only after that does each constructor body actually run, starting with `Employee`, then `Manager`, and finally `Director`.

---

## Why This Order Matters

<div class="term-box">
This order guarantees the superclass's part is already fully built before the subclass adds its own part. A subclass's constructor never has to worry about accessing a superclass part that is not yet ready.
</div>

<div class="warn-box">
A call to <code>super(...)</code>, if written, must always be the first statement inside a constructor. Java raises a compile error if <code>super(...)</code> is placed after another statement.
</div>

---

## The `protected` Keyword and Multilevel Inheritance

![h:320 Four visibility levels in Java](../assets/illustrations/protected-visibility.svg)

<div class="term-box">
<code>protected</code> sits between default (same package only) and <code>public</code>: a member marked <code>protected</code> is accessible to a subclass, even one in a different package.
</div>

---

## Code Example: Accessing a `protected` Member

```java
class Employee {
    protected String name;
}

class Manager extends Employee {
    public String greet() {
        return "Hello, " + name;  // accesses name directly, protected
    }
}
```

---

## Multilevel Inheritance

![h:280 Object as the root of every class, with Employee, Manager, and Director stacked below it](../assets/illustrations/multilevel-ladder.svg)

A subclass may itself be further derived into a superclass for yet another subclass. Every class in Java, without exception, is ultimately derived from the class `Object`, even though the words `extends Object` are never written explicitly.

---

## Class Diagram: Employee, Manager, Director

![h:280 Employee as the superclass, with Manager and Director stacked below it](../assets/uml/p06-employee-multilevel.png)

`name` is marked `#` (protected) so `Manager` and `Director` can access it directly. `describe()` is marked `{final}`: this method is deliberately not allowed to be overridden, so its output format stays consistent across every kind of employee.

---

## Common Mistake: Forgetting super(...) Must Come First

<div class="warn-box">
<b>Wrong:</b> writing another statement (for example, filling in its own attribute) before calling <code>super(...)</code> inside a subclass's constructor.
</div>

**Correct:** `super(...)`, if written, must always be the first line, with no exceptions. Java raises a compile error the moment this rule is broken, not merely a warning.

---

## Exercise

`Employee` has only one constructor: `Employee(String name, double baseSalary)`, with no no-argument constructor. `Manager` writes its constructor without calling `super(...)` at all.

What happens when this code is compiled? Explain why.

---

## Exercise Answer

**It fails to compile.** Without an explicit `super(...)`, Java automatically tries to call `Employee`'s no-argument constructor. Since `Employee` has no such constructor, compilation fails. `Manager` must call `super(name, baseSalary)` explicitly.

---

## Part 2 Summary

- A subclass's constructor always calls its superclass's constructor first through `super(...)`, either explicitly or implicitly.
- `super(...)`, if written, must be the first line; a superclass with no no-argument constructor forces it to be explicit.
- `protected` opens access to a subclass across packages; inheritance can stack in multiple levels, rooted at `Object`.

Next: Part 3 covers when inheritance should be used, and when it should be avoided.

---

<!-- _class: divider -->

# Part 3
## When Should Inheritance Be Used?

Session 3 of 4

---

## IS-A vs HAS-A

![h:280 A quick test: read the relationship, is it a better fit for is-a or has-a](../assets/illustrations/is-a-vs-has-a.svg)

<div class="warn-box">
Inheritance is often used incorrectly just because two classes happen to share a few attributes. Always test first whether the relationship is genuinely "is-a"; if it does not sound natural, a relationship ("has-a", covered in Meeting 4) is usually the better choice.
</div>

---

## Why Does This Matter?

Forcing inheritance onto a relationship that is really "has-a" creates a rigid dependency. A subclass inherits EVERY member of its superclass, including ones that are irrelevant or even confusing, and every change to the superclass automatically propagates to all of its subclasses, including ones that should not be affected at all.

<div class="term-box">
In a large application, misplaced inheritance makes a class hierarchy rigid and hard to change: adding a single new method to a superclass can silently affect dozens of subclasses that never actually needed it.
</div>

---

## Code Example: A Forced IS-A

```java
// Wrong: Car is not a kind of Engine, inheritance is forced
class Car extends Engine { ... }

// Correct: a relationship (composition), as covered in Meeting 4
class Car {
    private Engine engine;
}
```

---

## Common Mistake: Inheritance Just to Avoid Duplication

<div class="warn-box">
<b>Wrong:</b> making <code>Truck extends Sedan</code> purely because the two happen to have similar methods, even though a Truck is not a kind of Sedan.
</div>

**Correct:** similar code alone is not enough reason to choose inheritance. If the relationship is not genuinely "is-a", similar code is better solved another way (for example, a shared helper class), not by forcing a subclass-superclass hierarchy.

---

## Exercise

For each pair below, determine **is-a** or **has-a**:

1. `Sedan` and `Car`
2. `Car` and `GPS`
3. `Manager` and `Employee`
4. `Restaurant` and `Menu`

---

## Exercise Answer

1. **is-a**, `Sedan` is a specific kind of `Car`.
2. **has-a**, `Car` owns a `GPS`, it is not a kind of `GPS`.
3. **is-a**, `Manager` is a specific kind of `Employee` (as covered in Part 2).
4. **has-a**, `Restaurant` owns a `Menu`, it is not a kind of `Menu`.

---

## Part 3 Summary

- Before using inheritance, test first whether the relationship is genuinely "is-a"; if not, "has-a" is usually the better fit.
- Forced inheritance creates a rigid dependency: a subclass inherits every member of its superclass, relevant or not.
- Similar code alone is not sufficient reason to choose inheritance.

Next: Part 4 applies inheritance to `Account` in Bank Mini.

---

<!-- _class: divider -->

# Part 4
## Applying Inheritance to Bank Mini

Session 4 of 4

---

## SavingsAccount and CheckingAccount

![h:280 Account as the superclass, SavingsAccount and CheckingAccount as subclasses](../assets/uml/p06-account-hierarchy.png)

Both subclasses add their own attribute (`interestRate` and `overdraftLimit`) and their own new method (`printAccountType()`), while still inheriting `deposit()`, `withdraw()`, and `printInfo()` from `Account` as-is, none of them rewritten yet.

---

## Code Example: SavingsAccount Adds an Attribute

```java
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, Customer owner,
            double balance, double interestRate) {
        super(accountNumber, owner, balance);
        this.interestRate = interestRate;
    }
}
```

---

## An Inherited Method May Not Fit Every Subclass

<div class="term-box">
<code>CheckingAccount</code> inherits <code>withdraw()</code>, which only allows a withdrawal up to the available balance, even though <code>overdraftLimit</code> should let this account be withdrawn from beyond its balance. A new attribute alone is not enough: a subclass also needs a way to rewrite inherited behavior.
</div>

<div class="tip-box">
This is exactly what Meeting 7 solves through overriding: a subclass rewrites a superclass's method to give it different behavior, without changing <code>Account</code>'s or <code>Bank</code>'s code at all.
</div>

---

## Common Mistake: Forgetting to Pass Data Through super(...)

<div class="warn-box">
<b>Wrong:</b> writing a <code>SavingsAccount</code> constructor that only fills in <code>interestRate</code>, without calling <code>super(accountNumber, owner, balance)</code>, hoping the three inherited attributes still get filled in correctly.
</div>

**Correct:** without a `super(...)` that passes along the real values, `accountNumber`, `owner`, and `balance` silently stay empty (their default values), not an error that is immediately visible. `SavingsAccount` must pass all three through `super(...)`.

---

## Exercise

`CheckingAccount` adds an attribute `overdraftLimit`, following the same pattern as `SavingsAccount`.

Write the correct constructor signature (name and parameter list) for `CheckingAccount`, complete with its `super(...)` call.

---

## Exercise Answer

```java
public CheckingAccount(String accountNumber, Customer owner,
        double balance, double overdraftLimit) {
    super(accountNumber, owner, balance);
    this.overdraftLimit = overdraftLimit;
}
```

---

## Part 4 Summary

- `SavingsAccount` and `CheckingAccount` add their own attributes, while still inheriting every `Account` method as-is.
- A subclass's constructor must pass its superclass's data through `super(...)`, not fill it in separately itself.
- An inherited method may not fit every subclass; rewriting its behavior is the topic of Meeting 7 (overriding).

---

## Meeting 6 Summary

- Inheritance makes a subclass inherit its superclass's attributes and methods through `extends`, avoiding code duplication.
- A subclass's constructor always calls its superclass's constructor first through `super(...)`, which must be the first line.
- `protected` opens access to a subclass across packages; inheritance can stack in multiple levels, rooted at `Object`.
- Choose inheritance only for a genuinely natural "is-a" relationship; Bank Mini applies it through `SavingsAccount` and `CheckingAccount`.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Inheritance chapter

Oracle Java Tutorials: "Inheritance", "The Object Class", "Using the Keyword super"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 6

---

## Discussion

Look again at the `SavingsAccount` and `CheckingAccount` you just built: should either of them also have its own subclasses (for example, splitting `SavingsAccount` further into a fixed-interest kind and a tiered-interest kind)? Give one example of a subclass you think would make sense, along with its new attribute, or explain why further splitting is not needed for Bank Mini.
