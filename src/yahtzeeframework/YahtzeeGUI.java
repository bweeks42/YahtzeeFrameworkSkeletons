package yahtzeeframework;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The YahtzeeGUI class is a view of the state of a YahtzeeGame. Buttons are
 * provided to enact the various actions of a YahtzeeGame: rolling, swapping,
 * and scoring. The YahtzeeGUI class uses its constructor argument "name" to
 * locate image resources.
 *
 * @author your name
 */
public class YahtzeeGUI extends javax.swing.JFrame implements I_View
{

    private final static int kCategoryRowLimit = 10;
    private String gameName;  // The name of the game of yahtzee being played
    private int maxDice; // the number of dice used by the plugin
    private JahtzeeController controller; // controller for game

    /**
     * Constructs a new YahtzeeGUI object. You may modify this as needed.
     *
     * @param name The plugin name that will be displayed in the window title.
     * Also used to locate the folder in which images are stored.
     */
    public YahtzeeGUI(String name, JahtzeeController controller, int maxDice)
    {
        try
        {
            String cn = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(cn);
        } catch (Exception cnf)
        {
            cnf.printStackTrace();
        }
        this.gameName = name;
        this.controller = controller;
        this.maxDice = maxDice;
        this.setTitle(name + " Yahtzee");

        initComponents();
        btnRoll.setActionCommand("l");
        btnRoll.addActionListener(controller);

        // Call createDicePanels to create combination and face value areas
        createDicePanels(maxDice);

        lblBonus.setVisible(false);  // turn off bonus indicator to start
        // Add listeners
        exitMenuItem.addActionListener(new MyQuitListener());
        newGameMenuItem.addActionListener(controller);
        newGameMenuItem.setActionCommand("n");
    }

    /**
     * Refreshes every component of the display.
     *
     * @param obs the observable who notified us
     * @param arg not used
     */
    @Override
    public void update(Observable obs, Object arg)
    {
        if (obs != null)
        {
            Jahtzee game = (Jahtzee) obs;
            // Draw the dice rolling and holding area
            drawDice(game);
            // Draw the Face Values categories
            drawRowsForCategory(pnlFaceValues, game, game.getFaceCategories(),
                    "f");
            // Draw the Combination categories
            drawRowsForCategory(pnlCombos, game, game.getComboCategories(),
                    "c");
            // Set the rolling button disabled if rolling is disabled
            btnRoll.setEnabled(game.canRoll());
            // Update the roll counter
            lblRollCount.setText(Integer.toString(game.getRollCount()));
            // Update the subtotal and total scores
            lblFaceValSubtotal.setText(Integer.toString(game.getFaceScore()));
            lblComboSubtotal.setText(Integer.toString(game.getComboScore()));
            lblTotalScore.setText(Integer.toString(game.getTotalScore()));

            // Show the bonus indicator if earned
            lblBonus.setVisible(game.isBonus());
        }
    }

    /**
     * Draws the rows in a Category area.
     *
     * @param dest The JPanel the buttons will be added to, either pnlFaceValues
     * or pnlCombos.
     * @param categories List of categories whose attributes will determine the
     * characteristics of each button.
     * @param handler Action listener to be added to each button
     */
    private void drawRowsForCategory(JPanel dest, Jahtzee game,
            List<JahtzeeCategory> categories, String prefix)
    {
        dest.removeAll();
        // Draw as many rows as the panel permits
        for (int row = 1; row <= kCategoryRowLimit; row++)
        {
            ScoreRow scorerow = new ScoreRow("row" + row, controller);
            // IF this row # < number of categories THEN
            if (row <= categories.size())
            {
                // Get the category to be shown
                JahtzeeCategory category = categories.get(row - 1);
                // IF this category is available to be scored THEN
                if (category.canScore())
                {
                    // Set the fields for this row
                    scorerow.setFields(
                            Integer.toString(
                                    category.calculateScore(game.getDiceInPlay())),
                            prefix + row,
                            category.getName());
                    // If scoring is inactive the buttons should be shown grey
                    scorerow.setEnabled(game.canScore());
                } else
                {
                    // show score assigned to this category
                    scorerow.setFields(
                            "",
                            "c" + row,
                            "<HTML><B>&nbsp;" + Integer.toString(
                                    category.calculateScore(game.getDiceInPlay()))
                            + "</B>&nbsp;&nbsp;&nbsp;"
                            + category.getName() + "</HTML>");
                } // END IF
            } else
            {
                // set invisible: unused rows take up space but aren't visible
                scorerow.setVisible(false);
            }
            // Add the row to the panel
            dest.add(scorerow);
        }
        dest.validate();
    }

    /*
     * This method is called once, from constructor, to create permanent die
     * buttons for each panel.  Subsequent redraws will just change the button
     * attribute values but will NOT recreate the buttons. Don't change.
     * @param diceLimit the number of dice used in this game
     */
    private void createDicePanels(int diceLimit)
    {
        // Make a button with a sample image for each allowed die in the game
        for (int dieCount = 0; dieCount < diceLimit; dieCount += 1)
        {
            /* Rolling die */
            JButton die = new JButton(createImage(gameName, "sample"));
            setButtonAttributes(die, "die", (dieCount + 1));
            die.addActionListener(controller);
            pnlDice.add(die);
            /* Held die */
            JButton die2 = new JButton(createImage(gameName, "sample"));
            die2.addActionListener(controller);
            setButtonAttributes(die2, "held", (dieCount + 1));
            pnlHold.add(die2);
        }
        pnlDice.validate();
        pnlHold.validate();
    }

    /* Apply desired button attributes to a die buttons - don't change */
    private void setButtonAttributes(JButton die, String prefix, int id)
    {
        die.setName(prefix + id);
        die.setEnabled(false);
        die.setVisible(false);  // make true for demo
        die.setMargin(new Insets(0, 0, 0, 0));
        die.setContentAreaFilled(false);
        die.setBorderPainted(false);
        die.setOpaque(false);
    }

    /**
     * TODO rework this method
     */
    private void drawDice(Jahtzee game)
    {

        List<JahtzeeDie> rolled = game.getRolled();
        List<JahtzeeDie> held = game.getHeld();

        // Draw the number of dice allowed in the game
        for (int dieCount = 0; dieCount < maxDice; dieCount += 1)
        {
            JButton currentDie = (JButton) pnlDice.getComponent(dieCount);
            JahtzeeDie currentJDie;
            // Draw the Rolled Dice

            if (dieCount < rolled.size())
            {
                currentJDie = rolled.get(dieCount);
                ImageIcon ico;
                ico = createImage(gameName, currentJDie.getFaceUpImage());
                currentDie.setIcon(ico);
                currentDie.setToolTipText(currentJDie.getFaceUpTip());
                // action commands will be of form ("r0", "r1", etc.)
                currentDie.setActionCommand("r" + dieCount);
                currentDie.setEnabled(game.canMoveDice());  // only enable if we can roll
                currentDie.setVisible(true);

            } else
            {
                currentDie.setVisible(false);
            }
            
            currentDie = (JButton) pnlHold.getComponent(dieCount);
            if (dieCount >= rolled.size() && 
                    dieCount - rolled.size() < held.size())
            {
                currentJDie = held.get(dieCount - rolled.size());
                ImageIcon ico;
                ico = createImage(gameName, currentJDie.getFaceUpImage());
                currentDie.setIcon(ico);
                currentDie.setToolTipText(currentJDie.getFaceUpTip()); //
                // action commands will be of form ("h0", "h1", etc.)
                currentDie.setActionCommand("h" + (dieCount - rolled.size()));
                currentDie.setEnabled(game.canMoveDice()); // only enable if we can roll 
                currentDie.setVisible(true);
            } else
            {
                currentDie.setVisible(false);
            }

        }
        pnlDice.validate();
        pnlHold.validate();
    }

    /**
     * Suggested helper method - should catch exceptions
     *
     * @param plugin the plugin name (package for locating images)
     * @param imgName the filename of the specific die image
     * @return the icon to display in the die button
     */
    private ImageIcon createImage(String plugin, String imgName)
    {
        String pluginName = plugin.toLowerCase() + "yahtzee";
        java.net.URL imgURL = getClass()
                .getResource("../" + pluginName + "/config/" + imgName);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, imgName);
        } else
        {
            return new ImageIcon("", "blank");
        }
    }

    /**
     * An listener for quit events. An instance of this class should be added to
     * the Exit menu option.
     */
    class MyQuitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonPanel = new javax.swing.JPanel();
        btnRoll = new javax.swing.JButton();
        lblForMoves = new javax.swing.JLabel();
        lblRollCount = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalScore = new javax.swing.JLabel();
        lblBonus = new javax.swing.JLabel();
        pnlDice = new javax.swing.JPanel();
        pnlHold = new javax.swing.JPanel();
        pnlScoring = new javax.swing.JPanel();
        pnlFaceValues = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblFaceValSubtotal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblComboSubtotal = new javax.swing.JLabel();
        pnlCombos = new javax.swing.JPanel();
        lblRollingArea = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuGame = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonPanel.setName("buttonPanel"); // NOI18N

        btnRoll.setText("Roll");
        btnRoll.setName("btnRoll"); // NOI18N

        lblForMoves.setText("Rolls");
        lblForMoves.setName("lblForMoves"); // NOI18N

        lblRollCount.setText("0");
        lblRollCount.setName("lblRollCount"); // NOI18N

        jLabel5.setText("Total");
        jLabel5.setName("jLabel5"); // NOI18N

        lblTotalScore.setText("0");
        lblTotalScore.setName("lblTotalScore"); // NOI18N

        lblBonus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yahtzeeframework/resources/bonus_star.png"))); // NOI18N
        lblBonus.setText(" ");
        lblBonus.setName("lblBonus"); // NOI18N

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRoll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblForMoves)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRollCount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(30, 30, 30)
                .addComponent(lblTotalScore)
                .addGap(34, 34, 34)
                .addComponent(lblBonus)
                .addGap(41, 41, 41))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRollCount)
                    .addComponent(lblForMoves)
                    .addComponent(btnRoll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTotalScore)
                    .addComponent(lblBonus))
                .addContainerGap())
        );

        pnlDice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDice.setMinimumSize(new java.awt.Dimension(57, 390));
        pnlDice.setName("pnlDice"); // NOI18N
        pnlDice.setPreferredSize(new java.awt.Dimension(57, 390));
        pnlDice.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pnlHold.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlHold.setMinimumSize(new java.awt.Dimension(57, 390));
        pnlHold.setName("pnlHold"); // NOI18N
        pnlHold.setPreferredSize(new java.awt.Dimension(57, 390));
        pnlHold.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pnlScoring.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlScoring.setName("pnlScoring"); // NOI18N

        pnlFaceValues.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlFaceValues.setName("pnlFaceValues"); // NOI18N
        pnlFaceValues.setLayout(new javax.swing.BoxLayout(pnlFaceValues, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel2.setText("Subtotal");
        jLabel2.setName("jLabel2"); // NOI18N

        lblFaceValSubtotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblFaceValSubtotal.setText("0");
        lblFaceValSubtotal.setName("lblFaceValSubtotal"); // NOI18N

        jLabel4.setText("Subtotal");
        jLabel4.setName("jLabel4"); // NOI18N

        lblComboSubtotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblComboSubtotal.setText("0");
        lblComboSubtotal.setName("lblComboSubtotal"); // NOI18N

        pnlCombos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlCombos.setName("pnlCombos"); // NOI18N
        pnlCombos.setLayout(new javax.swing.BoxLayout(pnlCombos, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout pnlScoringLayout = new javax.swing.GroupLayout(pnlScoring);
        pnlScoring.setLayout(pnlScoringLayout);
        pnlScoringLayout.setHorizontalGroup(
            pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScoringLayout.createSequentialGroup()
                .addGroup(pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlScoringLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblFaceValSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(131, 131, 131)
                        .addComponent(lblComboSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(pnlScoringLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlFaceValues, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlCombos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlScoringLayout.setVerticalGroup(
            pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScoringLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlCombos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFaceValues, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlScoringLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFaceValSubtotal)
                            .addComponent(jLabel2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlScoringLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnlScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblComboSubtotal)
                            .addComponent(jLabel4))))
                .addContainerGap())
        );

        lblRollingArea.setText("Rolling Area     Hold                    Face Values                                   Combinations");
        lblRollingArea.setName("lblRollingArea"); // NOI18N

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        mnuGame.setMnemonic('G');
        mnuGame.setText("Game");
        mnuGame.setName("mnuGame"); // NOI18N

        newGameMenuItem.setMnemonic('N');
        newGameMenuItem.setText("New Game");
        newGameMenuItem.setName("newGameMenuItem"); // NOI18N
        mnuGame.add(newGameMenuItem);

        exitMenuItem.setMnemonic('X');
        exitMenuItem.setText("Exit");
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        mnuGame.add(exitMenuItem);

        jMenuBar1.add(mnuGame);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblRollingArea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pnlDice, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlHold, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pnlScoring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRollingArea)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlScoring, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRoll;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblBonus;
    private javax.swing.JLabel lblComboSubtotal;
    private javax.swing.JLabel lblFaceValSubtotal;
    private javax.swing.JLabel lblForMoves;
    private javax.swing.JLabel lblRollCount;
    private javax.swing.JLabel lblRollingArea;
    private javax.swing.JLabel lblTotalScore;
    private javax.swing.JMenu mnuGame;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JPanel pnlCombos;
    private javax.swing.JPanel pnlDice;
    private javax.swing.JPanel pnlFaceValues;
    private javax.swing.JPanel pnlHold;
    private javax.swing.JPanel pnlScoring;
    // End of variables declaration//GEN-END:variables
}
