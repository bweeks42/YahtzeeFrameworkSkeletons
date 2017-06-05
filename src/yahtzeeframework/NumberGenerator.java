package yahtzeeframework;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * An integer generator that can be sourced from a String. Since it is derived from
 * Random it can be used in a similar manner. Warning: This class is a singleton.
 * http://misko.hevery.com/2008/08/17/singletons-are-pathological-liars/
 *
 * @author jdalbey
 */
public final class NumberGenerator extends java.util.Random
{
    private static Scanner scanner = null;
    private static NumberGenerator instance = null;

    private NumberGenerator()
    {
        super();
    }

    /**
     * Accessor to the single instance of this class.
     *
     * @param presetData A string representing a sequence of integers to use instead of
     *                   random numbers. The format is blank-delimited integers, for
     *                   example, "1 2 3 4". Providing an empty string will effectively
     *                   cause this generator to act like Random. (Note this parameter is
     *                   only used on the first call to this method.)
     *
     * @return the instance
     */
    public static NumberGenerator getInstance(String presetData)
    {
        // Has an instance been created yet?
        if (instance == null)
        {
            instance = new NumberGenerator();
            // If preset data has been given to us
            if (presetData.length() > 0)
            {
                scanner = new Scanner(presetData);
            }
        }
        return instance;
    }

    /**
     * Reset the state of this instance. This method is generally used only for unit
     * testing of this class.
     *
     * @param presetData A string representing a sequence of integers to use instead of
     *                   random numbers. The format is blank-delimited integers, for
     *                   example, "1 2 3 4". Providing an empty string will effectively
     *                   cause this generator to act like Random.
     */
    public static void reset(String presetData)
    {
        scanner = null;
        // If preset data has been given to us
        if (presetData.length() > 0)
        {
            scanner = new Scanner(presetData);
        }
    }

    /**
     * Return a pseudo random integer.
     *
     * @param n the bound on the random number to be returned. Must be positive. If
     *          setInput was called this parameter is ignored.
     * @return If setInput was called the return value is the next integer in the String
     *         of preset data, otherwise, the next pseudorandom, uniformly distributed
     *         int value between 0 (inclusive) and n (exclusive) from this random number
     *         generator's sequence.
     */
    @Override
    public int nextInt(int n)
    {
        // If we aren't reading from preset data
        if (scanner == null)
        {
            return super.nextInt(n);
        }
        else
        {
            try
            {
                // get the next item of preset data
                return scanner.nextInt();
            }
            catch (java.util.NoSuchElementException exc)
            {
                JOptionPane.showMessageDialog(null,
                        "NumberGenerator preset data file has no more items.\n"
                        + "Using zero instead.", "NumberGenerator Error",
                        JOptionPane.ERROR_MESSAGE, null);
                return 0;
            }
        }
    }
}
