package board;

import helper.Colour;
import helper.Position;
import pieces.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    private static Board chessBoard;
    private final static Piece[][] board = new Piece[8][8];

    @org.junit.Before
    public void before(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        board[0][4] = new King(Colour.BLACK, new Position(0,4));
        board[7][4] = new King(Colour.WHITE, new Position(7,4));
        chessBoard = new Board(board);
    }
    @org.junit.Test
    public void isChecked() {
        board[4][4] = new Queen(Colour.BLACK, new Position(4,4));
        board[0][4] = new King(Colour.BLACK, new Position(0,4));
        board[7][4] = new King(Colour.WHITE, new Position(7,4));

        King whiteKing = (King)(Board.getPiece(new Position(7,4)));
        assertTrue(whiteKing.isUnderCheck());
    }

    @org.junit.Test
    public void movePiece() {
        board[0][3] = new Rook(Colour.BLACK, new Position(0,3));
        board[0][4] = new King(Colour.BLACK, new Position(0,4));
        board[0][5] = new Rook(Colour.BLACK, new Position(0,5));
        board[7][4] = new King(Colour.WHITE, new Position(7,4));

        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,3), Colour.WHITE));
    }

    @org.junit.Test
    public void isEnd() {
        chessBoard.movePiece(new Position(7,4), new Position(7,5), Colour.WHITE);
        chessBoard.movePiece(new Position(7,5), new Position(7,6), Colour.WHITE);
        chessBoard.movePiece(new Position(7,6), new Position(7,7), Colour.WHITE);
        board[0][5] = new Queen(Colour.BLACK, new Position(0,5));
        List<Piece> list = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Pawn(Colour.WHITE, new Position(0,0)));
        }
        chessBoard.setWhites(list);
        chessBoard.movePiece(new Position(0,5), new Position(6,5), Colour.BLACK);

        assertTrue(chessBoard.isEnd());
    }

    @org.junit.Test
    public void leftCastle() {
        board[7][0] = new Rook(Colour.WHITE, new Position(7,0));
        assertTrue(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void leftCastle2() {
        board[7][0] = new Rook(Colour.WHITE, new Position(7,0));
        chessBoard.movePiece(new Position(7,4), new Position(7,5), Colour.WHITE);
        chessBoard.movePiece(new Position(7,5), new Position(7,4), Colour.WHITE);
        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void leftCastle3() {
        board[7][0] = new Rook(Colour.WHITE, new Position(7,0));
        chessBoard.movePiece(new Position(7,0), new Position(6,0), Colour.WHITE);
        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void leftCastle4() {
        board[7][0] = new Bishop(Colour.WHITE, new Position(7,0));
        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void leftCastle5() {
        board[7][0] = new Rook(Colour.BLACK, new Position(7,0));
        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void leftCastle6() {
        board[7][0] = new Rook(Colour.WHITE, new Position(7,0));
        board[2][7] = new Bishop(Colour.BLACK, new Position(2,7));
        assertFalse(chessBoard.movePiece(new Position(7,4), new Position(7,2), Colour.WHITE));
    }
    @org.junit.Test
    public void checkMate() {
        board[3][7] = new Bishop(Colour.BLACK, new Position(3,7));
        board[5][5] = new Queen(Colour.BLACK, new Position(5,5));
        board[6][0] = new Rook(Colour.WHITE, new Position(6,0));
        chessBoard.movePiece(new Position(5,5), new Position(6,4), Colour.BLACK);

        assertFalse(chessBoard.isEnd());
    }
    @org.junit.Test
    public void checkMate2() {
        board[3][7] = new Bishop(Colour.BLACK, new Position(3,7));
        board[5][5] = new Queen(Colour.BLACK, new Position(5,5));
        chessBoard.movePiece(new Position(5,5), new Position(6,4), Colour.BLACK);

        assertTrue(chessBoard.isEnd());
    }
    @org.junit.Test
    public void checkMate3() {
        board[5][5] = new Queen(Colour.BLACK, new Position(5,5));
        chessBoard.movePiece(new Position(5,5), new Position(5,4), Colour.BLACK);
        assertTrue(chessBoard.movePiece(new Position(7,4), new Position(7,3), Colour.WHITE));
    }
    @org.junit.Test
    public void move() {
        board[1][4] = new Pawn(Colour.BLACK, new Position(1,4));
        board[2][3] = new Pawn(Colour.WHITE, new Position(2,3));
        board[6][4] = new Rook(Colour.WHITE, new Position(6,4));
        assertFalse(chessBoard.movePiece(new Position(1,4), new Position(2,3), Colour.BLACK));
    }


    @org.junit.After
    public void after(){
        chessBoard.printChessBoard();
    }
}