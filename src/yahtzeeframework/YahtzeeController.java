/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yahtzeeframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author bweeks
 */
public class YahtzeeController implements ActionListener
{
    private final Jahtzee game;
    public YahtzeeController(Jahtzee game) {
        this.game = game;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() instanceof JButton) {
            game.processCommand(evt.getActionCommand());
        }
    }

}
