/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowyahtzee;

import java.util.ArrayList;
import java.util.List;
import yahtzeeframework.JahtzeeBonus;
import yahtzeeframework.JahtzeeCategory;
import yahtzeeframework.JahtzeeGame;

/**
 * Plugin class used to pass the combo categories and bonuses to the Jahtzee
 * Framework. Implements the JahtzeeGame interface.
 *
 * RainbowYahtzee has three combo categories: 3, 4 and 5 of a kind. It also has
 * a bonus category for scoring more than 0 points in every single category.
 *
 * @author Blain
 */
public class RainbowYahtzee implements JahtzeeGame
{

    /**
     * Creates and returns the combo categories associated with rainbow yahtzee.
     *
     * @return the list of JahtzeeCategories associated with rainbow yahtzee
     */
    @Override
    public List<JahtzeeCategory> getComboCategories()
    {
        List<JahtzeeCategory> cats = new ArrayList<>();
        cats.add(new NOfAKind(3, 10));
        cats.add(new NOfAKind(4, 30));
        cats.add(new NOfAKind(5, 60));

        return cats;
    }

    /**
     * Creates and returns the bonuses associated with rainbow yahtzee.
     *
     * @return the list of JahtzeeBonuses for rainbow yahtzee
     */
    @Override
    public List<JahtzeeBonus> getBonuses()
    {
        List<JahtzeeBonus> bonuses = new ArrayList<>();
        bonuses.add(new AllScoredBonus());

        return bonuses;
    }

}
