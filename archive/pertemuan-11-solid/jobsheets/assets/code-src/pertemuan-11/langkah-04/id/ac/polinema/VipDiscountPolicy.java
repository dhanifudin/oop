package id.ac.polinema;

public class VipDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 15 / 100;
    }
}
