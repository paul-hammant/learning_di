package learning.di.testing.components;


// This is one per Request
public class UpSell {
    private static int REF = 1;
    private final String item;
    private final Cart cart;
    private int inst;

    public UpSell(Cart cart) {
        this.cart = cart;
        if (cart.contains("iMac")) {
            item = "InkJet Printer";
        } else {
            item = "iMac";
        }
        inst = REF++;
    }

    @Override
    public String toString() {
        return "UpSell# " + inst + ", item: " + item + ", Cart: (" + cart + ")";
    }
}
