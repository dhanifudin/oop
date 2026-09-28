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

- Why similar code belongs in one place instead of being copied repeatedly
- How a new class inherits an existing one, and the order in which it is constructed
- Access levels, multilevel inheritance up to `Object`, and what is not inherited
- Upcasting and downcasting: when a subclass object may be held as its superclass
- When a class should genuinely be a specific kind of another class, and when not
- Designing one small class hierarchy from start to finish

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 6.
</div>

---

## Today's Session Map

- **Session 1 (50')**: The concept of inheritance, superclass and subclass
- **Session 2 (50')**: Constructors, `super(...)`, `protected`, and multilevel inheritance
- **Session 3 (50')**: When inheritance should be used
- **Session 4 (50')**: Synthesis case study, designing a library collection hierarchy

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
<b>Inheritance</b> lets a class (called a <b>subclass</b>) inherit attributes and methods from another class (called a <b>superclass</b>), so the same code only needs to be written once in the superclass, then shared by all of its subclasses. A superclass is also called a <b>parent class</b> or <b>base class</b>; a subclass is also called a <b>child class</b> or <b>derived class</b>.
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

The keyword `extends` states this relationship in Java: `class Sedan extends Vehicle` means `Sedan` is a subclass of `Vehicle`, its superclass. In a UML class diagram this relationship is drawn as a hollow-triangle arrow (generalization) that always points from the subclass to the superclass, read as "`Sedan` is-a `Vehicle`".

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

## One Superclass Only

<div class="term-box">
Java allows only <b>one</b> <code>extends</code> per class (single inheritance). One superclass may have many subclasses (<code>Vehicle</code> is inherited by <code>Sedan</code>, <code>Truck</code>, <code>Bus</code>), but one subclass may not have two superclasses at once.
</div>

```java
class Sedan extends Vehicle, Truck { }   // compile error
```

<div class="tip-box">
The need to "combine capabilities from several sources" is solved through interfaces, the topic of Meeting 9.
</div>

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
- A class can `extends` only one superclass; one superclass may be inherited by many subclasses.

Next: Part 2 covers how a constructor works when a subclass is created.

---

<!-- _class: divider -->

# Part 2
## Constructors, super(...), and Access Modifiers

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

## What Is Not Inherited

<div class="term-box">
<b>Constructors are not inherited.</b> That is why every subclass writes its own constructor, then passes the superclass's data along through <code>super(...)</code>, rather than relying on <code>Vehicle</code>'s constructor "coming down" into <code>Sedan</code>.
</div>

<div class="warn-box">
<b><code>private</code> members cannot be accessed directly.</b> <code>Vehicle</code>'s <code>name</code> attribute still exists inside every <code>Sedan</code> object, but code in <code>Sedan</code> may not touch it directly: use a getter (<code>getName()</code>) or change it to <code>protected</code>, covered next.
</div>

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

## The `protected` Keyword

![h:320 Four access modifier levels in Java](../assets/illustrations/protected-visibility.svg)

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

## Inherited from Object: toString() and equals()

Because every class is rooted at `Object`, every object automatically has `toString()`, `equals()`, and `hashCode()` without writing them at all:

```java
Sedan civic = new Sedan("Civic");
System.out.println(civic);           // Sedan@1b6d3586
System.out.println(civic.toString()); // same, println calls toString()
```

The default `toString()` is just the class name plus a hash code, which is where the "odd" output above comes from. Giving it a more meaningful form means rewriting this inherited method, the topic of Meeting 7.

---

## Class Diagram: Employee, Manager, Director

![h:280 Employee as the superclass, with Manager and Director stacked below it](../assets/uml/p06-employee-multilevel.png)

`name` is marked `#` (protected) so `Manager` and `Director` can access it directly. `describe()` is marked `{final}`: this method is deliberately not allowed to be overridden, so its output format stays consistent across every kind of employee. A class can also be marked `final` (for example, `String`) so that it cannot be extended at all.

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
- Every class inherits `toString()`/`equals()` from `Object`; constructors are not inherited.

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

Next: Part 4 brings all of these concepts together in one case study, designing a library collection hierarchy from scratch.

---

<!-- _class: divider -->

# Part 4
## Synthesis Case Study: A Library Collection Hierarchy

Session 4 of 4

---

## From Requirements to a Hierarchy

A library stores books, DVDs, and magazines. All of them have a title, a publication year, a checked-out-or-not status, and the exact same check-out and return actions. Each also has its own extra data: page count for a book, running time for a DVD, issue number for a magazine.

Apply the "is-a" test from Part 3: a `Book` is a kind of `LibraryItem` (yes), a `Dvd` is a kind of `LibraryItem` (yes). By contrast, a `Library` owns many `LibraryItem` objects, it is not a kind of one, so that relationship is "has-a", not inheritance.

<div class="warn-box">
Shared attributes alone are no reason to use <code>extends</code>. Only once the "is-a" test sounds natural may that shared part be lifted into a superclass.
</div>

---

## Class Diagram: LibraryItem, Book, Dvd, Magazine

![h:280 LibraryItem as the superclass, with Book, Dvd, and Magazine as subclasses](../assets/uml/p06-libraryitem-hierarchy.png)

`title` and `year` are marked `#` (protected) so subclasses may use them directly. `checkOut()`, `returnItem()`, `loanDays()`, and `describe()` are written once in `LibraryItem`, then inherited as-is by all three subclasses. Each subclass also provides a getter for its extra attribute (`getPages()`, `getDurationMinutes()`, `getIssueNumber()`).

---

## Code Example: The LibraryItem Superclass

```java
class LibraryItem {
    protected String title;
    protected int year;
    private boolean available = true;

    public LibraryItem(String title, int year) { this.title = title; this.year = year; }
    public int loanDays() { return 14; }
}
```

---

## Code Example: Book Adds an Attribute

```java
class Book extends LibraryItem {
    private int pages;

    public Book(String title, int year, int pages) {
        super(title, year);  // the LibraryItem part is built first
        this.pages = pages;
    }
}
```

<div class="tip-box">
<code>Dvd</code> and <code>Magazine</code> follow exactly the same pattern: <code>super(title, year)</code> on the first line, then fill in their own attribute.
</div>

---

## Upcasting: A Subclass Object as a Superclass Object

<div class="term-box">
Because <code>Book</code> is-a <code>LibraryItem</code>, the statement <code>LibraryItem item = new Book("Dune", 1965, 412);</code> is valid. Storing a subclass object in a variable of the superclass type is called <b>upcasting</b>; it happens automatically with no extra syntax because it is always safe. Through a variable of type <code>LibraryItem</code> only <code>LibraryItem</code>'s members are visible: <code>item.loanDays()</code> is allowed, <code>item.getPages()</code> is not.
</div>

The reverse, `Book b = new LibraryItem("Dune", 1965);`, is a compile error: not every `LibraryItem` is a `Book`.

<div class="tip-box">
This is what lets one array or collection of type <code>LibraryItem</code> hold every kind of item at once. What happens when an inherited method is rewritten by a subclass is the topic of Polymorphism (Meeting 10).
</div>

---

## Downcasting: Back to the Subclass Type

<div class="term-box">
The opposite direction, from a variable of the superclass type to the subclass type, is called <b>downcasting</b>. It must be written explicitly with parentheses, because it is NOT always safe: the compiler cannot guarantee that the object behind the variable really is that subclass.
</div>

```java
LibraryItem item = new Book("Dune", 1965, 412);
Book book = (Book) item;                 // explicit downcast
System.out.println(book.getPages());     // 412

LibraryItem other = new Dvd("Inception", 2010, 148);
Book wrong = (Book) other;               // compiles, fails at run time
```

<div class="warn-box">
The last line throws a <code>ClassCastException</code> at run time, because the object is actually a <code>Dvd</code>. Always check first: <code>if (item instanceof Book) { Book b = (Book) item; }</code>. The shorter form (pattern matching) is covered in the Polymorphism topic (Meeting 10).
</div>

---

## Code Example: One Collection for Every Kind

```java
LibraryItem[] items = {
    new Book("Dune", 1965, 412),
    new Dvd("Inception", 2010, 148),
    new Magazine("Tempo", 2024, 12)
};
for (LibraryItem item : items) {
    System.out.println(item.title + ": " + item.loanDays() + " days");
}
```

All three output lines end with `14 days`, since every one of them uses the exact same inherited `loanDays()`.

---

## An Inherited Method May Not Fit Every Subclass

<div class="term-box">
<code>Dvd</code> inherits <code>loanDays()</code>, which always returns 14 days, even though the library wants DVDs to be loaned for only 7 days. Adding a <code>durationMinutes</code> attribute changes nothing about that. A new attribute alone is not enough: a subclass also needs a way to rewrite inherited behavior.
</div>

<div class="tip-box">
This is exactly what Meeting 7 solves through overriding: a subclass rewrites a superclass's method to give it different behavior, without changing <code>LibraryItem</code>'s code at all.
</div>

---

## Common Mistake: Accessing a Superclass's private Attribute from a Subclass

<div class="warn-box">
<b>Wrong:</b> <code>LibraryItem</code> declares <code>private String title</code>, then <code>Book</code> writes <code>return title + " (" + pages + " pages)"</code>. Compilation fails: <code>title has private access in LibraryItem</code>.
</div>

**Correct:** `private` members are inherited, but they may not be accessed directly from a subclass. Change it to `protected` (as in the diagram), or provide a `getTitle()` getter in `LibraryItem` and call that from `Book`.

---

## Exercise

1. Write the complete `Magazine` class: it inherits `LibraryItem`, adds an `issueNumber` attribute, and has a constructor that calls `super(...)`.
2. `Library` and `LibraryItem`: is the relationship **is-a** or **has-a**? Explain briefly.
3. `LibraryItem item = new Magazine("Tempo", 2024, 12);` May `item.loanDays()` be called? May `item.getIssueNumber()` be called? How can it be called safely?

---

## Exercise Answer

```java
class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(String title, int year, int issueNumber) {
        super(title, year);
        this.issueNumber = issueNumber;
    }
}
```

**has-a**: a `Library` owns many `LibraryItem` objects, it is not a special kind of one. `item.loanDays()` is allowed, since it is a `LibraryItem` member; `item.getIssueNumber()` is not, since a `Magazine` member is not visible through a variable of type `LibraryItem`. Safe way: `if (item instanceof Magazine) { ((Magazine) item).getIssueNumber(); }`.

---

## Part 4 Summary

- Test "is-a" first, then `extends`: `Book`, `Dvd`, and `Magazine` pass, `Library` does not.
- Shared members are written once in the superclass; a subclass only adds its own through a constructor that calls `super(...)`.
- A subclass object may be stored in a variable or collection of the superclass type; only the superclass's members are visible through that variable. Upcasting is automatic and always safe; downcasting is explicit and must be guarded by `instanceof`.
- An inherited method may not fit every subclass; rewriting its behavior is the topic of Meeting 7 (overriding).

---

## Meeting 6 Summary

- Inheritance makes a subclass inherit its superclass's attributes and methods through `extends`, avoiding code duplication.
- A subclass's constructor always calls its superclass's constructor first through `super(...)`, which must be the first line.
- `protected` opens access to a subclass across packages; inheritance can stack in multiple levels, rooted at `Object`.
- Java allows only one superclass; constructors are not inherited; upcasting is automatic, downcasting is explicit and guarded by `instanceof`.
- Choose inheritance only for a genuinely natural "is-a" relationship; the `LibraryItem` hierarchy shows all of these concepts working together in one design.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Inheritance chapter

Oracle Java Tutorials: "Inheritance", "The Object Class", "Using the Keyword super"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 6

---

## Assignment: The Library Collection Hierarchy

The library wants to add audiobooks, `AudioBook`, which have a `durationMinutes` (like `Dvd`) as well as a `narrator` (the reader's name). Should it be `AudioBook extends Book`, `AudioBook extends Dvd`, or `AudioBook extends LibraryItem` directly?

Apply the "is-a" question, write down your choice with its reasoning, then draw the class diagram on paper: state which attributes and methods are inherited, and which must be written in `AudioBook` itself.

---

## Assignment: Find Your Own Case Study

Think of one real application or system you have used (not a library, not Bank Mini), for example a ride-hailing app, a game, or a social media platform.

Find one superclass with two or three subclasses inside it that pass the "is-a" test. Draw its class diagram on paper (shared members in the superclass, extra members in each subclass, the correct generalization arrows), then point out one inherited method whose behavior should differ in one of the subclasses, as a lead-in to Meeting 7.
