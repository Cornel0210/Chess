package player;

import helper.Colour;
import helper.Input;
import helper.Position;

import java.util.InputMismatchException;

public class HumanPlayer extends Player{

    public HumanPlayer(Colour colour) {
        super(colour);
    }

    @Override
    public Position move() {
        int x;
        int y;
        String input = Input.getInstance().getPosition();
        String allowed = "01234567";
        while (input.length()!=3 || !allowed.contains(input.substring(0,1)) ||
                !allowed.contains(input.substring(2)) || !"-".contains(input.substring(1,2))) {
            System.out.println("Only 'x-y' format is allowed, where 'x' and 'y' are int primitive type");
            input = Input.getInstance().getPosition();
        }
        x = Integer.parseInt(input.substring(0,1));
        y = Integer.parseInt(input.substring(2));
        return new Position(x, y);
    }
}
