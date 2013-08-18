package learning.di.testing.components;


// This is one per Session (each connected user has inventory separate session)
public class Order {

    private static int REF = 1;
    private int inst;

    public Order() {
        inst = REF++;
    }

    @Override
    public String toString() {
        return "Order# " + inst;
    }
}
