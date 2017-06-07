/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowyahtzee;

import java.util.List;
import yahtzeeframework.JahtzeeBonus;
import yahtzeeframework.JahtzeeCategory;

/**
 * The class models a bonus for the rainbow yahtzee game. Point bonus for the
 * game is scored when every category (faces and combos) has been scored with a
 * value greater than 0.
 *
 * Rewards a bonus of 100 points.
 *
 * @author bweeks
 */
public class AllScoredBonus implements JahtzeeBonus
{

    private final int bonus = 100;

    /**
     * Calculates the score of the bonus
     *
     * @param faces the list of face categories
     * @param combos the list of combo categories
     * @return the score of the bonus
     */
    @Override
    public int getScore(List<JahtzeeCategory> faces,
            List<JahtzeeCategory> combos)
    {
        boolean allScored = true;
        int val = 0;

        // make sure all face categories have been scored
        for (JahtzeeCategory cat : faces)
        {
            allScored &= cat.getCurrentScore() > 0;
        }

        // make sure all combo categories have been scored
        for (JahtzeeCategory cat : combos)
        {
            allScored &= cat.getCurrentScore() > 0;
        }

        // if everyone's been scored
        if (allScored)
        {
            val = bonus;
        }

        return val;

    }

}
