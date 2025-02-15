package beadando1.tile;

import beadando1.player.Player;

public class Property extends Tile {
    /**
     * Base cost of a property tile
     */
    static public final int PROP_COST = 1000;
    /**
     * Cost of buying a house on an empty property
     */
    static public final int HOUSE_COST = 4000;
    /**
     * Amount to be paid for landing on an owned empty property
     */
    static private final int EMPTY_RENT = 500;
    /**
     * Amount to be paid for landing on an owned property with a house
     */
    static private final int HOUSE_RENT = 2000;
    private Player owner = null;
    private boolean hasHouse = false;

    /**
     * Creates a new Property tile Object
     */
    public Property() {}

    /**
     * Getter function of the owner prop.
     * @return The owner of the property, or null if it's unowned
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Getter function of the hasHouse prop.
     * @return Whether there is a house built on the property or not
     */
    public boolean hasHouse() {
        return hasHouse;
    }

    /**
     * Sets the owner of the property tile to the given player
     * @param owner The player who bought the tile
     */
    public void buy(Player owner) {
        if (this.owner != null) {
            throw new IllegalStateException("Property already has an owner");
        }
        this.owner = owner;
        owner.addProperty(this);
    }

    /**
     * Sets the hasHouse property to true if it isn't already
     */
    public void buildHouse() {
        if (hasHouse) {
            throw new IllegalStateException("Property already has a house");
        }
        this.hasHouse = true;
    }

    /**
     * Sets the owner and hasHouse props to null and false
     */
    public void reset() {
        owner = null;
        hasHouse = false;
    }

    /**
     * Calculates the rent to be paid when the non-owner lands on this Property tile
     * @return The total rent to be paid
     */
    public int getRent() {
        if (owner == null) {
            throw new IllegalStateException("Property has no owner");
        }
        if (hasHouse) {
            return HOUSE_RENT;
        } else {
            return EMPTY_RENT;
        }
    }

    /**
     * Transfers the paid amount to the owner of the Property tile
     * @param amount The amount to pay the owner of the property
     */
    public void payRent(int amount) {
        if (owner == null) {
            throw new IllegalStateException("Property has no owner");
        }
        owner.pay(amount);
    }

    /**
     * Calls the Player.handleTile() method to handle Property tiles
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
        return String.format("Property - %s house", hasHouse ? "has" : "no");
    }

}
