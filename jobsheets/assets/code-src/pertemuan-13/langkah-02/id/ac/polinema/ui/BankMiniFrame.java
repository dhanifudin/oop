package id.ac.polinema.ui;

import id.ac.polinema.Bank;
import id.ac.polinema.model.Account;
import id.ac.polinema.model.CheckingAccount;
import id.ac.polinema.model.Customer;
import id.ac.polinema.model.SavingsAccount;
import id.ac.polinema.repository.InMemoryAccountRepository;

public class BankMiniFrame extends javax.swing.JFrame {

    private Bank bank;

    public BankMiniFrame() {
        initComponents();
        bank = new Bank(new InMemoryAccountRepository());
        seedSampleAccounts();
        loadAccounts();
    }

    private void seedSampleAccounts() {
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        accountScrollPane = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accountScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadAccounts();
    }

    private javax.swing.JScrollPane accountScrollPane;
    private javax.swing.JTable accountTable;
    private javax.swing.JButton refreshButton;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
    }
}
