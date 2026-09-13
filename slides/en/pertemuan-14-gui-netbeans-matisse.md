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

Meeting 14: **GUI with NetBeans Matisse (Part 2)**

Accepting user input safely

---

## What You Will Learn

- How to validate input at its entry point (boundary): input from a user can never simply be trusted
- How to display a failure as a dialog the user can read, rather than printing it to the console or letting the program halt forcibly
- How to read the currently selected row on a `JTable`, then use it to PREVENT an invalid action (disabling a button), rather than merely catching it after it happens
- Application to Bank Mini: an add-account dialog with an automatic number, deposit and withdraw buttons on `BankMiniFrame`

<div class="tip-box">
Programming exercises for today's material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 14.
</div>

---

## Today's Session Map

- **Session 1 (50')**: Validating input at its entry point
- **Session 2 (50')**: The selected row on a JTable
- **Session 3 (50')**: Applying the add-account dialog to Bank Mini
- **Session 4 (50')**: Applying deposit and withdraw to Bank Mini

---

<!-- _class: divider -->

# Part 1
## Validating Input at Its Entry Point

Session 1 of 4

---

## A User Can Type Anything

A text input field on a GUI does not restrict what a user can type: a field meant for a number can still be filled with letters, left empty, or filled with an unexpected format.

<div class="warn-box">
Code that directly uses an input field's contents without checking it first will halt forcibly (an exception) the moment its contents do not match expectations.
</div>

---

## Why Does This Matter?

Most bugs reported by users of real applications do not come from a wrong algorithm, but from unexpected input that was never validated: a field left empty, a date format different from what was expected, a decimal number written with a different separator. Input validation, especially at an application's data entry point (boundary), is one of the most fundamental practices for preventing this class of bug.

<div class="term-box">
An application that fails to validate input at its entry point also opens a security gap: many software vulnerabilities, from merely annoying to genuinely serious, are rooted in data that was simply trusted without being checked first.
</div>

---

## Validation, Not Just Hoping

![h:280 Comparing unvalidated input (the program halts forcibly) with validated input (a message dialog, the program keeps running)](../assets/illustrations/input-validation-boundary.svg)

<div class="term-box">
The pattern used is the same as exception handling in Meeting 10: wrap an operation at risk of failing (e.g. <code>Double.parseDouble(...)</code>) in <code>try</code>, handle its failure in <code>catch</code>. The difference is that in a GUI, the handling takes the form of a dialog the user can read directly, rather than a message on the console.
</div>

---

## Code Example: Catching NumberFormatException

```java
double initialBalance;
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    // display a dialog, then return
}
```

`Double.parseDouble(...)` throws `NumberFormatException` (unchecked, a subclass of `RuntimeException`) the moment its input is not a valid number; `try`/`catch` is still used so the program does not halt forcibly, even though the compiler itself does not require it.

---

## JOptionPane: Displaying a Dialog

<div class="term-box">
<code>JOptionPane.showMessageDialog(parent, message, title, type)</code> displays a small dialog window containing a message, then temporarily blocks interaction with other windows until the user closes it. The <i>type</i> parameter (e.g. <code>ERROR_MESSAGE</code>, <code>WARNING_MESSAGE</code>) determines the icon displayed.
</div>

---

## Code Example: Displaying an Error Dialog

```java
JOptionPane.showMessageDialog(this,
        "Amount must be a number.",
        "Invalid input", JOptionPane.ERROR_MESSAGE);
return;
```

This dialog is what the user actually reads, replacing an error message that previously was only printed to the console or that halted the program forcibly.

---

## Common Mistake: Forgetting return After Showing a Dialog

<div class="warn-box">
<b>Wrong:</b> displaying an error dialog through <code>JOptionPane.showMessageDialog(...)</code>, then still continuing to the next code with no <code>return;</code>, as if validation were already "done" once the dialog appears.
</div>

**Right:** a dialog ONLY displays a message; it does not halt the program's flow by itself. Without a `return;` afterward, the following code still runs using a value that may not be valid (e.g. an `initialBalance` variable that failed to be assigned), risking another exception or incorrect data.

---

## Exercise

```java
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Initial balance must be a number.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
}
Account account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
```

This code does not write `return;` inside the `catch` block. Explain what could go wrong, then state the fix.

---

## Exercise Answer

**Problem:** if `Double.parseDouble(...)` fails, `initialBalance` is never assigned (a "variable might not have been initialized" compile error), or if it already had a previous default value, `new SavingsAccount(...)` still gets called with an old value the user never intended. **Fix:** add `return;` as the last line of the `catch` block, so a new account is never created using an invalid `initialBalance`.

---

## Part 1 Summary

- Input from a user can never simply be trusted; validation at the entry point prevents both an exception and a security gap.
- The same `try`/`catch` pattern from Meeting 10 is used again, only its handling now takes the form of a dialog (`JOptionPane`), not a console message.
- Every branch where validation fails must end with `return;`; a dialog alone does not halt the program's flow.

Next: Part 2 covers how to know which row a user currently has selected in a table.

---

<!-- _class: divider -->

# Part 2
## The Selected Row on a JTable

Session 2 of 4

---

## One Table, Many Rows, One Selected

A `JTable` can display dozens of rows at once. When a user clicks an action button (e.g. "Delete" or "Edit"), that code itself has no idea which data row is meant, unless it is told which row the user currently has highlighted.

---

## Why Does This Matter?

The "find the selected index, then fetch its data" pattern is used repeatedly in nearly every application that displays a list with action buttons: an email client (choosing which message to delete), a file manager (choosing which file to copy), even a spreadsheet application (choosing which cell to format). Without a way to know the selected row, an action button would never know which object to process.

<div class="term-box">
Mastering this pattern once means understanding how nearly every list/table-based interface works, far beyond this one Bank Mini application.
</div>

---

## getSelectedRow() and getValueAt()

![h:260 The currently selected row in a table determines which object gets processed](../assets/illustrations/table-selection.svg)

<div class="term-box">
<code>JTable.getSelectedRow()</code> returns the index of the row currently highlighted by the user (or <code>-1</code> if none is selected). <code>getValueAt(row, column)</code> retrieves the value at a specific cell in that row.
</div>

---

## Code Example: Reading the Selected Row

```java
int row = table.getSelectedRow();
if (row < 0) {
    return; // no row selected yet
}
String name = (String) table.getValueAt(row, 0);
```

Checking `row < 0` BEFORE calling `getValueAt(...)` prevents the code from trying to read a row that does not actually exist.

---

## Common Mistake: Forgetting to Check -1

<div class="warn-box">
<b>Wrong:</b> calling <code>table.getValueAt(table.getSelectedRow(), 0)</code> directly without first checking whether <code>getSelectedRow()</code> returns <code>-1</code>.
</div>

**Right:** when no row is selected, `getSelectedRow()` returns `-1`, and `getValueAt(-1, 0)` throws `ArrayIndexOutOfBoundsException`. The returned index must be checked first (`if (row < 0) return;`) before being used to fetch data.

---

## Exercise

A customer table has not had a single row selected by the user yet. The following code runs:

```java
int row = table.getSelectedRow();
String name = (String) table.getValueAt(row, 0);
```

What happens? Explain, then state the fix.

---

## Exercise Answer

**`ArrayIndexOutOfBoundsException` is thrown.** `getSelectedRow()` returns `-1` since no row has been selected, then `getValueAt(-1, 0)` tries to access row -1, which never exists. Fix: add a check `if (row < 0) { return; }` (or display a warning dialog) before calling `getValueAt(...)`.

---

## Part 2 Summary

- `getSelectedRow()` returns the index of the row highlighted by the user, or `-1` if none is selected.
- `getValueAt(row, column)` retrieves a specific cell's value; the index must be checked before being used.
- This pattern applies broadly to nearly every list/table-based interface, not only `JTable` in Java.

Next: Part 3 applies input validation to Bank Mini's add-account form.

---

<!-- _class: divider -->

# Part 3
## Applying the Add-Account Dialog to Bank Mini

Session 3 of 4

---

## The Add-Account Dialog

![h:280 An Add Account dialog with an automatically generated account number](../assets/illustrations/bank-mini-add-account-dialog.svg)

`BankMiniFrame` now has a separate `JDialog`, `AddAccountDialog`, for adding a new account: account number (generated automatically, not typed), owner name, phone, account type, and initial balance. Before an `Account` object is actually created, every field that CAN still be typed is checked first.

---

## `JDialog`: A Modal Window

<div class="term-box">
<code>JFrame</code> fits an application's main window, but is less suited to a single-use form such as "add account": once finished or cancelled, its window should close immediately and return control to the window that opened it. <code>JDialog</code> is designed for this need. A modal dialog (<code>true</code>) BLOCKS interaction with its opening window while the dialog is still open, exactly like <code>JOptionPane.showMessageDialog(...)</code> already used since Part 1, only this time with a display designed by hand through Matisse.
</div>

---

## Preventing Duplicates by Design: An Automatic Account Number

An earlier version let the user type an account number themselves, then CHECKED whether that number was already taken. That approach still leaves room for error: the user could still end up typing a number that collides with an existing one.

<div class="tip-box">
A better approach: do not let the user type an account number at all. <code>accountNumberValueLabel</code> on this dialog is a <code>JLabel</code> (not a <code>JTextField</code>), filled automatically from <code>Bank.nextAccountNumber()</code>. The class of error "duplicate account number" is PREVENTED by design, rather than caught after it has already been typed.
</div>

---

## Code Example: Bank.nextAccountNumber()

```java
public String nextAccountNumber() {
    int max = 0;
    for (Account acc : repository.findAll()) {
        String number = acc.getAccountNumber();
        if (number.length() == 4 && number.charAt(0) == 'A') {
            max = Math.max(max, Integer.parseInt(number.substring(1)));
        }
    }
    return String.format("A%03d", max + 1);
}
```

The highest existing account number is found first, then the next number is generated from it, e.g. `A001`, `A002` produces `A003`.

---

## Code Example: Checking a Required Field

```java
String ownerName = ownerField.getText().trim();

if (ownerName.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Owner name is required.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
    return;
}
```

Only `ownerField` needs to be checked for emptiness; `accountNumberValueLabel` is never empty or wrongly formatted, since its content is never typed by the user.

---

## Code Example: Parsing the Initial Balance

```java
double initialBalance;
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,
            "Initial balance must be a number.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
    return;
}
```

The pattern from Part 1 is used exactly the same way: wrap `Double.parseDouble(...)` in `try`, display a dialog and `return;` in `catch`.

---

## Code Example: Creating and Saving an Account

```java
String accountNumber = accountNumberValueLabel.getText();
Account account;
if (accountTypeCombo.getSelectedItem().equals("Savings")) {
    account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
} else {
    account = new CheckingAccount(accountNumber, owner, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
}

bank.addAccount(account);
dispose();
```

The account type selected in `accountTypeCombo` decides which `Account` subclass gets created; nothing differs from how these two classes have been used since Meeting 6. `bank.addAccount(...)` no longer needs its return value checked as an older version did, since `nextAccountNumber()` guarantees the number is always new; `dispose()` closes the dialog, and `BankMiniFrame` reloads its table afterward.

---

## Exercise

Suppose `AddAccountDialog` were built NOT modal (`super(parent, false)`). A user clicks the **Add Account...** button twice in a row before the first dialog has a chance to close, fills in both, then presses **Save** on both dialogs quickly.

What could go wrong? Relate your answer to `Bank.nextAccountNumber()`.

---

## Exercise Answer

Since it is not modal, `BankMiniFrame` stays responsive while the first dialog is open, so the **Add Account...** button can be clicked again, opening a SECOND dialog. If both dialogs remain open before either one saves, BOTH call `bank.nextAccountNumber()` while the highest account number is still the same, producing the EXACT SAME account number for two different accounts. A modal dialog (`true`) prevents this by blocking interaction with other windows, including clicking the **Add Account...** button again, while the first dialog is still open.

---

## Part 3 Summary

- `AddAccountDialog` is a separate modal window; its account number is generated automatically through `Bank.nextAccountNumber()`, not typed by the user, preventing duplicates by design.
- Required field validation and initial balance parsing use the same pattern as Part 1.
- A `JDialog`'s modal nature also prevents two add-account dialogs from being open at once, which could break the assumption that account numbers are always unique.

Next: Part 4 applies the selected-row pattern to PREVENT the deposit and withdraw buttons from being clicked with no account selected.

---

<!-- _class: divider -->

# Part 4
## Applying Deposit and Withdraw to Bank Mini

Session 4 of 4

---

## Deposit and Withdraw

![h:220 The Deposit and Withdraw buttons become active once a row is selected](../assets/illustrations/bank-mini-window-selected.svg)

The **Deposit...** and **Withdraw...** buttons start out DISABLED (gray). Both only turn active once the user selects a row in the table, then use `getSelectedAccount()` to find the matching `Account` object through `Bank.findAccount()`.

<div class="tip-box">
Not one of the <code>Account</code>, <code>Bank</code>, or <code>InsufficientBalanceException</code> classes is changed to support this GUI. The GUI simply calls methods already in place since a few meetings ago, through a different route.
</div>

---

## Prevent, Not Just Handle: Disable the Button

A version that only checks `row < 0` and then shows a warning dialog ("Select an account first") still LETS the user click a button that should not be clickable yet, only handling it after the fact.

<div class="term-box">
A better approach: connect the table's selection state directly to its own button's ENABLED/DISABLED state through a <code>ListSelectionListener</code>. The moment no row is selected, the Deposit and Withdraw buttons are disabled (<code>setEnabled(false)</code>); the user is PHYSICALLY unable to click them. The class of error "no account selected" is prevented by interface design, rather than caught through a warning dialog after the button has already been clicked.
</div>

---

## Code Example: configureSelectionListener()

```java
private void configureSelectionListener() {
    accountTable.getSelectionModel().addListSelectionListener(evt -> {
        boolean rowSelected = accountTable.getSelectedRow() >= 0;
        depositButton.setEnabled(rowSelected);
        withdrawButton.setEnabled(rowSelected);
    });
}
```

Called once in the constructor, AFTER `loadAccounts()`. This one listener controls BOTH buttons at once, every time the selected row changes.

---

## Code Example: getSelectedAccount()

```java
private Account getSelectedAccount() {
    int row = accountTable.getSelectedRow();
    String accountNumber = (String) accountTable.getValueAt(row, 0);
    return bank.findAccount(accountNumber);
}
```

Unlike Part 2, this method NO LONGER checks `row < 0`: since `depositButton`/`withdrawButton` can only be clicked once a row is genuinely selected, `getSelectedRow()` here is guaranteed valid by `configureSelectionListener()`.

---

## Code Example: depositButtonActionPerformed

```java
Account account = getSelectedAccount();
String input = JOptionPane.showInputDialog(this,
        "Deposit amount for " + account.getAccountNumber()
                + " (" + account.getOwner().getName() + "):");
if (input == null) {
    return;
}
```

`JOptionPane.showInputDialog(...)` displays a dialog containing one text field, returning its contents as a `String`, or `null` if the user presses Cancel.

---

## Common Mistake: Treating Cancel the Same as an Empty Field

<div class="warn-box">
<b>Wrong:</b> calling <code>Double.parseDouble(input.trim())</code> directly with no check for <code>input == null</code> first, assuming Cancel produces the same <code>NumberFormatException</code> as an empty field.
</div>

**Right:** pressing Cancel on `showInputDialog(...)` returns `null`, NOT an empty string `""`. Calling `.trim()` on `null` throws `NullPointerException`, not `NumberFormatException`. The check `if (input == null) return;` must be done BEFORE the `try`/`catch` block for `NumberFormatException`, since the two are different kinds of failure.

---

## Code Example: withdrawButtonActionPerformed

```java
try {
    account.withdraw(amount);
    loadAccounts();
} catch (InsufficientBalanceException e) {
    JOptionPane.showMessageDialog(this,
            e.getMessage(),
            "Withdrawal failed", JOptionPane.ERROR_MESSAGE);
}
```

Exactly the Meeting 10 pattern: `withdraw()` throws `InsufficientBalanceException`, this time its message is displayed through a dialog instead of printed to the console.

---

## Exercise

A `SavingsAccount` with a balance of Rp 100,000 is selected in the table, then the user clicks **Withdraw...**, types `500000` into the input dialog, and presses OK.

What happens to the input dialog and to the table? Explain the flow line by line.

---

## Exercise Answer

The input dialog ALWAYS closes once OK is pressed, since `showInputDialog` is not a text field that stays put in the main window. `input` contains `"500000"`, and `Double.parseDouble(...)` succeeds. `account.withdraw(500000)` is called, throwing `InsufficientBalanceException` since a balance of Rp 100,000 is not enough to leave `SavingsAccount`'s `MINIMUM_BALANCE`. The line `loadAccounts()` is NEVER reached (skipped by the exception), the "Withdrawal failed" error dialog appears displaying its message, and the table still shows the OLD balance, since no change was successfully saved.

---

## Part 4 Summary

- `configureSelectionListener()` connects the selected row to `depositButton`/`withdrawButton`'s enabled state, preventing a click with no account selected through design, not a warning dialog.
- `JOptionPane.showInputDialog(...)` returns `null` (not an empty string) if Cancel is pressed; this MUST be checked before `Double.parseDouble(...)`.
- `withdrawButtonActionPerformed` uses the same `try`/`catch` pattern since Meeting 10 for `InsufficientBalanceException`.

---

## Meeting 14 Summary

- User input is validated at its entry point through `try`/`catch`, with its failure displayed through `JOptionPane`, not the console.
- The selected row on a `JTable` is used to PREVENT an invalid action through `ListSelectionListener` and `setEnabled(...)`, rather than merely catching it after a button has already been clicked.
- Bank Mini applies both: an add-account dialog with an automatic number, and deposit/withdraw buttons disabled until an account is selected.

---

<!-- _class: lead -->

# References

Deitel, *Java How to Program*, chapter on GUI Components, Exception Handling

Oracle Java Tutorials: ["How to Use Tables"](https://docs.oracle.com/javase/tutorial/uiswing/components/table.html), "Validating Input"

Programming exercises for this material are available in the Practicum: Object-Oriented Programming jobsheet (RTI253008), Meeting 14

---

## Discussion

`configureSelectionListener()` sets the enabled state of BOTH buttons (`depositButton` and `withdrawButton`) at once from ONE listener, rather than two separate listeners on each button. Explain in your own words why this approach is better than checking `getSelectedRow()` separately inside each of `depositButtonActionPerformed`/`withdrawButtonActionPerformed` (relate your answer to the Single Responsibility Principle from Meeting 11).
