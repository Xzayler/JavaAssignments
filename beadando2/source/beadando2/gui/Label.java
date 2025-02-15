package beadando2.gui;

import javax.swing.*;

public class Label extends JLabel {

    /**
     * Creates a Label object.
     * @param steps The number of moves left to display
     */
    public Label(int steps) {
        super("Moves Left: " + steps, SwingConstants.CENTER);
    }

    /**
     * Changes the number's value in the label's text.
     * @param steps The number to change it to.
     */
    public void setSteps(int steps) {
        setText("Moves Left: " + steps);
    }
}
