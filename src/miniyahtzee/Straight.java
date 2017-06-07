/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniyahtzee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import yahtzeeframework.JahtzeeCategory;
import yahtzeeframework.JahtzeeDie;

/**
 * Models a combo category in the game of mini yahtzee. If a continuous series
 * of dice values is found that exceeds 3, the category can be scored. For
 * example : 1-2-3-6-4 can be scored because 1-2-3-4 are 4 continuous dice
 * values.
 *
 * Once this category is scored, it will keep its score until the game is reset.
 *
 * @author Blain
 */
public class Straight implements JahtzeeCategory
{

    private int score = 0;
    private boolean hasScored = false;

    /**
     * Returns the name of the category
     *
     * @return the name of the category
     */
    @Override
    public String getName()
    {
        return "Straight (25 pts)";
    }

    /**
     * Calculates and returns the score of the category given the presented
     * dice. If this category has not been scored, it also sets the current
     * score to the calculated score.
     *
     * @param dice List of dice used to calculate score
     * @return calculated score
     */
    @Override
    public int calculateScore(List<JahtzeeDie> dice)
    {
        // if we haven't scored yet
        if (!hasScored)
        {
            score = 0;
            List<String> names = new ArrayList<>();
            // for every die, gather up the names
            for (JahtzeeDie die : dice)
            {
                names.add(die.getFaceUpName());
            }
            String[] hand =
            {
                "threes", "fours"
            };
            List<String> mid = Arrays.asList(hand);
            // see if the list of names contains the required two names
            if (names.containsAll(mid))
            {
                // see if the list has contains a required pair of names
                if (names.contains("ones") && names.contains("twos")
                        || names.contains("twos") && names.contains("fives")
                        || names.contains("fives") && names.contains("sixes"))
                {
                    score = 25;
                }
            }
        }

        return score;
    }

    /**
     * Whether the category can be scored
     *
     * @return whether the category can be scored
     */
    @Override
    public boolean canScore()
    {
        return !hasScored;
    }

    /**
     * Maintains the current score until reset
     */
    @Override
    public void score()
    {
        hasScored = true;
    }

    /**
     * Resets the category to its initial state
     */
    public void reset()
    {
        hasScored = false;
        score = 0;
    }

    /**
     * Get the score that has been counted by the game. If the category has been
     * scored, it returns the scored value. Otherwise, it will be 0.
     *
     * @return the score that has been counted by the game
     */
    @Override
    public int getCurrentScore()
    {
        int val = 0;
        // if we've scored, use internal score
        if (hasScored)
        {
            val = score;
        }
        return val;
    }

}
