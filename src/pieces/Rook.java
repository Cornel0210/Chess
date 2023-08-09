package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Rook implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;
    private boolean wasMoved = false; //track for castling
    public Rook(Colour colour, Position position) {
        this.name = "Rook";
        this.colour = colour;
        this.position = position;
    }

    /**
     * This method checks if the destination position is on the same row or column and there are no other pieces on the
     * path. Moreover, it checks if the destination position is free or occupied by an enemy piece.
     * Return: true if the Rook can be moved, false otherwise.
     */
    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(Piece.super.isValid(toPosition)){
            if (this.position.getX()==toPosition.getX()){ //moving on the same lane
                if (this.position.getY() < toPosition.getY()){ //moving to the right
                    for (int i = this.position.getY()+1; i < toPosition.getY(); i++) {
                        if (Board.getPiece(new Position(this.position.getX(), i))!=null){
                            return false;
                        }
                    }
                    if (Board.getPiece(toPosition)!=null &&
                            (Board.getPiece(this.position).getColour() == Board.getPiece(toPosition).getColour())){
                        return false;
                    } else {
                        return true;
                    }
                } else if (this.position.getY() > toPosition.getY()) { //moving to the left
                    for (int i = this.position.getY()-1; i > toPosition.getY(); i--) {
                        if (Board.getPiece(new Position(this.position.getX(), i))!=null){
                            return false;
                        }
                    }
                    if (Board.getPiece(toPosition)!=null &&
                            (Board.getPiece(this.position).getColour() == Board.getPiece(toPosition).getColour())){
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                if (this.position.getY() == toPosition.getY()) { //moving on the same column
                    if (this.position.getX() < toPosition.getX()){ //moving down
                        for (int i = this.position.getX()+1; i < toPosition.getX(); i++) {
                            if (Board.getPiece(new Position(i, this.position.getY()))!=null){
                                return false;
                            }
                        }
                        if (Board.getPiece(toPosition)!=null &&
                                (Board.getPiece(this.position).getColour() == Board.getPiece(toPosition).getColour())){
                            return false;
                        } else {
                            return true;
                        }
                    } else if (this.position.getX() > toPosition.getX()) { //moving up
                        for (int i = this.position.getX()-1; i > toPosition.getX(); i--) {
                            if (Board.getPiece(new Position(i, this.position.getY()))!=null){
                                return false;
                            }
                        }
                        if (Board.getPiece(toPosition)!=null &&
                                (Board.getPiece(this.position).getColour() == Board.getPiece(toPosition).getColour())){
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean wasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
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
            return "\u265C";
        } else {
            return "\u2656";
        }
    }
}