package learning.di.testing;

import learning.di.testing.components.Cart;
import learning.di.testing.components.Checkout;
import learning.di.testing.components.Inventory;
import learning.di.testing.components.Order;
import learning.di.testing.components.Promotions;
import learning.di.testing.components.UpSell;

public class Generator {

    // to delete ...
    public static void main(String[] args) {

        Generator g = new Generator();

        // Register Application Scoped Comps

        g.registerAppScopeComponent(Inventory.class.getName());
        g.registerAppScopeComponent(Promotions.class.getName());

        // Register Session Scoped Comps

        g.registerSessionScopeComponent(Cart.class.getName());
        g.registerSessionScopeComponent(Order.class.getName());

        // Register Request Scoped Comps

        g.registerRequestScopeComponent(Checkout.class.getName());
        g.registerRequestScopeComponent(UpSell.class.getName());

        // Generate Filter.

    }

    private void registerAppScopeComponent(String name) {
    }

    private void registerSessionScopeComponent(String name) {
    }

    private void registerRequestScopeComponent(String name) {
    }

}
