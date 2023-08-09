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
    public String getPosition(){
        String input = null;
        while (scanner.hasNextLine()){
            try {
                input = scanner.nextLine();
                break;
            } catch (InputMismatchException e){
                System.out.println("Only 'x-y' format is allowed, where 'x' and 'y' are int primitive type");
            }
        }
        return input;
    }

    public String getPiece(){
        String input = null;
        while (scanner.hasNextLine()){
            try {
                input = scanner.nextLine();
                break;
            } catch (InputMismatchException e){
                System.out.println("Unrecognised piece. Try again!");
                //scanner.nextLine();
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
