/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import org.json.JSONObject;

/**
 *
 * @author Blain
 */
class JahtzeeFace {

    static JahtzeeFace createFace(JSONObject json) {
        return new JahtzeeFace(json.getString("image"),
        json.getInt("value"), json.getString("name"), 
                json.getString("tooltip"));
    }
    
    final String image;
    final int value;
    final String name;
    final String tooltip;
    
    private JahtzeeFace(String image, int value, String name, String tooltip) {
        this.image = image;
        this.value = value;
        this.name = name;
        this.tooltip = tooltip;
    }
}
