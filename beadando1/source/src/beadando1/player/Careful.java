package beadando1.player;

import beadando1.tile.Property;

public class Careful extends Player{

    /**
     * Creates a new Careful object
     * @param name The name of the player. Not used for anything but identification
     */
    public Careful(String name) {
        super(name);
    }

    @Override
    protected boolean willBuy() {
        return capital / 2 >= Property.PROP_COST;
    }

    @Override
    protected boolean willBuild() {
        return capital / 2 >= Property.HOUSE_COST;
    }

    // Debugging
    @Override
    protected String typeString() {
        return "Careful";
    }

}
