package player;

import helper.Colour;
import helper.Input;
import helper.Position;

public class HumanPlayer extends Player{

    public HumanPlayer(Colour colour) {
        super(colour);
    }

    @Override
    public Position move() {
        int x;
        int y;
        do {
            String input = Input.getInstance().getPosition();
            x = Integer.parseInt(input.substring(0,1));
            y = Integer.parseInt(input.substring(2));
        } while (x<0 || x>7 || y<0 || y>7);
        return new Position(x, y);
    }
}
