/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.util.List;

/**
 * Interface for a JahtzeeBonus. JahtzeeBonuses are extra points the player can
 * accrue by meeting requirements in face and combo categories.
 *
 * @author Blain
 */
public interface JahtzeeBonus
{

    /**
     * Gets the current score of the combo
     * @param faces list of face categories
     * @param combos list of combo categories
     * @return the current score of the combo
     */
    public int getScore(List<JahtzeeCategory> faces,
            List<JahtzeeCategory> combos);
}
