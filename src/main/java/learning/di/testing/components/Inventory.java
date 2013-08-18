package learning.di.testing.components;


import java.util.List;

import static java.util.Arrays.asList;

// This is one per Application (one per tomcat-process)
public class Inventory {
    private static int REF = 1;
    private int inst;
    private List<String> contents;

    public Inventory() {
        inst = REF++;
        contents = asList("TV", "iMac", "Washer", "InkJet Printer");
    }

    public List<String> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Inventory# " + inst + ", contents: " + contents;
    }
}
