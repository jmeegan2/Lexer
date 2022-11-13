package ClassesLexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static ClassesLexer.Reader.reader;


public class Main {

    //Variables
//    private static int currentLine = 0;
//    private static int currentCharInLine = 0;

    public static void main(String[] args) throws IOException {
        String filenameToRead;
        System.out.println("Please enter a file to analyze (or quit) ");
        //This part takes in the user input for the file to be read, currently does not have quit option
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            filenameToRead = userInput.readLine();
            if (filenameToRead.equalsIgnoreCase("quit")) {
                System.out.println("Exiting program");
                System.exit(0);
            }
        } catch (IOException ioe) {
            throw new RuntimeException();
        }
        //calling of reader class
        reader(filenameToRead);
    }
}