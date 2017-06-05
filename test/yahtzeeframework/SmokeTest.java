package yahtzeeframework;

import junit.framework.TestCase;
import org.uispec4j.*;

/**
 * A Superficial test of major GUI functionality.
 *
 * @author jdalbey
 */
public class SmokeTest extends TestCase
{
    private Window win;

    public SmokeTest(String testName)
    {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        String[] args =
        {
            "Mini"
        };
        String pluginName = YahtzeeLoader.processArgs(args);
        YahtzeeLoader loader = new YahtzeeLoader(pluginName);
        YahtzeeGUI gui = loader.gui;
        win = new Window(gui);
    }

    public void testSmokeTest()
    {
        System.out.println("smoke test");
        String actual;
        assertEquals("Start screen title incorrect for Mini Yahtzee", "Mini Yahtzee", win.getTitle());
        Button rollButton = win.getPanel("buttonPanel").getButton("btnRoll");
        assertTrue("Start screen roll button not enabled", win.getPanel("buttonPanel").getButton("btnRoll").getAwtComponent().isEnabled());
        assertFalse("Start screen face value button not disabled", win.getPanel("pnlFaceValues").getPanel("row1").getButton().getAwtComponent().isEnabled());
        NumberGenerator.reset("5 5 4 2 0 ");
        rollButton.click();
        Button sixesButton = win.getPanel("pnlFaceValues").getPanel("row3").getButton();
        actual = sixesButton.getAwtComponent().getText();
        assertEquals("Start screen sixes button text should be 20 was "
                + actual, "20", actual);

        int len = win.getPanel("pnlDice").getUIComponents(Button.class).length;
        assertEquals("Should be five buttons in rolling area, found " + len, 5, len);
        Button rollDie1 = win.getPanel("pnlDice").getButton("die1");
        assertTrue("roll die 1 should be visible after Roll clicked",
                rollDie1.getAwtComponent().isVisible());
        assertTrue("die tooltip should be 10", rollDie1.tooltipEquals("10").isTrue());
        actual = rollDie1.getAwtComponent().getIcon().toString();
        assertTrue("Die has correct tooltip but seems to have wrong image, "
                + "expected face6.png", actual.endsWith("face6.png"));
        rollDie1.click();  // hold this die

        // choose sixes score
        sixesButton.click();
        String totalScore = win.getPanel("buttonPanel").getTextBox("lblTotalScore").getText();
        assertEquals("Total score incorrect, expected 20 was " + totalScore, "20", totalScore);
    }
}
