package id.ac.polinema;

public class InvoicePrinter implements OrderNotifier {
    public void printInvoice() {
        System.out.println("Printing invoice...");
    }

    public void sendEmailReceipt() {
        throw new UnsupportedOperationException("Invoice printer cannot send email.");
    }

    public void printShippingLabel() {
        throw new UnsupportedOperationException("Invoice printer cannot print shipping labels.");
    }
}
