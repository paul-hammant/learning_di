package learning.di;

import learning.di.testing.Generator;
import org.junit.Test;

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
}
