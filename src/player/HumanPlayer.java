package player;

import helper.Colour;
import helper.Input;
import helper.Position;
import pieces.Piece;

public class HumanPlayer extends Player{

    public HumanPlayer(Colour colour) {
        super(colour);
    }

    @Override
    public Position move() {
        int x = Input.getInstance().getInt();
        int y = Input.getInstance().getInt();
        while (x<0 || x>7 || y<0 || y>7) {
            System.out.println("insert a number between [0 and 7]");
            x = Input.getInstance().getInt();
            y = Input.getInstance().getInt();
        }
        return new Position(x, y);
    }
}
