package pieces;

import helper.Colour;
import helper.Position;

public interface Piece{
    /**
     * This method must be defined by each class that implements 'Piece' interface.
     * It is supposed to only check if the move can be performed, it doesn't actually move the chosen piece.
     * Return: true if the piece can be moved, false otherwise.
     */
    boolean checkIfCanMove(Position toPosition);

    Colour getColour();

    /**
     * This method checks if a position is on the board (limits of the board are lines 0 and 7 and columns 0 and 7).
     * Return: true if the coordinates of position ('x' and 'y') are between board's limits, false otherwise.
     */
    default boolean isValid(Position position){
        return (position.getX()>=0 && position.getX()<=7 &&
                position.getY()>=0 && position.getY()<=7);
    }

    Position getPosition();
    void setPosition(Position position);
}
