package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Knight implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;

    public Knight(Colour colour, Position position) {
        this.name = "Knight";
        this.colour = colour;
        this.position = position;
    }

    /**
     * As long as a Knight can be moved on 'L' shape (2 squares forward/backward and 1 square left/right or
     * 1 square forward/backward and 2 squares left/right), this method checks if the destination position is free or
     * occupied by an enemy piece (it does not matter if there are other pieces between the initial square and the
     * destination one).
     * Return: true if the move can be performed and false otherwise.
     */
    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(Piece.super.isValid(toPosition)){
            if ((Math.abs(this.position.getY() - toPosition.getY()) == 2) && (Math.abs(this.position.getX() - toPosition.getX()) == 1)) {
                if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()!=this.colour){
                    return true;
                } else if (Board.getPiece(toPosition)==null) {
                    return true;
                }
            } else if ((Math.abs(this.position.getX() - toPosition.getX()) == 2) && (Math.abs(this.position.getY() - toPosition.getY()) == 1)) {
                if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()!=this.colour){
                    return true;
                } else if (Board.getPiece(toPosition)==null) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        if (colour==Colour.BLACK){
            return "\u265E";
        } else {
            return "\u2658";
        }
    }
}