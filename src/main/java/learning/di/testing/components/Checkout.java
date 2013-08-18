package learning.di.testing.components;


// This is one per Request
public class Checkout {

    private static int REF = 1;
    private int inst;

    private Cart cart;
    private final Order order;

    public Checkout(Cart cart, Order order) {
        this.cart = cart;
        this.order = order;
        inst = REF++;
    }

    @Override
    public String toString() {
        return "Checkout: " + inst + " (" + cart.toString() + ")";
    }

    public Object calculateSalesTax(String zipCode) {
        return new SalesTax(Integer.parseInt(zipCode));
    }

    public CartContents cartContents(UpSell upSell) {
        return new CartContents(upSell);
    }

    public class CartContents {

        private final UpSell upSell;

        public CartContents(UpSell upSell) {
            this.upSell = upSell;
        }

        @Override
        public String toString() {
            return "CartContents{" +
                    "cart=" + cart +
                    ", order=" + order +
                    ", upSell=" + upSell +
                    '}';
        }
    }

    public class SalesTax {
        int zipCode;
        int taxAmountCents = 875;

        public SalesTax(int zipCode) {
            this.zipCode = zipCode;
        }

        @Override
        public String toString() {
            return "SalesTax{" +
                    "cart: " + cart +
                    ", order: " + order +
                    ", zipCode:" + zipCode +
                    ", taxAmountCents:" + taxAmountCents +
                    '}';
        }
    }

}
