/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Blain
 */
public class JahtzeeDie {

    private List<JahtzeeFace> faces;
    private int faceUp;
    
    private JahtzeeDie(List<JahtzeeFace> faces) {
        this.faces = faces;
    }
    
    static JahtzeeDie createDie(JSONArray faceArr) {
        List<JahtzeeFace> faces = new ArrayList<>();
        for (int ind = 0; ind < faceArr.length(); ind++) {
            faces.add(JahtzeeFace.createFace(faceArr.getJSONObject(ind)));
        }
        return new JahtzeeDie(faces);
    }
    
    public void roll(int faceUp) {
        this.faceUp = faceUp;
    }

    public String getFaceUpImage() {
        return faces.get(faceUp).image;
    }

    String getFaceUpString() {
        return faces.get(faceUp).tooltip;
    }
    
}
