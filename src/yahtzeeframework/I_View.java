package yahtzeeframework;

import java.util.Observer;

/**
 * I_View defines the interface for a View of the PSP history.
 *
 * @author jdalbey
 */
public interface I_View extends Observer
{
    /**
     * Shows or hides this View depending on the value of parameter.
     *
     * @param visible should this view be visible
     */
    void setVisible(boolean visible);
}
