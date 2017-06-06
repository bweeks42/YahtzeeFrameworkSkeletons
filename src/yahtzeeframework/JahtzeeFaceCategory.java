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
class JahtzeeFaceCategory implements JahtzeeCategory
{

    private final int value;
    private int score;
    private final String name;
    private boolean hasScored;

    /**
     * Creates and returns a JahtzeeFaceCategory if one can be created.
     * Otherwise returns null.
     *
     * @param face JSONObject representing the face.
     * @return created JahtzeeFaceCategory
     */
    public static JahtzeeFaceCategory createCategory(JSONObject face)
    {
        JahtzeeFaceCategory cat = null;
        if (face.getInt("value") != 0)
        {
            cat = new JahtzeeFaceCategory(face.getInt("value"),
                    face.getString("name"));
        }
        return cat;
    }

    private JahtzeeFaceCategory(int value, String name)
    {
        this.value = value;
        this.name = name;
        this.hasScored = false;
        score = 0;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public int calculateScore(List<JahtzeeDie> dice)
    {
        if (!hasScored) {
            score = 0;
            for (JahtzeeDie die : dice)
            {
                if (die.getFaceUpName().equals(name))
                {
                    score += this.value;
                }
            }
        }
        return score;
    }

    public void score()
    {
        this.hasScored = true;
    }

    @Override
    public boolean canScore()
    {
        return !hasScored;
    }

    public void reset()
    {
        hasScored = false;
        score = 0;
    }

    @Override
    public int getCurrentScore()
    {
        int val = 0;
        if (hasScored) {
            val = score;
        }
        return val;
    }
}
