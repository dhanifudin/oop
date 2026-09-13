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
  section.lead a {
    color: #bfdbfe;
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

Meeting 13: **GUI with NetBeans Matisse (Part 1)**

From the console to an application window

---

## What You Will Learn

- How a GUI program reacts to user actions, instead of running sequentially from top to bottom like a console program
- How to lay out a screen through the GUI Builder (Matisse) with drag-and-drop, without writing manual layout code
- How to separate interface (GUI) code from the business logic code already built in previous meetings
- Application to Bank Mini: reorganizing the `model`/`repository`/`ui` packages, and `BankMiniFrame` as Bank Mini's first window

<div class="tip-box">
Programming exercises for today's material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 13.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Event-driven programming
- **Session 2 (50')**: GUI Builder (Matisse)
- **Session 3 (50')**: Applying the package reorganization to Bank Mini
- **Session 4 (50')**: Applying BankMiniFrame to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Event-Driven Programming

Session 1 of 4

---

## A Console Program Runs Sequentially, a GUI Program Does Not

The console programs you have written since Meeting 2 have always run sequentially: the first line executes, then the second, and so on, pausing only when reading input through `Scanner`. A GUI program is entirely different: the user can click any button, at any time, in any order, and the program can no longer simply "read input sequentially".

<div class="warn-box">
There is no single fixed order that can be written for a GUI program, since the user is the one deciding the order of their actions.
</div>

---

## Why Does This Matter?

Nearly every application you use daily, desktop applications, mobile applications, even web pages, is built on the same pattern: the program waits, then reacts when something happens (a button is clicked, a page is swiped, a notification arrives). This "wait and react" mindset is called event-driven programming, and nearly all modern human-computer interaction is built on top of it.

<div class="term-box">
A programmer only used to thinking "sequentially from top to bottom" will struggle to understand why the code inside one GUI method can be called many times, or not at all, depending on the user's actions. Understanding event-driven programming from the start is far cheaper than unlearning a procedural mindset later.
</div>

---

## Event, Listener, and Handler

![h:260 One event from a button click triggers one handler method](../assets/illustrations/event-callback-flow.svg)

<div class="term-box">
When a user does something to a GUI component (clicking a button, for instance), that component emits an <b>event</b>. A <i>listener</i> registered on that component catches the event, then runs its <i>handler</i> method. This handler method is the only part of the code you actually write; when it is called is decided entirely by the user's action, not by the order of the code's lines.
</div>

---

## Code Example: Registering a Listener

```java
JButton plusButton = new JButton("+");
plusButton.addActionListener(evt -> {
    int result = a + b;
    display.setText(String.valueOf(result));
});
```

`addActionListener` registers the code inside the lambda as a handler; this code waits, and is not run immediately when this line executes.

---

## Code Example: A Listener's Original Form (Anonymous Class)

```java
plusButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        int result = a + b;
        display.setText(String.valueOf(result));
    }
});
```

The lambda on the previous slide is merely a concise way of writing this pattern; NetBeans itself still generates this anonymous class form, as will be seen in Part 2.

---

## Common Mistake: Assuming a Handler Runs Like main()

<div class="warn-box">
<b>Wrong:</b> assuming the code inside <code>plusButtonActionPerformed()</code> automatically runs once when the program starts, the same way lines inside <code>main()</code> do.
</div>

**Right:** the code inside a handler only runs when the matching event actually occurs (that button is clicked). It may run zero times (the button is never clicked), once, or many times, and is never called automatically by the program's sequence.

---

## Exercise

A form has two buttons, `saveButton` and `deleteButton`, each with its own handler.

The user clicks `saveButton` three times, then `deleteButton` once. How many times does each of `saveButtonActionPerformed()` and `deleteButtonActionPerformed()` run? Explain.

---

## Exercise Answer

`saveButtonActionPerformed()` runs **three times**, `deleteButtonActionPerformed()` runs **once**, exactly matching how many times each button is clicked. There is no fixed order determining this beforehand; the program simply reacts every time a click event genuinely occurs on the relevant button.

---

## Part 1 Summary

- A GUI program does not run sequentially; it waits and reacts to events triggered by user actions.
- An event is caught by a listener that runs a handler method; a handler may run zero times, once, or many times.
- A handler is never called automatically by the program's sequence, only by an event that genuinely occurs.

Next: Part 2 covers the GUI Builder (Matisse), how to lay out a screen without writing manual layout code.

---

<!-- _class: divider -->

# Part 2
## GUI Builder (Matisse)

Session 2 of 4

---

## Writing Layout Manually Is a Hassle

A GUI's layout, the position and size of each component, can be written in plain Java code. But for a form with many components, manual layout code becomes long, hard to read, and hard to adjust every time the display changes even slightly.

<div class="term-box">
NetBeans provides a GUI Builder, known as <b>Matisse</b>, that lets components be arranged by dragging them (drag-and-drop) in a visual editor. NetBeans itself writes the layout code (<code>GroupLayout</code>) behind the scenes, inside a code block marked "Generated Code".
</div>

---

## Why Does This Matter?

Building an interface through a visual tool, rather than manual code, is a widely used industry practice: Android Studio has a Layout Editor, Xcode has Interface Builder, many web development tools have a page builder, all using the same idea, separating "what it looks like" (arranged visually) from "how it behaves" (written as code). Mastering one GUI Builder, such as Matisse, makes it easier to adapt to similar tools in other ecosystems.

---

## JFrame, Content Pane, and Components

<div class="term-box">
A <code>JFrame</code> (window) does not hold its components directly, but instead through a container inside it called the <i>content pane</i>. Every component (button, table, and so on) is added to that content pane, then arranged by a layout object (e.g. <code>GroupLayout</code>) installed on that same content pane.
</div>

Matisse hides these details behind its visual editor, but the code it generates still calls `getContentPane()` and `setLayout(...)` just like ordinary manual code.

---

## Planning a Form Before Building It

<div class="term-box">
Before opening Matisse, plan the form first: what data needs to be displayed or requested, and which component fits each one (static text uses <code>JLabel</code>, user input uses <code>JTextField</code>, an action uses <code>JButton</code>, a data list uses <code>JTable</code>).
</div>

```
Add Account
Account Number: A003 (automatic)
Owner:          [____________]
Initial Balance:[____________]
                 [Save] [Cancel]
```

A simple sketch like this is already enough to decide which components need to be dragged into Matisse in Meeting 14 (the add-account dialog), before a single component has actually been added.

---

## Configuring Components Through the Properties Panel

Besides arranging position through drag-and-drop, Matisse has a **Properties** panel to set a component's initial values (a button's text, a variable name) without writing any code at all.

<div class="term-box">
Changing a button's <b>text</b> property to "Refresh" through the Properties panel produces the line <code>refreshButton.setText("Refresh");</code> in the Generated Code block, exactly as if written manually, just through a visual editor.
</div>

---

## The "Generated Code" Block

<div class="term-box">
The code Matisse generates is always wrapped in a marker <code>// &lt;editor-fold desc="Generated Code"&gt;</code>. Code you write yourself (constructor, helper methods, event handlers) always sits OUTSIDE this block.
</div>

```java
// <editor-fold desc="Generated Code">
private void initComponents() {
    accountTable = new javax.swing.JTable();
    // ...GroupLayout is built automatically here...
}
// </editor-fold>
```

---

## Opening Another Window from a Button

So far, only one window has ever been created. A real application almost always has more than one window: a login window, a main window, a detail window, and so on.

<div class="term-box">
The method <code>dispose()</code> closes the CURRENT window and releases its resources. <code>new SomeFrame().setVisible(true)</code> creates another window object and displays it. Combining the two inside one event handler is the standard pattern for moving from one window to another.
</div>

---

## Code Example: Switching to Another Window

```java
private void nextButtonActionPerformed(ActionEvent evt) {
    dispose();
    new DetailFrame().setVisible(true);
}
```

This pattern is used again in Meeting 15, when `LoginFrame` switches to `BankMiniFrame` after a successful login.

---

## Common Mistake: Editing the Generated Code Block Directly

<div class="warn-box">
<b>Wrong:</b> adding your own line of code (e.g. changing a component's color) directly inside the <code>initComponents()</code> block guarded by NetBeans.
</div>

**Right:** NetBeans OVERWRITES that block's entire contents every time the visual design is changed in the Design tab, with no warning at all; manual changes inside it simply disappear. Additional code is always written outside the block, for instance in the constructor after the call to `initComponents()`.

---

## Exercise

A student adds the line `accountTable.setBackground(Color.YELLOW);` directly inside the `initComponents()` block, then returns to the Design tab and slightly moves the Refresh button's position.

What happens to the newly added line? Explain, then state the correct approach.

---

## Exercise Answer

**The line disappears.** Switching to the Design tab and changing anything makes NetBeans rewrite the entire `initComponents()` block from scratch, overwriting the manually added line inside it. The correct approach: place `accountTable.setBackground(Color.YELLOW);` in the constructor, after the call to `initComponents()`, outside the block guarded by NetBeans.

---

## Part 2 Summary

- Plan the needed components before opening Matisse; Matisse arranges layout through drag-and-drop, writing its `GroupLayout` code automatically.
- Generated code is wrapped in a "Generated Code" block and overwritten every time the visual design is changed; your own code is always written outside that block.
- Switching to another window uses `dispose()` followed by `new SomeFrame().setVisible(true)`, a pattern used again in Meeting 15 for `LoginFrame`.

Next: Part 3 applies the package reorganization to every Bank Mini class already built.

---

<!-- _class: divider -->

# Part 3
## Applying the Package Reorganization to Bank Mini

Session 3 of 4

---

## Package Reorganization: model/repository/ui

![h:280 Bank depending on AccountRepository (repository) and Account (model); BankMiniFrame (ui) depending on Bank](../assets/uml/p13-layered-packages.png)

The Bank Mini classes already built since Meeting 2 are now grouped by their role: `model` for data and core business rules, `repository` for data storage, `ui` for the GUI interface starting to be built this meeting. `Bank` itself stays in the parent package, as the connector between layers.

---

## Why Does This Matter?

Imagine a project with hundreds of classes all placed directly in one package with no grouping at all. Finding one particular class means scanning a long, unorganized list of names, with no way to tell at a glance which classes are allowed to depend on each other.

<div class="term-box">
Grouping classes by their role (data, storage, interface, and so on) is a standard practice in nearly every real project, not merely file tidying. The package name itself already explains the role of the classes inside it, and the direction of dependency between layers (<code>ui</code> depends on the business layer, not the other way around) becomes visible directly from the structure.
</div>

---

## Maven Project Folder Structure

```
src/main/java/id/ac/polinema/
├── Bank.java
├── Main.java
├── model/       (Account, Customer, Transaction, ...)
├── repository/  (AccountRepository, InMemoryAccountRepository)
└── ui/          (BankMiniFrame)
```

The physical folder structure follows the package name exactly; moving a class to a new package means moving its file to the matching folder.

---

## Code Example: Package Declaration and Cross-Package Import

```java
// Account.java
package id.ac.polinema.model;

// Bank.java
package id.ac.polinema;
import id.ac.polinema.model.Account;
import id.ac.polinema.repository.AccountRepository;
```

Moving a class to a new package requires updating the `package` line in that class ITSELF, and adding `import` to any other class that uses it across packages.

---

## Bank Gets getAllAccounts()

So far, `Bank` can only print accounts directly to the console through `printAllAccounts()`. The GUI in Part 4 needs the raw data, not printed text, so `Bank` gets a new method:

```java
public Collection<Account> getAllAccounts() {
    return repository.findAll();
}
```

`getAllAccounts()` returns the raw data; the calling code decides whether to print it to the console, display it in a GUI table, or use it some other way.

---

## Common Mistake: Forgetting to Update Package or Import

<div class="warn-box">
<b>Wrong:</b> moving the file <code>Account.java</code> into the <code>model</code> folder, but forgetting to change the line <code>package id.ac.polinema;</code> on its first line to <code>package id.ac.polinema.model;</code>.
</div>

**Right:** the compiler displays the error `package id.ac.polinema does not exist` or `cannot find symbol` on other classes that use it. Every moved file must have its OWN `package` declaration updated, and any other class using it across packages must be given an `import`.

---

## Exercise

`Customer.java` is moved into package `id.ac.polinema.model`, but its `package` line still reads `package id.ac.polinema;`.

What happens when the project is compiled? Explain, then state the fix.

---

## Exercise Answer

**It fails to compile.** The file's physical location (the `model` folder) does not match its `package` declaration (`id.ac.polinema`, with no `.model`), causing the compiler to display a package mismatch error. Fix: change `Customer.java`'s first line to `package id.ac.polinema.model;`, then add `import id.ac.polinema.model.Customer;` to every other class using it across packages.

---

## Part 3 Summary

- Bank Mini classes are grouped by their role: `model`, `repository`, and `ui`; `Bank` stays in the parent package as the connector.
- Moving a class requires updating its own `package` declaration, and adding `import` for any cross-package usage.
- `Bank` gets `getAllAccounts()`, returning raw data instead of printing it, so it can be used by a GUI.

Next: Part 4 applies `BankMiniFrame`, Bank Mini's first window, using this `getAllAccounts()`.

---

<!-- _class: divider -->

# Part 4
## Applying BankMiniFrame to Bank Mini

Session 4 of 4

---

## BankMiniFrame, Bank Mini's First Window

![BankMiniFrame window displaying the account table](../assets/screenshots/pertemuan-13/p13-account-list.png)

`BankMiniFrame` displays every account through a `JTable`, filled from `Bank.getAllAccounts()`. The `Bank` and `AccountRepository` `BankMiniFrame` uses are the exact same classes already built since Meeting 11; not a single line of their business code changes to support this GUI.

---

## EventQueue.invokeLater(): Running the GUI on the Right Thread

<div class="term-box">
Swing components must be created and changed from one special thread, called the <i>Event Dispatch Thread</i> (EDT), where every event (clicks, keystrokes) is actually processed one at a time. <code>EventQueue.invokeLater(...)</code> schedules the GUI-creation code to run on the EDT, rather than directly on the ordinary <code>main()</code> thread.
</div>

```java
public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
}
```

This is why a GUI program's `main()` almost always ends with the `invokeLater(...)` pattern, instead of calling the window's constructor directly.

---

## TableModel and JTable: Display Separated from Data

<div class="term-box">
<code>JTable</code> itself does not store its own cell data; it merely displays whatever the <code>TableModel</code> object installed on it provides. <code>DefaultTableModel</code> is a ready-made implementation that stores data as ordinary rows, with extra methods such as <code>addRow(...)</code> to change its contents.
</div>

Separating "what is displayed" (`JTable`) from "what data exists" (`TableModel`) means the data source can be swapped without changing how the table is displayed.

---

## Code Example: Constructor Preparing Sample Data

```java
public BankMiniFrame() {
    initComponents();
    bank = new Bank(new InMemoryAccountRepository());
    seedSampleAccounts();
    loadAccounts();
}
```

`initComponents()` (the Generated Code block) is called first, then your own code: setting up `Bank`, filling in sample data, then loading it into the table.

---

## Code Example: Filling a JTable from Bank

```java
private void loadAccounts() {
    DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
    model.setRowCount(0);
    for (Account acc : bank.getAllAccounts()) {
        model.addRow(new Object[]{acc.getAccountNumber(), acc.getOwner().getName(), acc.getBalance()});
    }
}
```

`getAllAccounts()` from Part 3 is iterated directly; each account becomes one table row through `model.addRow(...)`.

---

## Common Mistake: Casting to TableModel, Not DefaultTableModel

<div class="warn-box">
<b>Wrong:</b> writing <code>TableModel model = accountTable.getModel();</code> then calling <code>model.addRow(...)</code>.
</div>

**Right:** the compiler displays an error, since the plain `TableModel` interface has no `addRow()`/`setRowCount()` method, only `DefaultTableModel` (the concrete implementation) has them. That line must be cast explicitly: `(DefaultTableModel) accountTable.getModel()`.

---

## Exercise

`loadAccounts()` is called, but `BankMiniFrame`'s table still appears empty after the program runs.

State two possible causes related to the ORDER of method calls in the constructor, then explain each one.

---

## Exercise Answer

**Possibility 1:** `loadAccounts()` is called BEFORE `seedSampleAccounts()`, so `bank.getAllAccounts()` is still empty when the table is loaded. **Possibility 2:** `loadAccounts()` is called BEFORE `initComponents()`, so `accountTable` has not been initialized (`null`) when `getModel()` is called, causing a `NullPointerException`. The correct order: `initComponents()`, then `seedSampleAccounts()`, then `loadAccounts()`.

---

## Part 4 Summary

- `BankMiniFrame` displays accounts through a `JTable`, filled from `Bank.getAllAccounts()` with no change to existing business code.
- Filling a `JTable` needs an explicit cast to `DefaultTableModel`, since a plain `TableModel` has no `addRow()`/`setRowCount()`.
- The order of calls in the constructor matters: `initComponents()`, then data setup, then loading into the table.

---

## Meeting 13 Summary

- A GUI program is event-driven: handler code waits and reacts to user actions, instead of running sequentially.
- Matisse arranges layout through drag-and-drop; generated code is overwritten, and your own code is always written outside that block.
- Bank Mini is reorganized into the `model`/`repository`/`ui` packages, and `BankMiniFrame` displays its data through `getAllAccounts()` with no change to business code already in place since Meeting 11.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, chapter on GUI Components, Event Handling

Oracle Java Tutorials: ["Creating a GUI With Swing"](https://docs.oracle.com/javase/tutorial/uiswing/TOC.html), ["Writing Event Listeners"](https://docs.oracle.com/javase/tutorial/uiswing/events/index.html)

Apache NetBeans: ["Java GUI Applications Learning Trail"](https://netbeans.apache.org/tutorial/main/kb/docs/matisse/) (official GUI Builder/Matisse guide)

Programming exercises for this material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 13

---

## Discussion

`BankMiniFrame` calls `bank.getAllAccounts()` to fill the table, not `bank.printAllAccounts()` already in place since Meeting 9. Explain in your own words why a method that prints directly to the console (`System.out.println`) cannot be reused to fill a GUI component like `JTable`, and why returning raw data (`Collection<Account>`) is far more flexible to use in various contexts (console, GUI, or even another format in the future).
