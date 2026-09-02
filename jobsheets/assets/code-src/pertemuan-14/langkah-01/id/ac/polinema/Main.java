package id.ac.polinema;

import id.ac.polinema.ui.BankMiniFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
    }
}
