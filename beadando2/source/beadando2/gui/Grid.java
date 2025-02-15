package beadando2.gui;

import beadando2.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    private JButton[][] cells;
    private final Controller controller;

    /**
     * Creates a Grid object.
     * @param gridSize The length, in cells, the sides of the square grid.
     * @param controller The Controller object that manages this Grid.
     */
    public Grid(int gridSize, Controller controller) {
        this.controller = controller;
        createCells(gridSize);
    }

    /**
     * Recreates the Grid's cells.
     * @param gridSize The length, in cells, the sides of the square grid.
     */
    public void newGame(int gridSize) {
        removeAll();
        createCells(gridSize);
        revalidate();
        repaint();
    }

    private void createCells(int gridSize) {
        cells = new JButton[gridSize][gridSize];
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                JButton cell = new JButton();
                cell.addActionListener(controller.new CellListener(j,i));
                cell.setPreferredSize(new Dimension(80, 80));
                cells[i][j] = cell;
                add(cell);
            }
        }
        setLayout(new GridLayout(gridSize, gridSize));
    }

    /**
     * Changes a given cell's color.
     * @param p The position of the cell within the Grid
     * @param color The color to change the cell to.
     */
    public void setCellColor(Point p, Color color) {
        cells[p.y][p.x].setBackground(color);
    }
}
