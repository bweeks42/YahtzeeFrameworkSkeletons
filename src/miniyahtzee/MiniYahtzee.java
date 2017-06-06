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
 *
 * @author Blain
 */
public class MiniYahtzee implements JahtzeeGame {

    
    private List<JahtzeeCategory> categories;
    
    /**
     * 
     */
    public MiniYahtzee() {
        this.categories = new ArrayList<>();
        
    }
    
    public List<JahtzeeCategory> getComboCategories() {
        List<JahtzeeCategory> categories = new ArrayList<>();
        categories.add(new ThreeOfAKind());
        categories.add(new Straight());
        
        
        return categories;
    }

    public List<JahtzeeBonus> getBonuses() {
        List<JahtzeeBonus> bonuses = new ArrayList<JahtzeeBonus>();
        bonuses.add(new PointBonus());
        
        
        return bonuses;
    }
    
}
