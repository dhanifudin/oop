# Practicum Jobsheet: Meeting 1
## Introduction to the Java Practicum Environment and IDE

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 1 (Week 1) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, you will be able to:

1. Verify the JDK and NetBeans installation on your own computer.
2. Create a first Java project with the `id.ac.polinema` package structure.
3. Explain the compile-run cycle (`javac` then `java`) and practice it, both through NetBeans and the command line.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the main editor throughout this course).
- **Project**: this meeting creates a new project named `bank-mini`. This is the very project that will grow into the complete Bank Mini application throughout the semester, so every Java class is placed inside the package `id.ac.polinema` (the standard Java package naming convention: the institution's domain name reversed, so "polinema.ac.id" becomes `id.ac.polinema`).
- **Quick verification** before starting:
  ```bash
  java -version
  javac -version
  ```
  If both display a version number of 17 or higher with no errors, the process may proceed.

> **Without NetBeans?** This jobsheet can still be followed using a plain text editor:
> ```bash
> mkdir -p bank-mini/src/id/ac/polinema
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> The checkpoints and program output remain exactly the same, regardless of the editor used.

## C. Work Steps

### Step 1: Verifying the JDK and NetBeans Installation

The JDK (Java Development Kit) provides two main tools: `javac` (the compiler, turning `.java` source files into `.class` bytecode) and `java` (running that bytecode). NetBeans uses both tools behind the scenes, but it is still important to confirm both are installed correctly before opening NetBeans.

Open a terminal (or Command Prompt), then run the verification commands from section B. Also open NetBeans to confirm the application itself runs.

> ✅ **Checkpoint:** `java -version` and `javac -version` display the same version number, 17 or higher. NetBeans opens without error.

> ⚠️ **If it fails:** if a `command not found` or `'java' is not recognized` message appears, the JDK is not installed or its location has not been added to the system PATH. Reinstall the JDK and make sure the option to add it to the PATH is checked during installation.

### Step 2: Creating the `bank-mini` Project

Open NetBeans, create a new project of type **Java Application**, and name it `bank-mini`. When NetBeans asks for the package name for the main class, enter `id.ac.polinema`. The folder structure and `Main.java` file are created automatically inside that package.

Replace the contents of `Main.java` with the following code:

![Main.java: printing a welcome message](../assets/code/pertemuan-01/p01-02-main.png){width=70%}

Run the project (right-click the project > Run, or press F6). If using a terminal: `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`.

> ✅ **Checkpoint:** the `bank-mini` project appears in the Projects panel with a package `id.ac.polinema` containing `Main.java`, and the program displays `Welcome to Bank Mini!`.

> ⚠️ **If it fails:** if `error: class Main is public, should be declared in a file named Main.java` appears, check whether the file name matches the class name exactly (`Main.java` for `class Main`). Java is case-sensitive, so uppercase and lowercase letters must match precisely.

### Step 3: Understanding the Compile and Run Cycle

Java code does not run directly the way a scripting language does. There are two separate stages: **compile** (`javac` reads a `.java` file and produces a `.class` file containing bytecode) and **run** (`java` executes that bytecode). NetBeans runs both stages automatically behind the scenes every time the Run button is pressed, but understanding these two stages separately matters for tracing errors later on: a compile error means the code was never successfully translated at all, while a run error means the code was translated successfully but has a problem while executing.

Add one more line to `Main.java`:

![Main.java with an additional print line](../assets/code/pertemuan-01/p01-03-main.png){width=70%}

Run the project again.

> ✅ **Checkpoint:** the program prints two lines, `Welcome to Bank Mini!` followed by `This program was prepared by Nadia.` (or your own name, if changed).

> ⚠️ **If it fails:** if only the first line appears, or a `';' expected` error appears, check whether the semicolon at the end of every `System.out.println(...)` statement is present.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the program output after Step 3.
- **Independent assignment:**
  1. Change the second line in `Main.java` to display your own name, run it again, and include a screenshot of the result.
  2. Answer briefly (2 to 3 sentences per question): (a) what is the difference between the compile process and the run process in Java? (b) what happens if a `.java` file's name does not match the public class name inside it?

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot/output) | Some checkpoints demonstrated |
| Independent assignment | 25% | Complete and accurate answers | Answers present even if incomplete |
