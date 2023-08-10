package board;

import helper.Colour;
import helper.Input;
import helper.Position;
import pieces.*;
import java.util.*;

public class Board {
    private static Piece[][] chessBoard;
    private List<Piece> whites = new LinkedList<>(); //used to keep track for white pieces removed
    private List<Piece> blacks = new LinkedList<>(); //used to keep track for black pieces removed
    private King blackKing; //used for knowing the current position of the King
    private King whiteKing; //used for knowing the current position of the King
    private boolean end = false;

    /**
     * This constructor is used to create a new chess board and to set a new reference for each King on the board.
     * The two references are useful because the program doesn't have to search for Kings' positions at each verification
     * for a possible check.
     */
    public Board() {
        chessBoard = new Piece[8][8];
        loadBoard();
        blackKing = (King)getPiece(new Position(0,4));
        whiteKing = (King)getPiece(new Position(7,4));
    }

    /**
     * This constructor is used for JUNIT to load a predefined board for tests.
     */
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

    /**
     * This method is used to initialize the chess board with pieces in standard positions for a new game.
     */
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

    /**
     * This method is used to perform the move that a player wants. It receives the initial position (oldPosition), the
     * destination position (newPosition) and the colour for the player that has to move a piece.
     * The method checks if:
     *  1) the player is allowed to move the piece (eq.: when it is white's turn to move, the player can only move white pieces);
     *  2) at the initial position exists a piece that can be moved;
     *  3) the move doesn't get the current player's King in check;
     *  4) the move can be actually performed (using 'checkIfCanMove' overrode method);
     *  5) the move brings the opponent's King in check (using 'isEnemyChecked' method);
     *  6) the move gets check mate to the opponent's King (using 'isCheckMate' method);
     *  7) the game will be a draw if the current move will be performed;
     *  8) the moved piece is a Pawn, and it can shape-shift (using 'shapeShift' method);
     *  9) the chosen piece is the King and the player wants to castle (using 'castle' method);
     *  Return: true if the move was performed, false otherwise.
     */
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
            return castle((King)piece, oldPosition, newPosition);
        }
        return false;
    }

    /**
     * It checks if the King is moved two squares to the right or to the left side.
     * This method performs the moves for a castle if the result of 'canCastle' method is 'true'.
     * Return: true if the castle was performed, false otherwise.
     */
    private boolean castle(King king, Position oldPosition, Position newPosition){
        if (Math.abs(newPosition.getY() - oldPosition.getY())==2){
            if (king.canCastle(newPosition)){
                chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
                chessBoard[newPosition.getX()][newPosition.getY()] = king;
                king.setPosition(newPosition);
                king.setWasMoved(true);
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

    /**
     * This method checks if the piece that was moved gets the rival's King in check.
     * Return: true if the opponent's King will be in check, false otherwise.
     */
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

    /**
     * This method is used to check if a player has won.
     * It checks if the King inserted as a parameter can get out of check:
     *  - if the King is in check from more than one piece, the only possibility is to check if the King itself can be
     *  moved into a place where it is no longer threatened by an enemy piece.
     *  - if the King is in check from one piece, this method checks:
     *      1) if there is a piece that can be moved on the path between the King and the piece which threatens it;
     *      2) if there is a piece that can take out the piece that attacks the King;
     *      3) if the King can be moved in a place where it is no longer in check.
     * Return: true if the opponent's King can't get out of check, false otherwise.
     */
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

    /**
     * When the King is in check, this method is used to see if there is a piece that can be moved on any square on
     * the path between the King and the enemy piece that threatens the King (including the enemy piece's position).
     * Return: true if it has found a piece that can be moved to the inserted position.
     */
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

    /**
     * This method checks if a Pawn has reached the last row on the opposite side, so the piece can shape-shift into any
     * other piece (except the King and other Pawn).
     * Also, the method checks if the movement of the Pawn on the last row gets the King in check from any enemy piece.
     * If the Pawn can be moved to the last row, it will be changed with a new piece that will be chosen by the player.
     * Return: true if the Pawn was shape-shifted into other piece, false otherwise.
     */
    private boolean shapeShift(Piece piece, King king, Position oldPosition, Position newPosition){
        chessBoard[oldPosition.getX()][oldPosition.getY()] = null;
        Piece oldPiece = piece;
        List<String> possibilities = Arrays.asList("queen", "bishop", "rook", "knight");
        System.out.println("Choose a piece to replace the pawn:");
        String input = Input.getInstance().getPiece();
        while (!possibilities.contains(input.trim().toLowerCase())){
            System.out.println("Wrong input!");
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
                Piece piece = chessBoard[i][j];
                if ((i+j)%2==0 && piece!=null){
                    if (piece.getColour() == Colour.BLACK){
                        System.out.printf("\u001B[31m\u001B[46m %s \u001B[0m", chessBoard[i][j]);
                    } else {
                        System.out.printf("\u001B[47m\u001B[46m %s \u001B[0m", chessBoard[i][j]);
                    }
                } else if ((i+j)%2==0){
                    System.out.printf("\u001B[36m\u001B[46m %s \u001B[0m", "\u2659");
                } else if ((i+j)%2!=0 && piece!=null) {
                    if (piece.getColour() == Colour.BLACK){
                        System.out.printf("\u001B[31m\u001B[40m %s \u001B[0m", chessBoard[i][j]);
                    } else {
                        System.out.printf("\u001B[37m\u001B[40m %s \u001B[0m", chessBoard[i][j]);
                    }
                } else if ((i+j)%2!=0) {
                    System.out.printf("\u001B[30m\u001B[40m %s \u001B[0m", "\u2659");
                }
            }
            System.out.println();
        }
        System.out.println("White pieces removed: " + whites);
        System.out.println("Red pieces removed: " + blacks);
        System.out.println("-----------------------------");
    }
}
