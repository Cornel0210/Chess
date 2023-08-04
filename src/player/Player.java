package player;

import helper.Colour;
import helper.Position;

public abstract class Player {

    private final Colour colour;
    public Player(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public abstract Position move();

}
