package yahtzeeframework;

import junit.framework.TestCase;

/**
 *
 * @author jdalbey
 */
public class NumberGeneratorTest extends TestCase
{
    public NumberGeneratorTest(String testName)
    {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Test of getInstance method, of class NumberGenerator.
     */
    public void testGetInstance()
    {
        System.out.println("getInstance");
        String data = "7 8 \n 9";
        NumberGenerator instance = NumberGenerator.getInstance("");
        instance.reset(data);
        assertEquals(7, instance.nextInt(0));
        assertEquals(8, instance.nextInt(0));
        instance = NumberGenerator.getInstance("");
        assertEquals(9, instance.nextInt(0));
    }

    /**
     * Test of nextInt method, of class NumberGenerator.
     */
    public void testNextInt()
    {
        System.out.println("nextInt");
        NumberGenerator instance = NumberGenerator.getInstance("");
        instance.reset("");
        int result = instance.nextInt(5);
        assertTrue(result >= 0);
        assertTrue(result < 5);
    }
}
