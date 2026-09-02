# Practicum Jobsheet: Meeting 2
## Classes and Objects

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 2 (Week 2) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |
| **Starting Code** | none, the project is built from scratch |
| **Final Code** | the `oop-praktikum` project after Step 7 |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Define a Java class with attributes, a constructor, and methods.
2. Create and manipulate objects using `new`, and understand reference behavior.
3. Translate a simple UML class diagram into Java code.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the editor used throughout this practicum).
- **Project**: in this meeting, a new project named `oop-praktikum` is created. All classes are placed inside the package `id.ac.polinema` (the standard Java package naming convention: the institution's domain name reversed, so "polinema.ac.id" becomes `id.ac.polinema`).
- **Quick verification** before starting:
  ```bash
  java -version
  javac -version
  ```
  If both display a version number with no errors, the process may proceed.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> mkdir -p oop-praktikum/src/id/ac/polinema
> cd oop-praktikum
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and resulting output remain exactly the same, regardless of which editor is used.

## C. Work Steps

### Step 1: Creating the Project

Open NetBeans, create a new project of type **Java Application**, and name it `oop-praktikum`. When NetBeans asks for the package name for the main class, enter `id.ac.polinema`. The folder structure and the `Main.java` file will be created automatically inside that package.

> ✅ **Checkpoint:** the Projects panel displays `oop-praktikum` with a package `id.ac.polinema` containing `Main.java`.

### Step 2: A Minimal `Rectangle` Class and the First Object

A **class** is a mold that describes the attributes and behavior that every **object** created from it will have. The `Rectangle` class below currently has only attributes (state); it does not yet have any methods.

Add a new class `Rectangle` to the package `id.ac.polinema` (right-click the package > New > Java Class):

![Rectangle.java: a class with two attributes](../assets/code/pertemuan-02/p02-02-rectangle.png){width=55%}

Replace the contents of `Main.java` to create a single `Rectangle` object and fill in its attributes one at a time:

![Main.java: creating a Rectangle object and filling in attributes manually](../assets/code/pertemuan-02/p02-02-main.png){width=75%}

Run the project (right-click the project > Run, or press F6). If using a terminal: `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`.

> ✅ **Checkpoint:** the program displays `Rectangle 6x4`.

> ⚠️ **If it fails:** if `error: cannot find symbol` appears, check whether the file name matches the class name exactly (`Rectangle.java` for `class Rectangle`). Java is case-sensitive, so uppercase and lowercase letters must match precisely.

### Step 3: Adding Methods (Behavior)

Attributes only store data and have no behavior of their own. A **method** gives a class its behavior, that is, something an object can do with its own data. Add two methods to `Rectangle` (the green lines mark the newly added code):

![Rectangle.java with the area() and perimeter() methods added](../assets/code/pertemuan-02/p02-03-rectangle.png){width=55%}

Simplify `Main.java` to use these two methods:

![Main.java using area() and perimeter()](../assets/code/pertemuan-02/p02-03-main.png){width=75%}

Run the program again.

> ✅ **Checkpoint:** the output now consists of two lines, `Area: 24` followed by `Perimeter: 20`.

> ⚠️ **If it fails:** if the error `non-static method area() cannot be referenced from a static context` appears, the method `area()` was called without going through an object (for example `Rectangle.area()`). A non-static method must be called through its object, that is, `r.area()`.

### Step 4: Constructor and `this`

There is a gap in time between creating an object with `new Rectangle()` and the completion of filling in its attributes (see Steps 2 through 3). During this gap, the object remains in a "half-finished" state, with attributes still empty (holding the value `0` for the type `int`). A **constructor** closes this gap by requiring complete data at the moment the object is created.

![Rectangle.java with a constructor added](../assets/code/pertemuan-02/p02-04-rectangle.png){width=55%}

`this.width` refers to the attribute belonging to the object, while `width` on the right-hand side is the constructor's parameter. Because both names are deliberately made identical, the keyword `this` is needed so that Java can distinguish between the two.

Simplify `Main.java`:

![Main.java using the Rectangle constructor](../assets/code/pertemuan-02/p02-04-main.png){width=75%}

> ✅ **Checkpoint:** the output is exactly the same as in Step 3, while the `Main.java` code is considerably more concise.

The following part often causes confusion: when the statement `new Rectangle(6, 4)` is executed, two things happen in memory. The variable `r` is merely a small slot on the **stack** that holds an address. The actual `Rectangle` object, complete with its `width` and `height`, resides separately on the **heap**. The illustration is as follows:

![A stack variable pointing to a Rectangle object on the heap](../assets/uml/p02-memory-new.png){width=45%}

> ⚠️ **If it fails:** the error `constructor Rectangle in class Rectangle cannot be applied to given types` generally indicates that the number or order of arguments in `new Rectangle(...)` does not match the constructor's parameters. Check the number and order of these arguments again.

### Step 5: References, Aliasing, and `null`

An object variable in Java is not the object itself, but rather a **reference** that points to an object in memory. Because of this, two variables can point to the exact same object. Add the following code to the end of `main` (the green lines):

![Main.java with an aliasing block and a null test added](../assets/code/pertemuan-02/p02-05-bug-main.png){width=75%}

Run the program.

> ✅ **Checkpoint:** the first two lines (`Via original:` and `Via copy:`) both display the value `40`, even though only `copy.width` was changed. This is not an error, but rather how references work: `original` and `copy` point to the exact same object, so a change made through either variable is automatically visible through the other. The third line produces a `NullPointerException`, and this is intentional.

The illustration on the stack and heap is as follows:

![Two stack variables pointing to the same single Rectangle object on the heap](../assets/uml/p02-memory-alias.png){width=55%}

Fix this by removing the last two lines (`Rectangle empty = null;` along with the `area()` call above it) so that the program runs again without errors:

![Main.java after the two null-test lines have been removed](../assets/code/pertemuan-02/p02-05-fix-main.png){width=75%}

> ⚠️ **If it fails:** `NullPointerException` always occurs when a method is called on a reference that does not yet point to any object (`null`). The solution is always the same: make sure the object has actually been created with `new` before its methods are called.

### Step 6: A `Student` Class from a UML Diagram

The following UML class diagram describes the `Student` class that needs to be created:

![UML class diagram for Student](../assets/uml/p02-student.png){width=45%}

The `-` mark in front of an attribute indicates a private nature (accessible only from within the class itself), while the `+` mark indicates a public nature. Create a new class `Student`:

![Student.java matching the UML diagram](../assets/code/pertemuan-02/p02-06-student.png){width=55%}

Add one test line at the end of `main` in `Main.java`:

![Main.java with a Student test added](../assets/code/pertemuan-02/p02-06-main.png){width=75%}

> ✅ **Checkpoint:** the program recompiles successfully and the last line displays `Nadia (S001, GPA: 3.8)`.

### Step 7: Array of Objects, Many Objects from One Class

A single class can produce many objects at once. Replace the `Rectangle` test section (Steps 4 through 5) with a `Rectangle[]` array, while keeping the `Student` section from Step 6 unchanged:

![Main.java final version: a Rectangle[] array and the Student test](../assets/code/pertemuan-02/p02-07-main.png){width=75%}

> ✅ **Checkpoint:** the program prints three lines of area/perimeter (one per array element, each with a different value since every `Rectangle` has its own dimensions), followed by the line `Nadia (S001, GPA: 3.8)`.

> ⚠️ **If it fails:** `ArrayIndexOutOfBoundsException` indicates that the index used does not exist, for example `shapes[3]` when the array is only of size 3 (valid indices: 0, 1, 2).

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 7.
- **Independent assignment:**
  1. Create a `Circle` class following the UML diagram below:

     ![UML class diagram for Circle](../assets/uml/p02-circle.png){width=45%}

     The methods `area()` and `circumference()` return a value of type `double`, calculated using the standard circle formulas (`Math.PI * radius * radius` for area, `2 * Math.PI * radius` for circumference). Demonstrate this by creating one `Circle` object in `Main` (radius 5) and printing both results.
  2. Answer briefly (2 to 3 sentences for each question): (a) what is the difference between an object and a reference to an object? (b) precisely when does a class's constructor run?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | `Circle` class correct and conceptual answers accurate | `Circle` class present even though answers are incomplete |
