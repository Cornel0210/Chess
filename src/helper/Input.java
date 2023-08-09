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
        while (scanner.hasNext()){
            try {
                input = scanner.nextLine();
                break;
            } catch (InputMismatchException e){
                System.out.println("Position's format must be like: 'x-y', where x and y are int primitives");
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
