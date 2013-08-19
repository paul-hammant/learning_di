package learning.di;

import learning.di.testing.Generator;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class GeneratorTest {

    @Test
    public void testSomething() {

        Generator g = new Generator();
        assertNotNull(g);
    }
}
