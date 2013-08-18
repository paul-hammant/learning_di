package learning.di.testing;

import learning.di.testing.components.Inventory;
import learning.di.testing.components.Cart;
import learning.di.testing.components.Checkout;
import learning.di.testing.components.UpSell;

public class Generator {

    public static void main(String[] args) {

        // Register Application Scoped Comps

        Inventory.class.getName();

        // Register Session Scoped Comps

        Cart.class.getName();

        // Register Request Scoped Comps

        Checkout.class.getName();
        UpSell.class.getName();

        // Generate Filter.

    }

}
