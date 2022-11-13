package ClassesLexer;


import java.io.IOException;

import static ClassesLexer.Print.print;
import static ClassesLexer.Functions.stringReset;
import static ClassesLexer.Functions.symbolNext;
import static ClassesLexer.Functions.maxMunch;
import static ClassesLexer.Reader.sb;
import static ClassesLexer.Functions.*;
import static ClassesLexer.GlobalVariables.*;

public class Next {
    static int j = 0;
    public static String munchedWord = "";
    //Gets next lexeme
    public static void next() throws IOException {
        //On chance an empty array is passed we return
        if (stringToChar(sb).length == 0) {
            return;
        }
        char charToMunch = stringToChar(sb)[j];

        //Used in the ThreeMainFunctions file to find the next char
        TokenData nextChar = new TokenData(stringToChar(sb), j);

        munchedWord = maxMunch(charToMunch, currentLine);
        if (munchedWord != null) {
            //Prints characters attached and before an unaccepted symbol
            if (wrongInput) {
                print(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
                System.out.println("\nIllegal character at " + position(currentLine, currentCharInLine) + ". Character is '" + charToMunch + "'.\nExiting program...");
                System.exit(0);
            }

            print(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
            stringReset();

            if (symbolNext) {
                munchedWord = String.valueOf(TokenData.currentChar);
                print(currentLine, munchedWord, kind(munchedWord), value(munchedWord));
                stringReset();
            }
        }
        j++;
        while (!TokenData.currentKeyword.equals("end-of-text")  && j <= stringToChar(sb).length - 1) {
            next();
        }
    }
}