package beadando1.player;

import beadando1.tile.Property;

public class Tactical extends Player {
    private boolean willBuyNext = false;

    /**
     * Creates a new Tactical object
     * @param name The name of the player. Not used for anything but identification
     */
    public Tactical(String name) {
        super(name);
    }

    @Override
    protected boolean willBuy() {
        boolean couldBuy = capital >= Property.PROP_COST;
        willBuyNext = couldBuy != willBuyNext;
        return couldBuy && willBuyNext;
    }

    @Override
    protected boolean willBuild() {
        return capital >= Property.HOUSE_COST;
    }

    // Debugging
    @Override
    protected String typeString() {
        return "Tactical";
    }
}
