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

Rewriting inherited behavior, and adding a new version of a method

---

## What You Will Learn

- How a subclass rewrites inherited behavior to fit its own needs
- How to still make use of old behavior while adding something new on top of it
- How to prevent a behavior from being rewritten at all
- Adding several ways to call the same operation, and how this differs from rewriting inherited behavior
- Applying this to Bank Mini: a different withdrawal rule for each account type, and a deposit with or without a note

<div class="tip-box">
Hands-on practice for today's material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 7.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Method overriding, rewriting inherited behavior
- **Session 2 (50')**: Method overloading, adding a new version of a method
- **Session 3 (50')**: Applying overriding to Bank Mini
- **Session 4 (50')**: Applying overloading to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Method Overriding

Session 1 of 4

---

## Rewriting an Inherited Method

Meeting 6 showed that a subclass inherits its superclass's methods as-is. Sometimes the inherited behavior does not fit a particular subclass: `Sedan` and `Truck` both inherit `honk()` from `Vehicle`, but of course their horn sounds should differ.

<div class="term-box">
<b>Overriding</b> is rewriting a superclass's method inside a subclass, with the exact same name and parameter list (signature). Java calls the version belonging to the object's actual type at runtime, not the version declared by the variable's type.
</div>

---

## What Is a Signature?

Two people can share the same name; they are told apart through other data (birth date, address). Two methods within one class can also share the same name, as long as Java can tell them apart through their parameter list, which is what is called a signature.

![h:220 Anatomy of a signature: honk(int times), the method's name and parameter list, separate from visibility and return type](../assets/illustrations/method-signature-anatomy.svg)

<div class="term-box">
A method's <b>signature</b> consists of its name and its parameter list (count, order, and data type). The return type and visibility are NOT part of the signature.
</div>

---

## Why Does This Matter?

Imagine a payment system with dozens of method types (credit card, bank transfer, e-wallet), with a superclass `PaymentMethod` whose subclasses keep growing over time. Without overriding, every time a new payment type is added, the code that processes payments would also have to change to handle that new case, risking breaking other payment types that already work fine.

<div class="term-box">
Overriding lets every subclass provide its own behavior without changing a single line of the superclass's code or any other existing subclass. This "open for extension, closed for modification" principle is one of the five SOLID principles, called the Open/Closed Principle, covered further in Meeting 11.
</div>

---

## Code Example: Overriding `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Truck extends Vehicle {
    @Override
    public String honk() { return "Honk honk!"; }
}
```

---

## The `@Override` Annotation

<div class="tip-box">
The <code>@Override</code> annotation tells the compiler to check that a method genuinely rewrites a superclass method with the exact same signature. If there is a typo in the method name, the compiler raises an error instead of silently creating a new method that is never called.
</div>

<div class="warn-box">
<code>@Override</code> itself is not syntactically required, but it is always included as good practice: an error caught early is far cheaper to fix than a bug only discovered once the program is running.
</div>

---

## Calling the Superclass Version: `super.method(...)`

<div class="term-box">
An overriding method may still call its superclass's version through <code>super.methodName(...)</code>, usually to add new behavior without rewriting the entire method body from scratch.
</div>

This pattern is often used when a subclass only wants to add a bit of extra information to existing behavior, for example printing an additional line after the line already printed by the superclass.

---

## Code Example: Calling `super.honk()`

```java
class Truck extends Vehicle {
    @Override
    public String honk() {
        return super.honk() + " (loud horn)";
    }
}
```

---

## Preventing a Method from Being Overridden: `final`

<div class="term-box">
A method marked <code>final</code> cannot be overridden by any subclass. Java raises a compile error if a subclass tries to rewrite that method.
</div>

<div class="warn-box">
Use <code>final</code> sparingly: only when there is a strong reason a behavior must always be identical across every subclass. Marking every method as <code>final</code> actually removes inheritance's main benefit, a subclass's ability to adapt its behavior.
</div>

---

## Common Mistake: Thinking the Parameters May Differ

<div class="warn-box">
<b>Wrong:</b> writing <code>public String honk(String mode)</code> in <code>Truck</code>, thinking this overrides <code>Vehicle</code>'s <code>honk()</code>, when the parameter list is actually different.
</div>

**Correct:** `honk(String mode)` is not an override, it is a NEW method that happens to share the same name. The signature (name and parameters) must be exactly identical; `@Override` raises a compile error when they do not match, catching this mistake early.

---

## Exercise

Class `Truck` writes method `public String honk(int times)`, while `Vehicle` has `public String honk()`.

Is this overriding? Explain, then predict what happens if `Truck` marks this method with `@Override`.

---

## Exercise Answer

**Not overriding**, since the parameters differ (`honk()` vs `honk(int times)`). If marked `@Override`, the compiler raises an error, since there is no `honk(int times)` method in `Vehicle` to rewrite.

---

## Part 1 Summary

- Overriding rewrites a superclass's method in a subclass, with a signature that must match exactly.
- `@Override` makes the compiler check that the signature genuinely matches, catching mistakes early.
- `super.method(...)` calls the superclass's version; `final` prevents a method from being overridden at all.

Next: Part 2 covers method overloading, a situation where a different parameter list turns out not to be overriding at all.

---

<!-- _class: divider -->

# Part 2
## Method Overloading

Session 2 of 4

---

## Same Name, Different Parameters

<div class="term-box">
<b>Overloading</b> is adding a method with the same name but a different parameter list (count or type), in other words: same name, different signature. The compiler chooses which version gets called based on the arguments given at the call site, decided when the program is compiled, not while it runs.
</div>

A common example: `println()` on `System.out` is actually dozens of overloaded methods, each accepting a different argument type (`String`, `int`, `double`, `boolean`, and so on), yet all called under the same name.

---

## Code Example: Overloading `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
    public String honk(int times) {
        return honk().repeat(times);
    }
}
```

---

## Why Does This Matter?

Without overloading, every variation of how to call an operation would need a different method name, for example `printString()`, `printInt()`, `printDouble()`, `printBoolean()`. The more data type variations there are, the harder it becomes for other programmers to remember which name to use for a given need.

<div class="term-box">
Overloading makes a class's API feel natural to use: one single method name, <code>println(...)</code>, is enough for every data type variation, with the compiler determining which version fits based on the arguments given.
</div>

---

## Overriding vs Overloading

![h:300 Comparing overriding and overloading](../assets/illustrations/override-vs-overload.svg)

The two sound similar in name, but their mechanisms are very different: overriding replaces an inherited method's behavior in a subclass (decided at runtime), while overloading adds a new version of a method within the same class (decided at compile time).

---

## Common Mistake: Thinking the Return Type Alone Is Enough

<div class="warn-box">
<b>Wrong:</b> writing <code>public String honk()</code> and <code>public int honk()</code> in the same class, thinking both are a valid overload since their return types differ.
</div>

**Correct:** the return type alone is not enough for overloading. Java distinguishes an overload through its parameter list; two methods with identical parameters but different return types cause a "duplicate method" compile error.

---

## Exercise

For each pair of methods below, within the same class, determine **valid overloading** or **compile error**:

1. `honk()` and `honk(int times)`
2. `String getName()` and `int getName()`
3. `setScore(int score)` and `setScore(double score)`

---

## Exercise Answer

1. **Valid overloading**, the parameters differ (in count).
2. **Compile error**, the parameters are identical (empty); only the return type differs, which is not enough.
3. **Valid overloading**, the parameter types differ (`int` vs `double`).

---

## Part 2 Summary

- Overloading adds a new version of a method with different parameters, chosen by the compiler based on the arguments at the call site.
- The return type alone is never enough to distinguish an overload; the parameters must differ.
- Overriding replaces inherited behavior (runtime); overloading adds a new version (compile time).

Next: Part 3 applies overriding to Bank Mini's withdrawal rules.

---

<!-- _class: divider -->

# Part 3
## Applying Overriding to Bank Mini

Session 3 of 4

---

## canWithdraw() Overridden by Each Account Type

![h:280 Account with canWithdraw as the override point, SavingsAccount and CheckingAccount rewriting their own rules](../assets/uml/p07-account-hierarchy.png)

Meeting 6 showed that `CheckingAccount`'s `overdraftLimit` did not yet affect anything, since the inherited `withdraw()` only knew one generic rule. With `canWithdraw()` overridden, `SavingsAccount` now maintains a minimum balance and `CheckingAccount` can genuinely be withdrawn from beyond its balance, up to its overdraft limit.

---

## Code Example: `canWithdraw()` Differing by Account Type

```java
class SavingsAccount extends Account {
    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= 50000;  // minimum balance
    }
}

class CheckingAccount extends Account {
    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= -overdraftLimit;
    }
}
```

---

## Common Mistake: Reducing Visibility While Overriding

<div class="warn-box">
<b>Wrong:</b> writing <code>private boolean canWithdraw(double amount)</code> in <code>SavingsAccount</code>, thinking this overrides <code>Account</code>'s <code>protected</code> method.
</div>

**Correct:** Java does not allow an override to reduce visibility. An overriding method must be equally or more open than its superclass's (`protected` may become `public`, but never `private`); this code fails to compile.

---

## Exercise

`SavingsAccount` maintains a minimum balance of Rp 50,000. Given a current `balance` of Rp 100,000, determine the result of `withdraw(60000)`: does it succeed or get rejected? Explain through `canWithdraw()`.

---

## Exercise Answer

**Rejected.** `SavingsAccount`'s `canWithdraw()` checks `balance - amount >= 50000`. With `balance` 100000 and `amount` 60000, the result is 40000, less than 50000, so `canWithdraw()` returns `false` and `withdraw()` is rejected.

---

## Part 3 Summary

- `canWithdraw()` is overridden by each `Account` subclass, giving each a different withdrawal rule without changing `withdraw()` itself.
- An overriding method must not reduce visibility compared to its superclass.
- The overriding rules from Part 1 (an exact matching signature, `@Override`) apply here in exactly the same way.

Next: Part 4 applies overloading to Bank Mini's deposit method.

---

<!-- _class: divider -->

# Part 4
## Applying Overloading to Bank Mini

Session 4 of 4

---

## An Overloaded deposit()

<div class="term-box">
<code>Account</code> gets a second version of <code>deposit(double amount)</code>, namely <code>deposit(double amount, String note)</code>, which accepts an extra note and then calls the first version for its storage logic. Both are different methods within the same class, chosen by Java based on the number of arguments given at the call site.
</div>

---

## Code Example: `deposit()` with Two Versions

```java
class Account {
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean deposit(double amount, String note) {
        System.out.println("Note: " + note);
        return deposit(amount);
    }
}
```

---

## Exercise

The calls `account.deposit(50000)` and `account.deposit(50000, "monthly salary")` are both valid.

Which method does Java call for each, and what does Java base that choice on?

---

## Exercise Answer

`account.deposit(50000)` calls the one-parameter version; `account.deposit(50000, "monthly salary")` calls the two-parameter version. Java chooses based on the number and type of arguments given at the call site, decided at compile time, not while the program runs.

---

## Part 4 Summary

- `deposit()` is overloaded: the two-parameter version accepts an extra note, then calls the one-parameter version for its core logic.
- Java chooses which overload to use based on the number and type of arguments at the call site, not while the program runs.

---

## Meeting 7 Summary

- Overriding rewrites inherited behavior with an exactly matching signature; overloading adds a new version with different parameters.
- `@Override` catches a signature mistake early; `super.method(...)` still makes use of old behavior; `final` prevents overriding entirely.
- Bank Mini uses overriding for each account type's withdrawal rule, and overloading for a deposit with or without a note.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Object-Oriented Programming: Overriding, Overloading chapter

Oracle Java Tutorials: "Overriding and Hiding Methods", "Defining Methods" (overloading)

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 7

---

## Discussion

`Employee.describe()` (Meeting 6) is marked `final` because its format must always stay consistent across every kind of employee. Look again at the `Account` methods you just built (`printInfo()`, `canWithdraw()`, `deposit()`, and others): do you think any of them also deserve to be marked `final`? Explain your reasoning, or explain why none of them need it.
