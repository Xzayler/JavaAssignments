package beadando1.tile;


import beadando1.player.Player;

public abstract class Tile {
    protected Tile(){}

    /**
     * Calls the proper tileHandler of the Player class depending on the class of the caller
     * @param player The Player object to handle the tile
     */
    public abstract void passToPlayer(Player player);

    /**
     * Override of the base Object class toString() method.
     * @return
     */
    @Override
    public abstract String toString();
}
