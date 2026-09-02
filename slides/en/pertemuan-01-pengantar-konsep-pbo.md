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
- The four pillars of object-oriented programming (OOP): encapsulation, inheritance, polymorphism, abstraction
- An overview of the case study used throughout the whole semester
- How the concept course (RTI253007) and the practicum course (RTI253008) complement each other

<div class="tip-box">
This course (RTI253007) focuses on concepts. All programming exercises for today's material are provided in the companion course, <b>Practicum: Object-Oriented Programming (RTI253008)</b>, Meeting 1 jobsheet.
</div>

---

<!-- _class: divider -->

# Part 1
## Two Ways of Thinking About a Program

---

## As a Program Grows Larger

A simple program can be written as a collection of separate data and functions. As long as the program stays small, this approach remains easy to follow. As the program grows larger, however, a question begins to surface: which functions are allowed to change which data, and how is consistency maintained across every part of the program?

<div class="term-box">
A <b>programming paradigm</b> is a way of thinking about how a program is structured. The two paradigms discussed today are <b>procedural</b> (data and functions kept separate) and <b>object-oriented</b> (data and functions bundled into a single unit).
</div>

---

## Why Does This Matter?

Imagine a banking application with hundreds of functions that all access balance data directly. When one function changes, say a new administrative fee rule is added, no single point guarantees that every other function that also touches the balance still behaves correctly. Bugs like this are hard to trace, because their cause can sit far away from where the symptom appears.

<div class="warn-box">
The larger a program grows, the more places can change the same data, and the harder it becomes to keep all of them consistent with one another.
</div>

<div class="term-box">
This is one reason nearly every large-scale software system in industry, from banking applications to mobile apps to enterprise systems, is built in an object-oriented language. It is not merely a stylistic preference, but a way of controlling complexity that keeps growing with the size of the program.
</div>

---

## Procedural vs Object-Oriented

![h:300 Shared data freely accessed by many functions, compared with an object that guards its own data](../assets/illustrations/paradigm-procedural-vs-oo.svg)

<div class="term-box">
In the <b>procedural</b> style, data is stored separately from the functions that operate on it, so any function is potentially able to change that data without a clear boundary.
</div>

<div class="term-box">
In the <b>object-oriented</b> style, data and the functions that operate on it are bundled into a single unit called an <b>object</b>. Other objects can only interact with it through the methods it provides, not by changing its data directly.
</div>

</div>
</div>

---

## Everything Around Us Is an Object

A simple example: a lamp has data (on or off) and behavior (turned on, turned off). A car has data (speed, fuel level) and behavior (accelerated, braked). A bank account has data (balance) and behavior (deposit, withdraw).

<div class="tip-box">
The same pattern keeps repeating: every object has <b>data that belongs to itself</b> and <b>things it can do with that data</b>. Object-oriented programming carries this pattern directly into code.
</div>

<div class="warn-box">
Quick discussion: name one other object around you, then identify its data and its behavior.
</div>

---

<!-- _class: divider -->

# Part 2
## The Four Pillars of OOP

---

## The Four Pillars of OOP

![Encapsulation, inheritance, polymorphism, and abstraction holding up one roof called OOP](../assets/illustrations/oop-four-pillars.svg)

Each of these four pillars is examined one at a time, one pillar across several meetings, throughout this semester. Today's meeting only introduces the names; deeper explanations follow in later meetings.

---

## A Preview of All Four Pillars

<div class="term-box">
<b>Encapsulation</b> (Meeting 3): an object's data is hidden and can only be accessed through the methods the object itself provides.
</div>

<div class="term-box">
<b>Inheritance</b> (Meetings 6-7): a class can inherit from and extend another class, so existing code can be reused.
</div>

<div class="term-box">
<b>Polymorphism</b> (Meetings 9, 12): the same message can produce different behavior, depending on which object receives it.
</div>

<div class="term-box">
<b>Abstraction</b> (Meetings 10-11): only the details that matter to the user are exposed, implementation details stay hidden.
</div>

---

<!-- _class: divider -->

# Part 3
## A Semester-Long Case Study: Bank Mini

---

## One Project, Growing All Semester

![Bank Mini roadmap: from a single Account class to a GUI application with a database](../assets/illustrations/bank-mini-roadmap.svg)

<div class="term-box">
Throughout this semester, the practicum jobsheets build one and the same application, <b>Bank Mini</b>, step by step. Starting from a single, very simple <code>Account</code> class, this application grows to include several account types, transaction history, a GUI, and a database connection.
</div>

---

## Why a Single Case Study?

<div class="tip-box">
Every new concept is still introduced first through a small, self-contained example (such as a lamp, a thermostat, or an animal), so the concept stands out clearly without the distraction of a different domain's details. The same concept is then applied to Bank Mini, so you see how these concepts actually work together in one real application, rather than as isolated pieces of code.
</div>

By the end of the semester, the Bank Mini application you build will be able to record several account types, process transactions, display data through an application window, and store everything in a database.

---

<!-- _class: lead -->

# References and Discussion

Deitel, *Java How to Program*, the Introduction and Classes and Objects chapters

Oracle Java Tutorials: "Object-Oriented Programming Concepts"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 1

Discussion: which other part of everyday life do you think could be modeled more naturally as an object than as a collection of separate data and functions?
