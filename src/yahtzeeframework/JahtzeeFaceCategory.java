/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.util.List;
import org.ini4j.Ini;

/**
 * A JahtzeeCategory to keep track of scoring for each face category.
 *
 * @author Blain
 */
class JahtzeeFaceCategory implements JahtzeeCategory
{

    private final int value;
    private int score;
    private final String name;
    private boolean hasScored;

    /**
     * Creates and returns a JahtzeeFaceCategory if one can be created.
     * Otherwise returns null. Categories cannot be created if the value of the
     * face is 0
     *
     * @param face Ini.Section representing the configuration of a face.
     * @return created JahtzeeFaceCategory
     */
    public static JahtzeeFaceCategory createCategory(Ini.Section face)
    {
        JahtzeeFaceCategory cat = null;
        // create if face value is not 0
        if (Integer.parseInt(face.get("value")) != 0)
        {
            cat = new JahtzeeFaceCategory(Integer.parseInt(face.get("value")),
                    face.get("name"));
        }
        return cat;
    }

    private JahtzeeFaceCategory(int value, String name)
    {
        this.value = value;
        this.name = name;
        this.hasScored = false;
        score = 0;
    }

    /**
     * Gets the name of the category
     *
     * @return the name of the category
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * Calculates whether the category has been scored based on the state of the
     * dice. If this category has not been scored, the current score is set to
     * the calculated value.
     *
     * @param dice list of dice used to determine the category score
     * @return the current score of the category.
     */
    @Override
    public int calculateScore(List<JahtzeeDie> dice)
    {
        // if we haven't scored yet
        if (!hasScored)
        {
            score = 0;
            // look at each die
            for (JahtzeeDie die : dice)
            {
                // if die matches this categories name
                if (die.getFaceUpName().equals(name))
                {
                    score += this.value;
                }
            }
        }
        return score;
    }

    /**
     * Set this category as scored
     */
    public void score()
    {
        this.hasScored = true;
    }

    /**
     * Determines whether this category can still be scored.
     *
     * @return whether this category can still be scored
     */
    @Override
    public boolean canScore()
    {
        return !hasScored;
    }

    /**
     * Resets this category to its initial state
     */
    @Override
    public void reset()
    {
        hasScored = false;
        score = 0;
    }

    /**
     * Gets the current score of the category. If the category has not been
     * scored, returns 0.
     *
     * @return the current score of the category
     */
    @Override
    public int getCurrentScore()
    {
        int val = 0;
        // if we've scored
        if (hasScored)
        {
            val = score;
        }
        return val;
    }
}
