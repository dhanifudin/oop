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
  table.small {
    font-size: 0.75em;
  }
  th, td {
    padding: 4px 10px;
  }
  th {
    background: #1d4ed8;
    color: #fff;
  }
  code {
    background: #f1f5f9;
    color: #0f172a;
  }
  pre {
    font-size: 0.65em;
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
    gap: 24px;
  }
  .cols > div {
    flex: 1;
  }
  .flow {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    margin-top: 30px;
    flex-wrap: wrap;
  }
  .flow .box {
    background: #1d4ed8;
    color: #fff;
    padding: 12px 18px;
    border-radius: 8px;
    font-weight: bold;
    font-size: 0.85em;
  }
  .flow .arrow {
    font-size: 1.4em;
    color: #1d4ed8;
  }
  .footnote {
    font-size: 0.55em;
    color: #64748b;
    position: absolute;
    bottom: 20px;
  }
  img {
    display: block;
    margin: 0 auto;
  }
---

<!-- _class: lead -->

# Object-Oriented Programming
## RTI253007 &nbsp;|&nbsp; D-IV Informatics Engineering

Meeting 11: **SOLID Principles**

Five principles that keep your class design standing up to change

---

## What You'll Learn Today

- Why a class design can "rot" over time
- The five SOLID principles: SRP, OCP, LSP, ISP, DIP
- One case study, refactored step by step

<div class="term-box">
<b>Prerequisite:</b> today's material builds on inheritance, abstract classes, interfaces, and polymorphism you've already covered in earlier meetings. SOLID isn't some brand-new concept, it's just using those same tools with more discipline.
</div>

---

<!-- _class: divider -->

# Part 1
## Why Designs Rot

---

## Three Symptoms of a Bad Design

<div class="warn-box">
<b>Rigidity:</b> one small change forces you to edit a bunch of classes that shouldn't even be related.
</div>

<div class="warn-box">
<b>Fragility:</b> you change one part of the code, and some other part that looked totally unrelated breaks.
</div>

<div class="warn-box">
<b>Immobility (hard to reuse):</b> a class is so tightly wired to its context that it's hard to use anywhere else.
</div>

---

## Case Study: an `OrderProcessor` That's Gotten Out of Hand

Picture an order-processing application that's been running for a while. One class, `OrderProcessor`, now handles everything order-related at once.

![OrderProcessor class diagram before refactoring](../assets/uml/p11-before.png)

---

## The Five SOLID Principles

<table class="small">
<tr><th>Letter</th><th>Name</th><th>Core Idea</th></tr>
<tr><td>S</td><td>Single Responsibility</td><td>One class, one reason to change</td></tr>
<tr><td>O</td><td>Open/Closed</td><td>Open for extension, closed for modification</td></tr>
<tr><td>L</td><td>Liskov Substitution</td><td>A subclass must be able to stand in for its superclass</td></tr>
<tr><td>I</td><td>Interface Segregation</td><td>Small, specific interfaces, not one giant interface</td></tr>
<tr><td>D</td><td>Dependency Inversion</td><td>Depend on abstractions, not concrete details</td></tr>
</table>

Across this deck, we'll fix `OrderProcessor` one principle at a time. The full code is in the jobsheet.

---

<!-- _class: divider -->

# Part 2
## SRP: Single Responsibility Principle

---

## "One Reason to Change"

<div class="term-box">
A class should only have ONE reason to change. If you can picture more than one different group of needs, each of which could trigger a change to this class, that's a sign SRP is being violated.
</div>

The original `OrderProcessor` changes when the discount rules change, or when the storage method changes, or when the receipt format changes. Three different reasons, all piled into one class.

<div class="tip-box">
Quick heuristic: try describing the class's job in one sentence. If you need the word "and" to string together two different jobs, be suspicious of SRP.
</div>

---

## After: Split by Responsibility

![Class diagram: OrderProcessor orchestrating three collaborators](../assets/uml/p11-srp.png)

`OrderProcessor` now just orchestrates `DiscountCalculator`, `OrderRepository`, and `ReceiptPrinter`, it no longer needs to know the details of how each one calculates, stores, or formats things.

---

<!-- _class: divider -->

# Part 3
## OCP: Open/Closed Principle

---

## "Open for Extension, Closed for Modification"

<div class="term-box">
When a new need comes up, ideally you should be able to just ADD new code, not EDIT old code that's already been tested.
</div>

```java
if (customer.getType().equals("REGULAR")) { ... }
else if (customer.getType().equals("VIP")) { ... }
else { ... }
```

<div class="warn-box">
Every time there's a new customer type, you're forced to reopen this method and bolt on another <code>else if</code> branch. Old code that already works gets touched too, and that's always a risk of breaking something else.
</div>

---

## After: A Strategy via Interface

![Class diagram: DiscountPolicy and its implementations](../assets/uml/p11-ocp.png)

<div class="tip-box">
Adding a new customer type now only needs ONE new class implementing <code>DiscountPolicy</code>, plus one line to register it. <code>DiscountCalculator</code> and <code>OrderProcessor</code> don't need to be touched at all.
</div>

---

<!-- _class: divider -->

# Part 4
## LSP: Liskov Substitution Principle

---

## "A Subclass Must Be Able to Stand in for Its Superclass"

<div class="term-box">
Code that works with an <code>Order</code>-typed object should keep working correctly, even when the object it's actually handed is an instance of any subclass of <code>Order</code>.
</div>

```java
for (Order o : orders) {
    System.out.println(o.ship());   // blows up on DigitalOrder!
}
```

<div class="warn-box">
This calling code treats every <code>Order</code> the same way (it just calls <code>ship()</code>), but <code>DigitalOrder</code> throws a surprise <code>UnsupportedOperationException</code>. The substitution fails, that's an LSP violation.
</div>

---

## After: Split the Capability Out via Interface

![Class diagram: Shippable separates PhysicalOrder from DigitalOrder](../assets/uml/p11-lsp.png)

<div class="tip-box">
Rule of thumb: if a subclass overrides a method just to throw <code>UnsupportedOperationException</code>, be suspicious of LSP.
</div>

---

<!-- _class: divider -->

# Part 5
## ISP: Interface Segregation Principle

---

## "Small Interfaces, Not One Fat Interface"

![Diagram: a fat interface vs. three small interfaces](../assets/uml/p11-isp.png)

<div class="tip-box">
You could call ISP the SRP of interfaces: one interface, one capability. <code>InvoicePrinter</code> is no longer forced to implement a method that has nothing to do with it.
</div>

---

<!-- _class: divider -->

# Part 6
## DIP: Dependency Inversion Principle

---

## "Depend on Abstractions, Not on Details"

```java
private OrderRepository orderRepository = new OrderRepository();
```

<div class="warn-box">
<code>OrderProcessor</code> (a high-level class holding business logic) directly depends on a concrete implementation detail (how data gets written to a file). The moment you want to swap the storage method, or test <code>OrderProcessor</code> without touching a file at all, you're stuck.
</div>

---

## After: Depend on an Interface, Injected via the Constructor

![Class diagram: OrderProcessor depending on the OrderRepository interface](../assets/uml/p11-dip.png)

<div class="tip-box">
Swapping <code>FileOrderRepository</code> for <code>InMemoryOrderRepository</code>? Just change ONE line in <code>Main</code>. <code>OrderProcessor</code> doesn't need to know which implementation is being used, it only depends on the <code>OrderRepository</code> interface (constructor injection).
</div>

---

## Recap: One Project, Five Principles

![Full class diagram after the SOLID refactoring](../assets/uml/p11-final.png)

One case study, five principles, all working together.

---

<!-- _class: lead -->

# References & Discussion

Robert C. Martin, *Agile Software Development: Principles, Patterns, and Practices*

Robert C. Martin, *Clean Architecture*

The Meeting 11 practicum jobsheet is available at `jobsheets/en/pertemuan-11-solid-principles.md`

Discussion: which SOLID principle do you catch yourself violating most often, without even noticing, in your midterm code?
