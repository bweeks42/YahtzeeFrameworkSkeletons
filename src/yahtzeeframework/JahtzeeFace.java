/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import org.ini4j.*;

/**
 * Class that encapsulates the information for a Die face in Jahtzee.
 *
 * @author Blain
 */
class JahtzeeFace
{

    final String image;
    final int value;
    final String name;
    final String tooltip;

    /**
     * Constructor for JahtzeeFace
     *
     * @param sec Ini.Section with the configuration of the face
     */
    public JahtzeeFace(Ini.Section sec)
    {
        this.image = sec.get("image");
        this.value = Integer.parseInt(sec.get("value"));
        this.name = sec.get("name");
        this.tooltip = sec.get("tooltip");
    }
}
