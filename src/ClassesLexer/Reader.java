package ClassesLexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static ClassesLexer.GlobalVariables.*;
import static ClassesLexer.Main.main;

public class Reader {

    public static StringBuilder sb = new StringBuilder();

    public static void reader(String fileNameToRead) throws IOException {
        File f = new File(fileNameToRead); //creation of file descriptor for input file

        //Clauses for if the file exist
        if (f.exists()) {
            FileReader fr = new FileReader(f); //creation of file reader object
            BufferedReader br = new BufferedReader(fr); //creation of BufferedReader object
            String analyst;
            sb = new StringBuilder();

            //File will be read line by line
            while((analyst = br.readLine()) != null) {
                //Increment currentLine variable each time new line is read
                currentLine++;
                //This will add a space to the last index so the next function properly
                analyst += " ";
                for(int i = 0; i < analyst.length();i++){
                    //initialize variable for specific index
                    char c = analyst.charAt(i);
                    //this will allow for skipping of code with comments
                    if (i + 1 < analyst.length() && c == '/' && analyst.charAt(i + 1) == '/') break;
                    sb.append(c);
                }
                //Clause for comment at end of file, allows for skipping with throwing out of bounds error
                sb.append(" ");
//                next();
            }
            br.close();
            fr.close();

//            kind(null);
        } else {
            System.out.println("File name does not exist, try again");
            main(new String[0]);
        }
    }
}
