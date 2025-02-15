package beadando1.player;

import beadando1.tile.Fortune;
import beadando1.tile.Property;
import beadando1.tile.Utility;

import java.util.ArrayList;

public abstract class Player {
    /**
     * The name of the player
     */
    public final String name;
    protected int capital = 10000;
    protected final ArrayList<Property> properties = new ArrayList<>();
    private int position = 0;
    private boolean bankrupt = false;

    protected Player(String name) {
        this.name = name;
    }

    /**
     * Add a Property tile to the list of the Properties owned by the Player
     * @param prop The Property tile to be owned by the Player
     */
    public void addProperty(Property prop) {
        if (properties.contains(prop)) {
            throw new IllegalStateException("Property is already owned by this Player");
        }
        properties.add(prop);
    }

    /**
     * Getter function of the Player's position prop
     * @return The position of the Player on the board
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getter function of the Player's capital prop
     * @return The amount of capital the player has
     */
    public int getCapital() {
        return capital;
    }

    /**
     * Increases the position of the Player on the board
     * @param step The amount the Player's position is increased by
     */
    public void stepBy(int step) {
        position += step;
    }

    /**
     * Getter function of the Player's bankrupt prop
     * @return Whether the Player is bankrupt (couldn't pay full rent or utility fine)
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Function to get the list of the Player's owned Properties
     * @return A copy of the Player's Properties prop
     */
    public ArrayList<Property> getProperties() {
        return new ArrayList<>(properties);
    }

    /**
     * Reset all owned Property tiles to their unowned and empty states
     */
    public void resetOwnedTiles() {
        properties.forEach(Property::reset);
    }

    /**
     * Receive payment, add it to the Player's capital prop
     * @param amount The amount to increase the capital by, providing negative values won't decrease it
     */
    public void pay(int amount) {
        if (amount <= 0) return;
        capital += amount;
    }

    protected abstract boolean willBuy();
    protected abstract boolean willBuild();

    /**
     * Handle landing on a Property tile, such as buying, building or paying rent
     * @param prop The tile the player landed on
     */
    public void handleTile(Property prop) {
        Player propOwner = prop.getOwner();
        if (propOwner == null) {
            if (willBuy()) {
                prop.buy(this);
                capital -= Property.PROP_COST;
            }
        } else if (propOwner != this) {
            int totalRent = prop.getRent();
            if (totalRent > capital) {
                this.bankrupt = true;
                prop.payRent(capital);
            } else {
                prop.payRent(totalRent);
                capital -= totalRent;
            }

        } else if (!prop.hasHouse() && willBuild()) {
            prop.buildHouse();
            capital -= Property.HOUSE_COST;
        }
    }

    /**
     * Handle landing on a Fortune tile, the player will receive the tile's value as payment
     * @param tile The tile the player landed on
     */
    public void handleTile(Fortune tile) {
        capital += tile.value;
    }

    /**
     * Handle landing on a Utility tile, the player has to pay the tile's value as a fine
     * @param tile The tile the player landed on
     */
    public void handleTile(Utility tile) {
        capital -= tile.value;
        bankrupt = capital < 0;
    }

    // Debugging
    protected abstract String typeString();

    /**
     * Override of the base Object class toString() method.
     * @return A String representation of the Player
     */
    @Override
    public String toString() {
        return String.format("%s %s: %d", typeString(), name, capital);
    }

}
