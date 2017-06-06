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
 *
 * @author Blain
 */
public class ThreeOfAKind implements JahtzeeCategory {

    private int maxScore = 10;
    private boolean hasScored = false;
    private int score = 0;
    
    @Override
    public String getName() {
        return "3 of a kind (10 pts)";
    }

    

    @Override
    public int calculateScore(List<JahtzeeDie> dice) {
        int numEqual;
        // if we haven't scored, need to dynamically rescore
        if (!hasScored) {
            score = 0;
            for (JahtzeeDie die1 : dice) {
            numEqual = 0;
                for (JahtzeeDie die2 : dice) {
                    // make sure to skip same object by comparing references
                    if (die1 != die2) {
                        if (die1.getFaceUpImage().equals(die2.getFaceUpImage())) {
                            numEqual += 1;
                        }
                    }
                }
                if (numEqual >= 2) {
                    score = maxScore;
                    break;
                }
                
            }
        }
        return score;
    }

    @Override
    public boolean canScore() {
        return !hasScored;
    }
    
    public void reset() {
        hasScored = false;
        score = 0;
    }
    
    public void score() {
        hasScored = true;
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
