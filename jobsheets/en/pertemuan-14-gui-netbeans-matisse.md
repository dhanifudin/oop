# Practicum Jobsheet: Meeting 14
## GUI with NetBeans Matisse (Part 2)

| | |
|---|---|
| **Course** | Practicum: Object-Oriented Programming (RTI253008) |
| **Meeting** | 14 (Week 14) |
| **Duration** | 1 &times; 4 &times; 50' practicum session; 1 &times; 1 &times; 50' independent assignment/report |

## A. Practicum Outcomes

After completing this jobsheet, students will be able to:

1. Build an input dialog (`JDialog`) using Matisse to add a new account, with the account number generated automatically by the application (not typed by the user), then validate the other fields before use.
2. Connect GUI buttons to `Account`/`Bank` methods already built since previous meetings, including displaying an exception as a dialog instead of printing it to the console.
3. Read the currently selected row on a `JTable`, then use it to PREVENT an invalid action (disabling a button) instead of merely showing a warning after the action has already been clicked.

## B. Preparation and Prerequisites

- **Tools**: JDK 17 or newer, NetBeans.
- **Project**: this meeting continues the Maven `bank-mini` project and the `BankMiniFrame` file from Meeting 13.
- **Quick verification** before starting:
  ```bash
  mvn -version
  ```

> **Without NetBeans?** The form and dialog design steps (using the GUI Builder) require NetBeans. Students without NetBeans may use the finished `BankMiniFrame.java` and `AddAccountDialog.java` files from the checkpoint directly (complete, with no `.form`), then run `mvn -q compile exec:java`; the checkpoint and resulting GUI display remain exactly the same.

## C. Work Steps

### Step 1: Add-Account Dialog

So far, the two sample accounts have only ever been added through code (`seedSampleAccounts()`); there is still no way to add a new account through the GUI.

> **Concept Brief: `JDialog`, a Modal Window.** `JFrame` fits an application's main window, but is less suited to a single-use form such as "add account": once that form is finished or cancelled, its window should close immediately and return control to the window that opened it. `JDialog` is designed for this need. A dialog built as modal (`true`) BLOCKS interaction with its opening window while the dialog is still open, forcing the user to finish or cancel that dialog before returning to the main window, exactly like `JOptionPane.showMessageDialog(...)` already used since Meeting 14 (Part 1) but this time with a hand-built display.

Build the dialog in NetBeans:

1. Right-click package `id.ac.polinema.ui` > **New > Other... > Swing GUI Forms > JDialog Form**. Name the class `AddAccountDialog`.
2. NetBeans creates the constructor `AddAccountDialog(java.awt.Frame parent, boolean modal)`. In the **Source** tab, change it to accept `Bank` directly and always be modal, the same way Meeting 15 will later change `BankMiniFrame`'s constructor to accept a `username`:

   ```java
   public AddAccountDialog(java.awt.Frame parent, Bank bank) {
       super(parent, true);
       initComponents();
       this.bank = bank;
       accountNumberValueLabel.setText(bank.nextAccountNumber());
   }
   ```

3. Return to the **Design** tab. From the **Swing Containers** palette, drag a **Panel** component onto the form. Name it `formPanel`, **Set Layout > Grid Layout** with **rows** = 5, **columns** = 2, **hgap** and **vgap** = 6.
4. Drag into `formPanel`, in order: a Label "Account Number:", a Label (NOT a Text Field, since its value is generated automatically and must not be typed by the user) named `accountNumberValueLabel`; a Label "Owner:", a Text Field (`ownerField`); a Label "Phone:", a Text Field (`phoneField`); a Label "Type:", a Combo Box (`accountTypeCombo`); a Label "Initial Balance:", a Text Field (`initialBalanceField`).
5. Right-click `accountTypeCombo` > **Properties** > property **model**, open the editor, and fill in two values: `Savings` and `Checking`.
6. Drag a second **Panel** below `formPanel`, name it `buttonsPanel`, **Set Layout > Grid Layout** with rows = 1, columns = 2. Drag into it a Button "Save" (`saveButton`) and a Button "Cancel" (`cancelButton`).
7. Double-click **Save** and **Cancel** in turn to create methods `saveButtonActionPerformed` and `cancelButtonActionPerformed`.

> ✅ **Checkpoint (design):** the **Design** tab shows five label-input pairs, with the first row being two Labels (not a Label and a Text Field), then the Save and Cancel buttons below.

Fill in the handlers in the **Source** tab:

![AddAccountDialog.java, constructor](../assets/code/pertemuan-14/p14-01-addaccountdialog-constructor.png){width=68%}

![AddAccountDialog.java, saveButtonActionPerformed](../assets/code/pertemuan-14/p14-01-addaccountdialog-savehandler.png){width=72%}

Note: `accountNumberValueLabel` is a `JLabel` (not a `JTextField`), so the account number CANNOT be edited by the user. With the number always coming from `Bank.nextAccountNumber()`, there is no way for the user to type a number that collides with another account; the class of error "duplicate account number" is prevented by design, rather than checked and rejected after it has already been typed.

Now update `BankMiniFrame` so the **Add Account...** button opens this dialog, instead of filling in a form in the main window:

1. Open `BankMiniFrame` in **Design** mode. Delete the `formPanel` component that may have been built in a previous exercise (if any), keeping `accountScrollPane` and the button panel.
2. Drag a **Panel** below `accountScrollPane`, name it `buttonsPanel`, **Set Layout > Grid Layout** with rows = 1, columns = 2. Drag into it a Button "Add Account..." (`addAccountButton`) and a Button "Refresh" (`refreshButton`).
3. Double-click **Add Account...** to create the method `addAccountButtonActionPerformed`.

Fill in the supporting methods in the **Source** tab:

![BankMiniFrame.java, constructor, seedSampleAccounts, loadAccounts](../assets/code/pertemuan-14/p14-01-bankminiframe-fields.png){width=68%}

![BankMiniFrame.java, addAccountButtonActionPerformed](../assets/code/pertemuan-14/p14-01-bankminiframe-addaccounthandler.png){width=60%}

<!-- TODO(screenshot): mock-up SVG, not a genuine screenshot (the writing sandbox has no X server/Xvfb). Replace with a genuine AddAccountDialog screenshot once display access is available; see conventions/bank-mini.md, section "Verifikasi visual GUI tanpa NetBeans/X server". -->
![Mock-up of the Add Account dialog with account number A003 filled in automatically](../assets/uml/p14-add-account-dialog.png){width=55%}

> ✅ **Checkpoint:** run **Run Project** (F6), click **Add Account...**. The dialog opens with an account number (e.g. `A003`) already filled in automatically and not retypeable. Fill in the owner name and initial balance, pick the account type, then click **Save**. The dialog closes and a new row appears in the main window's table.

> ⚠️ **If it fails:** if an "Invalid input" dialog appears even though the fields look correct, check whether **Initial Balance** contains only digits (no thousands separator or currency symbol), since `Double.parseDouble()` cannot parse that kind of format. If the dialog opens but the account number is empty or always `A001`, check whether `accountNumberValueLabel.setText(bank.nextAccountNumber())` is genuinely called in the constructor AFTER `initComponents()`.

### Step 2: Deposit and Withdraw

> **Concept Brief: The Selected Row on a JTable.** `JTable` provides `getSelectedRow()`, returning the index of the row currently highlighted by the user (or `-1` if none is selected). The value at a specific cell in that row can be fetched through `getValueAt(row, column)`. Instead of checking for `-1` inside every action button and then showing a warning, this selected index can also be watched through a `ListSelectionListener` to ENABLE or DISABLE the action buttons themselves; the user is physically unable to click a button that should not be clickable yet, an error prevented by design, rather than caught after it happens.

Add two action buttons, following steps similar to Step 1:

1. Open `BankMiniFrame` Design. Change `buttonsPanel` to rows = 1, columns = 4.
2. Drag two new Buttons BETWEEN "Add Account..." and "Refresh": "Deposit..." (`depositButton`) and "Withdraw..." (`withdrawButton`).
3. In both buttons' **Properties** panel, change the **enabled** property to `false`, so both appear gray (disabled) until a row is selected.
4. Double-click each button to create methods `depositButtonActionPerformed` and `withdrawButtonActionPerformed`.

> ✅ **Checkpoint (design):** the Deposit... and Withdraw... buttons appear gray (disabled) in the **Design** tab.

Fill in both methods, plus one helper method to read the currently selected account, and one method connecting row selection to button state:

![BankMiniFrame.java, configureSelectionListener](../assets/code/pertemuan-14/p14-02-selectionlistener.png){width=62%}

![BankMiniFrame.java, getSelectedAccount](../assets/code/pertemuan-14/p14-02-getselectedaccount.png){width=60%}

![BankMiniFrame.java, depositButtonActionPerformed](../assets/code/pertemuan-14/p14-02-deposithandler.png){width=72%}

![BankMiniFrame.java, withdrawButtonActionPerformed](../assets/code/pertemuan-14/p14-02-withdrawhandler.png){width=72%}

Call `configureSelectionListener()` in the constructor, AFTER `loadAccounts()`, so its listener is installed before the user has any chance to select a row.

<!-- TODO(screenshot): mock-up SVG, not a genuine screenshot. Replace with a genuine BankMiniFrame screenshot once display access is available; see conventions/bank-mini.md, section "Verifikasi visual GUI tanpa NetBeans/X server". -->
![Mock-up of the BankMiniFrame window with one row selected, Deposit and Withdraw buttons active](../assets/uml/p14-window-selected.png){width=62%}

> ✅ **Checkpoint:** run **Run Project** (F6). The **Deposit...** and **Withdraw...** buttons appear gray until an account row is clicked. Select one row, both buttons turn active; click **Deposit...**, an input dialog appears reading "Deposit amount for A001 (Nadia):"; type a number, that row's balance increases. Also try **Withdraw...** with an amount exceeding that account's limit (see Meeting 6-7 for each account kind's rule): an error dialog appears displaying the `InsufficientBalanceException` message, not the program halting forcibly.

> ⚠️ **If it fails:** if the Deposit/Withdraw buttons can still be clicked even with no row selected, check whether both buttons' **enabled** property is set to `false` in Matisse, and whether `configureSelectionListener()` is genuinely called in the constructor. If clicking Cancel on the amount input dialog instead displays an "Amount must be a number" dialog, check whether the method checks `input == null` (Cancel) BEFORE trying to parse its contents.

## D. Assignment and Deliverables

Submit the following according to the format requested by the instructor:

- Screenshot of the `BankMiniFrame` window after Step 2, including one failed Withdraw attempt (error dialog shown).
- **Independent assignment:**
  1. Add a **Process Month End** button (extend `buttonsPanel` to rows = 1, columns = 5), calling `bank.processMonthEnd()` (from Meeting 10) then displaying a confirmation dialog "Month-end processing complete.":

     ![BankMiniFrame.java, processMonthEndButtonActionPerformed](../assets/code/pertemuan-14/p14-tugas-processmonthend.png){width=68%}

  2. Answer briefly (2 to 3 sentences for each question):
     - (a) why is `NumberFormatException` validation still needed in the GUI, even though the amount input dialog "should" only ever be filled with a number?
     - (b) Compare how `InsufficientBalanceException` was handled in Meeting 10 (printed to the console) with how it is handled in this jobsheet (displayed as a dialog). What changed, and what stayed the same?

Note one thing deliberately left undiscussed so far: anyone running `BankMiniFrame` gets full access to every account, with no login at all. A real banking application is never released like this. Meeting 15 closes this gap by adding a genuine authentication mechanism, and along with it, the first concrete reason Bank Mini needs a database: login credentials must be stored and checked against stored data, not values written directly in the Java code.

## E. Grading Criteria

| Component | Weight | Full Criteria (100%) | Minimum Criteria |
|---|---:|---|---|
| Work steps completed | 40% | All steps carried out and functioning | Most steps completed, final result runs |
| Checkpoints verified | 35% | All checkpoints reached and demonstrated (screenshot) | Some checkpoints demonstrated |
| Independent assignment | 25% | Process Month End button correct and conceptual answers accurate | Some of the assignment completed even though answers are incomplete |
