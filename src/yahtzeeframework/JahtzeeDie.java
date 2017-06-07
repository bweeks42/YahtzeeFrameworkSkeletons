/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.util.ArrayList;
import java.util.List;
import org.ini4j.*;

/**
 * Class to represent a die in Jahtzee. Contains the information about the
 * images of the die faces, how many faces there are and which face is up.
 *
 * @author Blain
 */
public class JahtzeeDie
{

    private final List<JahtzeeFace> faces;
    private int faceUp;

    /**
     * Constructor for JahtzeeDie
     *
     * @param ini Ini file containing the configuration of the die
     * @param numFaces number of faces to create
     */
    public JahtzeeDie(Ini ini, int numFaces)
    {
        faces = new ArrayList<>();

        // create every face
        for (int ind = 1; ind <= numFaces; ind++)
        {
            faces.add(new JahtzeeFace(ini.get("face" + ind)));
        }
    }

    /**
     * Get the name of the image that is faced up
     *
     * @return the name of the image that is faced up
     */
    public String getFaceUpImage()
    {
        return faces.get(faceUp).image;
    }

    /**
     * Get the tooltip of the face that is faced up
     *
     * @return the tooltip of the face that is faced up
     */
    public String getFaceUpTip()
    {
        return faces.get(faceUp).tooltip;
    }

    /**
     * Get the number of faces on this die
     *
     * @return the number of faces on this die
     */
    int faceNum()
    {
        return this.faces.size();
    }

    /**
     * Set the current face up
     *
     * @param the current face up
     */
    void setFace(int face)
    {
        this.faceUp = face;
    }

    /**
     * Get the name of the face that is face up
     *
     * @return the name of the face that is face up
     */
    public String getFaceUpName()
    {
        return faces.get(faceUp).name;
    }

}
