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
 *
 * @author bweeks
 */
public class PointBonus implements JahtzeeBonus
{
    private final int bonus = 10;
    
    @Override
    public int getScore(List<JahtzeeCategory> faces, 
            List<JahtzeeCategory> combos)
    {
        int total = 0;
        int score = 0;
        for (JahtzeeCategory cat : faces) {
            total += cat.getCurrentScore();
        }
        if (total > 37) {
            score = bonus;
        }
        return score;
    }
    
}
