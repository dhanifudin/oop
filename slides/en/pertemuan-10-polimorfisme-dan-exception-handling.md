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

Meeting 10: **Polymorphism and Exception Handling**

One command, many behaviors; failures that must not be kept silent

---

## What You Will Learn

- How one method call produces different behavior (polymorphism)
- How to check an object's kind safely through `instanceof`
- How to handle problems while the program runs through `try` and `catch`
- How to create and throw your own exception through `throw`
- Applying all of it to one case study

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 10.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Polymorphism and `instanceof`
- **Session 2 (50')**: Exception handling with `try` and `catch`
- **Session 3 (50')**: Throwing your own exception
- **Session 4 (50')**: Case study, the library collection

---

<!-- _class: divider -->

# Part 1
## Polymorphism

Session 1 of 4

---

## The Problem: One Branch for Each Kind

```java
for (Shape s : shapes) {
    if (s instanceof Circle) { /* circle formula */ }
    else if (s instanceof Square) { /* square formula */ }
    // a new kind? add another branch
}
```

Every time a new kind of shape appears, another `if` branch must be added. Code like this can be scattered across many places.

---

## What Is Polymorphism?

A coach shouts "go!". The swimmer starts swimming, the runner starts running. There is one command, and each athlete carries it out in their own way.

<div class="term-box">
<b>Polymorphism</b> means one and the same method call runs the version belonging to the object that receives it. That version is chosen while the program runs.
</div>

---

## Why Does This Matter?

An online store has dozens of product kinds. Each kind computes its shipping cost in its own way. Without polymorphism, every new kind means finding and changing all the `if` branches across the application. Miss just one, and a customer's shipping cost is wrong.

<div class="term-box">
With polymorphism, the calling code simply writes <code>product.shippingCost()</code>. The object itself knows how to compute it. Adding a new kind does not change old code.
</div>

---

## One Call, Two Results

![h:300 One call to area() resolved differently while the program runs](../assets/illustrations/polymorphic-dispatch.svg)

The call `s.area()` is written only once. For a `Circle`, Java runs the circle formula. For a `Square`, Java runs the square formula.

---

## Code Example: No `if` at All

```java
Shape[] shapes = { new Circle("c1", 2), new Square("s1", 4) };

for (Shape s : shapes) {
    System.out.println(s.area());   // one line for every kind
}
```

---

## Trace It Step by Step

1. First pass: `s` holds a `Circle` object. Java runs `Circle`'s `area()`: 3.14 x 2 x 2.
2. Second pass: `s` holds a `Square` object. Java runs `Square`'s `area()`: 4 x 4.

```
12.56
16.0
```

The variable's type is always `Shape`. What decides the result is the object.

---

## Three Ingredients of Polymorphism

1. A family relationship: `extends` or `implements`.
2. Subclasses fill in or override the same method.
3. The object is held through a variable of the superclass or interface type.

<div class="tip-box">
You have already learned all three in the Inheritance, Overriding, and Abstract Classes and Interfaces topics. Polymorphism is what they produce together.
</div>

---

## Checking an Object's Kind: `instanceof`

Sometimes code needs to know the object's kind, for example to call a method only `Circle` has. It is like a clerk checking a membership card before giving a special service.

```java
if (s instanceof Circle) {
    Circle c = (Circle) s;   // downcasting
    System.out.println(c.getRadius());
}
```

---

## Code Example: A Shorter `instanceof`

```java
for (Shape s : shapes) {
    if (s instanceof Circle c) {              // check, then get variable c right away
        System.out.println("radius: " + c.getRadius());
    }
}
```

This form is called pattern matching. Its output is `radius: 2.0`, only for the element that really is a `Circle`.

---

## Common Mistake: `if` Where Polymorphism Would Do

<div class="warn-box">
<b>Wrong:</b> checking <code>instanceof Circle</code> then <code>instanceof Square</code> to compute an area, even though both already have <code>area()</code>.
</div>

**Correct:** simply call `s.area()`. Use `instanceof` only for a capability that not every subclass has.

---

## Exercise

`Circle.area()` computes 3.14 x radius x radius. `Square.area()` computes side x side. Predict the output of this program:

```java
Shape[] shapes = { new Square("s1", 5), new Circle("c1", 1) };
for (Shape s : shapes) {
    System.out.println(s.area());
}
```

---

## Exercise Answer

```
25.0
3.14
```

The first element is a `Square`, so 5 x 5. The second element is a `Circle`, so 3.14 x 1 x 1. The output order follows the order of the objects in the array.

---

## Part 1 Summary

- Polymorphism: one method call, and the version that runs follows the object.
- The calling code needs no `if` branch for each kind of object.
- `instanceof` is used sparingly, for a capability that not every subclass has.

Next: Part 2 covers what happens when a program meets a problem while running.

---

<!-- _class: divider -->

# Part 2
## Exception Handling

Session 2 of 4

---

## The Problem: The Program Stops Abruptly

```java
int[] scores = { 80, 90 };
System.out.println(scores[5]);   // index 5 does not exist
System.out.println("done");      // never runs
```

The program stops at the second line with the message `ArrayIndexOutOfBoundsException`. The line after it does not run.

---

## What Is an Exception?

Picture a fire alarm. When there is a problem, the alarm sounds and every activity stops until someone deals with it.

<div class="term-box">
An <b>exception</b> is an object that signals a problem while the program runs. If nothing handles it, the program stops.
</div>

---

## Why Does This Matter?

A finance system silently turns a wrong transfer amount into zero, with no notice. A few weeks later the financial report does not balance. The cause is already buried among thousands of transactions and is very hard to trace.

<div class="term-box">
An exception makes a problem surface exactly where it happens. A problem can no longer slip through silently.
</div>

---

## `try` and `catch`: Try, and Prepare a Backup Plan

<div class="term-box">
Code that might have a problem goes inside a <code>try</code> block. If an exception occurs, the rest of the <code>try</code> block is skipped and the <code>catch</code> block runs. After that the program continues as usual.
</div>

---

## Code Example: Catching an Exception

```java
try {
    System.out.println(scores[5]);
    System.out.println("this line is skipped");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Failed: " + e.getMessage());
}
System.out.println("done");
```

---

## Trace It Step by Step

1. `scores[5]` fails. Java creates an exception object.
2. The rest of the `try` block is skipped.
3. The `catch` block runs. `e.getMessage()` holds the explanation of the problem.
4. The program continues at the line after `try`/`catch`.

```
Failed: Index 5 out of bounds for length 2
done
```

---

## Common Mistake: An Empty `catch` Block

<div class="warn-box">
<b>Wrong:</b> writing <code>catch (Exception e) { }</code> with nothing inside, just so the program does not stop.
</div>

**Correct:** an empty `catch` block hides the problem. At least print `e.getMessage()`, so someone knows that something failed.

---

## Exercise

Predict the output of this program:

```java
int[] data = { 1, 2, 3 };
try {
    System.out.println(data[0]);
    System.out.println(data[9]);
    System.out.println("end of try");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("caught");
}
```

---

## Exercise Answer

```
1
caught
```

`data[0]` succeeds and prints 1. `data[9]` fails, so `"end of try"` is skipped and the `catch` block runs.

---

## Part 2 Summary

- An exception is an object that signals a problem while the program runs.
- Risky code goes in `try`; its backup plan goes in `catch`.
- Do not leave a `catch` block empty.

Next: Part 3 covers how to throw an exception from a method we write ourselves.

---

<!-- _class: divider -->

# Part 3
## Throwing Your Own Exception

Session 3 of 4

---

## The Problem: A Failure Kept Silent

```java
class Grade {
    private int score;
    public void setScore(int score) {
        if (score > 100) { score = 100; }   // silently changed
        this.score = score;
    }
}
```

`setScore(150)` does not reject the wrong value. The value is changed to 100, and the caller never finds out.

---

## `throw`: Raising a Hand and Reporting

A clerk who finds a wrong form does not quietly fix it. They raise a hand and report it, and their work stops right there.

<div class="term-box">
<code>throw</code> throws an exception object. The method stops at once, and the problem is handed to the calling code.
</div>

---

## The Journey of an Exception

![h:300 An exception halts the method that throws it and is passed upward until it is caught](../assets/illustrations/exception-throw-catch.svg)

The exception is thrown inside `setScore(150)`, then travels up to the calling code until a `catch` block catches it.

---

## Code Example: An Exception of Your Own

```java
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);   // the message is stored by Exception
    }
}
```

Just `extends Exception` and one constructor. The class name already explains the kind of problem.

---

## Exceptions in a Class Diagram

![h:260 Exception, InvalidScoreException, and the Grade that throws it](../assets/uml/p10-invalidscore-exception.png)

`InvalidScoreException` is a subclass of `Exception`. The dashed arrow labeled "throws" means `Grade` can throw that exception.

---

## Code Example: `setScore()` Throws an Exception

```java
public void setScore(int score) throws InvalidScoreException {
    if (score < 0 || score > 100) {
        throw new InvalidScoreException("Score must be 0-100: " + score);
    }
    this.score = score;
}
```

The word `throws` in the signature tells the caller that this method can fail.

---

## Trace It Step by Step

```java
try {
    grade.setScore(150);
    System.out.println("saved");
} catch (InvalidScoreException e) {
    System.out.println("Failed: " + e.getMessage());
}
```

1. `setScore(150)` finds a value outside 0-100, then throws an exception.
2. The line `this.score = score` does not run. The old value stays safe.
3. `"saved"` is skipped, the `catch` block runs.

Output: `Failed: Score must be 0-100: 150`

---

## Exceptions That Must Be Handled

<div class="term-box">
An exception that <code>extends Exception</code> must be handled. The caller has two choices: wrap the call in <code>try</code>/<code>catch</code>, or pass it on by writing <code>throws</code> on its own method.
</div>

If neither is done, the compiler rejects the code.

---

## Common Mistake: Forgetting `try`/`catch`

<div class="warn-box">
<b>Wrong:</b> calling <code>grade.setScore(150);</code> as it is, with no <code>try</code>/<code>catch</code> and no <code>throws</code>.
</div>

**Correct:** the compiler reports the error `unreported exception InvalidScoreException; must be caught or declared to be thrown`. Wrap that call in `try`/`catch`.

---

## Exercise

`setScore(int)` is declared `throws InvalidScoreException`. Decide **valid** or **error**:

1. `grade.setScore(90);` with no `try`/`catch`, inside an ordinary `main`.
2. `try { grade.setScore(90); } catch (InvalidScoreException e) { System.out.println(e.getMessage()); }`
3. `void update(Grade g) throws InvalidScoreException { g.setScore(90); }`

---

## Exercise Answer

1. **Error.** Even though 90 is a correct value, the compiler still requires handling.
2. **Valid.** The call is wrapped in `try`/`catch`.
3. **Valid.** The method `update` passes the exception on through `throws`.

---

## Part 3 Summary

- `throw` throws an exception and stops the method at once.
- An exception of your own only needs `extends Exception` and one constructor.
- `throws` in the signature requires the caller to use `try`/`catch` or pass it on.

Next: Part 4 uses polymorphism and exceptions on the library collection.

---

<!-- _class: divider -->

# Part 4
## Case Study: The Library Collection

Session 4 of 4

---

## Back to the Library

`LibraryItem` is already abstract, and `Dvd` is already `Playable`. There are three new needs:

1. Print every item's late fee with no `if` branch for each kind.
2. Play only the items that can be played.
3. Reject a loan of an item that is already on loan, with a clear message.

Number 1 uses polymorphism. Number 2 uses `instanceof`. Number 3 uses an exception.

---

## Class Diagram: The Collection and Its Exception

![h:300 Abstract LibraryItem with checkOut throwing ItemNotAvailableException, Book, Dvd, and Playable](../assets/uml/p10-libraryitem-exception.png)

`ItemNotAvailableException` is a subclass of `Exception`. The "throws" arrow shows that `checkOut()` in `LibraryItem` can throw it.

---

## Trace It: Late Fees and Playback

```java
for (LibraryItem item : items) {      // items holds Book "Dune" and Dvd "Inception"
    System.out.println(item.title + ": " + item.lateFeePerDay());
    if (item instanceof Playable p) { p.play(); }
}
```

```
Dune: 1000
Inception: 5000
Playing Inception
```

`lateFeePerDay()` is called for every item. `play()` is only for those that are `Playable`.

---

## Code Example: `checkOut()` Throws an Exception

```java
public void checkOut() throws ItemNotAvailableException {
    if (!available) {
        throw new ItemNotAvailableException(title + " is already on loan");
    }
    available = false;
}
```

---

## Trace It: Borrowing Twice

```java
Book dune = new Book("Dune");
try {
    dune.checkOut();
    dune.checkOut();
    System.out.println("borrowed twice");
} catch (ItemNotAvailableException e) {
    System.out.println("Failed: " + e.getMessage());
}
```

The first call succeeds. The second call throws an exception, so `"borrowed twice"` is skipped. Output: `Failed: Dune is already on loan`

---

## Common Mistake: Checking the Class, Not the Capability

<div class="warn-box">
<b>Wrong:</b> writing <code>if (item instanceof Dvd)</code> to decide when to call <code>play()</code>.
</div>

**Correct:** check `instanceof Playable`. If an `AudioBook` that is also `Playable` is added later, it gets played right away without changing the loop code.

---

## Exercise

1. `items` holds a `Magazine` "Tempo" (fee 500) and a `Dvd` "Inception" (fee 5000). Predict the output of the loop on the slide "Trace It: Late Fees and Playback".
2. Can `dune.checkOut();` with no `try`/`catch` be compiled? Yes or no?

---

## Exercise Answer

```
Tempo: 500
Inception: 5000
Playing Inception
```

**No.** `checkOut()` is declared `throws ItemNotAvailableException`, so its caller must use `try`/`catch` or `throws`.

---

## Part 4 Summary

- One loop prints every item's late fee, with no `if` branch per kind.
- `instanceof Playable` checks a capability, not a class name.
- `checkOut()` throws an exception, so a double loan cannot slip through silently.

---

## Meeting 10 Summary

| | Polymorphism | Exception Handling |
|---|---|---|
| Problem it solves | an `if` branch for each kind of object | failures kept silent |
| Keywords | `@Override`, `instanceof` | `try`, `catch`, `throw`, `throws` |
| What decides | the actual object | the method that finds the problem |
| Today's examples | `s.area()`, `item.lateFeePerDay()` | `setScore()`, `checkOut()` |

Both let a program grow larger without changing old code.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Polymorphism and Interfaces, Exception Handling chapters

Oracle Java Tutorials: "Polymorphism", "Exceptions"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 10

---

## Assignment: The Library Collection

The library adds a rule: a member (`Member`) may borrow at most 3 items.

1. Create an exception `LoanLimitExceededException`. Write its class declaration.
2. Write the signature of a method `borrow(LibraryItem item)` in `Member` that can throw that exception.
3. Write a `try`/`catch` snippet that calls `borrow(...)` and prints a message when it fails.
4. Draw the class diagram on paper, complete with the "throws" arrow.

---

## Assignment: Your Own Case Study

Reuse the class hierarchy from the previous meetings' assignment (the application you chose yourself).

1. Write one loop that calls the same method on several kinds of objects, with no `if` branch. Write the output you expect.
2. Pick one method that can fail. Create your own exception for it, then write the `try`/`catch` of its caller.
3. Update your class diagram on paper.
