package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Pawn implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;

    public Pawn(Colour colour, Position position) {
        this.name = "Pawn";
        this.colour = colour;
        this.position = position;
    }

    /**
     * This method checks if the selected Pawn can be moved to the chosen position.
     * It checks if:
     *  1) the destination position is two squares forward, the Pawn was never moved before and there are no other
     *  pieces between its initial and final (inclusive) positions.
     *  2) the destination position is one square forward and the square is not occupied by other piece.
     *  3) the destination position is one square along forward diagonal, and it is occupied by an enemy piece.
     * Return: true if the move can be performed, false otherwise.
     */
    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(Piece.super.isValid(toPosition)){
            if (this.colour==Colour.BLACK){ //pawn can only go to down direction
                if (this.position.getX()>= toPosition.getX() || Math.abs(this.position.getX()) - toPosition.getX()>2) {
                    return false;
                }
                if (this.position.getY() == toPosition.getY()){ // want to move ONE square forward
                    if (Math.abs(toPosition.getX()-this.position.getX())==1 && Board.getPiece(toPosition)!=null){
                        return false;
                    }
                    else if (Math.abs(toPosition.getX()-this.position.getX())==2 && this.position.getX() !=1){  //want to move TWO squares forward
                        return false;
                    } else if (this.position.getX() ==1 && Math.abs(toPosition.getX()-this.position.getX())==2){
                        if (Board.getPiece(toPosition)!=null || //intermediary position
                                Board.getPiece(new Position(toPosition.getX()-1, toPosition.getY()))!=null){
                            return false;
                        }
                    }
                    return true;
                } else if (toPosition.getX()-this.position.getX()==1 && //take an enemy piece
                        Math.abs(this.position.getY()-toPosition.getY())==1 && Board.getPiece(toPosition)!=null
                        && Board.getPiece(toPosition).getColour()==Colour.WHITE){
                    return true;
                } else {
                    return false;
                }
            } else { //pawn can only go to up direction
                if (this.position.getX()<= toPosition.getX() ||
                        Math.abs(this.position.getX()) - toPosition.getX()>2){
                    return false;
                }
                if (this.position.getY() == toPosition.getY()){ // want to move ONE square forward
                    if (Math.abs(toPosition.getX()-this.position.getX())==1 && Board.getPiece(toPosition)!=null){
                        return false;
                    }
                    else if (Math.abs(toPosition.getX()-this.position.getX())==2 &&  this.position.getX() !=6) {//want to move TWO squares forward
                        return false;
                    } else if (this.position.getX() ==6 && Math.abs(toPosition.getX()-this.position.getX())==2){
                        if (Board.getPiece(toPosition)!=null ||
                                Board.getPiece(new Position(toPosition.getX()+1, toPosition.getY()))!=null) { //intermediary position
                            return false;
                        }
                    }
                    return true;
                } else if (this.position.getX()-toPosition.getX()==1 && //take an enemy piece
                        Math.abs(this.position.getY()-toPosition.getY())==1 && Board.getPiece(toPosition)!=null
                        && Board.getPiece(toPosition).getColour()==Colour.BLACK){
                    return true;
                } else {
                    return false;
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
        return "\u2659";
    }
}
