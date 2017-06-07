/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple controller class for the Jahtzee framework. Passes commands from a
 * observer to the internal Jahtzee.
 *
 * @author Blain
 */
class JahtzeeController implements ActionListener
{

    private final Jahtzee jahtzee;

    /**
     * Constructor for the JahtzeeController
     *
     * @param jahtzee underlying Jahtzee model of the game.
     */
    JahtzeeController(Jahtzee jahtzee)
    {
        this.jahtzee = jahtzee;
    }

    /**
     * Passes the action command of an event to the underlying model
     *
     * @param evt the triggered event
     */
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        jahtzee.processCommand(evt.getActionCommand());
    }
}
