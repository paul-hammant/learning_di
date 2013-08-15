package learning.di;


// This is one per Session (each connected user has a separate session)
public class B {

    private static int REF = 1;
    private int inst;

    private A a;

    public B() {
        inst = REF++;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return a.toString() + ", B: " + inst;
    }
}
