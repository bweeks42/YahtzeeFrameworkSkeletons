/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 * Java framework for playing Yahtzee.
 *
 * @author bweeks
 */
public class Jahtzee extends Observable {

    private final JahtzeeGame plugin;
    private final NumberGenerator generator;
    private int maxDice;
    private int total;
    private int rolls;
    private int faceSubtotal;
    private int comboSubtotal;

    private final List<JahtzeeCategory> combos;
    private final List<JahtzeeCategory> faces;
    
    private final List<JahtzeeDie> held;
    private final List<JahtzeeDie> rolled;
    private final List<JahtzeeDie> dice;
    
    private final List<JahtzeeBonus> bonuses;

    public Jahtzee(JahtzeeGame plugin, String pluginPackage,
            NumberGenerator generator) {
        this.plugin = plugin;
        this.generator = generator;
        this.combos = new ArrayList<>();
        this.faces = new ArrayList<>();
        this.held = new ArrayList<>();
        this.rolled = new ArrayList<>();
        this.dice = new ArrayList<>();
        this.bonuses = plugin.getBonuses();
        parseConfig(pluginPackage);
    }

    private void parseConfig(String pluginPackage) {
        String config = null;
        FileReader fr;
        try {
            fr = new FileReader("./src/" + pluginPackage
                    + "/config/config.json");
            Scanner s = new Scanner(fr).useDelimiter("\\A");
            config = s.next();

        } catch (FileNotFoundException ex) {
            System.out.println("Config file not found");
        }

        JSONObject configObj = new JSONObject(config);
        System.out.println(configObj.keySet());
        maxDice = configObj.getInt("num_dice");
        JSONArray faceArr = configObj.getJSONArray("faces");
        initDice(faceArr);
        initFaceCategories(faceArr);
    }
    
    public void roll() {
        this.rolls += 1;
    }

    private void initDice(JSONArray faceArr) {
        for (int diceInd = 0; diceInd < maxDice; diceInd++) {
            this.dice.add(JahtzeeDie.createDie(faceArr));
        }
    }
    
    private void initFaceCategories(JSONArray faceArr) {
        for (int ind = 0; ind < faceArr.length(); ind++) {
            JSONObject face = faceArr.getJSONObject(ind);
            JahtzeeCategory cat = JahtzeeFaceCategory.createCategory(face);
            if (cat != null) {
                faces.add(cat);
            }
        }
    }

    int getDiceLimit() {
        return this.maxDice;
    }

    boolean canRoll() {
        return !isGameOver() && rolls < getRollsPerRound();
    }
    
    private int getRollsPerRound() {
        return this.combos.size() + this.faces.size();
    }
    
    boolean isGameOver() {
        return false;
    }

    boolean canMoveDice() {
        return false;
    }
    
    public List<JahtzeeDie> getDice() {
        return dice;
    }

    public List<JahtzeeCategory> getFaceCategories() {
        return faces;
    }

    public List<JahtzeeCategory> getComboCategories() {
        return combos;
    }

    boolean canScore() {
        return true;
    }

    int getFaceScore() {
        return this.faceSubtotal;
    }

    int getComboScore() {
        return this.comboSubtotal;
    }

    int getTotalScore() {
        return this.total;
    }

    boolean isBonus() {
        boolean isBonus = false;
        for (JahtzeeBonus bonus : bonuses) {
            isBonus |= bonus.isActive();
        }
        
        return isBonus;
    }

    int getRollCount() {
        return this.rolls;
    }

    List<JahtzeeDie> getRolled() {
        return this.rolled;
    }

    List<JahtzeeDie> getHeld() {
        return this.held;
    }
}
