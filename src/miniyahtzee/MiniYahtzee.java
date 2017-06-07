/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniyahtzee;

import java.util.ArrayList;
import java.util.List;
import yahtzeeframework.JahtzeeBonus;
import yahtzeeframework.JahtzeeCategory;
import yahtzeeframework.JahtzeeGame;

/**
 * Plugin class used to pass the combo categories and bonuses to the Jahtzee
 * Framework. Implements the JahtzeeGame interface.
 *
 * MiniYahtzee has two combo categories: 3 of a kind and Straight. It also has a
 * bonus category for scoring more than 37 points in the face categories.
 *
 * @author Blain
 */
public class MiniYahtzee implements JahtzeeGame
{

    /**
     * Creates and returns the combo categories associated with mini yahtzee.
     *
     * @return the list of JahtzeeCategories associated with mini yahtzee
     */
    public List<JahtzeeCategory> getComboCategories()
    {
        List<JahtzeeCategory> categories = new ArrayList<>();
        categories.add(new NOfAKind(3, 10));
        categories.add(new Straight());

        return categories;
    }

    /**
     * Creates and returns the bonuses associated with mini yahtzee.
     *
     * @return the list of JahtzeeBonuses for mini yahtzee
     */
    public List<JahtzeeBonus> getBonuses()
    {
        List<JahtzeeBonus> bonuses = new ArrayList<>();
        bonuses.add(new PointBonus());

        return bonuses;
    }

}
