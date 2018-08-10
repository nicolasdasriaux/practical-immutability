package practicalimmutability.kata;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JsonAppTest extends TestCase {
    public JsonAppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JsonAppTest.class);
    }

    public void testApp() {
        assertTrue(true);
    }
}
