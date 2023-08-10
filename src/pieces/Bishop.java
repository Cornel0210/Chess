package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Bishop implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;

    public Bishop(Colour colour, Position position) {
        this.name = "Bishop";
        this.colour = colour;
        this.position = position;
    }

    /**
     * This method checks if the movement chosen by the player is valid:
     *  1) the movement is along the diagonal;
     *  2) there are no pieces between its current position and the destination position;
     *  3) the destination position is free or occupied by an opponent's piece.
     * Return: true if the move can be performed, false otherwise;
     */
    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(Piece.super.isValid(toPosition)){
            int fromX = this.position.getX();
            int fromY = this.position.getY();
            int toX = toPosition.getX();
            int toY = toPosition.getY();
            if (Board.getPiece(toPosition)!=null &&
                    Board.getPiece(toPosition).getColour() == Board.getPiece(this.position).getColour()){
                return false;
            } else if (Math.abs(fromX - toX) != Math.abs(fromY - toY)){
                return false;
            } else if (fromX==toX && fromY == toY){
                return false;
            } else {
                if (fromX > toX && fromY > toY){
                    int tempX = fromX-1;
                    int tempY = fromY-1;
                    while (tempX>toX && tempY > toY && Board.getPiece(new Position(tempX, tempY))==null){
                        tempX--;
                        tempY--;
                    }
                    if (tempX==toX){
                        return true;
                    } else {
                        return false;
                    }
                } else if (fromX > toX && fromY < toY){
                    int tempX = fromX-1;
                    int tempY = fromY+1;
                    while (tempX>toX && tempY < toY && Board.getPiece(new Position(tempX, tempY))==null){
                        tempX--;
                        tempY++;
                    }
                    if (tempX==toX){
                        return true;
                    } else {
                        return false;
                    }
                } else if (fromX < toX && fromY > toY){
                    int tempX = fromX+1;
                    int tempY = fromY-1;
                    while (tempX<toX && tempY > toY && Board.getPiece(new Position(tempX, tempY))==null){
                        tempX++;
                        tempY--;
                    }
                    if (tempX==toX){
                        return true;
                    } else {
                        return false;
                    }
                } else if (fromX < toX && fromY < toY){
                    int tempX = fromX+1;
                    int tempY = fromY+1;
                    while (tempX < toX && tempY < toY && Board.getPiece(new Position(tempX, tempY))==null){
                        tempX++;
                        tempY++;
                    }
                    if (tempX==toX){
                        return true;
                    } else {
                        return false;
                    }
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
            return "\u265D";
        } else {
            return "\u2657";
        }
    }
}