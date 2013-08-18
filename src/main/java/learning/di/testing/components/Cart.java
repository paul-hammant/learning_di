package learning.di.testing.components;


import java.util.ArrayList;
import java.util.List;

// This is one per Session (each connected user has inventory separate session)
public class Cart {

    private static int REF = 1;
    private int inst;
    private List<String> contents = new ArrayList<String>();

    private Inventory inventory;

    public Cart() {
        inst = REF++;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String addTo(String item) {
        if (inventory.getContents().contains(item)) {
            contents.add(item);
            return "OK";
        }
        return "Not In Inventory";
    }

    @Override
    public String toString() {
        return "Cart# " + inst + ", Inventory: (" + inventory.toString() + "), contents: " + contents;
    }

    public boolean contains(String item) {
        return contents.contains(item);
    }
}
