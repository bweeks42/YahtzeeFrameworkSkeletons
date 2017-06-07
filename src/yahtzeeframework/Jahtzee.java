/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.ini4j.*;

/**
 * Jahtzee (Java Yahtzee) is a framework for playing a generic yahtzee game.
 * Requires a client plugin to assist with the logic of the game.
 *
 * Jahtzee manages the state of the yahtzee game. It is capable of playing
 * multiple games in a row and monitoring the status of bonuses and categories
 * specified by the plugin author.
 *
 * @author bweeks
 */
public class Jahtzee extends Observable
{

    private final NumberGenerator generator;
    private int maxDice;
    private int maxRolls;
    private int rolls;
    private int round;

    private final List<JahtzeeCategory> combos;
    private final List<JahtzeeCategory> faceCats;

    private final List<JahtzeeDie> held;
    private final List<JahtzeeDie> rolled;
    private final List<JahtzeeDie> dice;

    private final List<JahtzeeBonus> bonuses;

    /**
     * Constructor for Jahtzee
     *
     * @param plugin Plugin class detailing game logic
     * @param pluginPackage location of the plugin, used to find resources
     * @param generator used to generate roll values
     */
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
        FileReader fr;
        Ini ini = new Ini();
        // try to read in the configuration file from the specified location
        try
        {
            fr = new FileReader("./" + pluginPackage
                    + "/config/config.ini");
            ini.load(fr);

        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Config file not found");
        }
        catch (IOException ex)
        {
            System.out.println("There was an issue reading the config file");
        }

        Ini.Section gameSection = ini.get("game");
        maxDice = Integer.parseInt(gameSection.get("num_dice"));
        maxRolls = Integer.parseInt(gameSection.get("num_rolls"));
        int numFaces = Integer.parseInt(gameSection.get("num_faces"));
        initDice(ini, numFaces);
        initFaceCategories(ini, numFaces);
    }

    private void initDice(Ini ini, int numFaces)
    {
        // initialize every die
        for (int diceInd = 0; diceInd < maxDice; diceInd++)
        {
            this.dice.add(new JahtzeeDie(ini, numFaces));
        }
    }

    private void initFaceCategories(Ini ini, int numFaces)
    {
        // initialize the correct number of categories
        for (int ind = 1; ind <= numFaces; ind++)
        {
            Ini.Section face = ini.get("face" + ind);
            JahtzeeCategory cat = JahtzeeFaceCategory.createCategory(face);
            // if returned category is null, skip
            if (cat != null)
            {
                faceCats.add(cat);
            }
        }
    }

    /**
     * Gets the max number of rollable dice
     *
     * @return the max number of rollable dice
     */
    int getDiceLimit()
    {
        return this.maxDice;
    }

    /**
     * Determines whether the player can roll the dice
     *
     * @return whether the player can roll the dice
     */
    boolean canRoll()
    {
        return !isGameOver() && rolls < maxRolls;
    }

    /**
     * Determines whether the game is over
     *
     * @return whether the game is over
     */
    boolean isGameOver()
    {
        return this.round == this.combos.size() + this.faceCats.size();
    }

    /**
     * Determines whether the player can move the dice
     *
     * @return whether the player can move the dice
     */
    boolean canMoveDice()
    {
        return this.rolls != maxRolls && this.rolls != 0;
    }

    /**
     * Gets all dice currently in play
     *
     * @return dice currently in play
     */
    public List<JahtzeeDie> getDiceInPlay()
    {
        List<JahtzeeDie> diceInPlay = new ArrayList<>();
        /**
         * if game hasn't just started
         */
        if (rolls != 0)
        {
            diceInPlay = dice;
        }
        return diceInPlay;
    }

    /**
     * Gets the face categories
     *
     * @return the face categories
     */
    public List<JahtzeeCategory> getFaceCategories()
    {
        return faceCats;
    }

    /**
     * Gets the combo categories
     *
     * @return the combo categories
     */
    public List<JahtzeeCategory> getComboCategories()
    {
        return combos;
    }

    /**
     * Determines whether the player can score this round
     *
     * @return whether the player can score this round
     */
    boolean canScore()
    {
        return rolls != 0;
    }

    /**
     * Gets the subtotal of all face category scores
     *
     * @return the subtotal of all face category scores
     */
    int getFaceScore()
    {
        int sub = 0;
        // sum up each categories score
        for (JahtzeeCategory cat : this.faceCats)
        {
            // only count if has been scored
            if (!cat.canScore())
            {
                sub += cat.getCurrentScore();
            }
        }
        return sub;
    }

    /**
     * Gets the subtotal of all combo category scores
     *
     * @return the subtotal of all combo category scores
     */
    int getComboScore()
    {
        int sub = 0;
        // sum up each categories score
        for (JahtzeeCategory cat : this.combos)
        {
            // only count if has been scored
            if (!cat.canScore())
            {
                sub += cat.getCurrentScore();
            }
        }
        return sub;
    }

    /**
     * Get the total game score
     *
     * @return the total game score
     */
    int getTotalScore()
    {
        return getComboScore() + getFaceScore() + getBonusScore();
    }

    private int getBonusScore()
    {
        int val = 0;

        // for each bonus get its score
        for (JahtzeeBonus bonus : bonuses)
        {
            val += bonus.getScore(faceCats, combos);
        }
        return val;
    }

    /**
     * Determines whether a bonus is active
     *
     * @return whether a bonus is active
     */
    boolean isBonus()
    {

        return getBonusScore() != 0;
    }

    /**
     * Get the current roll count
     *
     * @return the current roll count
     */
    int getRollCount()
    {
        return this.rolls;
    }

    /**
     * Get the dice in the rolled column
     *
     * @return the dice in the rolled column
     */
    List<JahtzeeDie> getRolled()
    {
        return this.rolled;
    }

    /**
     * Get the dice in the hel column
     *
     * @return the dice in the held column
     */
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

    /**
     * Process a command to change the state of the game. Commands can be: rn -
     * move die n in rolled to held hn - move die n in held to rolled l - roll
     * dice fn - score category n cn - score category n n - start a new game
     *
     * Notifies all observers after processing.
     *
     * @param actionCommand command to process
     */
    void processCommand(String actionCommand)
    {
        // determine the kind of command based on the first character
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
        round += 1;
        // empty out the held category
        while (!held.isEmpty())
        {
            rolled.add(held.remove(0));
        }
    }

    private void newGame()
    {
        rolled.clear();
        held.clear();

        // reset all
        for (JahtzeeCategory cat : combos)
        {
            cat.reset();
        }
        // reset all
        for (JahtzeeCategory cat : faceCats)
        {
            cat.reset();
        }
        round = 0;
    }
}
