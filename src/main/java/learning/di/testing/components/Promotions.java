package learning.di.testing.components;


// This is one per Application (one per tomcat-process)
public class Promotions {
    private static int REF = 1;
    private int inst;

    public Promotions() {
        inst = REF++;
    }

    @Override
    public String toString() {
        return "Promotions# " + inst;
    }
}
