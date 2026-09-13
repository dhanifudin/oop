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

Meeting 3: **Encapsulation**

Protecting an object's data from uncontrolled access

---

## What You Will Learn

- The risk that arises when an object's data can be freely changed from outside
- How to protect an object's data so that any change stays controlled
- A condition that must always hold true for an object's data throughout its lifetime
- Patterns for reading and changing data safely, including data deliberately made unchangeable
- Applying all of these concepts to one of Bank Mini's core classes

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 3.
</div>

---

## Today's Session Map

- **Session 1 (50')**: The risk of direct attribute access
- **Session 2 (50')**: Encapsulation, access modifiers, and class invariants
- **Session 3 (50')**: The getter and setter pattern
- **Session 4 (50')**: Applying encapsulation to Account

---

<!-- _class: divider -->

# Part 1
## The Risk of Direct Access

Session 1 of 4

---

## When Anyone Can Change an Attribute

Imagine a class `Grade` with a public attribute `score`. An exam score only makes sense in the range 0 to 100. Because `score` is public, other code can change its value directly, without going through any method. What stops that code from setting `score` to a number far outside that range, say `-20`?

<div class="warn-box">
A public attribute means there is no single point that guarantees an object's data stays valid.
</div>

---

## Illustration: Unchecked Direct Access

![h:300 Outside code writing directly to a public attribute, with no validation at all](../assets/illustrations/direct-access-bug.svg)

<div class="warn-box">
Because the attribute is public, no code checks the new value before it is stored. A value outside any sensible range, such as <code>-20</code>, is simply accepted.
</div>

---

## Code Example: A Public Attribute, No Guard

```java
class Grade {
    public int score;
}

Grade g = new Grade();
g.score = -20;  // accepted as-is, nothing checks it
```

<div class="tip-box">
Java only makes sure <code>-20</code> is a number. Java does not know, and does not care, that an exam score is never negative.
</div>

---

## Why Does This Matter?

A bug like this is not merely a theoretical risk. In a real application, a public attribute means any part of the program, including code written by other teams, can change that data directly. When a bug appears, a programmer must trace through the entire codebase to find where it was changed, since there is no single point to check.

<div class="term-box">
This is one reason encapsulation is considered one of the most fundamental principles in OOP. By hiding data behind methods, a team can change how that data is stored at any time, without breaking other code, as long as its public methods stay the same.
</div>

---

## Common Mistake: Thinking a Comment Is Enough Protection

<div class="warn-box">
<b>Wrong:</b> adding a comment such as "do not set outside 0-100" above a public attribute, hoping that is enough to prevent an invalid value.
</div>

**Correct:** a comment is only text, never actually enforced by Java. The only way to enforce a rule is through code that actually runs, that is, a method that checks the value before storing it.

---

## Exercise

A class `Elevator` has a public attribute `currentFloor`. The building this elevator is installed in only has floors 1 through 20.

Write one line of code from outside the class that could corrupt this `Elevator`'s data, then explain why Java does not prevent it.

---

## Exercise Answer

For example, `elevator.currentFloor = 99;`. Java does not prevent this because `currentFloor` is public; the compiler only makes sure the data type is correct (a number), not whether the value makes sense for that building.

---

## Part 1 Summary

- A public attribute can be changed by any code, with nothing checking its value first.
- Java only checks the data type, not whether the value makes sense.
- A comment is no substitute for validation; only code that actually runs can enforce a rule.

Next: Part 2 introduces encapsulation, how Java actually closes this gap.

---

<!-- _class: divider -->

# Part 2
## Encapsulation: Data Behind Methods

Session 2 of 4

---

## The Concept of Encapsulation

![h:300 An object's private data can only be reached through its methods, no shortcuts allowed](../assets/illustrations/capsule-shield.svg)

<div class="term-box">
<b>Encapsulation</b> means an object's data is hidden (made <code>private</code>), accessible only through methods belonging to the object itself. Those methods are the only "door" leading to that data.
</div>

---

## Code Example: Making an Attribute Private

```java
class Grade {
    private int score;
}

Grade g = new Grade();
g.score = 85;  // fails to compile: score is private
```

<div class="warn-box">
The last line never even gets to run; Java rejects it right at compile time, before the program has a chance to start.
</div>

---

## Encapsulation and Information Hiding

These two terms are often treated as the same thing, but they differ. **Encapsulation** is the mechanism: bundling data together with methods inside a single class. **Information hiding** is the goal: hiding the detail of how data is stored, so outside code depends only on public methods, not on what is inside.

<div class="term-box">
A class can bundle data and methods together (encapsulation) without actually hiding anything, for instance if its attributes stay <code>public</code>. It is the <code>private</code> access modifier that makes information hiding actually happen.
</div>

---

## Access Modifiers in Java

| Modifier | Same class | Other class, same package | Subclass, different package | Other class, different package |
|---|:---:|:---:|:---:|:---:|
| `private` | yes | no | no | no |
| (no modifier) | yes | yes | no | no |
| `protected` | yes | yes | yes | no |
| `public` | yes | yes | yes | yes |

<div class="tip-box">
This meeting only needs <code>private</code> and <code>public</code>. <code>protected</code> comes up again once inheritance is covered in Meeting 6; no-modifier (package-private) access is rarely used explicitly in Bank Mini.
</div>

<div class="term-box">
Rule of thumb: pick the strictest access modifier the class can still work with. Attributes are almost always <code>private</code>; methods are opened up (<code>public</code>) only when they need to be called from outside.
</div>

---

## Validation Guaranteed in One Place

With encapsulation, every change to the data must pass through a designated method. That method can validate the new value before storing it, so the object is never left in a state that does not make sense.

<div class="term-box">
This principle is often summarized as <b>"hide the data, expose the behavior"</b>: the outside world does not need to know how data is stored internally, only what methods are available to call.
</div>

---

## Class Invariant

A **class invariant** is a condition that must always hold true for every object, throughout that object's lifetime. For `Grade`, its invariant is "`score` is always in the range 0 to 100". This condition must remain true whenever the object is inspected, no matter which method was just called.

<div class="term-box">
Encapsulation is what actually makes an invariant enforceable. The only way to change the data is through a method belonging to the class, so that method can check the invariant first. Without encapsulation, a public attribute turns the invariant into a hope, not a guarantee, since any code can break it at any time.
</div>

---

## Common Mistake: Thinking Private Alone Is Enough

<div class="warn-box">
<b>Wrong:</b> making an attribute <code>private</code>, then adding a getter and setter for it with no validation at all, and considering this "proper encapsulation".
</div>

**Correct:** a setter that accepts any value without checking it is no different from a public attribute, just wrapped in a method. Encapsulation only genuinely protects data once its methods actually validate, rather than merely copying a value across.

---

## Exercise

A class `Grade` has the following method:

`public void setScore(int score) { this.score = score; }`

Does this method genuinely protect the invariant "`score` is always 0-100"? Explain.

---

## Exercise Answer

**No.** This setter copies any value across without checking its range, exactly like a public attribute. Encapsulation here only wraps the data, it does not protect it; the setter needs to reject or adjust a value outside 0-100 before storing it.

---

## Part 2 Summary

- Encapsulation bundles data and methods together; information hiding is achieved once the data is genuinely `private`.
- Access modifiers determine who may access what; attributes should generally be `private`, methods public only as needed.
- A class invariant is only genuinely guaranteed once its setter validates, rather than merely copying a value across.

Next: Part 3 covers the getter and setter pattern in more depth, including when an attribute does not need both.

---

<!-- _class: divider -->

# Part 3
## Getters and Setters

Session 3 of 4

---

## A Setter That Validates

![h:300 A setter checking an incoming value before storing it in a field](../assets/illustrations/getter-setter-gate.svg)

<div class="term-box">
A <b>setter</b> is a method that changes the value of a private attribute. Because it is an ordinary method, a setter can check the value first, for instance clamping it to a safe range, before storing it in the field.
</div>

---

## Code Example: A Setter and Getter

```java
public void setScore(int score) {
    if (score < 0) score = 0;
    if (score > 100) score = 100;
    this.score = score;
}

public int getScore() {
    return score;
}
```

---

## Getter: Reading Data Safely

A **getter** is a method that returns the value of a private attribute, without allowing outside code to change it directly.

<div class="term-box">
A common Java naming convention: a setter is named <code>setAttributeName(...)</code>, a getter is named <code>getAttributeName()</code>. Together, the two are called the <b>getter-setter</b> pattern.
</div>

---

## Code Example: A Getter That Derives Another Value

```java
public String getLetterGrade() {
    if (score >= 85) return "A";
    if (score >= 70) return "B";
    if (score >= 55) return "C";
    return "D";
}
```

<div class="tip-box">
A getter does not have to simply return a field as-is. <code>getLetterGrade()</code> computes a fresh value from <code>score</code> every time it is called, rather than storing the letter separately.
</div>

---

## Read-Only Attributes

An attribute does not have to have both a getter and a setter. An attribute whose value is set once when the object is created, and must never change again, only needs a getter. This pattern is called a **read-only attribute**: the constructor sets its value up front, and with no setter, no other method can ever change it.

<div class="term-box">
A read-only attribute is the strictest form of encapsulation: not only is validation guaranteed, the change itself can never happen again. A common example: an identification number such as an account number or a student ID, which by nature is never meant to change.
</div>

---

## Code Example: A Read-Only Attribute

```java
class Student {
    private String studentId;

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }
}
```

<div class="tip-box">
There is no <code>setStudentId(...)</code> at all. Once set through the constructor, <code>studentId</code> can never be changed again by any code.
</div>

---

## Common Mistake: Assuming Every Attribute Needs Both

<div class="warn-box">
<b>Wrong:</b> adding a getter and setter for EVERY private attribute without considering whether outside code genuinely needs to access it.
</div>

**Correct:** getters and setters are created only when actually needed. An attribute that outside code has no reason to read or change should not get either at all; blindly adding a getter/setter for every attribute actually undermines the whole point of encapsulation.

---

## Exercise

A class `Stopwatch` has two attributes:

1. `elapsedSeconds`, the elapsed running time, displayed to the user but only allowed to change through the `start()`/`stop()` methods, never set directly from outside.
2. `id`, the stopwatch's serial number, set once when the object is created, never displayed or changed from outside.

For each attribute, determine: does it need a getter only, a setter only, both, or neither?

---

## Exercise Answer

`elapsedSeconds`: **getter only** (displayed to the user, but changed through `start()`/`stop()`, not through a direct setter).

`id`: **neither** (never displayed nor changed from outside, used only internally by the class itself).

---

## Part 3 Summary

- A good setter validates a value before storing it; a getter simply returns a value safely.
- A read-only attribute only has a getter; its value is set once through the constructor and never changes again.
- Not every attribute needs a getter and setter; both are created only when genuinely needed from outside.

Next: Part 4 applies all of these patterns to the `Account` class in Bank Mini.

---

<!-- _class: divider -->

# Part 4
## Applying Encapsulation to Account

Session 4 of 4

---

## Account Before Encapsulation

The `Account` class from Meeting 2 has public attributes `ownerName` and `balance`, with no validation at all in `deposit()` or `withdraw()`.

<div class="warn-box">
The same risk as <code>Grade</code> applies here: the balance can be changed directly to any value, and a negative deposit or withdrawal could be accepted without being rejected.
</div>

---

## Account After Encapsulation

![h:280 Class diagram for Account after encapsulation is applied](../assets/uml/p03-account-encapsulated.png)

<div class="term-box">
Every attribute is now <code>private</code>, accessed through a <b>getter</b> (<code>getBalance()</code>, and so on). The <code>deposit()</code> and <code>withdraw()</code> methods return a <code>boolean</code> value: <code>true</code> on success, <code>false</code> if the given value is invalid.
</div>

---

## Code Example: Account After Encapsulation

```java
class Account {
    private double balance;

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance = balance + amount;
        return true;
    }
}
```

---

## The Constructor Sets Data Once, Up Front

The constructor `Account(accountNumber, ownerName, balance)` requires all three values to be provided the moment the object is created. The `accountNumber` attribute is deliberately given only a getter, no setter. Once set through the constructor, an `Account`'s account number never changes again.

<div class="term-box">
This is the read-only attribute pattern from Part 3, applied for real. <code>accountNumber</code> is an account's identity, just like a student ID is for a student, so it would not make sense for any method to be able to change it.
</div>

---

## Exercise

The method `withdraw(double amount)` on `Account` returns `false` if `amount` exceeds the current `balance`, or if `amount` is not a positive number.

Given a current `balance` of 100000, determine the result of the following calls: `withdraw(150000)`, `withdraw(-5000)`, `withdraw(50000)`.

---

## Exercise Answer

`withdraw(150000)` returns `false` (exceeds the balance). `withdraw(-5000)` returns `false` (not a positive number). `withdraw(50000)` returns `true` (valid, balance is sufficient).

---

## Part 4 Summary

- `Account` now hides all of its attributes, accessible only through getters and validated methods.
- `deposit()` and `withdraw()` reject invalid values through their `boolean` return value.
- `accountNumber` is read-only, set once through the constructor, and never changes again.

---

## Meeting 3 Summary

- A public attribute has no guard; any value at all, including one that makes no sense, is simply accepted.
- Encapsulation hides data behind methods, so validation can be guaranteed in one place.
- A class invariant is only genuinely upheld once its setter validates, rather than merely copying a value across.
- Getters and setters are created only as needed; a read-only attribute only has a getter, set once through the constructor.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Classes and Objects: Encapsulation chapter

Oracle Java Tutorials: "Controlling Access to Members of a Class"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 3

---

## Independent Assignment: Finding Encapsulation in the Real World

Find one real system outside Bank Mini that you know or use every day, from any domain you choose. Identify how that system applies encapsulation: what data is hidden, what invariant is maintained, and what it can be accessed through.

<div class="tip-box">
Write down your findings briefly (the system's name, the data it hides, the invariant it maintains, how it is accessed) and be ready to discuss them at the start of Meeting 4.
</div>
