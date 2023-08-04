package helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    private static Input INSTANCE;
    private final Scanner scanner;

    private Input(){
        this.scanner = new Scanner(System.in);
    }

    public static Input getInstance() {
        if (INSTANCE ==null){
            INSTANCE = new Input();
        }
        return INSTANCE;
    }
    public int getInt(){
        int i=-1;
        while (scanner.hasNext()){
            try {
                i = scanner.nextInt();
                break;
            } catch (InputMismatchException e){
                System.out.println("insert a number between [0 and 7]");
                scanner.nextLine();
            }
            finally {
                scanner.nextLine();
            }
        }
        return i;
    }

    public String getPiece(){
        String input = null;
        while (scanner.hasNextLine()){
            try {
                input = scanner.nextLine();
                break;
            } catch (InputMismatchException e){
                System.out.println("Unrecognised piece. Try again!");
                scanner.nextLine();
            }
        }
        return input;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        scanner.close();
    }
}
