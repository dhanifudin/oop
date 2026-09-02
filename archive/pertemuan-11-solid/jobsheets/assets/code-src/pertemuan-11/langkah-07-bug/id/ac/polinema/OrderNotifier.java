package id.ac.polinema;

public interface OrderNotifier {
    void printInvoice();
    void sendEmailReceipt();
    void printShippingLabel();
}
