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

Classes that are not yet complete, and contracts of capability

---

## What You Will Learn

- How to make a superclass whose objects may not be created (abstract class)
- How to require every subclass to fill in a method (abstract method)
- How to give the same capability to classes that are unrelated (interface)
- How to choose between an abstract class and an interface
- Applying both to one case study

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 9.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Abstract classes and abstract methods
- **Session 2 (50')**: Interfaces
- **Session 3 (50')**: Abstract class or interface?
- **Session 4 (50')**: Case study, the library collection

---

<!-- _class: divider -->

# Part 1
## Abstract Classes

Session 1 of 4

---

## The Problem: An Object That Makes No Sense

```java
class Shape {
    public double area() { return 0; }   // area of what shape?
}

Shape s = new Shape();
System.out.println(s.area());   // 0.0
```

A circle has an area. A square has an area. A "general shape" has no area formula, yet Java still lets its object be created.

---

## What Is an Abstract Class?

Picture a basic cake recipe with one step still blank: "fill to taste". That recipe cannot be cooked until the blank step is filled in.

<div class="term-box">
An <b>abstract class</b> is a class that is not yet complete. Its objects may not be created through <code>new</code>. It is used only as a superclass.
</div>

---

## Why Does This Matter?

A display library is used by hundreds of applications. Every component must know how to draw itself. Without abstract classes, a component that does not yet know how to draw could still be created, and the mistake would only show up when a user runs the application.

<div class="term-box">
An abstract class moves that mistake to compile time. The compiler rejects it, rather than a user discovering it.
</div>

---

## Abstract Methods: A Blank Step That Must Be Filled

<div class="term-box">
An <b>abstract method</b> has only a signature, with no body. Every subclass must fill it in through overriding. Until it is filled in, that subclass cannot be compiled.
</div>

An abstract method may only be written inside an abstract class.

---

## Code Example: `abstract class Shape`

```java
abstract class Shape {
    private String label;
    public Shape(String label) { this.label = label; }

    public String getLabel() { return label; }   // ordinary method, inherited
    public abstract double area();               // abstract method, no body
}
```

Now `new Shape("x")` is rejected by the compiler: `Shape is abstract; cannot be instantiated`.

---

## Abstract Classes in a Class Diagram

![h:260 Shape as an abstract class, Circle and Square filling in area()](../assets/uml/p09-shape-abstract.png)

The names of an abstract class and an abstract method are written in italics. `area()` appears again in `Circle` and `Square`, meaning both subclasses fill it in.

---

## Code Example: `Circle` Fills In `area()`

```java
class Circle extends Shape {
    private double radius;
    public Circle(String label, double radius) { super(label); this.radius = radius; }

    @Override
    public double area() { return 3.14 * radius * radius; }   // blank step filled in
}
```

---

## Trace It Step by Step

```java
Shape s = new Circle("small circle", 2);
System.out.println(s.getLabel() + ": " + s.area());
```

1. The object is a `Circle`. Its variable may be of type `Shape`.
2. `getLabel()` is not written in `Circle`, so the version inherited from `Shape` runs.
3. `area()` is written in `Circle`, so `Circle`'s version runs: 3.14 x 2 x 2.

Output: `small circle: 12.56`

---

## Common Mistake: Forgetting to Fill In an Abstract Method

<div class="warn-box">
<b>Wrong:</b> writing <code>class Square extends Shape { }</code> without filling in <code>area()</code>.
</div>

**Correct:** the compiler reports the error `Square is not abstract and does not override abstract method area()`. Fill in `area()` in `Square`, and the class becomes usable.

---

## Exercise

`Shape` is an abstract class with an abstract method `area()`. Decide **valid** or **error**:

1. `Shape s = new Shape("x");`
2. `class Square extends Shape { }` (without `area()`)
3. `Shape s = new Circle("c", 1);`

---

## Exercise Answer

1. **Error.** An object of an abstract class may not be created.
2. **Error.** `Square` has not filled in the abstract method `area()`.
3. **Valid.** The object is a `Circle`, a complete class. Its variable may be of type `Shape`.

---

## Part 1 Summary

- An abstract class is not yet complete: its objects may not be created, it is used only as a superclass.
- An abstract method has no body; every subclass must fill it in.
- An abstract class may still have attributes, constructors, and ordinary methods.

Next: Part 2 covers interfaces, a contract for classes that are unrelated.

---

<!-- _class: divider -->

# Part 2
## Interfaces

Session 2 of 4

---

## The Problem: Unrelated, Yet Sharing One Capability

```java
class Phone { }         // communication device
class ElectricCar { }   // vehicle
```

A phone and an electric car can both be charged. They have no sensible superclass: a phone is not a vehicle, a car is not a communication device.

---

## What Is an Interface?

Picture a USB-C port. Anything that has that port can be charged with the same charger, whether it is a phone, a laptop, or a lamp.

<div class="term-box">
An <b>interface</b> is a list of methods with no bodies. A class that declares <code>implements</code> promises to fill in all of those methods.
</div>

---

## Why Does This Matter?

One team writes the payment-processing code. Another team writes credit cards, another writes e-wallets. Without a clear contract, every small change forces all teams to coordinate again.

<div class="term-box">
With an interface, the processing code only needs to know the contract. Implementations may be added or replaced without changing the code that uses them. This principle returns in Meeting 11.
</div>

---

## Code Example: `Chargeable` and `Phone`

```java
interface Chargeable {
    void charge();   // no body
}

class Phone implements Chargeable {
    @Override
    public void charge() { System.out.println("Phone is charging"); }
}
```

---

## Interfaces in a Class Diagram

![h:280 Chargeable implemented by Phone and ElectricCar](../assets/uml/p09-chargeable.png)

An interface's name is also written in italics. The difference is in the arrow: it is dashed, pointing from the class to the interface it implements.

---

## Trace It: One Contract, Two Classes

```java
Chargeable[] devices = { new Phone(), new ElectricCar() };
for (Chargeable d : devices) {
    d.charge();
}
```

```
Phone is charging
Car is charging
```

The loop only knows `Chargeable`. Each object runs its own `charge()`.

---

## One Class, Many Interfaces

A person has only one birth mother, but may hold many skill certificates.

```java
class Phone implements Chargeable, Connectable {
    @Override public void charge() { System.out.println("Phone is charging"); }
    @Override public void connect() { System.out.println("Phone is online"); }
}
```

A class may `extends` only one superclass, but may `implements` many interfaces.

---

## Common Mistake: Using `extends` for an Interface

<div class="warn-box">
<b>Wrong:</b> writing <code>class Phone extends Chargeable</code>.
</div>

**Correct:** a class uses `implements` for an interface, and `extends` for a superclass. The code above fails to compile.

---

## Exercise

`Chargeable` has a method `charge()`. `Connectable` has a method `connect()`. Decide **valid** or **error**:

1. `class Laptop extends Chargeable { ... }`
2. `class Laptop implements Chargeable { }` (without `charge()`)
3. `class Laptop implements Chargeable, Connectable` with `charge()` and `connect()` filled in

---

## Exercise Answer

1. **Error.** An interface is used with `implements`, not `extends`.
2. **Error.** `Laptop` promises to fill in `charge()`, but has not done so.
3. **Valid.** One class may `implements` many interfaces, as long as every method is filled in.

---

## Part 2 Summary

- An interface is a list of methods with no bodies, used through `implements`.
- Unrelated classes can use the same interface.
- One class may `implements` many interfaces.

Next: Part 3 covers how to choose between an abstract class and an interface.

---

<!-- _class: divider -->

# Part 3
## Abstract Class or Interface?

Session 3 of 4

---

## Two Simple Questions

1. Are these classes one family, sharing the same attributes or methods? Use an **abstract class**.
2. Is this an extra capability that any class could have? Use an **interface**.

<div class="tip-box">
An abstract class answers "what kind of thing is this?". An interface answers "what can this do?".
</div>

---

## Abstract Class vs Interface

| | Abstract Class | Interface |
|---|---|---|
| Keyword in the subclass | `extends` | `implements` |
| Number per class | only one | may be many |
| Contents | attributes, ordinary methods, abstract methods | a list of methods with no bodies |
| Meaning | "is a kind of" | "can do" |

---

## Code Example: Both Used Together

```java
abstract class Vehicle {
    public abstract String honk();
}

class ElectricCar extends Vehicle implements Chargeable {
    @Override public String honk() { return "Beep!"; }
    @Override public void charge() { System.out.println("Car is charging"); }
}
```

---

## Trace It: One Object, Two Roles

```java
ElectricCar car = new ElectricCar();
Vehicle v = car;        // car is a Vehicle
Chargeable c = car;     // car can be charged
System.out.println(v.honk());
c.charge();
```

Output: `Beep!` then `Car is charging`. There is one object, but it can be held through two variable types.

---

## Common Mistake: A Special Capability Put in the Superclass

<div class="warn-box">
<b>Wrong:</b> adding <code>abstract void charge()</code> to <code>Vehicle</code>. As a result <code>Bicycle</code> must also fill in <code>charge()</code>, even though a bicycle has no battery.
</div>

**Correct:** a capability that only some subclasses have becomes an interface. Only the classes that need it declare `implements Chargeable`.

---

## Exercise

Choose **abstract class** or **interface**:

1. `Animal` as the parent of `Cat` and `Dog`, with a shared attribute `name`.
2. The capability "can be printed" (`print()`) for `Invoice`, `Photo`, and `Ticket`.
3. `Employee` as the parent of `Manager` and `Staff`, with a shared attribute `baseSalary`.

---

## Exercise Answer

1. **Abstract class.** `Cat` and `Dog` are one family and share the attribute `name`.
2. **Interface.** `Invoice`, `Photo`, and `Ticket` are unrelated; "can be printed" is an extra capability.
3. **Abstract class.** `Manager` and `Staff` are one family and share the attribute `baseSalary`.

---

## Part 3 Summary

- One family with shared attributes: abstract class.
- An extra capability for any class: interface.
- One class may `extends` one abstract class and `implements` several interfaces at once.

Next: Part 4 uses both on the library collection.

---

<!-- _class: divider -->

# Part 4
## Case Study: The Library Collection

Session 4 of 4

---

## Back to the Library

In the previous meetings, `Book`, `Dvd`, and `Magazine` inherited from `LibraryItem`. Three things are still unresolved:

1. `new LibraryItem("?")` can still be created, even though no "general item" sits on a shelf.
2. Each kind of item has its own late fee, but nothing requires it.
3. Only a DVD can be played. Books and magazines cannot.

Numbers 1 and 2 are solved with an abstract class. Number 3 is solved with an interface.

---

## Class Diagram: Abstract Class and Interface Together

![h:300 Abstract LibraryItem with lateFeePerDay, three subclasses filling it in, Dvd also implementing Playable](../assets/uml/p09-libraryitem-abstract.png)

`lateFeePerDay()` is abstract in `LibraryItem`, then filled in by all three subclasses. Only `Dvd` has a dashed arrow to `Playable`.

---

## Code Example: `LibraryItem` Becomes Abstract

```java
abstract class LibraryItem {
    protected String title;
    public LibraryItem(String title) { this.title = title; }

    public abstract int lateFeePerDay();   // must be filled in by each kind of item
}
```

---

## Code Example: `Book` Fills In `lateFeePerDay()`

```java
class Book extends LibraryItem {
    public Book(String title) { super(title); }

    @Override
    public int lateFeePerDay() { return 1000; }
}
```

`Dvd` follows the same pattern, with a fee of 5000 per day.

---

## Code Example: `Dvd` Is Also `Playable`

```java
interface Playable {
    void play();
}

class Dvd extends LibraryItem implements Playable {
    public Dvd(String title) { super(title); }
    @Override public int lateFeePerDay() { return 5000; }
    @Override public void play() { System.out.println("Playing " + title); }
}
```

---

## Trace It: The Late Fee of Each Item

```java
LibraryItem[] items = { new Book("Dune"), new Dvd("Inception") };
for (LibraryItem item : items) {
    System.out.println(item.title + ": " + item.lateFeePerDay());
}
```

```
Dune: 1000
Inception: 5000
```

Each object runs its own `lateFeePerDay()`. `new LibraryItem("?")` is now rejected by the compiler.

---

## Common Mistake: `play()` Put in `LibraryItem`

<div class="warn-box">
<b>Wrong:</b> adding <code>abstract void play()</code> to <code>LibraryItem</code>. As a result <code>Book</code> and <code>Magazine</code> must fill in <code>play()</code>, even though neither can be played.
</div>

**Correct:** `play()` goes in the interface `Playable`. Only `Dvd` declares `implements Playable`.

---

## Exercise

1. Complete the code so a magazine's fee is 500 per day:

```java
class Magazine ________ LibraryItem {
    public Magazine(String title) { super(title); }
    @Override
    public int lateFeePerDay() { return ___; }
}
```

2. Does `Magazine` need to declare `implements Playable`? Yes or no?

---

## Exercise Answer

```java
class Magazine extends LibraryItem {
    public Magazine(String title) { super(title); }
    @Override
    public int lateFeePerDay() { return 500; }
}
```

**No.** A magazine cannot be played, so it need not promise to fill in `play()`.

---

## Part 4 Summary

- `LibraryItem` becomes abstract: a "general item" object can no longer be created.
- `lateFeePerDay()` is abstract, so each kind of item must decide its own fee.
- `Playable` is an interface: only `Dvd` uses it.

---

## Meeting 9 Summary

| | Abstract Class | Interface |
|---|---|---|
| Used for | one family of classes | an extra capability |
| Keyword | `extends` (only one) | `implements` (may be many) |
| Contents | attributes, ordinary methods, abstract methods | a list of methods with no bodies |
| Today's examples | `Shape`, `LibraryItem` | `Chargeable`, `Playable` |

Both move mistakes to compile time: a method that has not been filled in is rejected by the compiler right away.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Polymorphism and Interfaces chapter

Oracle Java Tutorials: "Abstract Methods and Classes", "Interfaces"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 9

---

## Assignment: The Library Collection

The library adds `AudioBook` and a new rule: only books and magazines may have their loans renewed.

1. `AudioBook` is a kind of item that can be played. Write its class declaration line (`class AudioBook ...`).
2. Create an interface `Renewable` with one method. Which classes declare `implements Renewable`?
3. Draw the complete class diagram on paper, with the correct arrows for `extends` and `implements`.

---

## Assignment: Your Own Case Study

Reuse the class hierarchy from the previous meetings' assignment (the application you chose yourself).

1. Make its superclass an abstract class, with one abstract method every subclass must fill in.
2. Add one interface for a capability only some of the subclasses have.
3. Update your class diagram on paper, then explain in one sentence why that capability became an interface.
