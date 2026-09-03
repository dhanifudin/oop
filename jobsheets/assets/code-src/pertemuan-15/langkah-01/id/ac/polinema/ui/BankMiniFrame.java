package id.ac.polinema.ui;

import id.ac.polinema.Bank;
import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.InsufficientBalanceException;
import id.ac.polinema.model.SavingsAccount;
import id.ac.polinema.repository.JdbcAccountRepository;

public class BankMiniFrame extends javax.swing.JFrame {

    private static final double DEFAULT_INTEREST_RATE = 0.01;
    private static final double DEFAULT_OVERDRAFT_LIMIT = 50000;

    private Bank bank;

    public BankMiniFrame() {
        initComponents();
        bank = new Bank(new JdbcAccountRepository("bankmini.db"));
        seedSampleAccountsIfEmpty();
        loadAccounts();
    }

    private void seedSampleAccountsIfEmpty() {
        if (!bank.getAllAccounts().isEmpty()) {
            return;
        }
        Customer customer1 = new Customer("Nadia", "0812-0000-0001");
        bank.addAccount(new SavingsAccount("A001", customer1, 500000, 0.01));

        Customer customer2 = new Customer("Sari", "0812-0000-0002");
        bank.addAccount(new CheckingAccount("A002", customer2, 200000, 50000));
    }

    private void loadAccounts() {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) accountTable.getModel();
        model.setRowCount(0);
        for (Account acc : bank.getAllAccounts()) {
            model.addRow(new Object[]{
                acc.getAccountNumber(),
                acc.getOwner().getName(),
                acc.getBalance()
            });
        }
    }

    private void clearAddAccountFields() {
        accountNumberField.setText("");
        ownerField.setText("");
        phoneField.setText("");
        initialBalanceField.setText("");
    }

    private Account getSelectedAccount() {
        int row = accountTable.getSelectedRow();
        if (row < 0) {
            return null;
        }
        String accountNumber = (String) accountTable.getValueAt(row, 0);
        return bank.findAccount(accountNumber);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        accountScrollPane = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();
        formPanel = new javax.swing.JPanel();
        accountNumberLabel = new javax.swing.JLabel();
        accountNumberField = new javax.swing.JTextField();
        ownerLabel = new javax.swing.JLabel();
        ownerField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        typeLabel = new javax.swing.JLabel();
        accountTypeCombo = new javax.swing.JComboBox<>();
        initialBalanceLabel = new javax.swing.JLabel();
        initialBalanceField = new javax.swing.JTextField();
        addAccountButton = new javax.swing.JButton();
        actionsPanel = new javax.swing.JPanel();
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        depositButton = new javax.swing.JButton();
        withdrawButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Mini");

        accountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account Number", "Owner", "Balance"
            }
        ));
        accountScrollPane.setViewportView(accountTable);

        formPanel.setLayout(new java.awt.GridLayout(3, 4, 6, 6));

        accountNumberLabel.setText("Account Number:");
        formPanel.add(accountNumberLabel);
        formPanel.add(accountNumberField);

        ownerLabel.setText("Owner:");
        formPanel.add(ownerLabel);
        formPanel.add(ownerField);

        phoneLabel.setText("Phone:");
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        typeLabel.setText("Type:");
        formPanel.add(typeLabel);
        accountTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Savings", "Checking" }));
        formPanel.add(accountTypeCombo);

        initialBalanceLabel.setText("Initial Balance:");
        formPanel.add(initialBalanceLabel);
        formPanel.add(initialBalanceField);

        addAccountButton.setText("Add Account");
        addAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAccountButtonActionPerformed(evt);
            }
        });
        formPanel.add(new javax.swing.JLabel());
        formPanel.add(addAccountButton);

        actionsPanel.setLayout(new java.awt.GridLayout(1, 4, 6, 6));

        amountLabel.setText("Amount:");
        actionsPanel.add(amountLabel);
        actionsPanel.add(amountField);

        depositButton.setText("Deposit");
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });
        actionsPanel.add(depositButton);

        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });
        actionsPanel.add(withdrawButton);

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(actionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(actionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadAccounts();
    }

    private void addAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String accountNumber = accountNumberField.getText().trim();
        String ownerName = ownerField.getText().trim();
        String phone = phoneField.getText().trim();

        if (accountNumber.isEmpty() || ownerName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Account number and owner name are required.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        double initialBalance;
        try {
            initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Initial balance must be a number.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer owner = new Customer(ownerName, phone);
        Account account;
        if (accountTypeCombo.getSelectedItem().equals("Savings")) {
            account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
        } else {
            account = new CheckingAccount(accountNumber, owner, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
        }

        if (!bank.addAccount(account)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Account number " + accountNumber + " already exists.",
                    "Duplicate account", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        clearAddAccountFields();
        loadAccounts();
    }

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Account account = getSelectedAccount();
        if (account == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Select an account in the table first.",
                    "No account selected", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Amount must be a number.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        account.deposit(amount);
        bank.saveAccount(account);
        amountField.setText("");
        loadAccounts();
    }

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Account account = getSelectedAccount();
        if (account == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Select an account in the table first.",
                    "No account selected", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Amount must be a number.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            account.withdraw(amount);
            bank.saveAccount(account);
            amountField.setText("");
            loadAccounts();
        } catch (InsufficientBalanceException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Withdrawal failed", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JScrollPane accountScrollPane;
    private javax.swing.JTable accountTable;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel accountNumberLabel;
    private javax.swing.JTextField accountNumberField;
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JTextField ownerField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JComboBox<String> accountTypeCombo;
    private javax.swing.JLabel initialBalanceLabel;
    private javax.swing.JTextField initialBalanceField;
    private javax.swing.JButton addAccountButton;
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JTextField amountField;
    private javax.swing.JButton depositButton;
    private javax.swing.JButton withdrawButton;
    private javax.swing.JButton refreshButton;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
    }
}
