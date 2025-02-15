package beadando3.gui;

import beadando3.lib.MazeGenerator.Direction;
import beadando3.logic.labyrinth.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * A component representing a graphical unit of the maze.
 */
public class Cell extends JPanel {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    /**
     * Determines whether the player will be drawn on this cell
     */
    public boolean hasPlayer = false;
    /**
     * Determines whether the dragon will be drawn on this cell
     */
    public boolean hasDragon = false;

    /**
     * Creates a Cell object which will draw a unit of the maze
     * @param tile The Tile Object to use to create the Cell tile from
     */
    public Cell(Tile tile) {
        setPreferredSize(new Dimension(80, 80));
        setBackground(Color.BLACK);

        up = tile.hasNeighbour(Direction.UP);
        down = tile.hasNeighbour(Direction.DOWN);
        left = tile.hasNeighbour(Direction.LEFT);
        right = tile.hasNeighbour(Direction.RIGHT);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int thickness = Math.min(width, height) / 10; // 10% of smaller dimension
        Graphics2D g2 = (Graphics2D) g;

        // Paint corners
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, thickness, thickness);
        g2.fillRect(width - thickness, 0, thickness, thickness);
        g2.fillRect(0, height - thickness, thickness, thickness);
        g2.fillRect(width - thickness, height - thickness, thickness, thickness);

        // Paint walls
        if (!up) {
            g2.fillRect(0,0,width,thickness);
        }
        if (!down) {
            g2.fillRect(0,height - thickness, width, thickness);
        }
        if (!left) {
            g2.fillRect(0,0,thickness,height);
        }
        if (!right) {
            g2.fillRect(width-thickness,0,width,height);
        }

        if (hasPlayer) {
            drawPlayer(g2);
        }

        if (hasDragon) {
            drawDragon(g2);
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.GREEN);
        int margin = getWidth() / 5;
        g.fillRect(margin, margin, getWidth() - (margin * 2), getHeight() - (margin * 2));
    }

    private void drawDragon(Graphics g) {
        g.setColor(Color.RED);
        int margin = getWidth() / 5;
        g.fillRect(margin, margin, getWidth() - (margin * 2), getHeight() - (margin * 2));
    }

}
