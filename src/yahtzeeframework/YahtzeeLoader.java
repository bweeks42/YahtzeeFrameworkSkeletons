package yahtzeeframework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The YahtzeeLoader class is a loader for plugins to the yahtzee framework. The
 * loader's main method is given the name of the plugin to be loaded as a string
 * beginning in a capital letter, for example, "Rainbow" or "Mini". (Do NOT
 * include the suffix "Yahtzee").
 *
 * @author Your Name
 */
public class YahtzeeLoader
{

    final YahtzeeGUI gui;   // made package-private for testing
    private static String dataFileName;

    /**
     * Creates a Yahtzee game from the specified plugin and creates the GUI to
     * interact with it.
     *
     * @param pluginName The name of the plugin to be loaded.
     * @post gui is constructed. If plugin is not found, gui is null.
     */
    public YahtzeeLoader(String pluginName)
    {
        String pluginPackage = pluginName.toLowerCase() + "yahtzee";
        String pluginClass = pluginPackage + "." + pluginName + "Yahtzee";

        Class plugin = null;
        Jahtzee jahtzee = null;
        JahtzeeController controller = null;
        try
        {
            plugin = Class.forName(pluginClass);
            JahtzeeGame jahtzeePlugin = (JahtzeeGame) plugin.newInstance();
            String presetData = "";
            if (dataFileName != null)
            {
                FileReader fr = new FileReader(dataFileName);
                Scanner s = new Scanner(fr).useDelimiter("\\A");
                presetData = s.next();

            }
            NumberGenerator generator = NumberGenerator.getInstance(presetData);
            jahtzee = new Jahtzee(jahtzeePlugin, pluginPackage, generator);
            controller = new JahtzeeController(jahtzee);
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Could not find class file " + pluginName);
        }
        catch (InstantiationException ex)
        {
            System.out.println("Unable to instantiate " + pluginClass);
        }
        catch (IllegalAccessException ex)
        {
            System.out.println("Illegal access to " + pluginClass);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Unable to find " + dataFileName);
        }

        // if plugin was instantiated correctly
        if (plugin != null)
        {
            gui = new YahtzeeGUI(pluginName, controller,
                    jahtzee.getDiceLimit());
            jahtzee.addObserver(gui);
            gui.update(jahtzee, null);
        }
        else
        {
            gui = null;
        }
    }

    /**
     * Process the command line arguments. Access mode is pkg-private to allow
     * JUnit testing.
     *
     * @param args [-r file] pluginName (See spec)
     *
     * @return String plugin name.
     */
    @SuppressWarnings("deprecation")
    static String processArgs(String[] args)
    {
        dataFileName = null;
        String pluginName = "";

        if (args.length == 3 && "-r".equals(args[0]))
        {
            dataFileName = args[1];
            pluginName = args[2];
        }
        else if (args.length == 1)
        {
            pluginName = args[0];
        }
        return pluginName;
    }

    /**
     * Entry point for the application. Calls helper methods to process command
     * line and create the views, then sets the views visible and exits.
     *
     * @param args the command line arguments (See processArgs)
     */
    public static void main(String[] args)
    {
        String pluginName = YahtzeeLoader.processArgs(args);
        // If a plugin name was provided
        if (pluginName.length() > 0)
        {
            YahtzeeLoader loader = new YahtzeeLoader(pluginName);
            // If plugin was not found, gui will be null
            if (loader.gui != null)
            {
                /* Make the JFrame visible */
                loader.gui.setVisible(true);
            }
        }
    }
}
