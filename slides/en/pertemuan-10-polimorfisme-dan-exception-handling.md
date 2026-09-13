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

One point of code, many behaviors; failure that cannot be ignored

---

## What You Will Learn

- How one identical method call runs a different behavior depending on the object receiving it, decided while the program is running
- How to check a type while also downcasting safely, for when code still needs to know an object's concrete type
- How to make a failure impossible to silently ignore, through `throw`, `try`/`catch`, and custom exceptions
- Application to Bank Mini: `withdraw()` throwing `InsufficientBalanceException`, `Bank.processMonthEnd()` processing accounts polymorphically

<div class="tip-box">
Programming exercises for today's material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 10.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Polymorphism, one method call, many behaviors
- **Session 2 (50')**: Exception handling, failure that cannot be ignored
- **Session 3 (50')**: Applying exception handling to Bank Mini
- **Session 4 (50')**: Applying polymorphism to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Polymorphism

Session 1 of 4

---

## One Array, Different Kinds of Objects

Imagine `Shape[] shapes` holding a mix of `Circle` and `Square`. Without polymorphism, code computing each element's area must check its kind one by one: `if (s instanceof Circle) { ... } else if (s instanceof Square) { ... }`, each branch calling a different area formula.

<div class="warn-box">
Every time a new shape kind is added, this <code>if</code>/<code>else</code> branch must be tracked down and extended, everywhere this kind of code was ever written.
</div>

---

## Why Does This Matter?

Imagine an e-commerce application with dozens of product kinds (books, electronics, groceries), each with its own way of computing shipping cost. Without polymorphism, the method that processes an order contains dozens of branches: `if (product instanceof Book) ... else if (product instanceof Electronics) ...`. Adding one new product kind means finding and changing EVERY such method across the entire application; missing just one becomes a bug that only surfaces once a customer complains about a wrong shipping charge.

<div class="term-box">
Polymorphism flips this responsibility around: the calling code simply calls <code>product.calculateShippingCost()</code>, and the object itself knows how to compute it. Adding a new product kind never changes a single line of existing code, in line with the Open/Closed Principle discussed further in Meeting 11.
</div>

---

## Dynamic Dispatch: Decided While the Program Runs

![h:280 One call to area() resolved differently while the program runs](../assets/illustrations/polymorphic-dispatch.svg)

<div class="term-box">
<b>Polymorphism</b> is the ability of one identical method call, invoked through a superclass or interface type, to run the version belonging to the object's actual runtime type (<i>dynamic dispatch</i>). This mechanism has actually been at work ever since method overriding was studied in Meeting 7; here it is given its formal name.
</div>

---

## Code Example: One Call, Different Behavior

```java
Shape[] shapes = { new Circle("c1", 3), new Square("s1", 4) };

for (Shape s : shapes) {
    System.out.println(s.area());
}
```

The exact same `s.area()` prints `28.27` for the first element and `16.0` for the second; `Shape[]` never needs to know each element's concrete kind.

---

## When Knowing the Concrete Type Is Still Needed

Sometimes code still needs to check an object's concrete type, for instance to call a capability that only some subclasses have (not the whole superclass). Imagine `Circle` has an extra method `getRadius()` that neither `Shape` nor `Square` has.

<div class="term-box">
<code>instanceof</code> with <i>pattern matching</i> (<code>if (obj instanceof SpecificType variable)</code>) checks an object's type while also immediately providing a variable of that specific type, replacing the old approach that required a separate manual cast after the check.
</div>

<div class="warn-box">
Too much <code>instanceof</code> checking concrete types one by one is a sign polymorphism has not been used fully. Use <code>instanceof</code> sparingly, especially to check an <i>interface</i> only some subclasses implement, as shown in Part 4.
</div>

---

## Code Example: Pattern Matching with `instanceof`

```java
for (Shape s : shapes) {
    if (s instanceof Circle c) {
        System.out.println("radius: " + c.getRadius());
    }
}
```

`c` is immediately typed as `Circle`, with no separate manual cast; this block only runs for elements that are genuinely `Circle`.

---

## Common Mistake: if/else When Polymorphism Would Do

<div class="warn-box">
<b>Wrong:</b> manually writing <code>if (s instanceof Circle) area = 3.14 * r * r; else if (s instanceof Square) area = side * side;</code> inside a loop, even though <code>Circle</code> and <code>Square</code> already both override <code>area()</code>.
</div>

**Right:** simply call `s.area()`. `Shape[]` automatically calls the version belonging to the object's actual kind through dynamic dispatch; the calling code does not need to know the concrete type at all.

---

## Exercise

```java
Shape[] shapes = { new Circle("c1", 2), new Square("s1", 5) };
for (Shape s : shapes) {
    System.out.println(s.area());
}
```

Predict the two lines this code prints (`Circle.area()` computes `Math.PI * radius * radius`, `Square.area()` computes `side * side`).

---

## Exercise Answer

The first line prints **`12.566370614359172`** (`Math.PI * 2 * 2`), the second prints **`25.0`** (`5 * 5`). The exact same `s.area()` is called for both elements, but each runs the `area()` version belonging to its own concrete type.

---

## Part 1 Summary

- Polymorphism makes one identical method call run the version belonging to the object's actual runtime type (dynamic dispatch).
- `instanceof` with pattern matching checks a type while also providing a variable of that specific type, used sparingly whenever code genuinely needs to know the concrete type.
- Code that branches `if`/`else` on type even though the method is already polymorphic is a sign polymorphism has not been used fully.

Next: Part 2 covers exception handling, how to make a failure impossible to simply ignore.

---

<!-- _class: divider -->

# Part 2
## Exception Handling

Session 2 of 4

---

## When a Failure Is Silently Left Alone

Meeting 3 showed `Grade.setScore()` silently clamping a score outside the 0-100 range instead of rejecting it. This approach is convenient, but the caller never knows the score it sent was actually changed silently.

<div class="warn-box">
A failure silently left alone can create a bug that only surfaces long after the real cause occurred, in a place entirely different from its source.
</div>

---

## Why Does This Matter?

Imagine a system that silently ignores invalid input instead of firmly rejecting it, for instance a negative transfer amount rounded down to zero with no notice. A few weeks later, the team discovers an unbalanced financial report, but the cause has long since been buried among thousands of other transactions, extremely hard to trace back to the actual line of code at fault.

<div class="term-box">
An exception makes a failure impossible to simply ignore: for a <i>checked exception</i>, the compiler forces the calling code to handle it explicitly. The problem surfaces right at the point it occurs, instead of sneaking silently into a distant, hard-to-trace part of the program.
</div>

---

## throw, try, catch

![h:280 An exception halts the method that threw it and propagates upward until caught](../assets/illustrations/exception-throw-catch.svg)

<div class="term-box">
A method throws (<code>throw</code>) an exception object when it encounters a condition it cannot handle reasonably, halting its own execution right there. The calling code wraps the call in a <code>try</code> block, then handles any exception that may be thrown through a <code>catch</code> block.
</div>

---

## Code Example: Handling an Exception with `try`/`catch`

```java
try {
    grade.setScore(150);
} catch (InvalidScoreException e) {
    System.out.println("Failed: " + e.getMessage());
}
```

If `setScore(150)` throws `InvalidScoreException`, the rest of the `try` block is skipped immediately and execution jumps to `catch`; the program keeps running afterward.

---

## Building a Custom Exception

![h:280 Exception, InvalidScoreException, and the Grade that throws it](../assets/uml/p10-invalidscore-exception.png)

<div class="term-box">
A custom exception is built by declaring a class that <code>extends</code> <code>Exception</code>, usually containing only a constructor that forwards the error message to its superclass constructor through <code>super(message)</code>. The class name itself already explains the kind of failure that occurred, far clearer than a mere <code>boolean</code> or <code>null</code> value.
</div>

---

## Code Example: Declaring a Custom Exception

```java
public class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
```

Just one constructor forwarding the message to `super(...)`; `Exception` already provides the entire base behavior.

---

## Common Mistake: Forgetting `try`/`catch` for a Checked Exception

<div class="warn-box">
<b>Wrong:</b> calling <code>grade.setScore(150);</code> directly with no <code>try</code>/<code>catch</code> wrapper, assuming the exception only needs handling if it actually happens.
</div>

**Right:** the compiler displays the error `unreported exception InvalidScoreException; must be caught or declared to be thrown`. `InvalidScoreException` is a checked exception, required to be handled through `try`/`catch` or declared through `throws` on the calling method, before the code can even compile.

---

## Exercise

```java
public void updateGrade(Grade grade, int newScore) {
    grade.setScore(newScore);
}
```

`setScore(int)` is declared `throws InvalidScoreException`. Does this `updateGrade` code compile? Explain, then state one way to fix it.

---

## Exercise Answer

**No, it does not compile.** `setScore(int)` is a checked exception, and `updateGrade` calls it with neither `try`/`catch` nor `throws`, so the compiler displays the error `unreported exception`. Fix: add `throws InvalidScoreException` to `updateGrade`'s signature, or wrap the call `grade.setScore(newScore)` in a `try`/`catch` block.

---

## Part 2 Summary

- An exception makes a failure impossible to simply ignore; a checked exception is forced by the compiler to be handled explicitly.
- `throw` throws an exception object and immediately halts the method; `try`/`catch` catches it and handles it in the calling code.
- A custom exception is built with `extends Exception`; the class name itself explains the kind of failure that occurred.

Next: Part 3 applies exception handling to Bank Mini's `withdraw()`.

---

<!-- _class: divider -->

# Part 3
## Applying Exception Handling to Bank Mini

Session 3 of 4

---

## withdraw() Throws InsufficientBalanceException

![h:280 Exception, InsufficientBalanceException, and the Account that throws it](../assets/uml/p10-insufficientbalance-exception.png)

So far, `withdraw()` has silently returned `false` when the balance was insufficient, exactly the risk discussed in Part 2. `withdraw()` now throws `InsufficientBalanceException`; the calling code is required to handle it through `try`/`catch`, and can no longer simply forget to check the result.

---

## Code Example: `withdraw()` Throws an Exception

```java
public void withdraw(double amount) throws InsufficientBalanceException {
    if (!canWithdraw(amount)) {
        throw new InsufficientBalanceException(
                accountNumber + ": insufficient balance");
    }
    balance -= amount;
}
```

`canWithdraw()` (the hook from Meeting 7) is used exactly as it was; only the way its failure is handled has changed.

---

## Common Mistake: Empty Catch Block

<div class="warn-box">
<b>Wrong:</b> writing <code>try { account.withdraw(500000); } catch (InsufficientBalanceException e) {}</code>, leaving the <code>catch</code> block empty with no handling at all.
</div>

**Right:** an empty `catch` block recreates exactly the problem Part 2 set out to solve, the failure is silently left alone, only now wrapped in `try`/`catch` so the compiler stops complaining. At minimum, print or log `e.getMessage()`, or display a notice to the user.

---

## Exercise

A `SavingsAccount` with a `balance` of Rp 100,000 and a minimum balance of Rp 50,000 calls `withdraw(70000)`.

What happens? Explain through `canWithdraw()`, then state whether `balance` changes.

---

## Exercise Answer

**`InsufficientBalanceException` is thrown.** `canWithdraw(70000)` checks `balance - amount >= 50000`; with `balance` at 100000, the result is 30000, less than 50000, so `canWithdraw()` returns `false`. `withdraw()` throws the exception BEFORE the line `balance -= amount` ever runs, so `balance` stays at 100000, unchanged.

---

## Part 3 Summary

- `withdraw()` throws `InsufficientBalanceException` instead of silently returning `false`; the calling code is required to handle it.
- The exception is thrown BEFORE `balance` is changed, so a failed withdrawal never leaves `Account` in an inconsistent state.
- An empty `catch` block recreates the problem of a silently ignored failure; an exception must still be handled meaningfully, not just enough to keep the compiler quiet.

Next: Part 4 applies polymorphism to `Bank.processMonthEnd()`.

---

<!-- _class: divider -->

# Part 4
## Applying Polymorphism to Bank Mini

Session 4 of 4

---

## processMonthEnd(): Polymorphism in Bank Mini

![h:280 Account as an abstract class, SavingsAccount implementing interface InterestBearing](../assets/uml/p09-account-abstract.png)

`Bank.processMonthEnd()` processes every account polymorphically through `monthlyFee()`. Only an account that implements `InterestBearing` receives `applyInterest()`, checked through `instanceof InterestBearing`, not `instanceof SavingsAccount`, so any new kind of interest-bearing account is automatically processed too, without changing this code at all.

---

## Code Example: `processMonthEnd()` Processes Every Account

```java
public void processMonthEnd() {
    for (int i = 0; i < count; i++) {
        Account acc = accounts[i];
        if (acc instanceof InterestBearing bearing) {
            bearing.applyInterest();
        }
        System.out.println(acc.monthlyFee());
    }
}
```

`acc.monthlyFee()` is called polymorphically for EVERY account; `applyInterest()` only for those that implement `InterestBearing`.

---

## Common Mistake: Checking the Concrete Class, Not the Interface

<div class="warn-box">
<b>Wrong:</b> writing <code>if (acc instanceof SavingsAccount)</code> to decide when to call <code>applyInterest()</code>, assuming this is the same as checking <code>InterestBearing</code>.
</div>

**Right:** checking `instanceof InterestBearing` (not `instanceof SavingsAccount`) means a NEW kind of interest-bearing account is automatically processed too, without changing `processMonthEnd()` at all. Checking the concrete class forces this method to be changed again every time a new interest-bearing account kind appears.

---

## Exercise

Bank Mini adds `BusinessAccount` (the independent assignment from Meeting 6), a business account that does not earn interest at all, so it does not implement `InterestBearing`.

Does `processMonthEnd()` call `applyInterest()` for `BusinessAccount`? Explain through its `instanceof` check.

---

## Exercise Answer

**No.** `processMonthEnd()` checks `acc instanceof InterestBearing`, and `BusinessAccount` does not implement `InterestBearing`. This check evaluates to `false` for `BusinessAccount`, so `applyInterest()` is skipped; only `acc.monthlyFee()` is still called for every account, `BusinessAccount` included.

---

## Part 4 Summary

- `processMonthEnd()` calls `monthlyFee()` polymorphically for every account, with no need to know its concrete kind.
- `instanceof InterestBearing` decides when `applyInterest()` is called, not `instanceof SavingsAccount`, so a new interest-bearing account kind is automatically processed too.
- Polymorphism and exception handling both let `Bank` grow (new account kinds, new failures) without changing existing code.

---

## Meeting 10 Summary

- Polymorphism makes one identical method call run the version belonging to the object's actual runtime type; `instanceof` with pattern matching is used sparingly whenever code still needs to know the concrete type.
- An exception makes a failure impossible to simply ignore; a checked exception is forced by the compiler to be handled through `try`/`catch`, and a custom exception is built through `extends Exception`.
- Bank Mini uses both: `withdraw()` throws `InsufficientBalanceException`, `processMonthEnd()` processes every account polymorphically through `instanceof InterestBearing`.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, chapters on Exception Handling, Polymorphism, Interfaces

Oracle Java Tutorials: "Polymorphism", "Exceptions"

Programming exercises for this material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 10

---

## Discussion

`processMonthEnd()` checks `instanceof InterestBearing`, not `instanceof SavingsAccount`, so a new interest-bearing account kind is automatically processed too without changing this method. Explain in your own words: what would happen (and what code would need to change) if that check were written as `instanceof SavingsAccount`, and Bank Mini then added a new interest-bearing account kind named `DepositAccount`?
