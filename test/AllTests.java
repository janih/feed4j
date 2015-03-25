import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("All JUnit Tests");
        suite.addTest(new JUnit4TestAdapter(feed4j.SimpleTest.class));
        return suite;
    }
}
