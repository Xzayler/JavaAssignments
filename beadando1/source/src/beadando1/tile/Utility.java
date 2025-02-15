package beadando1.tile;

import beadando1.player.Player;

public class Utility extends Tile {
    /**
     * The value to be paid when stepping on this tile.
     */
    public final int value;

    /**
     * Creates a new Utility tile Object
     * @param value The amount to be paid by Players when landing on this tile
     */
    public Utility(int value){
        this.value = value;
    }

    /**
     * Calls the Player.handleTile() method to handle Utility tiles
     * @param player The Player object to handle the tile
     */
    @Override
    public void passToPlayer(Player player) {
        player.handleTile(this);
    }

    // Debugging

    /**
     * Override of the base Object class toString() method.
     * @return a String representing this object
     */
    @Override
    public String toString() {
        return String.format("Utility: %d", value);
    }
}
