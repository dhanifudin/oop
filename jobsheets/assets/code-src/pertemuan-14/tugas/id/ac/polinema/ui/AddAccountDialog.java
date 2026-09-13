package id.ac.polinema.ui;

import id.ac.polinema.Bank;
import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.SavingsAccount;

public class AddAccountDialog extends javax.swing.JDialog {

    private static final double DEFAULT_INTEREST_RATE = 0.01;
    private static final double DEFAULT_OVERDRAFT_LIMIT = 50000;

    private Bank bank;

    public AddAccountDialog(java.awt.Frame parent, Bank bank) {
        super(parent, true);
        initComponents();
        this.bank = bank;
        accountNumberValueLabel.setText(bank.nextAccountNumber());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        formPanel = new javax.swing.JPanel();
        accountNumberLabel = new javax.swing.JLabel();
        accountNumberValueLabel = new javax.swing.JLabel();
        ownerLabel = new javax.swing.JLabel();
        ownerField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        typeLabel = new javax.swing.JLabel();
        accountTypeCombo = new javax.swing.JComboBox<>();
        initialBalanceLabel = new javax.swing.JLabel();
        initialBalanceField = new javax.swing.JTextField();
        buttonsPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Account");

        formPanel.setLayout(new java.awt.GridLayout(5, 2, 6, 6));

        accountNumberLabel.setText("Account Number:");
        formPanel.add(accountNumberLabel);
        accountNumberValueLabel.setText("A000");
        formPanel.add(accountNumberValueLabel);

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

        buttonsPanel.setLayout(new java.awt.GridLayout(1, 2, 6, 6));

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(saveButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(cancelButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String ownerName = ownerField.getText().trim();
        String phone = phoneField.getText().trim();

        if (ownerName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Owner name is required.",
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

        String accountNumber = accountNumberValueLabel.getText();
        Customer owner = new Customer(ownerName, phone);
        Account account;
        if (accountTypeCombo.getSelectedItem().equals("Savings")) {
            account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
        } else {
            account = new CheckingAccount(accountNumber, owner, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
        }

        bank.addAccount(account);
        dispose();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel accountNumberLabel;
    private javax.swing.JLabel accountNumberValueLabel;
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JTextField ownerField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JComboBox<String> accountTypeCombo;
    private javax.swing.JLabel initialBalanceLabel;
    private javax.swing.JTextField initialBalanceField;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton cancelButton;
}
