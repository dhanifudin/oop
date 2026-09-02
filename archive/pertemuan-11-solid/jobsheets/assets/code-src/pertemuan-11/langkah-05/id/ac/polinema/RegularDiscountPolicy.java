package id.ac.polinema;

public class RegularDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 5 / 100;
    }
}
