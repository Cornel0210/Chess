package game;

import board.Board;
import helper.Colour;
import helper.Position;
import helper.SaveFile;
import player.HumanPlayer;
import player.Player;

import java.io.IOException;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private boolean isWhite = true;
    private final StringBuilder whiteHistory;
    private final StringBuilder blackHistory;

    public Game() {
        this.board = new Board();
        this.player1 = new HumanPlayer(Colour.WHITE);
        this.player2 = new HumanPlayer(Colour.BLACK);
        whiteHistory = new StringBuilder("White`s moves:\n");
        blackHistory = new StringBuilder("Red`s moves:\n");
    }

    public void run() {
        board.printChessBoard();
        System.out.println("Good luck!");
        System.out.println("Insert the position of the piece you want to move (allowed format is 'x-y')");
        while (!board.isEnd()){
            Player current = isWhite ? player1 : player2;
            if (isWhite){
                System.out.println("White`s turn to move.");
            } else {
                System.out.println("Red`s turn to move.");
            }
            Position from = current.move();
            Position to = current.move();
            while (!board.movePiece(from, to, current.getColour())){
                System.out.println("You cannot perform that move, try again!");
                from = current.move();
                to = current.move();
            }

            this.update(from, to, current.getColour());

            isWhite=!isWhite;
            board.printChessBoard();
        }
        try {
            SaveFile.getInstance().print("white", whiteHistory);
            SaveFile.getInstance().print("red", blackHistory);
        } catch (IOException e) {
            System.out.println("Cannot print players moves.");
        }
    }
    private void update (Position from, Position to, Colour colour){
        if (colour == Colour.BLACK) {
            blackHistory.append(from.getX()).append("-").append(from.getY()).append("  to>  ")
                    .append(to.getX()).append("-").append(to.getY()).append("\n");
        } else {
            whiteHistory.append(from.getX()).append("-").append(from.getY()).append("  to>  ")
                    .append(to.getX()).append("-").append(to.getY()).append("\n");
        }
    }

}
