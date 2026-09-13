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

Meeting 1: **Introduction to OOP Concepts**

Why the software world thinks in objects

---

## What You Will Learn

- The fundamental difference between the procedural paradigm and the object paradigm
- How real-world things have both data and behavior at once
- The four big ideas underlying object-oriented programming, and why all four matter throughout this semester
- An overview of the semester-long case study, and how the concept course and the practicum course complement each other

<div class="tip-box">
This course (RTI253007) focuses on concepts. All programming exercises for today's material are available in the companion course, <b>Practicum: Object-Oriented Programming (RTI253008)</b>, Meeting 1 jobsheet.
</div>

---

## Today's Session Map

- **Session 1 (50')**: From procedural programming to object orientation
- **Session 2 (50')**: Objects in the real world, data and behavior
- **Session 3 (50')**: The four pillars of OOP
- **Session 4 (50')**: The Bank Mini case study and course structure

---

<!-- _class: divider -->

# Part 1
## From Procedural to Object-Oriented

Session 1 of 4

---

## As a Program Grows Larger

A simple program can be written as a collection of separate data and functions. As long as the program stays small, this approach remains easy to follow. As the program grows larger, however, a question begins to surface: which functions are allowed to change which data, and how does the program stay consistent across every one of its parts?

<div class="term-box">
A <b>programming paradigm</b> is a way of thinking about how a program is structured. The two paradigms discussed today are <b>procedural</b> (data and functions kept separate) and <b>object-oriented</b> (data and functions bundled into a single unit).
</div>

---

## Why Does This Matter?

Imagine a banking application with hundreds of functions that can all change balance data directly. If one function changes, say a new administrative fee rule is added, nothing guarantees that every other function that also touches the balance still behaves correctly. Bugs like this are hard to trace, because their cause can sit far away from where the symptom appears.

<div class="term-box">
This is one reason nearly every large-scale system in industry, from banking applications to mobile apps, is built in an object-oriented style. It is not merely a matter of writing style, but a way of controlling complexity that keeps growing along with the size of the program.
</div>

---

## Procedural vs Object-Oriented

![h:300 Shared data freely accessed by many functions, compared with an object that guards its own data](../assets/illustrations/paradigm-procedural-vs-oo.svg)

<div class="term-box">
In the <b>procedural</b> style, data is stored separately from the functions that operate on it, so any function can potentially change that data without a clear boundary.
</div>

<div class="term-box">
In the <b>object-oriented</b> style, data and the functions that operate on it are bundled into a single unit called an <b>object</b>. Other objects can only interact with it through the methods it provides, not by changing its data directly.
</div>

---

## A Concrete Example: Turning On a Lamp

Procedural style: a free-standing "turn on the lamp" function changes the lamp's status directly from outside; anyone can call it at any time.

Object-oriented style: you call the "turn on" command that belongs to the lamp itself; the lamp changes its own status, rather than outside code changing it directly.

<div class="tip-box">
The difference is not WHAT happens (the lamp still turns on), but WHO is responsible for changing its data.
</div>

---

## A Quick Comparison Table

| | Procedural | Object-Oriented |
|---|---|---|
| Data is stored in | separate variables, freely accessible | inside an object, protected by that object |
| What changes the data | any function, from anywhere | the object itself, through its methods |
| Risk as the program grows | hard to trace who changed what | changes to data are more controlled |

---

## Common Mistake: Thinking OOP Is Just a Writing Style

<div class="warn-box">
<b>Wrong:</b> assuming object orientation just means "mentioning the word object" in the code, while the data itself stays freely accessible and changeable from anywhere.
</div>

**Correct:** object orientation means data is genuinely bundled together with the functions that operate on it, and can only be changed through those functions. If the data can still be freely changed from outside, that is still the procedural style, no matter how much it is dressed up in object terminology.

---

## Exercise

A student-records program stores name, student ID, and GPA as separate variables. The program also has several free-standing functions such as "print record" and "compute honor level" that can be called from anywhere and can change those variables directly.

Is this program procedural or object-oriented in style? Explain your reasoning.

---

## Exercise Answer

**Procedural.** The data (name, student ID, GPA) is stored separately from the functions that operate on it, and any function can access it without restriction. The hallmark of the object-oriented style is that data and functions are bundled into a single unit, with access controlled through methods, rather than free variables that can be changed from anywhere.

---

## Part 1 Summary

- A procedural program keeps data and functions separate; an object-oriented program bundles both into a single unit called an object.
- The larger a program grows, the more important it becomes to control who is allowed to change which data.
- Merely using the word "object" is not enough; the data must genuinely be protected and changed only through methods.

Next: Part 2 takes a closer look at how objects mirror the way real-world things work.

---

<!-- _class: divider -->

# Part 2
## Objects in the Real World: Data and Behavior

Session 2 of 4

---

## Everything Around Us Is an Object

A simple example: a lamp has data (on or off) and behavior (turned on, turned off). A car has data (speed, fuel level) and behavior (accelerated, braked). A bank account has data (balance) and behavior (deposit, withdraw).

<div class="tip-box">
The same pattern keeps repeating: every object has <b>data that belongs to itself</b> and <b>things it can do with that data</b>. Object-oriented programming carries this pattern directly into code.
</div>

---

## More Examples, Summarized in a Table

| Thing | State (data) | Behavior |
|---|---|---|
| Lamp | on or off | turned on, turned off |
| Car | speed, fuel level | accelerated, braked |
| Bank account | balance | deposit, withdraw |

<div class="term-box">
The term <b>state</b> for the data an object holds, and <b>behavior</b> for what an object can do, will be used frequently throughout this course.
</div>

---

## Why Does This Matter?

Imagine explaining an application's design to a teammate, but its data and behavior are discussed as two separate things with no clear relationship between them. The discussion drags on, because there is no single clear "unit" to talk about together.

<div class="term-box">
By bundling data and behavior into a single object, a team gains a shared language for discussing a system's design: naming the object is enough for everyone to know exactly what data and behavior are being referred to. This is what keeps large systems, built by many people, understandable as a whole.
</div>

---

## Common Mistake: Thinking an Object Must Be a Physical Thing

<div class="warn-box">
<b>Wrong:</b> assuming that an "object" in programming must be a physical thing, such as a lamp or a car.
</div>

**Correct:** an object can also represent a concept with no physical form, such as a transaction, an order, or a schedule. As long as something has data and behavior that belong to it, it can be modeled as an object.

---

## Exercise

Pick one thing you use every day (for example, a bicycle, a phone, or a thermos). Name at least three states (data) and two behaviors of that thing.

---

## Exercise Answer

There is no single correct answer, since the thing is your own choice. For a bicycle: its states might include speed, tire pressure, and current gear; its behaviors might include pedaled and braked. Check your own answer: is every state genuinely data that belongs to that thing, and is every behavior genuinely something the thing itself can do?

---

## Part 2 Summary

- Every thing, whether physical or an abstract concept, has state (data) and behavior that belong to it.
- Objects in programming mirror this pattern: bundling state and behavior into a single unit.
- Bundling state and behavior gives a team a shared language for discussing a system's design.

Next: Part 3 introduces the four pillars of OOP, the four ideas that make the object-oriented style actually work.

---

<!-- _class: divider -->

# Part 3
## The Four Pillars of OOP

Session 3 of 4

---

## The Four Pillars of OOP

![h:260 Encapsulation, inheritance, polymorphism, and abstraction holding up one roof called OOP](../assets/illustrations/oop-four-pillars.svg)

Each of these four pillars is examined one at a time, one pillar across several meetings, throughout this semester. Today's meeting only introduces the names briefly; deeper explanations follow in later meetings.

---

## Encapsulation: Protecting Data

![h:280 An object's private data can only be reached through its methods, no shortcuts allowed](../assets/illustrations/capsule-shield.svg)

<div class="term-box">
<b>Encapsulation</b> (covered in depth in Meeting 3): an object's data is hidden, and can only be accessed through the methods the object itself provides. Like a safe, its contents can only be retrieved through its official door, not forced open from outside.
</div>

---

## Inheritance: Inheriting and Extending

![h:220 Sedan and Truck both inherit from Vehicle](../assets/illustrations/inheritance-tree.svg)

<div class="term-box">
<b>Inheritance</b> (covered in depth in Meetings 6-7): a class can inherit from and extend another class, so existing code can be reused. Like a derivative recipe, a new recipe only needs to add extra ingredients, without rewriting the entire base recipe.
</div>

---

## Polymorphism: One Message, Many Responses

![h:210 One call to area() produces different results depending on whether the object is a Circle or a Square](../assets/illustrations/polymorphic-dispatch.svg)

<div class="term-box">
<b>Polymorphism</b> (covered in depth in Meeting 10): the same message can produce different behavior, depending on which object receives it. Like giving a "make a sound" command to a cat and a dog, the result differs even though the command is identical.
</div>

---

## Abstraction: Hiding Detail

<div class="term-box">
<b>Abstraction</b> (covered in depth in Meeting 9): only the details that matter to the user are exposed, implementation detail stays hidden. Like a television remote, you only need to press the power button, with no need to know the circuitry behind it.
</div>

---

## All Four Work Together, Not in Isolation

In a real application, these four pillars usually appear all at once, complementing one another. A point-of-sale application, for instance, might protect price data through encapsulation, share common traits across product types through inheritance, compute different discounts through polymorphism, and hide a complicated tax formula through abstraction, all within the same design.

---

## Common Mistake: Thinking the Four Pillars Stand Alone

<div class="warn-box">
<b>Wrong:</b> studying the four pillars as four separate, unrelated features, then getting confused about which one to use when.
</div>

**Correct:** the four pillars complement one another within a single design. The more practice you get building real applications, the clearer it becomes how all four show up together, rather than being picked one at a time in isolation.

---

## Exercise

Match each statement below to one of the four pillars of OOP:

1. An application hides a complicated tax formula behind a single "compute total" command; its users never need to know the formula's details.
2. A bank account does not allow its balance to be changed directly from outside; the only way to change it is through deposit and withdrawal operations that validate the amount.
3. Electric cars and gasoline cars both inherit the common traits of "car", so both can automatically "drive" and "stop" without being redefined from scratch.
4. A "compute shipping cost" command produces a different number depending on the shipping type, regular or express, even though the command itself is exactly the same.

---

## Exercise Answer

1. **Abstraction**, since only the end result is exposed, and the formula's detail stays hidden.
2. **Encapsulation**, since the data (balance) is protected, and can only be changed through an official operation that validates it.
3. **Inheritance**, since the common traits are inherited, and do not need to be rewritten for every type.
4. **Polymorphism**, since the same command produces different behavior depending on the kind of object involved.

---

## Part 3 Summary

- The four pillars of OOP: encapsulation (protecting data), inheritance (inheriting), polymorphism (one message, many responses), abstraction (hiding detail).
- All four usually appear together within a single real application, complementing one another rather than being chosen one at a time.
- Today is only an introduction to the names and analogies; each pillar is covered in depth in upcoming meetings.

Next: Part 4 introduces the project you will build throughout the semester to put all of this into practice.

---

<!-- _class: divider -->

# Part 4
## Bank Mini and Course Structure

Session 4 of 4

---

## One Project, Growing All Semester

![Bank Mini roadmap: from a single Account class to a GUI application with a database](../assets/illustrations/bank-mini-roadmap.svg)

<div class="term-box">
Throughout this semester, the practicum jobsheets build one and the same application, <b>Bank Mini</b>, step by step. Starting from a single, very simple <code>Account</code> class, this application grows to include several account types, transaction history, a GUI, and a database connection.
</div>

---

## Why a Single Case Study?

If every meeting used a different example, you would never see how these concepts work together within one real application, only isolated snippets of code standing on their own.

<div class="tip-box">
Every new concept is still introduced first through a small, self-contained example, as in Parts 1 through 3 above, so the concept stands out clearly without the distraction of a different domain's detail. That same concept is then applied to Bank Mini, one application that keeps growing from meeting to meeting.
</div>

---

## How Concept and Practicum Complement Each Other

This course is split into two: **RTI253007** (the concept course, where you are sitting right now) covers the ideas and the reasoning behind them through slides like these. **RTI253008** (the practicum) is where you actually write the code yourself, through jobsheets, usually applying that same concept to the Bank Mini project.

<div class="term-box">
The Practicum Meeting 1 jobsheet starts the Bank Mini project from scratch: setting up the working environment, then writing the first program. Today's concept becomes the foundation for understanding WHAT you are building there, rather than simply following the steps one by one.
</div>

---

## Exercise

By the end of the semester, the Bank Mini application you build will be able to record several account types, process transactions, display data through an application window, and store everything in a database.

In your opinion, what is the most sensible first class to build in Meeting 2, before any of those capabilities exist yet? Explain your reasoning.

---

## Exercise Answer

There is no single absolute answer, but the expected direction: a simple account class, which you will later come to know as Account, since almost every other capability (account types, transactions, display, database) ultimately revolves around the data of a single account. Building the most central piece first, then adding capabilities around it, is a pattern that will repeat throughout this semester.

---

## Meeting 1 Summary

- Object orientation bundles data and functions into a single unit, unlike the procedural style, which keeps the two separate.
- Every thing, whether physical or an abstract concept, has state (data) and behavior that belong to it.
- The four pillars of OOP (encapsulation, inheritance, polymorphism, abstraction) usually appear together within a single design, covered in depth one at a time in upcoming meetings.
- A single case study, Bank Mini, accompanies the practicum throughout the semester, from a single class to a GUI and database application.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, the Introduction and Classes and Objects chapters

Oracle Java Tutorials: "Object-Oriented Programming Concepts"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 1

---

## Discussion

Pick a different everyday thing from the one you chose in the Part 2 exercise. In your view, does bundling that thing's data and behavior into a single object make more sense than storing its data separately from the functions that operate on it, as in the procedural style from Part 1? Give a concrete reason, not simply "because that is how OOP works".
