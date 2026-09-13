# Practicum Jobsheet: Meeting 13
## GUI with NetBeans Matisse (Part 1)

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 13 (Week 13) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Organize the existing Bank Mini classes into the subpackages `model`, `repository`, and `ui` inside a Maven project.
2. Design a user interface (GUI) using NetBeans Matisse (drag-and-drop), without writing layout code manually.
3. Connect GUI components to business classes (`Bank`, `AccountRepository`) already built since previous meetings, with no line of their logic changed.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans (the main editor, already including built-in Maven and GUI Builder/Matisse support).
- **Project**: starting this meeting, Bank Mini switches from a plain `javac`/`java` project to a **Maven** project. Create a new project: **File > New Project > Java with Maven > Java Application**, name it `bank-mini`, Group Id `id.ac.polinema`.
- **Quick verification** before starting:
  ```bash
  java -version
  mvn -version
  ```
  If both display a version number with no errors, the process may proceed.

> **Without NetBeans?** Step 1 (package reorganization) can still be followed using a plain text editor and command-line Maven:
> ```bash
> mvn -q compile exec:java
> ```
> Step 2 (GUI design with Matisse) requires NetBeans, since the GUI Builder is a built-in NetBeans feature with no command-line equivalent. Students without NetBeans may use the finished `BankMiniFrame.java` file from the checkpoint directly (complete, with no `.form`) and compile it with `mvn -q compile exec:java`; the checkpoint and resulting GUI display remain exactly the same.

## C. Work Steps

### Step 1: Package Reorganization model/repository/ui

So far, every Bank Mini class has sat directly in package `id.ac.polinema`. As the project grows with the addition of a GUI interface, these classes are grouped by their role: `id.ac.polinema.model` for data classes and core business rules (`Account` and its subclasses, `Customer`, `Transaction`, exceptions, interfaces), `id.ac.polinema.repository` for data storage classes (`AccountRepository`, `InMemoryAccountRepository`), while `Bank` and `Main` stay in the parent package `id.ac.polinema` as the connector between layers. Package `id.ac.polinema.ui` is prepared for the GUI interface classes to be built in Step 2.

Move each file into its new package (right-click `src/main/java` > **New > Java Package**, then move files by drag-and-drop in the Projects window), update the `package` declaration on each file's first line, and add `import` for classes that now come from a different package:

![Bank.java with cross-package imports for model and repository](../assets/code/pertemuan-13/p13-01-bank.png){width=68%}

![Account.java with package model](../assets/code/pertemuan-13/p13-01-account.png){width=55%}

`Bank` also gets a new method, `getAllAccounts()`, returning every account without printing it, since the GUI in Step 2 needs the raw data, not text printed to the console:

![Bank.java with method getAllAccounts](../assets/code/pertemuan-13/p13-01-bank-getall.png){width=60%}

> ✅ **Checkpoint:** after every file is moved and its `import` fixed, run **Run Project** (F6). The output remains identical to Meeting 11 (the `Withdrawal failed`, `Withdrawal succeeded`, `interest applied`, `monthly fee`, and A003 transaction history lines).

> ⚠️ **If it fails:** if the error `package id.ac.polinema does not exist` or `cannot find symbol` appears, check whether every moved file has had its `package` declaration updated to match its new location, and whether classes used across packages have been `import`ed.

### Step 2: BankMiniFrame, an Account List with Matisse

> **Concept Brief: GUI Builder (Matisse).** Writing a GUI's layout manually through code is a hassle: the position and size of each component must be calculated and adjusted one by one every time the display changes even slightly. NetBeans provides a GUI Builder (known as Matisse) that lets components (buttons, tables, and so on) be arranged by dragging them (drag-and-drop) in a visual editor, with the Java layout code (`GroupLayout`) generated automatically by NetBeans behind the scenes.

> **Concept Brief: Planning a Display Before Building It.** Before dragging components into the GUI Builder, sketch it roughly first: what components are needed, and how they are positioned relative to each other. For `BankMiniFrame` at this step, the sketch is as simple as:
> ```
> [ Table: Account Number | Owner | Balance ]
>                                  [ Refresh ]
> ```
> The sketch does not need to be neat, it only needs to answer two things: which component (a table for a data list, a button for an action) and where it sits (the button below the table). The more complex the form (see Meeting 14), the more useful it is to plan on paper first before dragging components in Matisse.

Follow these steps in NetBeans:

1. Right-click package `id.ac.polinema.ui` > **New > Other... > Swing GUI Forms > JFrame Form**. Name the class `BankMiniFrame`.
2. NetBeans opens `BankMiniFrame` in **Design** mode. From the **Palette** panel, group **Swing Containers**, drag a **Scroll Pane** component onto the form.
3. From group **Swing Controls**, drag a **Table** component INTO the Scroll Pane just added.
4. Right-click that table > **Table Contents...**. In the dialog that opens, delete the default column row, then add three columns of type `Object`: `Account Number`, `Owner`, `Balance`. Leave the data rows empty (0 rows), since the table will be filled by code.
5. From group **Swing Controls**, drag a **Button** component below the Scroll Pane.
6. In that button's **Properties** panel, change the **text** property to `Refresh`.
7. Right-click the Scroll Pane and the button in turn, select **Change Variable Name...**, and name them `accountScrollPane` and `refreshButton` (the table inside the Scroll Pane is named `accountTable`).
8. Double-click the **Refresh** button in the Design view. NetBeans opens the **Source** tab and creates an empty method `refreshButtonActionPerformed`.

> ✅ **Checkpoint (design):** back in the **Design** tab, the form displays an empty table with three columns at the top and a Refresh button at the bottom right.

Fill in the method NetBeans created, plus a few supporting methods and fields, all in the **Source** tab (outside the gray code block guarded by NetBeans):

![BankMiniFrame.java, constructor and methods loadAccounts/seedSampleAccounts](../assets/code/pertemuan-13/p13-02-bankminiframe-fields.png){width=68%}

![BankMiniFrame.java, refreshButtonActionPerformed](../assets/code/pertemuan-13/p13-02-bankminiframe-handler.png){width=60%}

Update `Main.java` so it runs `BankMiniFrame`, no longer printing to the console:

![Main.java running BankMiniFrame](../assets/code/pertemuan-13/p13-02-main.png){width=68%}

![BankMiniFrame display after running, showing two sample accounts](../assets/screenshots/pertemuan-13/p13-account-list.png){width=60%}

> ✅ **Checkpoint:** run **Run Project** (F6). The `BankMiniFrame` window appears, displaying a table containing two sample accounts (A001 - Nadia - 500000.0, A002 - Sari - 200000.0). Pressing the **Refresh** button changes nothing for now (there is no way yet to add a new account through the GUI), but shows no error.

> ⚠️ **If it fails:** if the table appears empty, check whether `loadAccounts()` is genuinely called in the constructor AFTER `seedSampleAccounts()`, and whether `accountTable.getModel()` is cast to `DefaultTableModel` (not the plain `TableModel`, which has no `addRow`/`setRowCount` method).

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the `BankMiniFrame` window after running Step 2.
- **Independent assignment:**
  1. Add a new column to the table, **Type**, displaying `"Savings"` or `"Checking"` according to the account kind (use `instanceof` as learned in Meeting 10).
  2. Answer briefly (2 to 3 sentences for each question):
     - (a) why does `Bank.getAllAccounts()` return a `Collection<Account>`, rather than printing it directly like `printAllAccounts()`?
     - (b) What would happen to `BankMiniFrame`'s code if `InMemoryAccountRepository` were someday replaced with a different implementation (e.g. connected to a database)? Relate your answer to the Dependency Inversion Principle learned in Meeting 11.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot) | Some checkpoints demonstrated |
| Independent assignment | 25% | Type column correct and conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
