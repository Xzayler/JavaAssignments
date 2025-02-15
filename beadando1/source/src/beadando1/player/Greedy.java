package beadando1.player;

import beadando1.tile.*;

public class Greedy extends Player {

    /**
     * Creates a new Greedy object
     * @param name The name of the player. Not used for anything but identification
     */
    public Greedy(String name) {
        super(name);
    }

    @Override
    protected boolean willBuy() {
        return capital >= Property.PROP_COST;
    }

    @Override
    protected boolean willBuild() {
        return capital >= Property.HOUSE_COST;
    }

    // Debugging
    @Override
    protected String typeString() {
        return "Greedy";
    }
}
