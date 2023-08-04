package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

public class Knight implements Piece{
    private Colour colour;
    private String name;
    private Position position;

    public Knight(Colour colour, Position position) {
        this.name = "Knight";
        this.colour = colour;
        this.position = position;
    }

    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(Piece.super.isValid(toPosition)){
            if (Board.getPiece(this.position).getColour()==Colour.BLACK) {
                if ((Math.abs(this.position.getY() - toPosition.getY()) == 2) && (Math.abs(this.position.getX() - toPosition.getX()) == 1)) {
                    if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()==Colour.WHITE){
                        return true;
                    } else if (Board.getPiece(toPosition)==null) {
                        return true;
                    }
                } else if ((Math.abs(this.position.getX() - toPosition.getX()) == 2) && (Math.abs(this.position.getY() - toPosition.getY()) == 1)) {
                    if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()==Colour.WHITE){
                        return true;
                    } else if (Board.getPiece(toPosition)==null) {
                        return true;
                    }
                }
            } else {
                if ((Math.abs(this.position.getY() - toPosition.getY()) == 2) && (Math.abs(this.position.getX() - toPosition.getX()) == 1)) {
                    if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()==Colour.BLACK){
                        return true;
                    } else if (Board.getPiece(toPosition)==null) {
                        return true;
                    }
                } else if ((Math.abs(this.position.getX() - toPosition.getX()) == 2) && (Math.abs(this.position.getY() - toPosition.getY()) == 1)) {
                    if (Board.getPiece(toPosition)!=null && Board.getPiece(toPosition).getColour()==Colour.BLACK){
                        return true;
                    } else if (Board.getPiece(toPosition)==null) {
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
            return "\u265E";
        } else {
            return "\u2658";
        }
    }
}