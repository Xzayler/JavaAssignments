package beadando1.tile;

import beadando1.player.Player;

public class Fortune extends Tile{
    /**
     * The value to receive when stepping on this tile.
     */
    public final int value;

    /**
     * Creates a new Fortune tile Object
     * @param value The amount to be paid to Players when landing on this tile
     */
    public Fortune(int value){
        this.value = value;
    }

    /**
     * Calls the Player.handleTile() method to handle Fortune tiles
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
        return String.format("Fortune: %d", value);
    }
}
