/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import java.util.List;
import org.json.JSONObject;

/**
 * Class that describes a scoring combination in a yahtzee game.
 * @author Blain
 */
public interface JahtzeeCategory {

    public String getName();
    public int getCurrentScore(List<JahtzeeDie> dice);

    public boolean canScore();
}