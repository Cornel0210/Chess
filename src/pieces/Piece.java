package pieces;

import helper.Colour;
import helper.Position;

public interface Piece{
    /* String RESET = "\033[0m"; //ansi reset;

     String BLACK_BACKGROUND = "\033[40m";  // BLACK
     String RED_BACKGROUND = "\033[41m";    // RED
     String GREEN_BACKGROUND = "\033[42m";  // GREEN
     String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
     String BLUE_BACKGROUND = "\033[44m";   // BLUE
     String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
     String CYAN_BACKGROUND = "\033[46m";   // CYAN
     String WHITE_BACKGROUND = "\033[47m";  // WHITE*/
    boolean checkIfCanMove(Position toPosition);
    Colour getColour();
    default boolean isValid(Position position){
        return (position.getX()>=0 && position.getX()<=7 &&
                position.getY()>=0 && position.getY()<=7);
    }
    Position getPosition();
    void setPosition(Position position);
}
