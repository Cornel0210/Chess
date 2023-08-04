package board;


import helper.Colour;
import helper.Input;
import helper.Position;
import pieces.*;

import java.util.*;

public class Board {
    private static Piece[][] chessBoard;
    private List<Piece> whites = new LinkedList<>();
    private List<Piece> blacks = new LinkedList<>();
    private King blackKing;
    private King whiteKing;
    private boolean end = false;
    public Board() {
        chessBoard = new Piece[8][8];
        loadBoard();
        blackKing = (King)getPiece(new Position(0,4));
        whiteKing = (King)getPiece(new Position(7,4));
    }

    public Board(Piece[][] chessBoard) {
        Board.chessBoard = chessBoard;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (chessBoard[i][j] instanceof King){
                    if (chessBoard[i][j].getColour() == Colour.BLACK){
                        blackKing = (King) getPiece(new Position(i,j));
                    } else {
                        whiteKing = (King) getPiece(new Position(i,j));
                    }
                }
            }
        }
    }

    private void loadBoard(){
        for (int i = 0; i < chessBoard.length; i++) {
            chessBoard[1][i] = new Pawn(Colour.BLACK, new Position(1, i));
            chessBoard[chessBoard.length-2][i] = new Pawn(Colour.WHITE, new Position(chessBoard.length-2, i));
        }
        //important black pieces
        chessBoard[0][0] = new Rook(Colour.BLACK, new Position(0,0));
        chessBoard[0][chessBoard.length-1] = new Rook(Colour.BLACK, new Position(0, chessBoard.length-1));
        chessBoard[0][1] = new Knight(Colour.BLACK, new Position(0,1));
        chessBoard[0][chessBoard.length-2] = new Knight(Colour.BLACK, new Position(0, chessBoard.length-2));
        chessBoard[0][2] = new Bishop(Colour.BLACK, new Position(0,2));
        chessBoard[0][chessBoard.length-3] = new Bishop(Colour.BLACK, new Position(0, chessBoard.length-3));
        chessBoard[0][3] = new Queen(Colour.BLACK, new Position(0,3));
        chessBoard[0][4] = new King(Colour.BLACK, new Position(0,4));

        //important white pieces
        chessBoard[chessBoard.length-1][0] = new Rook(Colour.WHITE, new Position(chessBoard.length-1,0));
        chessBoard[chessBoard.length-1][chessBoard.length-1] = new Rook(Colour.WHITE, new Position(chessBoard.length-1, chessBoard.length-1));
        chessBoard[chessBoard.length-1][1] = new Knight(Colour.WHITE, new Position(chessBoard.length-1,1));
        chessBoard[chessBoard.length-1][chessBoard.length-2] = new Knight(Colour.WHITE, new Position(chessBoard.length-1, chessBoard.length-2));
        chessBoard[chessBoard.length-1][2] = new Bishop(Colour.WHITE, new Position(chessBoard.length-1,2));
        chessBoard[chessBoard.length-1][chessBoard.length-3] = new Bishop(Colour.WHITE, new Position(chessBoard.length-1, chessBoard.length-3));
        chessBoard[chessBoard.length-1][3] = new Queen(Colour.WHITE, new Position(chessBoard.length-1,3));
        chessBoard[chessBoard.length-1][4] = new King(Colour.WHITE, new Position(chessBoard.length-1,4));
    }

    public boolean movePiece(Position oldPosition, Position newPosition, Colour playersColour){
        Piece piece = getPiece(oldPosition);
        if (piece!=null && piece.getColour() != playersColour){
            return false;
        }
        if (piece!=null && piece.checkIfCanMove(newPosition)) {
            Piece removedPiece = getPiece(newPosition);
            if (piece.getColour()==Colour.BLACK){
                if (piece instanceof King) { //the move that you want to perform gets you in chess=> not allowed
                    if (blackKing.isUnderCheck(newPosition)) {
                        return false;
                    } else {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                        chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                        blackKing.setPosition(newPosition);
                        if (blacks.size()==15) {
                            blackKing.addMove();
                            if (blackKing.isDraw()){
                                System.out.println("--->Draw<---");
                                end = true;
                                return true;
                            }
                        }
                    }
                } else if (piece instanceof Pawn){ //checks if pawn can shape-shift
                    if (newPosition.getX()==7) {
                        if (!shapeShift(piece, blackKing, oldPosition, newPosition)){
                            return false;
                        }
                    } else {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                        chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                        piece.setPosition(newPosition);
                        if (blackKing.isUnderCheck()) {
                            chessBoard[oldPosition.getX()][oldPosition.getY()] = piece;
                            if (removedPiece != null){
                                chessBoard[newPosition.getX()][newPosition.getY()] = removedPiece;
                            } else {
                                chessBoard[newPosition.getX()][newPosition.getY()] = null;
                            }
                            piece.setPosition(oldPosition);
                            return false;
                        }
                    }
                } else {
                    chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                    chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                    piece.setPosition(newPosition);
                    if (blackKing.isUnderCheck()) {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = piece;
                        if (removedPiece != null){
                            chessBoard[newPosition.getX()][newPosition.getY()] = removedPiece;
                        } else {
                            chessBoard[newPosition.getX()][newPosition.getY()] = null;
                        }
                        piece.setPosition(oldPosition);
                        return false;
                    }
                }
            } else if (piece.getColour()==Colour.WHITE) {
                if (piece instanceof King) {
                    if (whiteKing.isUnderCheck(newPosition)) {
                        return false;
                    } else {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                        chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                        whiteKing.setPosition(newPosition);
                        if (whites.size()==15) {
                            whiteKing.addMove();
                            if (whiteKing.isDraw()){
                                System.out.println("--->Draw<---");
                                end=true;
                                return true;
                            }
                        }
                    }
                } else if (piece instanceof Pawn) { //checks if pawn can shape-shift
                    if (newPosition.getX() == 0) {
                        if (!shapeShift(piece, whiteKing, oldPosition, newPosition)) {
                            return false;
                        }
                    } else {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                        chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                        piece.setPosition(newPosition);
                        if (whiteKing.isUnderCheck()) {
                            chessBoard[oldPosition.getX()][oldPosition.getY()] = piece;
                            if (removedPiece != null){
                                chessBoard[newPosition.getX()][newPosition.getY()] = removedPiece;
                            } else {
                                chessBoard[newPosition.getX()][newPosition.getY()] = null;
                            }
                            piece.setPosition(oldPosition);
                            return false;
                        }
                    }
                } else {
                    chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                    chessBoard[newPosition.getX()][newPosition.getY()] = piece;
                    piece.setPosition(newPosition);
                    if (whiteKing.isUnderCheck()) {
                        chessBoard[oldPosition.getX()][oldPosition.getY()] = piece;
                        if (removedPiece != null){
                            chessBoard[newPosition.getX()][newPosition.getY()] = removedPiece;
                        } else {
                            chessBoard[newPosition.getX()][newPosition.getY()] = null;
                        }
                        piece.setPosition(oldPosition);
                        return false;
                    }
                }
            }
            if (removedPiece!=null && !getPiece(newPosition).equals(removedPiece)) {
                if (removedPiece.getColour()==Colour.BLACK) {
                    blacks.add(removedPiece);
                } else whites.add(removedPiece);
            }

            if (piece instanceof King){ //can`t castle anymore
                ((King) piece).setWasMoved(true);
            } else if (piece instanceof Rook){
                ((Rook) piece).setWasMoved(true);
            }

            if (isEnemyChecked(piece)) {//the move that you want to perform gets enemy in chess=> allowed
                if (piece.getColour()==Colour.BLACK){
                    if (isCheckMate(whiteKing)) { //checks if the game continues
                        System.out.println("Checkmate for " + Colour.WHITE);
                    }
                } else {
                    if (isCheckMate(blackKing)) { //checks if the game continues
                        System.out.println("Checkmate for " + Colour.BLACK);
                    }
                }

            } else {
                if (piece.getColour()==Colour.BLACK){
                    if (whites.size() == 15 && !hasMoves(whiteKing)){
                        System.out.println("--->Draw<---");
                        end = true;
                    }
                } else {
                    if (blacks.size() == 15 && !hasMoves(blackKing)){
                        System.out.println("--->Draw<---");
                        end = true;
                    }
                }
            }

            return true;
        } else if (piece instanceof King){
            return checkForCastling((King)piece, oldPosition, newPosition);
        }
        return false;
    }
    private boolean checkForCastling(King king, Position oldPosition, Position newPosition){
        if (Math.abs(newPosition.getY() - oldPosition.getY())==2){
            if (king.canCastle(newPosition)){
                chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                chessBoard[newPosition.getX()][newPosition.getY()] = king;
                king.setPosition(newPosition);
                if (newPosition.getY()>oldPosition.getY()){
                    Rook rook = (Rook)getPiece(new Position(newPosition.getX(), newPosition.getY()+1));
                    chessBoard[newPosition.getX()][newPosition.getY()-1] = rook;
                    chessBoard[rook.getPosition().getX()][rook.getPosition().getY()] = null;
                    return true;
                } else {
                    Rook rook = (Rook)getPiece(new Position(newPosition.getX(), newPosition.getY()-2));
                    chessBoard[newPosition.getX()][newPosition.getY()+1] = rook;
                    chessBoard[rook.getPosition().getX()][rook.getPosition().getY()] = null;
                }
                return true;
            }
        }
        return false;
    }

    private boolean isEnemyChecked(Piece piece){
        if (piece.getColour()==Colour.BLACK){ //the move that you want to perform gets enemy in chess=> allowed
            if (whiteKing.isUnderCheck()){
                System.out.println("Check to " + whiteKing + " from " + whiteKing.getLastCheckedPieces());
                return true;
            } else {
                return false;
            }
        } else if (piece.getColour()==Colour.WHITE) {
            if (blackKing.isUnderCheck()) {
                System.out.println("Check to " + blackKing + " from " + blackKing.getLastCheckedPieces());
                return true;
            }
        }
        return false;
    }

    private boolean isCheckMate(King king){
        Colour colour = king.getColour();
        int x = king.getPosition().getX();
        int y = king.getPosition().getY();
        Position tempPosition;

        //if the king is in check from more than one piece, it can only continue if it has available moves for king
        // otherwise, there will be check mate
        if (king.getLastCheckedPieces().size()>1){
            return !hasMoves(king);
        }
        if (hasMoves(king)){
            return false;
        }

        tempPosition = king.getLastCheckedPieces().get(0).getPosition();
        int tempX = tempPosition.getX();
        int tempY = tempPosition.getY();
        while (tempX > x && tempY > y){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempX--;
            tempY--;
        }
        tempX = tempPosition.getX();
        tempY = tempPosition.getY();
        while (tempX > x && tempY < y) {
            if (canDefend(colour, new Position(tempX, tempY))) {
                return false;
            }
            tempX--;
            tempY++;
        }
        tempX = tempPosition.getX();
        tempY = tempPosition.getY();
        while (tempX < x && tempY > y) {
            if (canDefend(colour, new Position(tempX, tempY))) {
                return false;
            }
            tempX++;
            tempY--;
        }
        tempX = tempPosition.getX();
        tempY = tempPosition.getY();
        while (tempX < x && tempY < y){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempX++;
            tempY++;
        }
        tempX = tempPosition.getX();
        tempY = tempPosition.getY();
        while (tempX < x && tempY == y){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempX++;
        }
        tempX = tempPosition.getX();
        while (tempX > x && tempY == y){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempX--;
        }
        tempX = tempPosition.getX();
        tempY = tempPosition.getY();
        while (tempY < y && tempX == x){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempY++;
        }
        tempY = tempPosition.getY();
        while (tempY > y && tempX == x){
            if (canDefend(colour, new Position(tempX, tempY))){
                return false;
            }
            tempY--;
        }
        end = true;
        return true;
    }
    private boolean hasMoves(King king){
        int x = king.getPosition().getX();
        int y = king.getPosition().getY();
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, 7); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, 7); j++) {
                if (king.checkIfCanMove(new Position(i,j))){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canDefend(Colour colour, Position position){
        int tempX = position.getX()-1;
        int tempY = position.getY();
        Piece piece;
        while (tempX>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX--;
        }
        if (tempX>=0){ // found a piece
            piece = getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX()+1;
        while (tempX<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX++;
        }
        if (tempX<=7){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX();
        tempY = position.getY()-1;
        while (tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempY--;
        }
        if (tempY>=0){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempY = position.getY()+1;
        while (tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempY++;
        }
        if (tempY<=7){ // found a piece
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Rook ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX()-1;
        tempY = position.getY()-1;
        while (tempX>=0 && tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX--;
            tempY--;
        }
        if (tempX>=0 && tempY>=0){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX()-1;
        tempY = position.getY()+1;
        while (tempX>=0 && tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX--;
            tempY++;
        }
        if (tempX>=0 && tempY<=7){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX()+1;
        tempY = position.getY()-1;
        while (tempX<=7 && tempY>=0 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX++;
            tempY--;
        }
        if (tempX<=7 && tempY>=0){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()+1;
        while (tempX<=7 && tempY<=7 &&
                (Board.getPiece(new Position(tempX, tempY)) == null)){
            tempX++;
            tempY++;
        }
        if (tempX<=7 && tempY<=7){
            piece = Board.getPiece(new Position(tempX,tempY));
            if (piece.getColour()==colour) {
                if (piece instanceof Bishop ||
                        piece instanceof Queen){
                    if (piece.checkIfCanMove(position)) {
                        return true;
                    }
                }
            }
        }

        tempX = position.getX()-2;
        tempY = position.getY()-1;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()+2;
        tempY = position.getY()-1;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()-2;
        tempY = position.getY()+1;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()+2;
        tempY = position.getY()+1;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()-1;
        tempY = position.getY()+2;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()-1;
        tempY = position.getY()-2;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()+2;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }
        tempX = position.getX()+1;
        tempY = position.getY()-2;
        if (isValid(new Position(tempX,tempY))) {
            piece = Board.getPiece(new Position(tempX, tempY));
            if (piece instanceof Knight && piece.getColour() == colour) {
                if (piece.checkIfCanMove(position)) {
                    return true;
                }
            }
        }

        Position tempPosition = new Position(position.getX()-1, position.getY());
        Piece tempPiece = getPiece(tempPosition);
        if (tempPiece instanceof Pawn && tempPiece.getColour()==colour){
            if (tempPiece.checkIfCanMove(position)) {
                return true;
            }
        }

        return false;
    }
    private boolean isValid(Position position){
        return (position.getX()>=0 && position.getX()<=7 &&
                position.getY()>=0 && position.getY()<=7);
    }
    private boolean shapeShift(Piece piece, King king, Position oldPosition, Position newPosition){
        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
        Piece oldPiece = piece;
        List<String> possibilities = Arrays.asList("queen", "bishop", "rook", "knight");
        System.out.println("Choose a piece to replace the pawn");
        String input = Input.getInstance().getPiece();
        while (!possibilities.contains(input.trim().toLowerCase())){
            System.out.println("Wrong input");
            input = Input.getInstance().getPiece();
        }
        switch (input.trim().toLowerCase()){
            case "queen":
                piece = new Queen(oldPiece.getColour(), newPosition);
                break;
            case "bishop":
                piece = new Bishop(oldPiece.getColour(), newPosition);
                break;
            case "rook":
                piece = new Rook(oldPiece.getColour(), newPosition);
                break;
            case "knight":
                piece = new Knight(oldPiece.getColour(), newPosition);
                break;
        }

        chessBoard[newPosition.getX()][newPosition.getY()] = piece;
        if (king.isUnderCheck(king.getPosition())) {
            piece = oldPiece;
            chessBoard[oldPosition.getX()][oldPosition.getY()] = piece;
            chessBoard[newPosition.getX()][newPosition.getY()] = null;
            piece.setPosition(oldPosition);
            return false;
        }
        return true;
    }

    public static Piece[][] getChessBoard() {
        return chessBoard;
    }

    public static Piece getPiece (Position position){
        return chessBoard[position.getX()][position.getY()];
    }

    public boolean isEnd() {
        return end;
    }

    public void setWhites(List<Piece> whites) {
        this.whites = whites;
    }

    public void setBlacks(List<Piece> blacks) {
        this.blacks = blacks;
    }

    public void printChessBoard(){
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if ((i+j)%2==0 && chessBoard[i][j]!=null){
                    System.out.printf("\033[44m%4s\033[0m", chessBoard[i][j]);
                } else if ((i+j)%2==0){
                    System.out.printf("\033[44m%4s\033[0m", "     ");
                } else if ((i+j)%2!=0 && chessBoard[i][j]!=null) {
                    System.out.printf("\033[47m%4s\033[0m", chessBoard[i][j]);
                } else if ((i+j)%2!=0) {
                    System.out.printf("\033[47m%4s\033[0m", "     ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(whites);
        System.out.println(blacks);
    }
}
