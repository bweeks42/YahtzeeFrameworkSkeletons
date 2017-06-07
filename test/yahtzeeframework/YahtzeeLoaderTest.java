package yahtzeeframework;

import junit.framework.TestCase;

/**
 *
 * @author jdalbey
 */
public class YahtzeeLoaderTest extends TestCase
{
    public YahtzeeLoaderTest(String testName)
    {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Test of main method, of class YahtzeeLoader.
     */
    public void testMain()
    {
        System.out.println("main");
        String[] args =
        {
            "-r", "rolldata1.txt", "Mini"
        };
        String pluginName = YahtzeeLoader.processArgs(args);
        YahtzeeLoader loader = new YahtzeeLoader(pluginName);

        yahtzeeframework.YahtzeeGUI gui = loader.gui;
        assertEquals("Mini Yahtzee", gui.getTitle());
    }

    public void testProcessArgs()
    {
    }
}
