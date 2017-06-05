/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Blain
 */
class JahtzeeFaceCategory implements JahtzeeCategory {
    
    private int score;
    private String name;

    /**
     * Creates and returns a JahtzeeFaceCategory if one can be created. Otherwise
     * returns null.
     * @param face JSONObject representing the face.
     * @return created JahtzeeFaceCategory
     */
    public static JahtzeeFaceCategory createCategory(JSONObject face) {
        JahtzeeFaceCategory cat = null;
        if (face.getBoolean("hasCategory")) {
            cat = new JahtzeeFaceCategory(face.getInt("value"),
            face.getString("name"));
        }
        return cat;
    }
    
    private JahtzeeFaceCategory(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCurrentScore(List<JahtzeeDie> dice) {
        int currentScore = 0;
        for (JahtzeeDie die : dice) {
            if (die.getFaceUpImage().equals(name)) {
                
            }
        }
        
        return currentScore;
    }

    @Override
    public boolean canScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
