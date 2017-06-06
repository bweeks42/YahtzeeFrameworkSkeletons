/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import java.util.List;

/**
 *
 * @author Blain
 */
public interface JahtzeeBonus {
    public int getScore(List<JahtzeeCategory> faces, 
            List<JahtzeeCategory> combos);
}
