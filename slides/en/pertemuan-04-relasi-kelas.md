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

Meeting 4: **Class Relationships**

How objects connect with one another to form a system

---

## What You Will Learn

- That an object can hold another object as part of itself
- Four strengths of relationship between classes, from the loosest to the tightest
- How an object's lifetime differs at each of these relationship strengths

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 4.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Dependency and association, relationships that do not own their object
- **Session 2 (50')**: Aggregation and composition, ownership relationships and their UML notation
- **Session 3 (50')**: References, null, and object arrays in memory
- **Session 4 (50')**: Applying all of these relationships to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Dependency and Association

Session 1 of 4

---

## From Simple Attributes to Object Attributes

So far, a class's attributes have always been simple types: `String`, `double`, `int`. In fact, a class's attribute can also be another class type, and a class can also use another class only briefly without ever storing it. A `Library` can hold many `Book` objects as its attribute; a `Car` can hold an `Engine` as its attribute; while a `Printer` only uses a `File` object briefly while printing, without ever storing it.

<div class="term-box">
When a class uses or holds another class, the two classes are said to have a <b>relationship</b> ("has-a"). This relationship differs from inheritance ("is-a"), which is only covered in Meeting 6.
</div>

---

## Why Does This Matter?

If `Printer` stored `File` as its attribute, when it should really only use it briefly, `Printer` becomes tied to that one `File` alone. Yet `Printer` should be able to print any file, at any time.

<div class="term-box">
A tie between classes like this is called <b>coupling</b>. The tighter the coupling, the harder it becomes to change one class without also having to change another. In a large program with hundreds of classes, this is one of the leading causes of code that is hard to maintain.
</div>

---

## Four Strengths of Relationship

![h:230 Four strengths of relationship between classes: dependency, association, aggregation, composition](../assets/illustrations/relation-strengths.svg)

All four describe how one class depends on another, ranging from merely being used briefly (dependency) to being owned for the entire lifetime of its object (composition). Part 1 covers the first two; Part 2 covers the two strongest.

---

## Dependency in Detail

The `Printer`/`File` illustration shows a dependency: `Printer` uses `File` only while one method is running, for example as the parameter `print(File document)`. Once the method finishes, `Printer` keeps no reference at all to that `File`.

<div class="term-box">
<b>Dependency</b> is the weakest relationship: the object being used only ever appears as a method parameter (or a local variable), never as a class attribute.
</div>

---

## Association in Detail

Unlike dependency, association stores a reference to another object as an attribute, such as `Driver` storing a reference to its own `Car`. Because it is stored as an attribute, this relationship persists for as long as the `Driver` object itself is alive, not just for the duration of one method call.

<div class="term-box">
In an association, both objects remain independent of each other: <code>Driver</code> can switch to a different <code>Car</code>, and the same <code>Car</code> can be used by a different <code>Driver</code>, without affecting either object's lifetime.
</div>

---

## Code Example: Dependency vs Association

```java
class Printer {
    void print(File document) {
        // document is used briefly: dependency
    }
}

class Driver {
    Car car;
    Driver(Car car) { this.car = car; }  // stored as an attribute: association
}
```

---

## Common Mistake: Storing What Should Only Be Brief

<div class="warn-box">
<b>Wrong:</b> the <code>File</code> parameter in method <code>print(File document)</code> gets stored into a field, <code>this.lastFile = document</code>, even though <code>Printer</code> never uses it again after printing finishes.
</div>

**Correct:** let `File` stay only a local parameter, not stored as a field. Unless `Printer` genuinely needs to remember it for later use, that would be a deliberate association, not an accident.

---

## Exercise

Classify each pair below as **dependency** or **association**, and explain your reasoning:

1. `OrderProcessor` receives a `Logger` as a parameter of method `process(Order order, Logger logger)`, and only uses it to record a single log line.
2. `Elevator` stores a reference to `ControlPanel` as its attribute for the entire lifetime of the `Elevator` object.
3. `ReportGenerator` receives a `DateFormatter` as a parameter of method `format(Date date, DateFormatter formatter)`, uses it once, then never again.
4. `MusicPlayer` stores a reference to the `Playlist` currently playing as its attribute.

---

## Exercise Answer

1. **Dependency**, `Logger` is only used briefly within one method call, never stored.
2. **Association**, `ControlPanel` is stored as an attribute, persisting for as long as `Elevator` is alive.
3. **Dependency**, the same as question 1: a parameter used briefly, not stored.
4. **Association**, `Playlist` is stored as `MusicPlayer`'s attribute.

---

## Part 1 Summary

- An object can be used briefly through a method parameter (dependency) or stored as an attribute (association).
- The strength of a relationship determines how tightly two classes are tied together; mistakenly turning a dependency into an association makes a class too tightly coupled.
- In an association, both objects remain independent: each can live and be replaced without affecting the other.

Next: Part 2 covers two even tighter relationships, ones where one object genuinely OWNS another object as part of itself.

---

<!-- _class: divider -->

# Part 2
## Aggregation, Composition, and UML Notation

Session 2 of 4

---

## From Merely Storing to Genuine Ownership

Association stores a reference to another object, but both objects remain fully independent, as with `Driver` and `Car` in Part 1. There is an even tighter has-a relationship: one object genuinely becomes part of another, so that the lifetimes of both are tied together. A `Library` holds many `Book` objects as its collection; a `Car` holds an `Engine` as its component. Both are ownership, but they differ in how tight that ownership is.

---

## Aggregation: Loose Ownership

In aggregation, the owner (`Library`) stores a reference to the part (`Book`), but that part can be created before it belongs to its owner, and can keep living independently after its owner is discarded. A `Book` can be moved to a different `Library`, or continue to exist even after its original `Library` has closed.

<div class="term-box">
<b>Aggregation</b>: the owner holds a collection of parts, but the parts do not fully depend on their owner to stay alive.
</div>

---

## Composition: Tight Ownership

In composition, the owner (`Car`) creates its own part (`Engine`) inside its constructor, and never shares a reference to it with the outside world. Once `Car` is discarded, the `Engine` that is part of it disappears along with it; there is no "orphan" `Engine` that keeps living on its own.

<div class="term-box">
<b>Composition</b>: the owner creates and fully controls its part; the part's lifetime is entirely tied to its owner's lifetime.
</div>

---

## Why Does This Matter?

Choosing composition or aggregation incorrectly can cause real trouble. Using composition when aggregation was really needed means something that should be shared (for example, the same `Book` shared across several `Library` branches) ends up being recreated over and over instead.

Conversely, using aggregation when composition was really needed means a part that should belong to only one owner can end up held and changed by other code elsewhere. The result is inconsistent data, values that differ when they should match. A bug like this is hard to trace, since its cause sits far from where the symptom appears.

---

## Difference in Object Lifetime

![h:280 Composition: the part disappears along with its owner. Association: the part keeps living even after its owner is gone](../assets/illustrations/whole-part-lifecycle.svg)

<div class="term-box">
In <b>composition</b>, the part is created inside its owner and never handed out to the outside world; when the owner is discarded, the part disappears along with it. In <b>association</b>, both objects are fully independent. <b>Aggregation</b> sits between the two, as already explained above.
</div>

---

## UML Notation: Reading a Relationship Diagram

| Relationship | Line notation | Example |
|---|---|---|
| Dependency | dashed, open arrow | `Printer` &#8674; `File` |
| Association | solid, open arrow | `Driver` &#8594; `Car` |
| Aggregation | solid, EMPTY diamond on the owner's side | `Library` &#9671;&#8212; `Book` |
| Composition | solid, FILLED diamond on the owner's side | `Car` &#9670;&#8212; `Engine` |

<div class="term-box">
The diamond is ALWAYS on the owner's side, never on the part's side.
</div>

---

## Multiplicity: How Many on Each Side

![h:300 Multiplicity on the Driver-Car association and the Library-Book aggregation](../assets/uml/p04-multiplicity.png)

Multiplicity at the end of a line states how many objects may be involved on that side. The number `1` means exactly one; `0..*` means zero or more. On `Driver` &#8594; `Car`, both sides are marked `1`: one `Driver` drives one `Car` at a time. On `Library` &#9671;&#8212; `Book`, the `Library` side is marked `1`, the `Book` side is marked `0..*`.

---

## Code Example: Composition vs Aggregation

```java
class Car {
    Engine engine;
    Car() { engine = new Engine(150); }  // creates its own: composition
}

class Library {
    Book[] books;
    Library(Book[] books) { this.books = books; }  // from outside: aggregation
}
```

<div class="tip-box">
A class that calls <code>new</code> to create its own part, like <code>Car</code>, is composition. A class that receives its part as a parameter, like <code>Library</code>, is aggregation.
</div>

---

## Common Mistake: The Diamond on the Wrong Side

<div class="warn-box">
<b>Wrong:</b> drawing the diamond on the <code>Book</code> (part) side, as though <code>Book</code> owned <code>Library</code>.
</div>

**Correct:** the diamond always sits on the `Library` (owner) side, matching whichever class stores the collection. Before drawing the diamond, reread the sentence: it should read "`Library` OWNS `Book`", not the other way around.

---

## Exercise

For each pair below, determine **aggregation** or **composition**, then determine which side the diamond is drawn on:

1. `Playlist` stores a list of `Song` objects that can be moved to a different `Playlist`.
2. `House` creates its own `Room` objects inside its constructor, and never shares them with the outside.
3. `Team` stores a list of `Player` objects that can be moved to a different `Team` at any time.
4. `Computer` creates its own `CPU` object inside its constructor.

---

## Exercise Answer

1. **Aggregation**, diamond on the `Playlist` side; `Song` can move without needing its old `Playlist`.
2. **Composition**, diamond on the `House` side; `Room` is created by `House` itself, never shared out.
3. **Aggregation**, diamond on the `Team` side; `Player` can move between `Team`s, as in the earlier object-lifetime illustration.
4. **Composition**, diamond on the `Computer` side; `CPU` is created by `Computer` itself inside its constructor.

---

## Part 2 Summary

- Aggregation: the owner stores a reference to its part, but the part can live independently (loose ownership).
- Composition: the owner creates its own part and never shares it out; the part's lifetime is entirely tied to its owner.
- UML notation: the diamond always sits on the owner's side, empty for aggregation, filled for composition; multiplicity at the end of a line states how many objects are involved.

Next: all of these relationships work through references, not copies of an object. Part 3 dives into how a reference actually works in memory.

---

<!-- _class: divider -->

# Part 3
## References, Null, and Object Arrays in Memory

Session 3 of 4

---

## A Reference, Not a Copy

![Two variables pointing to the same single object on the heap; a change through either one is visible through both](../assets/illustrations/stack-heap-alias.svg)

<div class="term-box">
As covered in Meeting 2, an object variable does not store the object itself, only a reference (address) to that object on the heap. This rule also applies to association, aggregation, and composition. When two variables point to the same object, changing its data through either one is visible through the other.
</div>

---

## Code Example: One Object, Two Variables

```java
Rectangle a = new Rectangle(10, 4);
Rectangle b = a;

System.out.println(a.getWidth());  // 10
System.out.println(b.getWidth());  // 10
```

<div class="tip-box">
<code>b = a</code> does not create a new object. <code>a</code> and <code>b</code> point to the exact same object on the heap, as shown in the illustration.
</div>

---

## Why Does This Matter?

Two variables pointing to the same object is called **aliasing**. Left unnoticed, aliasing can easily cause bugs. For example, a method receives an object through a parameter, then changes it just to try something out. Meanwhile, whoever called that method still holds the same reference, and does not expect the object to have changed too. A bug like this is hard to trace, since where the change happens sits far from where the symptom appears.

---

## An Empty Reference (null)

![A variable that does not yet point to any object holds the value null; calling a method on it always fails](../assets/illustrations/null-reference.svg)

<div class="term-box">
An object variable that has never been assigned, or has been deliberately cleared, holds the special value <code>null</code>: it does not yet point to any object.
</div>

---

## Why Null Is Dangerous

Calling a method on a variable holding `null` always produces a `NullPointerException`, since there is no actual object at the end of that reference.

<div class="term-box">
A method that can return <code>null</code> when data is not found (for example, a search method) MUST have its result checked by the caller before it is used; skipping that check is one of the most common causes of crashes in production applications.
</div>

---

## Object Arrays: Many Objects from One Class

![A single class producing many independent objects, held together in one array of references](../assets/illustrations/multiple-objects-array.svg)

A single class `Rectangle` can produce many independent objects, each with its own data. An array holds many REFERENCES to these objects at once, not the objects themselves.

---

## An Array of References: Slots Not Yet Filled

A newly created object array (for example, `new Account[10]`) holds 10 slots, but all of them are still `null` until filled in one by one.

<div class="tip-box">
A class that manages an array like this usually also keeps a counter (<code>count</code>) to know how many slots have been filled, so a <code>null</code> slot is never mistaken for real data.
</div>

---

## Preview: IS-A vs HAS-A

![h:260 A simple verbal test to tell an IS-A relationship (inheritance) apart from a HAS-A relationship (an attribute relationship)](../assets/illustrations/is-a-vs-has-a.svg)

Every relationship covered in this meeting is HAS-A: one class OWNS another class as an attribute. There is one more category of relationship that instead states that one class IS a specific kind of another class, called IS-A, covered in depth in Meeting 6.

---

## Common Mistake: Thinking Assignment Copies an Object

<div class="warn-box">
<b>Wrong:</b> writing <code>Rectangle b = a;</code> and then assuming <code>b</code> and <code>a</code> are two separate objects.
</div>

**Correct:** `b` and `a` point to the SAME object. To genuinely get a separate object, a new object must be created explicitly (for example `new Rectangle(a.getWidth(), a.getHeight())`), not merely through assignment.

---

## Exercise

Given the following code (`Rectangle` has methods `setWidth` and `getWidth`):

`Rectangle a = new Rectangle(5, 10);`
`Rectangle b = a;`
`b.setWidth(99);`
`System.out.println(a.getWidth());`

What value gets printed, and why?

---

## Exercise Answer

It prints **99**. `b = a` only copies the REFERENCE, not the object; `a` and `b` point to the exact same object on the heap, so `b.setWidth(99)` is also visible through `a`.

---

## Part 3 Summary

- Every relationship between classes works through references, not copies of an object; assignment only copies that reference.
- A variable holding `null` does not yet point to any object; calling a method on `null` always throws a `NullPointerException`.
- An object array holds many references at once, including slots that are still `null` before being filled.

Next: Part 4 applies all of these relationships, references, and arrays directly to Bank Mini.

---

<!-- _class: divider -->

# Part 4
## Applying Class Relationships to Bank Mini

Session 4 of 4

---

## Account Relates to Customer

![Bank has an aggregation with Account, and Account has an association with Customer](../assets/uml/p04-bank-customer-account.png)

`Account` now stores a reference to a `Customer` object (association) instead of merely an owner name as plain text. `Bank` stores many `Account` objects in an array (aggregation).

---

## Why Association, Not Aggregation or Composition?

`Account` only stores a reference to a single, already-existing `Customer`, received from outside. `Account` does not create its own `Customer` (so it is not composition), and does not manage many `Customer` objects as a collection (so it is not aggregation).

<div class="term-box">
Since <code>Account</code> merely stores a reference to an object that already exists, the relationship that best fits this pattern is <b>association</b>.
</div>

---

## Bank Manages Many Account Objects

`Bank` stores references to many `Account` objects in an array, similar to the `Account[]` array created in Meeting 2. The difference is that this array is now a class attribute, not a local variable inside `main`.

<div class="term-box">
An <code>Account</code> is created separately, then added to <code>Bank</code> through <code>addAccount(...)</code>. If removed from <code>Bank</code>, an <code>Account</code> can still stand on its own, the hallmark of <b>aggregation</b>.
</div>

---

## An Object Graph on the Heap

![One Bank references an array, which references Account objects, which reference Customer objects](../assets/illustrations/object-graph-references.svg)

<div class="tip-box">
Objects related to one another form an <b>object graph</b> on the heap: one object points to another through a reference, rather than copying its data. Changing a <code>Customer</code>'s data through one <code>Account</code> is visible to anyone holding a reference to that same <code>Customer</code>, exactly like the aliasing covered in Part 3.
</div>

---

## Bank's Methods: Adding, Finding, and Displaying

Method `addAccount()` adds a member to the array. Method `findAccount()` searches by account number, and returns `null` if not found. Method `printAllAccounts()` prints every member one by one.

---

## Code Example: Checking the Result of `findAccount`

```java
Account result = bank.findAccount("Z999");
if (result != null) {
    result.printInfo();
} else {
    System.out.println("Account not found");
}
```

<div class="tip-box">
Always check the result of <code>findAccount(...)</code> against <code>null</code> before using it, as covered in Part 3.
</div>

---

## Exercise

If a `Customer` object (say, Nadia) were removed from memory while its `Account` is still held by `Bank`, would that `Account` be removed along with it? Tie your answer to the direction of the association arrow `Account` &#8594; `Customer` on the class diagram.

---

## Exercise Answer

**No.** An association only states that `Account` STORES A REFERENCE to `Customer`, not that it OWNS its lifetime. The association arrow points in ONE DIRECTION: `Account` knows about `Customer`, but `Customer` does not store a reference back to any `Account`. As long as some reference still points to that `Customer` object (including through `Account`), that object will not be discarded.

---

## Part 4 Summary

- `Account`-`Customer`: association, a reference to an already-existing object, received from outside.
- `Bank`-`Account`: aggregation, the owner stores a collection of parts that can still stand on their own.
- Every `Bank` method operates on an array of references, including the possibility of `null` that the caller must check.

---

## Meeting 4 Summary

- Four strengths of relationship between classes, from weakest to strongest: dependency, association, aggregation, composition.
- UML notation: dependency (dashed arrow), association (solid arrow), aggregation (empty diamond on the owner's side), composition (filled diamond on the owner's side).
- All of these relationships work through references, not copies of an object; an object array holds many references at once.
- Bank Mini: `Account` has an association with `Customer`, and `Bank` has an aggregation with `Account`.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Classes and Objects: Object References chapter

Oracle Java Tutorials: "Creating Objects" and "Using Objects"

Fowler, *UML Distilled*, the Class Diagrams chapter: Association, Aggregation, Composition

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 4

---

## Discussion

Imagine a `Bank` object is removed from memory. In your view, would the `Account` objects already added to it be removed along with it, or would they still be able to stand on their own, independently? Explain your answer, then conclude: what does that answer mean for the kind of relationship between `Bank` and `Account`, aggregation or composition?
