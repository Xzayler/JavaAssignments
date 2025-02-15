package beadando3.gui;

import beadando3.lib.MazeGenerator.Maze;
import beadando3.logic.labyrinth.Labyrinth;
import beadando3.logic.labyrinth.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * A component displaying Cells in a grid
 */
public class Grid extends JPanel {

    private Cell[][] cells;

    /**
     * Sets up the Cells within itself
     * @param tiles The Tiles on which to base the Cells off of
     */
    public Grid(Tile[][] tiles) {
        createCells(tiles);
    }

    /**
     * Recreates a new Cells grid based on the new input
     * @param tiles The new Tiles to create Cells from
     */
    public void newGame(Tile[][] tiles) {
        removeAll();
        createCells(tiles);
        revalidate();
        repaint();
    }

    private void createCells(Tile[][] tiles) {
        int gridSize = tiles.length;
        cells = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                Cell cell = new Cell(tiles[i][j]);
                cells[i][j] = cell;
                add(cell);
            }
        }
        setLayout(new GridLayout(gridSize, gridSize));
    }

    /**
     * Move the player from its previous position
     * @param pos The player's new position
     * @param prevPos The player's previous position
     */
    public void setPlayer(Point pos, Point prevPos) {
        Cell pc = cells[prevPos.y][prevPos.x];
        pc.hasPlayer = false;
        pc.repaint();
        Cell c = cells[pos.y][pos.x];
        c.hasPlayer = true;
        c.repaint();
    }

    /**
     * Move the dragon from its previous position
     * @param pos The dragon's new position
     * @param prevPos The dragon's previous position
     */
    public void setDragon(Point pos, Point prevPos) {
        Cell pc = cells[prevPos.y][prevPos.x];
        pc.hasDragon = false;
        pc.repaint();
        Cell c = cells[pos.y][pos.x];
        c.hasDragon = true;
        c.repaint();
    }
}
