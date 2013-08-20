package learning.di;

import learning.di.testing.components.Cart;
import learning.di.testing.components.Checkout;
import learning.di.testing.components.Inventory;
import learning.di.testing.components.Order;
import learning.di.testing.components.Promotions;
import learning.di.testing.components.UpSell;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Class> appScopedComponents = new ArrayList<Class>();

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

        appScopedComponents.add(type);
    }

    public void registerSessionScopeComponent(String name) {
    }

    public void registerRequestScopeComponent(String name) {
    }

    public void generate() {
        verifyComponentsHaveInjectables();
    }

    private void verifyComponentsHaveInjectables() {
        for (int classes = 0; classes < appScopedComponents.size(); classes++) {
            Class clazz = appScopedComponents.get(classes);
            Class[] parameterTypes = clazz.getConstructors()[0].getParameterTypes();
            for (Class parameterType : parameterTypes) {
                if (!appScopedComponents.contains(parameterType)) {
                    throw new RuntimeException("Component type:" + clazz.getName() + " needs " + parameterType.getName() + " to be injected, but does not have it.");
                }
            }

        }
    }
}
