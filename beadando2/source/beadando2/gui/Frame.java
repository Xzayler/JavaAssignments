package beadando2.gui;

import beadando2.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private final Grid grid;
    private final Label label;

    /**
     * Creates a Frame object
     * @param gridSize The length, in cells, the sides of the square grid.
     * @param controller The controller the frame is part of
     */
    public Frame(int gridSize, Controller controller) {
        this.grid = new Grid(gridSize, controller);
        this.label = new Label(gridSize*4);
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(grid, BorderLayout.CENTER);
        setFrameProps();
    }

    private void setFrameProps() {
        setTitle("Vadászat");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(100,100);
    }

    /**
     * Getter for the Frame's Grid
     * @return The frame's Grid property
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Getter for the Frame's Label
     * @return The frame's Label property
     */
    public Label getLabel() {
        return label;
    }
}
