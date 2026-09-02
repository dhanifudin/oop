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

Meeting 2: **Classes and Objects**

Foundations of object-oriented programming

---

## What You Will Learn

- The difference between a **class** and an **object**
- The anatomy of a class: attributes, methods, constructor
- What actually happens in memory when an object is created
- References versus objects, and the risk of empty (null) references
- How to read a simple UML class diagram

<div class="term-box">
<b>Prerequisite (Meeting 1):</b> students are already familiar with the difference between the procedural paradigm (data and functions kept separate) and the object paradigm (data and functions combined into a single unit). This meeting examines that unit in greater depth.
</div>

<div class="tip-box">
This course (RTI253007) focuses on concepts. All programming exercises for today's material are provided in the companion course, <b>Practicum: Object-Oriented Programming (RTI253008)</b>, Meeting 2 jobsheet.
</div>

---

<!-- _class: divider -->

# Part 1
## From the Real World to a Class

---

## The Cookie Cutter Analogy

![h:340 One class produces many objects, each with its own data](../assets/illustrations/class-object-cutter.svg)

<div class="term-box">
A <b>class</b> is a mold, or blueprint, while an <b>object</b> is the concrete form produced from that mold. A single class can produce many objects, and each object holds its own data.
</div>

---

## An Object as State and Behavior

![h:300 Contrast between data and behavior combined into one unit (object-oriented) versus scattered apart (procedural)](../assets/illustrations/state-behavior-bundle.svg)

Every object combines two things: **state** (the data belonging to the object itself) and **behavior** (what the object can do with its own data). This is what sets it apart from the procedural approach, in which data and the functions that process it are typically scattered across different locations, so their consistency must be maintained manually.

---

## Why Does This Matter?

Classes and objects are not just a way of organizing code. They are the foundation that every other OOP concept covered this semester rests on: encapsulation, inheritance, and polymorphism all operate on the same unit, the object. Without this unit, there is no "thing" whose traits can be inherited, whose data can be hidden, or whose behavior can vary depending on its kind.

<div class="term-box">
Bundling data and behavior into one object also lets each part of a program be tested and understood in isolation, without tracing through the entire codebase to see how a piece of data is used. This is one reason large applications built from many small objects are far easier to maintain than a single giant program whose data is tangled together without clear boundaries.
</div>

---

## Key Definitions

<div class="term-box">
<b>Class:</b> a mold or template that defines the attributes (data) and methods (behavior) that its objects will have.
</div>

<div class="term-box">
<b>Object:</b> a concrete form created from a class, with its own data.
</div>

<div class="term-box">
<b>Instantiation:</b> the process of creating a new object from a class.
</div>

---

<!-- _class: divider -->

# Part 2
## The Anatomy of a Class

---

## Attributes and Methods

A class describes two things for every object created from it:

- **Attributes**: the data belonging to the object itself, for example the dimensions of a rectangle.
- **Methods**: the behavior an object can perform with its own data, for example calculating its own area.

<div class="warn-box">
If an object is created but its attributes have not yet been filled in, the object remains in a "half-finished" state. The next section examines how a constructor closes this gap.
</div>

---

## The Constructor: Closing the "Half-Finished" Gap

![h:400 An object before and after the constructor fills in its attributes](../assets/illustrations/constructor-before-after.svg)

A **constructor** is the part of a class that runs automatically the moment a new object is created. Its task is to ensure that every attribute is immediately and completely filled in, so that the object never remains in a "half-finished" state.

---

## Why Is `this` Needed?

![h:360 this refers to the object itself, distinct from a parameter that comes from outside](../assets/illustrations/this-self-reference.svg)

Constructor parameters are often given exactly the same name as their attributes to keep their intent clear. So that Java can distinguish between the two, the keyword `this` is available, referring to the object currently being created or used, as distinct from a parameter, which is simply a value received from outside.

---

## Default Constructor and Parameterized Constructor

<div class="cols">
<div>

**When no constructor is written**

Java automatically provides a default no-argument constructor, with attributes given empty values.

</div>
<div>

**Parameterized constructor**

Once a constructor is written explicitly, that default constructor is automatically no longer available.

</div>
</div>

---

## Parameters and Return Values

![h:300 A method as a small machine: taking in input and returning a result](../assets/illustrations/function-io.svg)

A method can accept input (**parameters**) and return a result (**return value**), similar to a small machine that processes input into output.

<div class="tip-box">
Java also allows several methods to share the same name with different parameters (overloading). This topic is covered in full in Meeting 9.
</div>

---

<!-- _class: divider -->

# Part 3
## Objects in Memory

---

## Stages of Object Creation

![Four stages that occur when a new object is created](../assets/illustrations/object-creation-flow.svg)

---

## Stack and Heap: the Variable Is Not the Object Itself

![h:400 A variable on the stack points to an object on the heap](../assets/illustrations/stack-heap-single.svg)

A variable residing on the **stack** stores only an address (a reference), not the object itself directly. The actual object, complete with all its data, is stored separately on the **heap**.

---

## Copying a Variable Is Not the Same as Copying an Object

Because a variable stores only an address, two variables can point to the exact same object. When this happens, a change to the data through one variable automatically becomes visible through the other variable as well, because both point to the exact same object on the heap.

---

## Illustration: Two Variables, One Object

![h:420 Two variables on the stack point to the same single object on the heap](../assets/illustrations/stack-heap-alias.svg)

This condition is called **aliasing**: two or more variables that point to the exact same object on the heap.

---

## A Reference That Does Not Yet Point to Any Object

![h:280 A null reference points into empty space](../assets/illustrations/null-reference.svg)

<div class="warn-box">
A reference that does not yet point to any object is said to hold a null value. If a method is called on a reference that is still null, the program stops immediately with an error. The solution is always the same: make sure the object has actually been created before its methods are used.
</div>

---

## Many Objects from One Class

![h:320 One class produces several independent objects held in an array](../assets/illustrations/multiple-objects-array.svg)

A single class can produce many objects at once, and all of them can be held in a single array. Each object remains independent, has its own distinct size, and its data does not affect the others.

<p class="footnote">An object no longer pointed to by any reference is automatically cleaned up from the heap by the garbage collector.</p>

---

<!-- _class: divider -->

# Part 4
## Reading a UML Class Diagram

---

## Anatomy of a UML Class Box

![h:320 Rectangle class diagram](../assets/uml/p02-rectangle.png)

<div class="term-box">
The <b>-</b> symbol indicates that an attribute or method is private (accessible only from within the class itself), while the <b>+</b> symbol indicates that it is public (accessible from outside the class). Encapsulation is covered in full in Meeting 3.
</div>

---

## Reading Exercise: the Student Class

![h:300 Student class diagram](../assets/uml/p02-student.png)

**Exercise:** how many attributes does this class have? Which attributes are private and which are public? What methods does it provide, and what input does each method require?

<div class="tip-box">
Translating this diagram into Java code is carried out as a hands-on exercise in the Meeting 2 practicum jobsheet (RTI253008).
</div>

---

<!-- _class: lead -->

# References and Discussion

Deitel, *Java How to Program*, the Classes and Objects chapter

Oracle Java Tutorials: "Classes and Objects"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 2

Discussion: what other class comes to mind whose attributes are already clear, but whose methods still need further design?
