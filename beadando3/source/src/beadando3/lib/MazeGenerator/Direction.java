package beadando3.lib.MazeGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * An enum representing the Cardinal direction
 */
public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    /**
     * Swaps the given direction
     * @return The direction opposite to the input, eg. UP if DOWN, LEFT if RIGHT
     */
    public Direction getOpposite() {
        return Direction.values()[(this.ordinal() + 2) % 4];
    }

    /**
     * Generates a random direction based on calculated possibilities.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param size The bounds to hold x and y
     * @return A random direction based on the parameters
     */
    public static Direction getRandomDirection(int x, int y, int size) {
        ArrayList<Direction> possibleDirs = new ArrayList<>(Arrays.asList(Direction.values()));
        if (y == 0) {
            possibleDirs.remove(Direction.UP);
        } else if (y == size - 1) {
            possibleDirs.remove(Direction.DOWN);
        }
        if (x == 0) {
            possibleDirs.remove(Direction.LEFT);
        } else if (x == size - 1) {
            possibleDirs.remove(Direction.RIGHT);
        }

        return possibleDirs.get(new Random().nextInt(possibleDirs.size()));
    }

    /**
     * Calculates the change in the y coordinate if we were to travel in the given direction
     * @return The vertical offset
     */
    public int getYOffset() {
        if (this.equals(UP) || this.equals(DOWN)) {
            return this.ordinal() - 1;
        }
        return 0;
    }

    /**
     * Calculates the change in the x coordinate if we were to travel in the given direction
     * @return The horizontal offset
     */
    public int getXOffset() {
        if (this.equals(RIGHT) || this.equals(LEFT)) {
            return 2 - this.ordinal();
        }
        return 0;
    }
}
