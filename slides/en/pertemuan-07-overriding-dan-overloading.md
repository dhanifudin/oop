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

Meeting 7: **Overriding and Overloading**

Replacing inherited behavior, and giving one name to several ways of calling

---

## What You Will Learn

- How a subclass replaces the behavior of a method it inherits (overriding)
- How to reuse the old behavior through `super`, and lock it through `final`
- How to make the output of `println(object)` readable through `toString()`
- How to give one method name to several ways of calling it (overloading)
- The difference between overriding and overloading, applied to one case study

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 7.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Method overriding
- **Session 2 (50')**: `super`, `final`, and `toString()`
- **Session 3 (50')**: Method overloading
- **Session 4 (50')**: Case study, the library collection

---

<!-- _class: divider -->

# Part 1
## Method Overriding

Session 1 of 4

---

## The Problem: Every Vehicle Sounds the Same

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Sedan extends Vehicle { }
class Truck extends Vehicle { }
```

`Sedan` and `Truck` inherit `honk()` as-is. Both say "Beep!", even though a truck's horn should sound different.

---

## What Is Overriding?

Picture a family recipe. A child cooks a dish with the same name, but uses their own recipe.

<div class="term-box">
<b>Overriding</b> is rewriting an inherited method inside a subclass. Its name and parameters stay the same; only its body is different.
</div>

---

## Why Does This Matter?

A payment application has many kinds of methods: credit card, bank transfer, e-wallet. New kinds keep being added. Without overriding, the payment-processing code must be changed every time a new kind appears, and that change can break other kinds that already work.

<div class="term-box">
With overriding, each subclass carries its own behavior. Old code does not need to be touched. This principle is called the Open/Closed Principle, covered in Meeting 11.
</div>

---

## A Method's Full Name: The Signature

![h:200 Anatomy of a signature: honk(int times), the method name and parameter list, separate from visibility and return type](../assets/illustrations/method-signature-anatomy.svg)

<div class="term-box">
A <b>signature</b> is the method name plus its parameter list. Two methods are considered the same when their signatures are the same.
</div>

---

## Overriding in a Class Diagram

![h:260 Class diagram of Vehicle, Sedan, and Truck, with honk() appearing again in both subclasses](../assets/uml/p06-vehicle.png)

A method that appears again in a subclass box is overridden. `honk()` is written in `Vehicle`, then written again in `Sedan` and `Truck`.

---

## Code Example: Truck Overrides `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Truck extends Vehicle {
    @Override
    public String honk() { return "Tin tin!"; }   // new body
}
```

---

## Trace It Step by Step

```java
Truck truck = new Truck();
System.out.println(truck.honk());
```

1. Java looks at the object: a `Truck`.
2. Java looks for `honk()` in class `Truck`, and finds it.
3. `Truck`'s version is the one that runs.

Output: `Tin tin!`

---

## The `@Override` Annotation: A Guard Against Typos

```java
class Truck extends Vehicle {
    @Override
    public String hunk() { return "Tin tin!"; }   // typo
}
```

<div class="tip-box">
With <code>@Override</code>, the compiler reports an error right away, since <code>Vehicle</code> has no method <code>hunk()</code>. Without <code>@Override</code>, this mistake slips through and the truck still says "Beep!".
</div>

---

## The Object Decides, Not the Variable Type

```java
Vehicle v = new Truck();
System.out.println(v.honk());   // Tin tin!
```

Variable `v` is of type `Vehicle`, but the object inside it is a `Truck`. Java runs the version that belongs to the object.

<div class="tip-box">
This behavior is the foundation of the Polymorphism topic in Meeting 10.
</div>

---

## Common Mistake: Different Parameters Are Not an Override

<div class="warn-box">
<b>Wrong:</b> writing <code>public String honk(String mode)</code> in <code>Truck</code>, then assuming <code>Vehicle</code>'s <code>honk()</code> has been replaced.
</div>

**Correct:** the parameters differ, so the signature differs. That is a new method, not an override. Add `@Override` so the compiler catches this mistake.

---

## Exercise

`Vehicle.honk()` returns `"Beep!"`. `Truck` overrides it to return `"Tin tin!"`. Predict the output of this program:

```java
Vehicle a = new Vehicle();
Vehicle b = new Truck();
System.out.println(a.honk());
System.out.println(b.honk());
```

---

## Exercise Answer

```
Beep!
Tin tin!
```

Object `a` is a `Vehicle`, so `Vehicle`'s version runs. Object `b` is a `Truck`, so `Truck`'s version runs, even though its variable is of type `Vehicle`.

---

## Part 1 Summary

- Overriding rewrites an inherited method in a subclass, with the same signature.
- `@Override` makes the compiler check that the method really exists in the superclass.
- The version that runs is decided by the object, not by the variable's type.

Next: Part 2 covers how to reuse the old behavior, and how to lock it.

---

<!-- _class: divider -->

# Part 2
## super, final, and toString()

Session 2 of 4

---

## `super.method(...)`: The Old Recipe Plus One Ingredient

Sometimes a subclass does not want to replace all of the old behavior. It only wants to add a little, like using a parent's recipe and adding one ingredient.

<div class="term-box">
<code>super.methodName(...)</code> calls the superclass's version from inside the method that overrides it.
</div>

---

## Code Example: Calling `super.honk()`

```java
class Truck extends Vehicle {
    @Override
    public String honk() {
        return super.honk() + " (loud horn)";   // old result + addition
    }
}
```

Output of `new Truck().honk()`: `Beep! (loud horn)`

---

## Three Rules of Overriding

| Rule | Right | Wrong |
|---|---|---|
| The signature must be exactly the same | `honk()` becomes `honk()` | `honk()` becomes `honk(int times)` |
| The access modifier must not be narrower | `protected` becomes `public` | `public` becomes `private` |
| `private` and `final` methods cannot be overridden | an ordinary method | a method marked `final` |

If one rule is broken, the compiler reports an error.

---

## `final`: A Recipe That Must Not Be Changed

```java
class Vehicle {
    public final String plateFormat() { return "N 1234 AB"; }
}
```

<div class="term-box">
A method marked <code>final</code> cannot be overridden. Use it only when the behavior really must be the same in every subclass.
</div>

---

## The Problem: `println(object)` Output Is Hard to Read

```java
Sedan civic = new Sedan("Civic");
System.out.println(civic);   // Sedan@1b6d3586
```

`println` calls `toString()`, a method every class inherits from `Object`. Its default version only prints the class name and a hash code.

<div class="tip-box">
Since <code>toString()</code> is an inherited method, we are allowed to override it.
</div>

---

## Code Example: Overriding `toString()`

```java
class Vehicle {
    private String name;
    public Vehicle(String name) { this.name = name; }

    @Override
    public String toString() { return "Vehicle: " + name; }
}
```

Output of `System.out.println(new Vehicle("Civic"))`: `Vehicle: Civic`

---

## Common Mistake: Narrowing the Access Modifier

<div class="warn-box">
<b>Wrong:</b> <code>Vehicle</code> has <code>public String honk()</code>, then <code>Truck</code> writes <code>private String honk()</code>.
</div>

**Correct:** an override must be just as open, or more open. `public` may not become `protected` or `private`. The code above fails to compile.

---

## Exercise

Decide **valid** or **error** for each override below:

1. `Vehicle`: `public String honk()`. `Truck`: `protected String honk()`.
2. `Vehicle`: `public final String plateFormat()`. `Truck` rewrites `plateFormat()`.
3. `Truck`: `@Override public String honk() { return super.honk() + "!"; }`

---

## Exercise Answer

1. **Error.** `protected` is narrower than `public`.
2. **Error.** A `final` method cannot be overridden.
3. **Valid.** Its output is `Beep!!`, the result of `super.honk()` plus one exclamation mark.

---

## Part 2 Summary

- `super.method(...)` reuses the superclass's behavior, then the subclass adds its own part.
- Three override rules: same signature, access modifier not narrower, `private` and `final` cannot be overridden.
- Overriding `toString()` makes the output of `println(object)` readable.

Next: Part 3 covers overloading, the same name with different parameters.

---

<!-- _class: divider -->

# Part 3
## Method Overloading

Session 3 of 4

---

## The Problem: Method Names Keep Multiplying

```java
class Vehicle {
    public String honkOnce() { return "Beep!"; }
    public String honkTimes(int times) { return "Beep!".repeat(times); }
    public String honkLoud(boolean loud) { return loud ? "BEEP!" : "Beep!"; }
}
```

All three do the same thing, which is sounding the horn. Anyone using this class has to memorize three different names.

---

## What Is Overloading?

At a cashier, the single word "pay" works for cash, card, and QR. The cashier picks the way from what you hand over.

<div class="term-box">
<b>Overloading</b> is several methods with the same name but different parameter lists. The compiler picks the version from the arguments given.
</div>

---

## Code Example: `honk()` and `honk(int times)`

```java
class Vehicle {
    public String honk() { return "Beep!"; }

    public String honk(int times) {      // same name, different parameters
        return honk().repeat(times);
    }
}
```

---

## Trace It: Which Version Is Picked?

```java
Vehicle v = new Vehicle();
System.out.println(v.honk());    // no argument
System.out.println(v.honk(3));   // one int argument
```

1. `v.honk()` carries no argument, so the compiler picks `honk()`.
2. `v.honk(3)` carries one `int`, so the compiler picks `honk(int times)`.

Output: `Beep!` then `Beep!Beep!Beep!`

---

## Why Does This Matter?

Without overloading, printing to the screen would need `printString()`, `printInt()`, `printDouble()`, and so on. Programmers would have to remember a different name for each data type.

<div class="term-box">
Thanks to overloading, the single name <code>println(...)</code> is enough for every data type. The classes we write are also easier for others to use when they follow the same approach.
</div>

---

## Constructors Can Be Overloaded Too

```java
class Vehicle {
    private String name;
    private int wheels;

    public Vehicle(String name) { this(name, 4); }   // calls the constructor below
    public Vehicle(String name, int wheels) { this.name = name; this.wheels = wheels; }
}
```

`new Vehicle("Civic")` and `new Vehicle("Hino", 6)` are both valid. `this(...)` calls another constructor in the same class.

---

## Overriding vs Overloading

![h:300 Comparison of overriding and overloading](../assets/illustrations/override-vs-overload.svg)

Overriding replaces the body of an inherited method. Overloading adds a new version with different parameters.

---

## Common Mistake: Only the Return Type Differs

<div class="warn-box">
<b>Wrong:</b> writing <code>public String honk()</code> and <code>public int honk()</code> in the same class, then assuming both are a valid overload.
</div>

**Correct:** the return type is not part of the signature. Both methods have the same signature, so the compiler reports an error. What must differ is the parameter list.

---

## Exercise

In the same class, decide **valid overload** or **error**:

1. `honk()` and `honk(int times)`
2. `String getName()` and `int getName()`
3. `setScore(int score)` and `setScore(double score)`

---

## Exercise Answer

1. **Valid.** The number of parameters differs.
2. **Error.** The parameters are the same (none); only the return type differs.
3. **Valid.** The parameter types differ, `int` and `double`.

---

## Part 3 Summary

- Overloading: same method name, different parameter list.
- The compiler picks the version from the number and types of the arguments in the call.
- Constructors can be overloaded too; `this(...)` calls another constructor in the same class.

Next: Part 4 uses overriding and overloading on the library collection.

---

<!-- _class: divider -->

# Part 4
## Case Study: The Library Collection

Session 4 of 4

---

## Back to the Library

In Meeting 6, `Book`, `Dvd`, and `Magazine` inherited from `LibraryItem`. Three things are still unresolved:

1. Every item is loaned for 14 days, even though a DVD should be 7 days.
2. `describe()` only prints the title and year, not the pages or running time.
3. Extending a loan needs two ways: the default 7 days, or a chosen number of days.

Numbers 1 and 2 are solved with overriding. Number 3 is solved with overloading.

---

## Class Diagram: Who Overrides What

![h:300 LibraryItem with loanDays, describe, and two versions of extendLoan; Book, Dvd, and Magazine rewrite some of the methods](../assets/uml/p07-libraryitem-override.png)

`loanDays()` appears again in `Dvd`. `describe()` appears again in all three subclasses. `extendLoan` is written twice in `LibraryItem` with different parameters.

---

## Code Example: `Dvd` Overrides `loanDays()`

```java
class LibraryItem {
    public int loanDays() { return 14; }
}

class Dvd extends LibraryItem {
    @Override
    public int loanDays() { return 7; }   // DVD only
}
```

---

## Code Example: `describe()` with `super`

```java
class LibraryItem {
    public String describe() { return title + " (" + year + ")"; }
}

class Book extends LibraryItem {
    @Override
    public String describe() { return super.describe() + ", " + pages + " pages"; }
}
```

Output for the book Dune: `Dune (1965), 412 pages`

---

## Code Example: Two Versions of `extendLoan`

```java
class LibraryItem {
    private int dueInDays = 14;

    public void extendLoan() { extendLoan(7); }            // no number: add 7 days
    public void extendLoan(int days) { dueInDays += days; }
}
```

`item.extendLoan()` adds 7 days. `item.extendLoan(3)` adds 3 days.

---

## Trace It: One Collection, Different Output

```java
LibraryItem[] items = { new Book("Dune", 1965, 412), new Dvd("Inception", 2010, 148) };
for (LibraryItem item : items) {
    System.out.println(item.describe() + ": " + item.loanDays() + " days");
}
```

```
Dune (1965), 412 pages: 14 days
Inception (2010), 148 min: 7 days
```

The loop code is unchanged from Meeting 6. Each object runs its own version.

---

## Common Mistake: An Overload Mistaken for an Override

<div class="warn-box">
<b>Wrong:</b> <code>Dvd</code> writes <code>public int loanDays(int extra) { return 7; }</code> without <code>@Override</code>, then wonders why a DVD is still loaned for 14 days.
</div>

**Correct:** the parameters differ, so that is an overload, not an override. The loop calls `loanDays()` with no argument, and that version still belongs to `LibraryItem`. `@Override` would have caught this mistake.

---

## Exercise

1. Complete the code so a magazine is loaned for 3 days:

```java
class Magazine extends LibraryItem {
    ________
    public int loanDays() { return ___; }
}
```

2. Which version of `extendLoan` is picked for `item.extendLoan()` and `item.extendLoan(5)`?

---

## Exercise Answer

```java
class Magazine extends LibraryItem {
    @Override
    public int loanDays() { return 3; }
}
```

`item.extendLoan()` picks the version with no parameter. `item.extendLoan(5)` picks the `extendLoan(int days)` version.

---

## Part 4 Summary

- `Dvd` overrides `loanDays()`, so a DVD is loaned for 7 days without changing `LibraryItem`.
- `describe()` in a subclass uses `super.describe()`, then adds its own data.
- `extendLoan()` and `extendLoan(int days)` are overloads: one name, two ways of calling.

---

## Meeting 7 Summary

| | Overriding | Overloading |
|---|---|---|
| Written in | the subclass | the same class |
| Signature | exactly the same | same name, different parameters |
| What decides the version | the actual object | the arguments in the call |
| When it is decided | while the program runs | at compile time |

`@Override` guards against typos, `super.method(...)` reuses the old behavior, `final` locks a method.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Inheritance and Polymorphism chapters

Oracle Java Tutorials: "Overriding and Hiding Methods", "Defining Methods" (overloading)

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 7

---

## Assignment: The Library Collection

The library adds `AudioBook`, a subclass of `LibraryItem`, with attributes `durationMinutes` and `narrator`. An audiobook is loaned for 10 days.

1. Which methods need to be overridden in `AudioBook`? Write their signatures.
2. Add one overload you think would be useful, then explain when that version is used.
3. Draw the class diagram of `AudioBook` on paper, complete with its arrow to `LibraryItem`.

---

## Assignment: Your Own Case Study

Reuse the class hierarchy from the Meeting 6 assignment (the application you chose yourself).

1. Pick one method in the superclass, then write its override in one of the subclasses. Use `super.method(...)` inside it.
2. Add one pair of overloads to one of the classes.
3. Update your class diagram on paper, then write the output you expect from one call to each method.
