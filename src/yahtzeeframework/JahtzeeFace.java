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

    static JahtzeeFace createFace(JSONObject jsonObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    final String image;
    final int value;
    final String name;
    final String tooltip;
    JahtzeeFace(String image, int value, String name, String tooltip) {
        this.image = image;
        this.value = value;
        this.name = name;
        this.tooltip = tooltip;
    }
}
