/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniyahtzee;

import java.util.List;
import yahtzeeframework.JahtzeeBonus;
import yahtzeeframework.JahtzeeCategory;

/**
 * The class models a bonus for the mini yahtzee game. Point bonus for the game
 * is scored when the point value of the categories sums to greater than 37.
 *
 * Rewards a bonus of 10 points.
 *
 * @author bweeks
 */
public class PointBonus implements JahtzeeBonus
{

    private final int bonus = 10;

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
        int total = 0;
        int score = 0;
        // for each face category, total the score
        for (JahtzeeCategory cat : faces)
        {
            total += cat.getCurrentScore();
        }
        // if we can score this bonus, do it
        if (total > 37)
        {
            score = bonus;
        }
        return score;
    }

}
