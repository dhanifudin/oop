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

Meeting 9: **Abstract Classes and Interfaces**

Declaring a contract a subclass must fulfill

---

## What You Will Learn

- How to prevent a class from being instantiated directly when it only makes sense as a superclass
- How to require every subclass to provide its own behavior, caught by the compiler rather than discovered later
- How to declare a capability contract that applies across unrelated class hierarchies
- When to choose an abstract class and when to choose an interface for the same need
- Applying this to Bank Mini: `Account` becomes abstract, an `InterestBearing` interface for interest-bearing accounts

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 9.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Abstract classes, a superclass that must not be instantiated
- **Session 2 (50')**: Interfaces, a contract across class hierarchies
- **Session 3 (50')**: Applying an abstract class to Bank Mini
- **Session 4 (50')**: Applying an interface to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Abstract Classes

Session 1 of 4

---

## A Superclass That Must Not Be Instantiated

Imagine a class `Shape` as a general superclass for `Circle` and `Square`. Every shape definitely has an area, but the area formula differs depending on the kind of shape. There is no "generic shape" that makes sense to instantiate directly, `Shape` only makes sense as a superclass.

---

## Why Does This Matter?

Imagine a GUI framework used by hundreds of different applications: every component must know how to draw itself, but a "generic component" that does not yet know how to draw anything should never actually be created. Without a way to guarantee this, an incomplete object could slip through and be created, with the error only surfacing much later, once a user actually calls the method that was never implemented.

<div class="term-box">
An abstract class moves this mistake from runtime to compile time: a subclass that has not implemented all of its inherited abstract methods can never be instantiated at all, caught by the compiler rather than discovered later by an application's user. This is why abstract classes form the foundation of many major frameworks and libraries, from GUI toolkits to database drivers.
</div>

---

## Abstract Classes

<div class="term-box">
An <b>abstract class</b> (<code>abstract class</code>) must not be instantiated directly through <code>new</code>, it may only serve as a superclass. Declared with the <code>abstract</code> keyword on the class itself.
</div>

---

## Code Example: Declaring `abstract class Shape`

```java
public abstract class Shape {
    private String label;

    public Shape(String label) { this.label = label; }

    public abstract double area();
}
```

`new Shape("shape")` is rejected by the compiler, `Shape` may only be used as a superclass.

---

## Abstract Methods

![h:320 Shape as an abstract class, Circle and Square implementing area()](../assets/uml/p09-shape-abstract.png)

<div class="term-box">
An <b>abstract method</b> only declares its signature (name, parameters, return type), with no body at all. Every concrete subclass (one that can be instantiated) must provide its own body, or the compiler raises an error.
</div>

---

## Code Example: `Circle` Implementing `area()`

```java
public class Circle extends Shape {
    private double radius;

    public Circle(String label, double radius) {
        super(label);
        this.radius = radius;
    }

    @Override
    public double area() { return Math.PI * radius * radius; }
}
```

---

## Concrete Subclass vs Abstract Class

<div class="warn-box">
A subclass of an abstract class remains abstract itself (and cannot be instantiated) as long as it has not implemented every one of its inherited abstract methods. Only a subclass that has implemented all of them becomes a concrete class.
</div>

<div class="tip-box">
An abstract class may still have ordinary methods (with a full body) alongside its abstract methods, exactly like an ordinary superclass. A subclass inherits those ordinary methods as-is, just like the inheritance already covered before.
</div>

---

## Common Mistake: Forgetting to Implement an Abstract Method

<div class="warn-box">
<b>Wrong:</b> writing <code>class Square extends Shape</code> without overriding <code>area()</code>, thinking this is enough since <code>Square</code> "obviously has an area".
</div>

**Correct:** the compiler raises the error `Square is not abstract and does not override abstract method area()`. `Square` remains abstract (cannot be instantiated) until `area()` is actually implemented.

---

## Exercise

Given `abstract class Shape` with abstract method `area()`. Class `Triangle extends Shape` does not override `area()` at all.

Can `new Triangle("triangle", 3, 4)` be compiled? Explain.

---

## Exercise Answer

**No, it cannot.** `Triangle` has not implemented the abstract method `area()` inherited from `Shape`, so `Triangle` automatically remains abstract as well. The compiler rejects instantiating any abstract class, including `Triangle`, until `area()` is implemented.

---

## Part 1 Summary

- An abstract class must not be instantiated directly, it may only serve as a superclass.
- An abstract method only declares its signature; a concrete subclass must implement its body.
- A subclass that has not implemented all of its inherited abstract methods remains abstract itself.

Next: Part 2 covers interfaces, a similar contract that applies across class hierarchies that are not related at all.

---

<!-- _class: divider -->

# Part 2
## Interfaces

Session 2 of 4

---

## A Contract Across Class Hierarchies

Imagine `Phone` and `ElectricCar`, two classes that are not related at all (one a communication device, one a vehicle), yet both are "chargeable". There is no sensible single superclass that could hold this capability through ordinary inheritance.

<div class="term-box">
An <b>interface</b> declares a method contract (a signature with no body) that any class stating <code>implements</code> against it must fulfill, without requiring an <code>extends</code> relationship at all.
</div>

---

## Why Does This Matter?

Imagine a large team building a payment system: one team writes the code that processes payments, another team writes the credit card implementation, yet another writes the e-wallet implementation, and there is also a team writing automated tests. Without a clear contract, these three teams would have to constantly coordinate every time one part changes even slightly.

<div class="term-box">
An interface lets the team processing payments depend only on the contract (which methods are available), not on the concrete implementation. The implementation can change, be added to, or even be swapped for a fake version for testing (called a mock), without changing the code that uses it. This principle is what underlies one of the SOLID principles, the Dependency Inversion Principle, covered further in Meeting 11.
</div>

---

## Code Example: `Phone` Implementing `Chargeable`

```java
public interface Chargeable {
    void charge();
}

public class Phone implements Chargeable {
    private String model;

    public Phone(String model) { this.model = model; }

    @Override
    public void charge() { System.out.println(model + " is charging"); }
}
```

---

## Two Unrelated Classes, One Contract

![h:300 Chargeable implemented by Phone and ElectricCar, two separate hierarchies](../assets/uml/p09-chargeable.png)

`Phone` and `ElectricCar` share no superclass other than `Object`, yet both must provide `charge()` since both state `implements Chargeable`.

---

## One Class, Many Interfaces

<div class="term-box">
Unlike an abstract class (a class may only <code>extends</code> one superclass), a class may <code>implements</code> many interfaces at once. An interface is well suited to an extra capability that applies across a variety of different class hierarchies.
</div>

<div class="warn-box">
A class that states <code>implements</code> against an interface must implement every method inside it. Missing even one causes the compiler to raise an error.
</div>

---

## Abstract Class vs Interface

| | Abstract Class | Interface |
|---|---|---|
| Keyword | `extends` | `implements` |
| Count per class | Only one | May have many at once |
| Ordinary attributes and methods | May have them | No (only a method contract) |
| Well suited for | A superclass that makes sense for every subclass | A capability across a variety of class hierarchies |

---

## Common Mistake: Using `extends` for an Interface

<div class="warn-box">
<b>Wrong:</b> writing <code>class Phone extends Chargeable</code>, thinking an interface is treated the same as an ordinary superclass.
</div>

**Correct:** a class states its relationship to an interface through `implements`, not `extends`. `extends Chargeable` causes a compile error, since `Chargeable` is not a class that can be inherited from through ordinary inheritance.

---

## Exercise

For each need below, determine which fits better, an **abstract class** or an **interface**:

1. `Vehicle` as a general superclass for `Car`, `Motorcycle`, and `Truck`, with a shared attribute `speed`.
2. The capability "comparable" (`compareTo()`), applied to `Student`, `Product`, and `Invoice`, three unrelated classes.

---

## Exercise Answer

1. **Abstract class.** `Car`, `Motorcycle`, and `Truck` are indeed related through `Vehicle`, and need a shared attribute (`speed`) that an interface cannot declare.
2. **Interface.** `Student`, `Product`, and `Invoice` are not related at all; each still needs its own superclass, and implementing an interface does not restrict that.

---

## Part 2 Summary

- An interface declares a method contract with no body, which must be fulfilled through `implements`.
- A class may implement many interfaces at once, unlike an abstract class, which may only be extended from one.
- An interface fits a capability across unrelated class hierarchies; an abstract class fits a superclass that makes sense for every subclass.

Next: Part 3 applies an abstract class to Bank Mini's `Account`.

---

<!-- _class: divider -->

# Part 3
## Applying an Abstract Class to Bank Mini

Session 3 of 4

---

## Account Becomes an Abstract Class

Not a single plain `Account` has ever been created directly in Bank Mini, every instantiation has always been a `SavingsAccount` or a `CheckingAccount`. This is a sign that `Account` should become an abstract class, with an abstract method `monthlyFee()` that every account type must implement with its own fee amount.

![h:280 Account abstract with abstract method monthlyFee, SavingsAccount and CheckingAccount each implementing it](../assets/uml/p09-account-monthlyfee.png)

---

## Code Example: `monthlyFee()` Differing by Account Type

```java
public class SavingsAccount extends Account {
    @Override
    public double monthlyFee() { return 0; }
}

public class CheckingAccount extends Account {
    @Override
    public double monthlyFee() { return MONTHLY_FEE; }
}
```

`SavingsAccount` has no monthly fee, `CheckingAccount` carries a fixed fee, both must provide their own `monthlyFee()` since `Account` declares it as an abstract method.

---

## Common Mistake: Old Code Creating an Account Directly

<div class="warn-box">
<b>Wrong:</b> code from an earlier meeting that still writes <code>new Account("A1", owner, 0)</code> directly, without going through <code>SavingsAccount</code> or <code>CheckingAccount</code>.
</div>

**Correct:** once `Account` becomes abstract, that line of code fails to compile (`Account is abstract; cannot be instantiated`). This is a deliberate change: a plain `Account` was never really meant to exist in Bank Mini.

---

## Exercise

`BusinessAccount` (the independent assignment from Meeting 6) also `extends Account`, but has never implemented `monthlyFee()`.

What happens if someone tries `new BusinessAccount(...)` now, after `Account` has become abstract? Explain.

---

## Exercise Answer

**It fails to compile.** `BusinessAccount` inherits the abstract method `monthlyFee()` from `Account` but has not implemented it, so `BusinessAccount` remains abstract as well. The fix: add `@Override public double monthlyFee()` in `BusinessAccount` with the appropriate fee amount.

---

## Part 3 Summary

- `Account` becomes an abstract class with abstract method `monthlyFee()`, preventing a plain `Account` from ever being instantiated.
- `SavingsAccount` and `CheckingAccount` must each implement `monthlyFee()` according to their own fee rule.
- An older subclass that has not implemented `monthlyFee()` (such as `BusinessAccount`) remains abstract until fixed.

Next: Part 4 applies an interface to a capability that only some account types have.

---

<!-- _class: divider -->

# Part 4
## Applying an Interface to Bank Mini

Session 4 of 4

---

## InterestBearing, an Interface for Interest-Bearing Accounts

![h:300 Account as an abstract class, SavingsAccount implementing the InterestBearing interface](../assets/uml/p09-account-abstract.png)

Only an account that earns interest needs `applyInterest()`, `CheckingAccount` does not need it at all. Rather than adding that method to `Account` (which would mean every subclass inherits it, including ones for which it is irrelevant), this method is declared as its own interface `InterestBearing`, applied only to `SavingsAccount`.

---

## Code Example: `SavingsAccount` Implementing `InterestBearing`

```java
public interface InterestBearing {
    void applyInterest();
}

public class SavingsAccount extends Account implements InterestBearing {
    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }
}
```

---

## Common Mistake: Putting `applyInterest()` in `Account`

<div class="warn-box">
<b>Wrong:</b> adding <code>abstract void applyInterest()</code> directly to <code>Account</code>, thinking this is simpler than creating a new interface.
</div>

**Correct:** `CheckingAccount` earns no interest at all, forcing it to implement `applyInterest()` (whether with an empty body or one that throws an error) purely because it inherits `Account`. A separate `InterestBearing` interface avoids this, an irrelevant capability does not need to be forced onto every subclass.

---

## Exercise

`BusinessAccount` (the independent assignment from Meeting 6) is a business account with no interest at all.

Does `BusinessAccount` need to implement `InterestBearing`? Explain your reasoning.

---

## Exercise Answer

**No, it does not.** `InterestBearing` is only relevant for an account that genuinely earns interest. `BusinessAccount` earns no interest, forcing it to implement `InterestBearing` would mean providing an `applyInterest()` that never has any real meaning, exactly the common mistake just covered.

---

## Part 4 Summary

- Interface `InterestBearing` declares `applyInterest()`, applied only to an account that genuinely earns interest.
- `SavingsAccount` inherits `Account` through `extends` while also implementing `InterestBearing` through `implements`, two different kinds of contract at once.
- A capability irrelevant to every subclass fits better as a separate interface than being forced through a superclass.

---

## Meeting 9 Summary

- An abstract class prevents direct instantiation and requires a concrete subclass to implement its abstract methods.
- An interface declares a method contract with no body, applying across unrelated class hierarchies, and a class may implement many at once.
- Bank Mini uses both: `Account` becomes abstract through `monthlyFee()`, and `InterestBearing` serves as an extra interface for an interest-bearing account.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Creating Abstract Superclasses and Concrete Subclasses, Interfaces chapter

Oracle Java Tutorials: "Abstract Methods and Classes", "Interfaces"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 9

---

## Discussion

`SavingsAccount` now has two "contracts" at once: inheriting `Account` (an abstract class) through `extends`, and implementing `InterestBearing` (an interface) through `implements`. Explain in your own words the fundamental difference between these two kinds of contract, then give one example of a new capability (other than interest) that you think fits better as a new interface than being added directly to `Account`.
