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
    private int currentScore = 0;
    
    @Override
    public String getName() {
        return "3 of a kind";
    }

    

    @Override
    public int getCurrentScore(List<JahtzeeDie> dice) {
        int score = 0;
        int numEqual;
        if (hasScored) {
            score = this.currentScore;
        }
        else {
            for (JahtzeeDie die1 : dice) {
                numEqual = 0;
                for (JahtzeeDie die2 : dice) {
                    // skip same object, not equivalent
                    if (die1 != die2) {
                        if (die1.getFaceUpImage().equals(die2.getFaceUpImage())) {
                            numEqual += 1;
                        }
                    }
                }
                if (numEqual >= 3) {
                    score = maxScore;
                    break;
                }
            }
        }
        
        return score;
    }

    @Override
    public boolean canScore() {
        return hasScored;
    }
    
    public void reset() {
        hasScored = false;
    }
    
    public void score() {
        hasScored = true;
    }
}
