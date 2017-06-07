/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.util.List;

/**
 * Class to specify the interface for Jahtzee plugins that detail the logic for
 * combo categories and bonuses.
 *
 * @author bweeks
 */
public interface JahtzeeGame
{

    /**
     * Gets the combo categories specified by the plugin.
     *
     * @return list of JahtzeeCategories used to calculate combos
     */
    public List<JahtzeeCategory> getComboCategories();

    /**
     * Gets the bonuses specified by the plugin.
     *
     * @return list of JahtzeeBonuses used supplement the game score
     */
    public List<JahtzeeBonus> getBonuses();
}
