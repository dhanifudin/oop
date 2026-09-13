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

The foundation of object-oriented programming

---

## What You Will Learn

- The difference between a class (as a blueprint) and an object (its concrete form)
- The parts that make up a class, and how a new object comes into being
- What happens in memory when an object is created and used
- How to read a simple class design diagram

<div class="term-box">
<b>Prerequisite (Meeting 1):</b> you have already met the difference between the procedural paradigm (data and functions kept separate) and the object paradigm (data and functions bundled into a single unit). This meeting examines that unit in greater depth.
</div>

<div class="tip-box">
This course (RTI253007) focuses on concepts. All programming exercises for today's material are provided in the companion course, <b>Practicum: Object-Oriented Programming (RTI253008)</b>, Meeting 2 jobsheet.
</div>

---

## Today's Session Map

- **Session 1 (50')**: From the real world to classes and objects
- **Session 2 (50')**: The anatomy of a class: attributes, methods, constructors
- **Session 3 (50')**: What happens in memory when an object is created
- **Session 4 (50')**: Reading a UML class diagram

---

<!-- _class: divider -->

# Part 1
## From the Real World to a Class

Session 1 of 4

---

## The Cookie-Cutter Analogy

![h:300 A single class produces many objects, each with its own data](../assets/illustrations/class-object-cutter.svg)

<div class="term-box">
A <b>class</b> is a mold or blueprint, while an <b>object</b> is the concrete form produced from that mold. A single class can produce many objects, and each object holds its own data.
</div>

---

## An Object as State and Behavior

![h:280 Contrast between data and behavior bundled into one (object-oriented) versus scattered apart (procedural)](../assets/illustrations/state-behavior-bundle.svg)

Every object bundles two things together: **state** (data the object holds itself) and **behavior** (what the object can do with its own data). This differs from the procedural approach, where data and the functions that process it are usually scattered across different locations.

---

## Why Does This Matter?

Classes and objects are not merely a way of organizing code. They are the foundation that every other OOP concept covered this semester rests on. Without the object as a single unit, there is no "thing" whose traits can be inherited, whose data can be hidden, or whose behavior can differ depending on its kind.

<div class="term-box">
Bundling data and behavior into a single object also lets each part of a program be understood on its own, without needing to trace through the entire codebase to see how a piece of data is used. This is one reason a large application composed of many small objects is far easier to maintain than one giant program.
</div>

---

## Key Definitions

<div class="term-box">
<b>Class:</b> a mold or template that defines the attributes (data) and methods (behavior) its objects will have.
</div>

<div class="term-box">
<b>Object:</b> a concrete instance created from a class, with its own data.
</div>

<div class="term-box">
<b>Instantiation:</b> the process of creating a new object from a class.
</div>

---

## Code Example: A First Class and Object

```java
class Rectangle {
    int width;
    int height;
}

Rectangle r1 = new Rectangle();
r1.width = 6;
r1.height = 4;
```

<div class="tip-box">
The first line defines the class (the mold). The <code>new Rectangle()</code> line creates one concrete object from that mold; <code>r1</code> is the name given to that object.
</div>

---

## Common Mistake: Thinking the Class Itself Holds Data

<div class="warn-box">
<b>Wrong:</b> assuming <code>Rectangle</code> (the class) already has its own width and height, before any object has been created from it.
</div>

**Correct:** a class is only a mold, and stores no data at all. Data (width, height) only comes into existence once an object is created through `new`. Two objects from the same class can have entirely different widths and heights.

---

## Exercise

For each statement below, determine whether it describes a **class** or an **object**:

1. A star-shaped cookie cutter hanging in the kitchen.
2. A star-shaped cookie that was just taken out of the oven, slightly larger than the previous star cookie.
3. The general "Car" design that defines that every car has a speed and a fuel level.
4. A red car currently parked in your garage, with half a tank of fuel.

---

## Exercise Answer

1. **Class**, the cutter itself, no cookie exists yet.
2. **Object**, a concrete result of the mold, with its own data (its size).
3. **Class**, a general design, not yet pointing to any particular car.
4. **Object**, a concrete car with its own data (color, fuel level).

---

## Part 1 Summary

- A class is a mold; an object is the concrete form produced from it, with its own data.
- An object bundles state (data) and behavior into a single unit.
- A class itself never stores data; data only exists once an object is created through `new`.

Next: Part 2 breaks down the parts that make up a class: attributes, methods, and constructors.

---

<!-- _class: divider -->

# Part 2
## The Anatomy of a Class

Session 2 of 4

---

## Attributes and Methods

A class describes two things for every object created from it:

- **Attributes**: data the object holds itself, such as the dimensions of a rectangle.
- **Methods**: behavior the object can perform with its own data, such as computing its own area.

<div class="warn-box">
If an object is created but its attributes have not yet been filled in, that object is left in a "half-finished" state. The next section covers how a constructor closes this gap.
</div>

---

## Constructor: Closing the "Half-Finished" Gap

![h:360 An object before and after a constructor fills in its attributes](../assets/illustrations/constructor-before-after.svg)

A **constructor** is the part of a class that runs automatically the moment a new object is created. Its job is to make sure every attribute is filled in right away, so the object is never left in a "half-finished" state.

---

## Why Is `this` Needed?

![h:320 this refers to the object itself, unlike a parameter, which comes from outside](../assets/illustrations/this-self-reference.svg)

A constructor's parameters are often given the exact same name as the attributes they fill, so their intent is clear. So that Java can tell the two apart, the keyword `this` is available, referring to the object currently being created, as opposed to a parameter, which is merely a value passed in from outside.

---

## Default Constructor and Parameterized Constructor

<div class="cols">
<div>

**Without a constructor written**

Java automatically provides a no-argument default constructor, with attributes set to empty values.

</div>
<div>

**Parameterized constructor**

Once one constructor is written explicitly, that default constructor is no longer available automatically.

</div>
</div>

---

## Code Example: A Constructor with `this`

```java
class Rectangle {
    int width;
    int height;

    Rectangle(int width, int height) {
        this.width = width;    // this.width: the object's attribute
        this.height = height;  // width: the incoming parameter
    }
}
```

<div class="tip-box">
A parameter and an attribute may share the exact same name. <code>this.width</code> always refers to the object's attribute, while <code>width</code> alone refers to the parameter.
</div>

---

## Parameters and Return Values

![h:280 A method as a small machine: taking in input and returning a result](../assets/illustrations/function-io.svg)

A method can accept input (a **parameter**) and return a result (a **return value**), much like a small machine that turns an input into an output.

<div class="tip-box">
Java also allows several methods to share the same name with different parameters (overloading). This topic is covered in full in Meeting 7.
</div>

---

## Why Does This Matter?

Imagine a class with ten attributes, but no constructor to fill them in. Every time a new object is created, someone must remember to fill in all ten attributes manually, one by one. Forget just one, and that attribute silently ends up empty, with a bug surfacing much later, once that attribute is actually used.

<div class="term-box">
A constructor shifts this responsibility away from "whoever creates the object" and onto "the class itself". An object that has finished being created is guaranteed to always be complete, regardless of whether the programmer creating it remembered every attribute.
</div>

---

## Common Mistake: Forgetting to Use `this`

<div class="warn-box">
<b>Wrong:</b> writing <code>width = width;</code> inside a constructor, expecting it to fill in the attribute, when in fact the parameter <code>width</code> is only copying its value onto itself, and the object's attribute is never touched at all.
</div>

**Correct:** write `this.width = width;`. The left-hand side (`this.width`) refers to the object's attribute, and the right-hand side (`width`) refers to the parameter.

---

## Exercise

A class `Rectangle` has attributes `width` and `height`, plus a constructor parameterized by `width` and `height`. Inside the constructor, a student writes:

`height = height;`

After an object is created with `new Rectangle(6, 4)`, what is the value of its `height` attribute? Explain your reasoning.

---

## Exercise Answer

Not **4**, but rather the default empty value (0). `height = height;` only copies the parameter `height`'s value onto itself; the object's attribute (`this.height`) is never filled in. It should have been written as `this.height = height;`.

---

## Part 2 Summary

- Attributes store an object's data, methods provide its behavior.
- A constructor fills in attributes automatically the moment an object is created, preventing a "half-finished" object.
- `this` refers to the object itself, used to tell an attribute apart from a parameter that happens to share its name.

Next: Part 3 looks at what actually happens in a computer's memory when an object is created.

---

<!-- _class: divider -->

# Part 3
## Objects in Memory

Session 3 of 4

---

## Stages of Object Creation

![Four stages that occur when a new object is created](../assets/illustrations/object-creation-flow.svg)

---

## Stack and Heap: A Variable Is Not the Object Itself

![h:360 A stack variable pointing to an object on the heap](../assets/illustrations/stack-heap-single.svg)

A variable on the **stack** only stores an address (a reference), not the object directly. The actual object, complete with all its data, is stored separately on the **heap**.

---

## Copying a Variable Differs from Copying an Object

Because a variable only stores an address, two variables can point to the exact same object. When this happens, a change made through either variable is automatically visible through the other, since both point to the exact same object on the heap.

---

## Illustration: Two Variables, One Object

![h:380 Two stack variables pointing to the same single object on the heap](../assets/illustrations/stack-heap-alias.svg)

This condition is called **aliasing**, meaning two or more variables that point to the exact same object on the heap.

---

## Code Example: A Reference, Not a Copy

```java
Rectangle a = new Rectangle(10, 4);
Rectangle b = a;

b.width = 99;
System.out.println(a.width);  // 99, not 10
```

<div class="tip-box">
<code>b = a</code> does not create a new object. <code>a</code> and <code>b</code> point to the exact same object on the heap.
</div>

---

## A Reference That Does Not Yet Point to Any Object

![h:260 A null reference pointing to empty space](../assets/illustrations/null-reference.svg)

<div class="warn-box">
A reference that does not yet point to any object is said to hold a null value. If a method is called on a reference that is still null, the program stops immediately with an error. The fix is always the same: make sure the object has actually been created before its methods are used.
</div>

---

## Why Does This Matter?

Aliasing and null references sound like small technical details, but both are a very common cause of bugs in real applications. A method that assumes it holds its own private object, while actually sharing that same object with another part of the program, can silently change data it was never meant to touch.

<div class="term-box">
Understanding that an object variable is only a reference, not the object itself, is one of the most important leaps in understanding early in learning OOP. Many confusing bugs later on actually trace back to this exact point.
</div>

---

## Many Objects from One Class

![h:300 A single class producing several independent objects inside an array](../assets/illustrations/multiple-objects-array.svg)

A single class can produce many objects at once, and all of them can be held in a single array. Each object remains independent, can hold different-sized data, and none of them affect each other's data.

<p class="footnote">An object no longer pointed to by any reference is automatically cleaned up from the heap by the garbage collector.</p>

---

## Common Mistake: Thinking Assignment Copies an Object

<div class="warn-box">
<b>Wrong:</b> writing <code>Rectangle b = a;</code> and then assuming <code>b</code> and <code>a</code> are two separate objects.
</div>

**Correct:** `b` and `a` point to the exact same object. To genuinely get a separate object, a new object must be created explicitly through `new`, not merely through assignment.

---

## Exercise

Given the following code:

`Rectangle p = new Rectangle(5, 8);`
`Rectangle q = p;`
`q.height = 20;`
`System.out.println(p.height);`

What value gets printed, and why?

---

## Exercise Answer

It prints **20**. `q = p` only copies the reference, not the object; `p` and `q` point to the exact same object on the heap, so `q.height = 20` is also visible through `p`.

---

## Part 3 Summary

- An object variable stores a reference (address) into the heap, not the object itself; assignment only copies that reference.
- Two variables can point to the same object (aliasing); a change made through one is visible through the other.
- A null reference does not yet point to any object; calling a method on it always fails.

Next: Part 4 closes this meeting by reading a class through a diagram, rather than through code.

---

<!-- _class: divider -->

# Part 4
## Reading a UML Class Diagram

Session 4 of 4

---

## Anatomy of a UML Class Box

![h:300 Class diagram for Rectangle](../assets/uml/p02-rectangle.png)

<div class="term-box">
The <b>-</b> mark indicates an attribute or method is private (accessible only from within the class itself), while the <b>+</b> mark indicates it is public (accessible from outside the class). Encapsulation is covered in full in Meeting 3.
</div>

---

## Code Example: From Diagram to Code

```java
class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) { ... }
    public int area() { ... }
    public int perimeter() { ... }
}
```

<div class="tip-box">
Every line of the diagram has a direct counterpart in code: an attribute becomes a field, a method becomes a method declaration, and the -/+ marks become the keywords <code>private</code>/<code>public</code>.
</div>

---

## Common Mistake: Misreading the -/+ Marks

<div class="warn-box">
<b>Wrong:</b> assuming the <code>-</code> and <code>+</code> marks in front of an attribute describe its data type (negative/positive), rather than its access level.
</div>

**Correct:** the marks have nothing at all to do with the data's value. They purely state who is allowed to access it: `-` means private, `+` means public.

---

## Reading Exercise: The Account Class

![h:280 Class diagram for Account](../assets/uml/p02-account.png)

**Exercise:** how many attributes does this class have? Are its attributes private or public? What methods does it provide, and what input does each method need?

---

## Exercise Answer

This class has two attributes, `ownerName` and `balance`, and both are public (marked `+`): they can be accessed and changed directly from outside the class, without going through any method. Its methods are also public: `deposit(amount)` and `withdraw(amount)` each take a single numeric parameter, while `printInfo()` takes no parameters at all. These public attributes are no accident; they are a gap deliberately covered in full in Meeting 3.

<div class="tip-box">
<code>Account</code> is the first class of the <b>Bank Mini</b> case study you will build throughout this semester. Translating this diagram into Java code is done as a hands-on exercise in the Practicum Meeting 2 jobsheet (RTI253008).
</div>

---

## Part 4 Summary

- A UML class diagram has three parts: the class name, its attributes, and its methods.
- A `-` mark means private, a `+` mark means public; both describe access level, not data type.
- Every line of the diagram has a direct counterpart in Java code: an attribute becomes a field, a method becomes a method declaration.

---

## Meeting 2 Summary

- A class is a mold; an object is the concrete form produced from it, with its own data.
- A constructor fills in an object's attributes automatically; `this` tells an attribute apart from a parameter that shares its name.
- An object variable stores a reference into the heap, not the object itself; two variables can point to the same object (aliasing).
- A UML class diagram reads a class through its name, attributes, and methods, complete with access-level marks.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program* - the Classes and Objects chapter

Oracle Java Tutorials: "Classes and Objects"

Hands-on practice for this material is available in the Practicum: Object-Oriented Programming (RTI253008) jobsheet, Meeting 2

---

## Discussion

Pick a real-world class not yet covered in this course (not `Rectangle`, `Student`, or `Circle`). Name at least three attributes and two methods you think that class should reasonably have, then sketch its simple UML diagram (class name, attributes, methods; it does not need to be neat, just written on paper).
