/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniyahtzee;

import java.util.List;
import yahtzeeframework.JahtzeeCategory;
import yahtzeeframework.JahtzeeDie;

/**
 * Models a combo category in the game of mini yahtzee. If N of the same kind of
 * dice are rolled at the same time, this category can be scored.
 *
 * Once this category is scored, it will keep its score until the game is reset.
 *
 * @author Blain
 */
public class NOfAKind implements JahtzeeCategory
{

    private final int N;
    private final int points;
    private boolean hasScored = false;
    private int score = 0;

    /**
     * Constructor for NOfAKind
     *
     * @param number number of matching dice required to score
     * @param points point value earned when scoring
     */
    public NOfAKind(int number, int points)
    {
        this.N = number;
        this.points = points;
    }

    /**
     * Returns the name of the category
     * @return the name of the category
     */
    @Override
    public String getName()
    {
        return N + " of a kind (" + points + " pts)";
    }

    /**
     * Calculates and returns the score of the category given the presented
     * dice. If this category has not been scored, it also sets the current
     * score to the calculated score.
     * @param dice List of dice used to calculate score
     * @return calculated score
     */
    @Override
    public int calculateScore(List<JahtzeeDie> dice)
    {
        int numEqual;
        // if we haven't scored, need to dynamically rescore
        if (!hasScored)
        {
            score = 0;
            // for each die we've been given
            for (JahtzeeDie die1 : dice)
            {
                numEqual = 1;
                // look at the other dice
                for (JahtzeeDie die2 : dice)
                {
                    // make sure to skip same object by comparing references
                    if (die1 != die2)
                    {
                        // compare the face up image names
                        if (die1.getFaceUpImage().equals(die2.getFaceUpImage()))
                        {
                            numEqual += 1;
                        }
                    }
                }
                // if we have N or more matches
                if (numEqual >= N)
                {
                    score = points;
                    break;
                }

            }
        }
        return score;
    }

    /**
     * Whether the category can be scored
     * @return whether the category can be scored
     */
    @Override
    public boolean canScore()
    {
        return !hasScored;
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
     * Maintains the current score until reset
     */
    public void score()
    {
        hasScored = true;
    }

    /**
     * Get the score that has been counted by the game. If the category has
     * been scored, it returns the scored value. Otherwise, it will be 0.
     * @return the score that has been counted by the game
     */
    @Override
    public int getCurrentScore()
    {
        int val = 0;
        // if we have scored
        if (hasScored)
        {
            val = score;
        }
        return val;
    }
}
