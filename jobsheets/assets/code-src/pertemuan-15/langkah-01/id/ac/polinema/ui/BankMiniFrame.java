package id.ac.polinema.ui;

import id.ac.polinema.Bank;
import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.InsufficientBalanceException;
import id.ac.polinema.model.SavingsAccount;
import id.ac.polinema.repository.JdbcAccountRepository;

public class BankMiniFrame extends javax.swing.JFrame {

    private Bank bank;

    public BankMiniFrame() {
        initComponents();
        bank = new Bank(new JdbcAccountRepository("bankmini.db"));
        seedSampleAccountsIfEmpty();
        loadAccounts();
        configureSelectionListener();
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

    private void configureSelectionListener() {
        accountTable.getSelectionModel().addListSelectionListener(evt -> {
            boolean rowSelected = accountTable.getSelectedRow() >= 0;
            depositButton.setEnabled(rowSelected);
            withdrawButton.setEnabled(rowSelected);
        });
    }

    private Account getSelectedAccount() {
        int row = accountTable.getSelectedRow();
        String accountNumber = (String) accountTable.getValueAt(row, 0);
        return bank.findAccount(accountNumber);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        accountScrollPane = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        addAccountButton = new javax.swing.JButton();
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

        buttonsPanel.setLayout(new java.awt.GridLayout(1, 4, 6, 6));

        addAccountButton.setText("Add Account...");
        addAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAccountButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(addAccountButton);

        depositButton.setText("Deposit...");
        depositButton.setEnabled(false);
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(depositButton);

        withdrawButton.setText("Withdraw...");
        withdrawButton.setEnabled(false);
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(withdrawButton);

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(refreshButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(accountScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadAccounts();
    }

    private void addAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new AddAccountDialog(this, bank).setVisible(true);
        loadAccounts();
    }

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Account account = getSelectedAccount();
        String input = javax.swing.JOptionPane.showInputDialog(this,
                "Deposit amount for " + account.getAccountNumber() + " (" + account.getOwner().getName() + "):");
        if (input == null) {
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Amount must be a number.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        account.deposit(amount);
        bank.saveAccount(account);
        loadAccounts();
    }

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Account account = getSelectedAccount();
        String input = javax.swing.JOptionPane.showInputDialog(this,
                "Withdraw amount for " + account.getAccountNumber() + " (" + account.getOwner().getName() + "):");
        if (input == null) {
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Amount must be a number.",
                    "Invalid input", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            account.withdraw(amount);
            bank.saveAccount(account);
            loadAccounts();
        } catch (InsufficientBalanceException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Withdrawal failed", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JScrollPane accountScrollPane;
    private javax.swing.JTable accountTable;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton addAccountButton;
    private javax.swing.JButton depositButton;
    private javax.swing.JButton withdrawButton;
    private javax.swing.JButton refreshButton;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
    }
}
