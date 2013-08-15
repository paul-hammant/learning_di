package learning.di;


// This is one per Application (one per tomcat-process)
public class A {
    private static int REF = 1;
    private int inst;

    public A() {
        inst = REF++;
    }

    @Override
    public String toString() {
        return "A: " + inst;
    }
}
