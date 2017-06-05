/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yahtzeeframework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Blain
 */
class JahtzeeController implements ActionListener {

    private Jahtzee jahtzee;
    
    JahtzeeController(Jahtzee jahtzee) {
        this.jahtzee = jahtzee;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
    }
    
}
