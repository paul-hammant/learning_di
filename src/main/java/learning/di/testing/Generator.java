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

        g.registerAppScopeComponent(Inventory.class);
        g.registerAppScopeComponent(Promotions.class);

        // Register Session Scoped Comps

        g.registerSessionScopeComponent(Cart.class.getName());
        g.registerSessionScopeComponent(Order.class.getName());

        // Register Request Scoped Comps

        g.registerRequestScopeComponent(Checkout.class.getName());
        g.registerRequestScopeComponent(UpSell.class.getName());

        // Generate Filter.

    }

    public void registerAppScopeComponent(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("parameter to class may not be null");
        }
    }

    public void registerSessionScopeComponent(String name) {
    }

    public void registerRequestScopeComponent(String name) {
    }

}
