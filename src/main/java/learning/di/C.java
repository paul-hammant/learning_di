package learning.di;


// This is one per Request
public class C {

    private static int REF = 1;
    private int inst;

    private B b;

    public C(B b) {
        this.b = b;
        inst = REF++;
    }

    @Override
    public String toString() {
        return b.toString() + "\nC: " + inst;
    }


    public Object aWebMappedMethod(String zipCode) {
        return new Foo(this.toString(), Integer.parseInt(zipCode));
    }

    public static class Foo {
        String c;
        int zipCode;

        public Foo(String s, int i) {
            this.c = s;
            this.zipCode = i;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "c='" + c + '\'' +
                    ", zipCode=" + zipCode +
                    '}';
        }
    }

}
