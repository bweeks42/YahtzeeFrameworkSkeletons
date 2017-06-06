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
 *
 * @author bweeks
 */
public class Straight implements JahtzeeCategory
{
    private int score = 0;
    private boolean hasScored = false;

    @Override
    public String getName()
    {
        return "Straight (25 pts)";
    }

    @Override
    public int calculateScore(List<JahtzeeDie> dice)
    {
        if (!hasScored) {
           score = 0;
           List<String> names = new ArrayList<>();
           for (JahtzeeDie die : dice) {
               names.add(die.getFaceUpName());
               System.out.println(die.getFaceUpName());
           }
           String[] hand = {"threes", "fours"};
           List<String> mid = Arrays.asList(hand);
           if (names.containsAll(mid)) {
               if (names.contains("ones") && names.contains("twos") ||
                       names.contains("twos") && names.contains("fives") || 
                       names.contains("fives") && names.contains("sixes")) {
                   score = 25;
               }
           }
        }
        
        return score;
    }

    @Override
    public boolean canScore()
    {
        return !hasScored;
    }

    @Override
    public void score()
    {
        hasScored = true;
    }
    
    public void reset() {
        hasScored = false;
        score = 0;
    }

    @Override
    public int getCurrentScore()
    {
        int val = 0;
        if (hasScored) {
            val = score;
        }
        return val;
    }
    
}
