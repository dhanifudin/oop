package id.ac.polinema.ui;

import id.ac.polinema.PasswordHasher;
import id.ac.polinema.model.User;
import id.ac.polinema.repository.JdbcUserRepository;
import id.ac.polinema.repository.UserRepository;

public class LoginFrame extends javax.swing.JFrame {

    private UserRepository userRepository;

    public LoginFrame() {
        initComponents();
        userRepository = new JdbcUserRepository("bankmini.db");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        credentialsPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Mini - Login");

        credentialsPanel.setLayout(new java.awt.GridLayout(2, 2, 6, 6));

        usernameLabel.setText("Username:");
        credentialsPanel.add(usernameLabel);
        credentialsPanel.add(usernameField);

        passwordLabel.setText("Password:");
        credentialsPanel.add(passwordLabel);
        credentialsPanel.add(passwordField);

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(credentialsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(loginButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(credentialsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPasswordHash().equals(PasswordHasher.hash(password))) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login failed", javax.swing.JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            return;
        }

        dispose();
        new BankMiniFrame(username).setVisible(true);
    }

    private javax.swing.JPanel credentialsPanel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton loginButton;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
