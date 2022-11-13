package ClassesLexer;

import static ClassesLexer.Functions.position;

public class Print {
    public static void print(int  currentLine, String munchedWord, String kindValue, String value) {
        int intPassed = 1;

        for (int i = 1; i < munchedWord.length(); i++) {
            if (i == munchedWord.length() - 1) {
                intPassed = i;
            }
        }
        if (!munchedWord.equals("") && !munchedWord.equals(" ") && !munchedWord.contains("\t")) {
            if (!kindValue.equals("'ID'") && !kindValue.equals("'NUM'") && !kindValue.equals("'KEYWORD'")){
                System.out.println(position(currentLine, intPassed) + " " + "'" + munchedWord + "'");
            }
            else {
                System.out.println(position(currentLine, intPassed) + " " + kindValue + " " + value);
            }
        }
    }
}

