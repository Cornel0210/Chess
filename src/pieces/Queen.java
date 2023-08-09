package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Queen implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;

    public Queen(Colour colour, Position position) {
        this.name = "Queen";
        this.colour = colour;
        this.position = position;
    }

    /**
     * This method checks if:
     *  1) the destination position is on the same row or column and there are no intervening pieces,
     *  2) the destination position is along the Queen's diagonal and there are no intervening pieces.
     * Additionally, it checks if the destination position is free of occupied by a rival's piece.
     * Return: true if the move can be performed, false otherwise.
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
            } else if (fromX==toX && fromY == toY){
                return false;
            } else if (Math.abs(fromX - toX) == Math.abs(fromY - toY)){
                if (fromX > toX && fromY > toY){
                    int tempX = fromX-1;
                    int tempY = fromY-1;
                    while (tempX>toX && tempY > toY && Board.getPiece(new Position(tempX, tempY))==null){
                        tempX--;
                        tempY--;
                    }
                    if (tempX==toX){
                        return true;
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
                    }
                }
            }

            if (fromX==toX){ //moving on the same lane
                if (fromY < toY){ //moving to the right
                    for (int i = fromY+1; i < toY; i++) {
                        if (Board.getPiece(new Position(fromX, i))!=null){
                            return false;
                        }
                    }
                    return true;
                } else if (fromY > toY) { //moving to the left
                    for (int i = fromY-1; i > toY; i--) {
                        if (Board.getPiece(new Position(fromX, i))!=null){
                            return false;
                        }
                    }
                    return true;
                }
            } else {
                if (fromY == toY) { //moving on the same column
                    if (fromX < toX){ //moving down
                        for (int i = fromX+1; i < toX; i++) {
                            if (Board.getPiece(new Position(i, fromY))!=null){
                                return false;
                            }
                        }
                        return true;
                    } else if (fromX > toX) { //moving up
                        for (int i = fromX - 1; i > toX; i--) {
                            if (Board.getPiece(new Position(i, fromY)) != null) {
                                return false;
                            }
                        }
                        return true;
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
            return "\u265B";
        } else {
            return "\u2655";
        }
    }
}