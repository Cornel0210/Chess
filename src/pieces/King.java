package pieces;

import board.Board;
import helper.Colour;
import helper.Position;

import java.util.LinkedList;
import java.util.List;

public class King implements Piece{
    private final Colour colour;
    private final String name;
    private Position position;
    private boolean wasMoved = false; //track for castling
    private final List<Piece> tempChecked;
    private final List<Piece> lastChecked;
    private int movesForDraw;

    public King(Colour colour, Position position) {
        this.name = "King";
        this.colour = colour;
        this.position = position;
        this.tempChecked = new LinkedList<>();
        this.lastChecked = new LinkedList<>();
        movesForDraw = 0;
    }

    @Override
    public boolean checkIfCanMove(Position toPosition) {
        if(isValid(toPosition)){
            int fromX = this.position.getX();
            int fromY = this.position.getY();
            int toX = toPosition.getX();
            int toY = toPosition.getY();
            if (Math.abs(fromX-toX)>1 || Math.abs(fromY-toY)>1){
                return false;
            }
            Piece toPiece = Board.getPiece(toPosition);
            if (toPiece!=null && toPiece.getColour()==this.colour){
                return false;
            }
            if (isUnderCheck(toPosition)){
                return false;
            }
        }
        return true;
    }

    public boolean isUnderCheck(){
        lastChecked.clear();
        boolean flag = isUnderCheck(this.position);
        lastChecked.addAll(tempChecked);
        return flag;
    }

    public boolean isUnderCheck(Position position){
        tempChecked.clear();
        int tempX = position.getX()-1;
        int tempY = position.getY();
        Piece piece;
        while (tempX>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX--;
        }
        if (tempX>=0){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX()+1;
        while (tempX<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX++;
        }
        if (tempX<=7){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX();
        tempY = position.getY()-1;
        while (tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempY--;
        }
        if (tempY>=0){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempY = position.getY()+1;
        while (tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempY++;
        }
        if (tempY<=7){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX()-1;
        tempY = position.getY()-1;
        while (tempX>=0 && tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX--;
            tempY--;
        }
        if (tempX>=0 && tempY>=0){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX()-1;
        tempY = position.getY()+1;
        while (tempX>=0 && tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX--;
            tempY++;
        }
        if (tempX>=0 && tempY<=7){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX()+1;
        tempY = position.getY()-1;
        while (tempX<=7 && tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX++;
            tempY--;
        }
        if (tempX<=7 && tempY>=0){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()+1;
        while (tempX<=7 && tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null || Board.getPiece(new Position(tempX, tempY)).equals(this))){
            tempX++;
            tempY++;
        }
        if (tempX<=7 && tempY<=7){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()!=this.getColour()) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    tempChecked.add(piece);
                }
            }
        }

        tempX = position.getX()-2;
        tempY = position.getY()-1;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()+2;
        tempY = position.getY()-1;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()-2;
        tempY = position.getY()+1;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()+2;
        tempY = position.getY()+1;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()-1;
        tempY = position.getY()+2;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()-1;
        tempY = position.getY()-2;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()+2;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()-2;
        if (Piece.super.isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() != this.getColour()) {
                tempChecked.add(piece);
            }
        }

        //checks for check from pawns
        tempX = position.getX();
        tempY = position.getY();
        Position tempPosition;
        for (int i = tempY-1; i <= tempY+1; i+=2) {
            tempPosition = this.colour==Colour.BLACK ? new Position(tempX+1, i) : new Position(tempX-1, i);
            if (Piece.super.isValid(tempPosition)){
                Piece tempPiece = Board.getPiece(tempPosition);
                if (tempPiece instanceof Pawn && tempPiece.getColour()!=this.colour){
                    tempChecked.add(tempPiece);
                }
            }
        }

        //checks for the other king
        for (int i = tempX-1; i <= tempX+1; i++) {
            for (int j = tempY-1; j <= tempY+1; j++) {
                tempPosition = new Position(i,j);
                if (Piece.super.isValid(tempPosition)){
                    Piece tempPiece = Board.getPiece(tempPosition);
                    if (tempPiece instanceof King && tempPiece.getColour()!=this.colour){
                        tempChecked.add(tempPiece);
                    }
                }
            }
        }

        return !tempChecked.isEmpty();
    }

    public boolean canCastle(Position toPosition) {
        if (!Piece.super.isValid(toPosition)){
            return false;
        }
        if (toPosition.equals(new Position(0,6)) ||
                toPosition.equals(new Position(7,6)) ||
                toPosition.equals(new Position(0,2)) ||
                toPosition.equals(new Position(7,2))) {
            if (wasMoved || lastChecked.size()!=0) {
                return false;
            }
            if (toPosition.getY() > this.position.getY()) {
                for (int i = this.position.getY() + 1; i <= toPosition.getY(); i++) {
                    Position tempPosition = new Position(this.position.getX(), i);
                    if (Board.getPiece(tempPosition) != null || isUnderCheck(tempPosition)) {
                        return false;
                    }
                }
                Piece piece;
                if (this.colour == Colour.BLACK) {
                    piece = Board.getPiece(new Position(0, 7));
                } else {
                    piece = Board.getPiece(new Position(7, 7));
                }
                if (piece instanceof Rook){
                    Rook rook = (Rook) piece;
                    if (rook.wasMoved()) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                for (int i = this.position.getY() - 1; i >= toPosition.getY(); i--) {
                    Position tempPosition = new Position(this.position.getX(), i);
                    if (Board.getPiece(tempPosition) != null || isUnderCheck(tempPosition)) {
                        return false;
                    }
                }
                Piece piece;
                if (this.colour == Colour.BLACK) {
                    piece = Board.getPiece(new Position(0, 0));
                } else {
                    piece = Board.getPiece(new Position(7, 0));
                }
                if (piece instanceof Rook){
                    Rook rook = (Rook) piece;
                    if (rook.wasMoved()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void addMove (){
        movesForDraw++;
    }
    public boolean isDraw(){
        return movesForDraw == 27;
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

    public List<Piece> getLastCheckedPieces() {
        return lastChecked;
    }

    @Override
    public void setPosition(Position position) {
        this.position = new Position(position.getX(), position.getY());
    }
    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public String toString() {
        if (colour==Colour.BLACK){
            return "\u265A";
        } else {
            return "\u2654";
        }
    }
}