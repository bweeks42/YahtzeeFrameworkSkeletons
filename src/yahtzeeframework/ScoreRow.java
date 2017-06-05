package yahtzeeframework;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * ScoreRow is one row or line in the display of a yahtzee gui scoring panel. The first
 * element of the row is a button and the second a label.
 *
 * @author jdalbey
 */
public class ScoreRow extends javax.swing.JPanel
{
    private JButton btnPoints;   // The points earned for this item
    private JLabel lblDescription;  // description of item

    /**
     * Creates new form ScoreRow
     *
     * @param name      the identifying name of this row
     * @param btnAction listener to attach to this row's button
     */
    public ScoreRow(String name, ActionListener btnAction)
    {
        setName(name);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 100));
        btnPoints = new JButton();
        btnPoints.setName("button");
        btnPoints.addActionListener(btnAction);
        btnPoints.setPreferredSize(new Dimension(30, 5));
        lblDescription = new JLabel();
        lblDescription.setName("label");
        add(btnPoints, BorderLayout.WEST);
        add(lblDescription, BorderLayout.CENTER);
    }

    /**
     * Assign values to the visual fields in the button and label.
     *
     * @param buttonText  new value for button text. If empty hide the button
     * @param command     The action command to attach to this row's button
     * @param description new value for label text
     */
    public void setFields(String buttonText, String command, String description)
    {
        // IF buttonText is empty hide the button
        if (buttonText.equals(""))
        {
            btnPoints.setVisible(false);
        }
        else
        {
            btnPoints.setVisible(true);
            btnPoints.setText(buttonText);
            btnPoints.setActionCommand(command);
        }
        lblDescription.setText(description);
    }

    /**
     * Set the visible state for the button and label. The panel itself is always visible
     * so it will take up space in the display.
     *
     * @param flag true for visible, false for hidden.
     */
    public void setVisible(boolean flag)
    {
        // If flag says visible
        if (flag)
        {
            btnPoints.setVisible(true);
            lblDescription.setVisible(true);
        }
        else
        {
            btnPoints.setVisible(false);
            lblDescription.setVisible(false);
        }
    }

    /**
     * Set the enabled state for the button. At certain points in the game the button
     * should be disabled (grayed out) so the user can't click it.
     *
     * @param flag true for enabled, false for disabled.
     */
    @Override
    public void setEnabled(boolean flag)
    {
        btnPoints.setEnabled(flag);
    }
}
