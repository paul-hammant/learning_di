package learning.di;

import learning.di.testing.components.Cart;
import learning.di.testing.components.Inventory;
import learning.di.testing.components.UpSell;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class GeneratorTest {

    @Test
    public void applicationScopedComponentsShouldNotBeNull() {

        Generator g = new Generator();
        try {
            g.registerAppScopeComponent(null);
            fail("should not get to this point");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    public void oneComponentShouldBeInjectableIntoAnother(){

        Generator generator = new Generator();

        generator.registerAppScopeComponent(UpSell.class);
        generator.registerAppScopeComponent(Cart.class);
        generator.generate();
    }

    @Test
    public void insufficientDependenciesShouldCauseFailure(){

        Generator generator = new Generator();


        generator.registerAppScopeComponent(UpSell.class);
        try {
            generator.generate();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Component type:learning.di.testing.components.UpSell needs learning.di.testing.components.Cart to be injected, but does not have it.");
        }
    }

    @Test
    @Ignore
    public void registerSessionScopeComponentShouldNotBeNull() {

        Generator g = new Generator();
        try {
            g.registerSessionScopeComponent(null);
            fail("should not get to this point");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }
}
