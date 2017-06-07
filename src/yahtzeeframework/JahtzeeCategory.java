/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.util.List;

/**
 * Interface describing a category that can be scored in Jahtzee. Categories are
 * scored based on state of the dice, and once scored they do not change until
 * the game begins again.
 *
 * @author Blain
 */
public interface JahtzeeCategory
{

    /**
     * Gets the name of the category
     *
     * @return the name of the category
     */
    public String getName();

    /**
     * Gets the current score of the category. If the category has not been
     * scored, returns 0.
     *
     * @return the current score of the category
     */
    public int getCurrentScore();

    /**
     * Calculates the score of the category based on the state of the dice. If
     * this category has not been scored, the score is set to the calculated
     * value.
     *
     * @param dice Dice used to calculate score
     * @return the calculated score
     */
    public int calculateScore(List<JahtzeeDie> dice);

    /**
     * Returns whether the category can be scored
     *
     * @return whether the category can be scored
     */
    public boolean canScore();

    /**
     * Scores the current category
     */
    public void score();

    /**
     * Resets the category to its initial state
     */
    public void reset();
}
