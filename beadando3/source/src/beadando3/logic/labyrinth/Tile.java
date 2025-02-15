package beadando3.logic.labyrinth;

import beadando3.lib.MazeGenerator.Direction;

import java.util.EnumMap;

/**
 * The logical representation of a space in the Labyrinth
 */
public class Tile {
    private final EnumMap<Direction, Tile> neighbours = new EnumMap<Direction, Tile>(Direction.class);

    /**
     * Checks whether there is a passage to another Tile in the given direction
     * @param direction The direction to check
     * @return Whether there is a passage
     */
    public boolean hasNeighbour(Direction direction) {
        return neighbours.get(direction) != null;
    }

    /**
     * Creates a passage between two tiles
     * @param tile1 The first tile
     * @param tile2 The second tile
     * @param direction The direction of the first time compared to the second one
     */
    public static void connect(Tile tile1, Tile tile2, Direction direction) {
        tile1.neighbours.put(direction, tile2);
        tile2.neighbours.put(direction.getOpposite(), tile1);
    }
}
