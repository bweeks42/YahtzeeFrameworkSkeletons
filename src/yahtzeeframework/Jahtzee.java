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
import org.json.*;

/**
 * Java framework for playing Yahtzee.
 *
 * @author bweeks
 */
public class Jahtzee extends Observable
{

    private final NumberGenerator generator;
    private int maxDice;
    private int maxRolls;
    private int total;
    private int rolls;
    private int round;

    private final List<JahtzeeCategory> combos;
    private final List<JahtzeeCategory> faceCats;

    private final List<JahtzeeDie> held;
    private final List<JahtzeeDie> rolled;
    private final List<JahtzeeDie> dice;

    private final List<JahtzeeBonus> bonuses;

    public Jahtzee(JahtzeeGame plugin, String pluginPackage,
            NumberGenerator generator)
    {
        round = 0;
        this.generator = generator;
        this.combos = plugin.getComboCategories();
        this.faceCats = new ArrayList<>();
        this.held = new ArrayList<>();
        this.rolled = new ArrayList<>();
        this.dice = new ArrayList<>();
        this.bonuses = plugin.getBonuses();
        parseConfig(pluginPackage);
    }

    private void parseConfig(String pluginPackage)
    {
        String config = null;
        FileReader fr;
        try
        {
            fr = new FileReader("./src/" + pluginPackage
                    + "/config/config.json");
            Scanner s = new Scanner(fr).useDelimiter("\\A");
            config = s.next();

        } catch (FileNotFoundException ex)
        {
            System.out.println("Config file not found");
        }

        JSONObject configObj = new JSONObject(config);
        maxDice = configObj.getInt("num_dice");
        maxRolls = configObj.getInt("num_rolls");
        JSONArray faceArr = configObj.getJSONArray("faces");
        initDice(faceArr);
        initFaceCategories(faceArr);
    }

    private void initDice(JSONArray faceArr)
    {
        for (int diceInd = 0; diceInd < maxDice; diceInd++)
        {
            this.dice.add(JahtzeeDie.createDie(faceArr));
        }
    }

    private void initFaceCategories(JSONArray faceArr)
    {
        for (int ind = 0; ind < faceArr.length(); ind++)
        {
            JSONObject face = faceArr.getJSONObject(ind);
            JahtzeeCategory cat = JahtzeeFaceCategory.createCategory(face);
            if (cat != null)
            {
                faceCats.add(cat);
            }
        }
    }

    int getDiceLimit()
    {
        return this.maxDice;
    }

    boolean canRoll()
    {
        return !isGameOver() && rolls < maxRolls;
    }

    boolean isGameOver()
    {
        return this.round == this.combos.size() + this.faceCats.size();
    }

    boolean canMoveDice()
    {
        return this.rolls != maxRolls && this.rolls != 0;
    }

    public List<JahtzeeDie> getDice()
    {
        List<JahtzeeDie> diceInPlay = new ArrayList<>();
        if (rolls != 0)
        {
            diceInPlay = dice;
        }
        return diceInPlay;
    }

    public List<JahtzeeCategory> getFaceCategories()
    {
        return faceCats;
    }

    public List<JahtzeeCategory> getComboCategories()
    {
        return combos;
    }

    boolean canScore()
    {
        return rolls != 0;
    }

    int getFaceScore()
    {
        int sub = 0;
        for (JahtzeeCategory cat : this.faceCats)
        {
            if (!cat.canScore())
            {
                sub += cat.getCurrentScore();
            }
        }
        return sub;
    }

    int getComboScore()
    {
        int sub = 0;
        for (JahtzeeCategory cat : this.combos)
        {
            if (!cat.canScore())
            {
                sub += cat.getCurrentScore();
            }
        }
        return sub;
    }

    int getTotalScore()
    {
        return getComboScore() + getFaceScore() + getBonusScore();
    }

    private int getBonusScore()
    {
        int val = 0;

        for (JahtzeeBonus bonus : bonuses)
        {
            val += bonus.getScore(faceCats, combos);
        }
        return val;
    }

    boolean isBonus()
    {

        return getBonusScore() != 0;
    }

    int getRollCount()
    {
        return this.rolls;
    }

    List<JahtzeeDie> getRolled()
    {
        return this.rolled;
    }

    List<JahtzeeDie> getHeld()
    {
        return this.held;
    }

    private void roll()
    {
        // roll all dice on first roll of game
        if (rolled.isEmpty())
        {
            for (JahtzeeDie die : dice)
            {
                rolled.add(die);
            }
        }
        for (JahtzeeDie die : rolled)
        {
            die.setFace(generator.nextInt(die.faceNum()));
        }
        rolls += 1;
    }

    void processCommand(String actionCommand)
    {
        switch (actionCommand.charAt(0))
        {
            case 'r':
                moveDie(actionCommand.charAt(1), this.rolled, this.held);
                break;
            case 'h':
                moveDie(actionCommand.charAt(1), this.held, this.rolled);
                break;
            case 'l':
                roll();
                break;
            case 'f':
                scoreCategory(faceCats, actionCommand.charAt(1));
                break;
            case 'c':
                scoreCategory(combos, actionCommand.charAt(1));
                break;
            case 'n':
                newGame();
                break;

        }

        setChanged();
        notifyObservers();
    }

    private void moveDie(char pos, List<JahtzeeDie> from, List<JahtzeeDie> to)
    {
        JahtzeeDie die = from.remove(Character.getNumericValue(pos));
        to.add(die);
    }

    private void scoreCategory(List<JahtzeeCategory> cats, char charAt)
    {
        cats.get(Character.getNumericValue(charAt) - 1).score();
        this.rolls = 0;
        while (!held.isEmpty())
        {
            rolled.add(held.remove(0));
        }
    }

    private void newGame()
    {
        rolled.clear();
        held.clear();
        for (JahtzeeCategory cat : combos)
        {
            cat.reset();
        }
        for (JahtzeeCategory cat : faceCats)
        {
            cat.reset();
        }
    }
}
