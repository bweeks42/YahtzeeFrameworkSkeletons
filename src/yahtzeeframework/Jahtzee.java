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
    private int numFaces;
    private int total;
    private int rolls;
    private int faceSubtotal;
    private int comboSubtotal;

    private final List<JahtzeeCategory> combos;
    private final List<JahtzeeCategory> faces;

    public Jahtzee(JahtzeeGame plugin, String pluginPackage,
            NumberGenerator generator) {
        this.plugin = plugin;
        this.generator = generator;
        this.combos = new ArrayList<>();
        this.faces = new ArrayList<>();
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
        initFaces(faceArr);
    }

    private void initFaces(JSONArray faceArr) {
        for (int ind = 0; ind < faceArr.length(); ind++) {
            JSONObject face = faceArr.getJSONObject(ind);
            JahtzeeCategory cat = JahtzeeFaceCategory.createCategory(face);
            if (cat != null) {
                faces.add(cat);
            }
        }
    }

    int getDiceLimit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<JahtzeeDie> getDice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean canRoll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean canMoveDice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<JahtzeeCategory> getFaceCategories() {
        return null;
    }

    public List<JahtzeeCategory> getComboCategories() {
        return null;
    }

    boolean canScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getFaceScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getComboScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String getTotalScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean isBonus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String getRollCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<JahtzeeDie> getRolled() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<JahtzeeDie> getHeld() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
