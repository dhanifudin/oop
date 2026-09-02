# Pertemuan 11 Solution: Order Processing (Refactored)

Reference solution for the Pertemuan 11 jobsheet (SOLID Principles), for instructor/grading use. Not linked from the student-facing jobsheet.

All five principles applied to the `pertemuan-11-starter` code:

- **SRP**: `OrderProcessor` orchestrates `DiscountCalculator`, `OrderRepository`, and `ReceiptPrinter` instead of doing everything itself.
- **OCP**: `DiscountPolicy` (`RegularDiscountPolicy`, `VipDiscountPolicy`, `WholesaleDiscountPolicy`) lets new customer types be added without editing `DiscountCalculator`.
- **LSP**: `Shippable` separates `PhysicalOrder` (can be shipped) from `DigitalOrder` (cannot), so `Order` substitution never surprises calling code.
- **ISP**: `InvoicePrintable`/`EmailReceiptSendable`/`ShippingLabelPrintable` replace one fat `OrderNotifier` interface.
- **DIP**: `OrderProcessor` depends on the `OrderRepository` interface and receives its collaborators via constructor injection (`FileOrderRepository` or `InMemoryOrderRepository` are interchangeable).

Run with Maven:

```bash
mvn -q compile exec:java                                             # order flow demo
mvn -q compile exec:java -Dexec.mainClass=id.ac.polinema.ShippingDemo # LSP demo
```

The `OrderValidator` extraction (the jobsheet's tugas mandiri) is intentionally left out here, it is the students' own exercise.
