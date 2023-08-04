package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    private static SaveFile INSTANCE;
    private BufferedWriter bufferedWriter;

    public static SaveFile getInstance() throws IOException {
        if (INSTANCE == null){
            INSTANCE = new SaveFile();
        }
        return INSTANCE;
    }
    public void print (String name, StringBuilder input) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(name + ".txt"));
        bufferedWriter.write(input.toString());
        bufferedWriter.close();
    }
}
