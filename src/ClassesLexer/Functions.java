package ClassesLexer;

import java.io.IOException;

import static ClassesLexer.Main.main;
import static ClassesLexer.GlobalVariables.*;

public class Functions {


    static String munchedString = "";
    static String munchedNumber = "";
    static String munchedSymbol = "";
    static final String NOTHINGWASMUNCHED = null;
    static boolean wrongInput = false;
    static boolean symbolNext = false;
    static boolean attachLostParent = false;
    private static final String[] keywords = {
            "program",
            "and",
            "not",
            "or",
            "end",
            "if",
            "fi",
            "then",
            "while",
            "do",
            "od",
            "int",
            "bool",
            "print",
            "else"
    };


    static String lostChar = "";
    //Report syntax errors
    public static boolean reportLexicalError(char c) {
        if (c == '!' && TokenData.nextChar == '=') {
            return false;
        }
        return c == '@' || c == '!' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '`' || c == '~' || c == ',' || c == '\"' || c == '?' || c == '\'' || c == '[' || c == ']' || c == '{' || c == '}';
    }

    //Reruns program after successful tokenization of a file
    public static void sequenceKeepRunning() throws IOException {
        reset();
        main(new String[0]);
    }

    //Reset variables to analyze next file
    private static void reset() {
        currentLine = 0;
        currentCharInLine = 0;
//        Next.j = 0;
    }
    public static void stringReset(){
        if (munchedString.length() == 1) {
            if (Character.isLetter(TokenData.currentChar)){
                if (Character.isLetter(TokenData.nextChar)) {
                    lostChar = String.valueOf(TokenData.currentChar);
                }
            }
        }
        munchedString = "";
        munchedNumber = "";
        munchedSymbol = "";
    }
    public static String position(int currentLine, int currentCharInLine) {
        return (currentLine) + ":" + (currentCharInLine);
    }
    public static char[] stringToChar (StringBuilder sb) {
        return sb.toString().toCharArray();
    }

    //Return the kind of lexeme
    public static String kind (String munchedWord) throws IOException {
        if (munchedWord == null) {
            TokenData.currentKeyword = "end-of-text";
            System.out.println("End of file reached resetting");
            sequenceKeepRunning();
        }
        char tFirstChar;
        assert munchedWord != null;
        if (!munchedWord.isEmpty()) {
            tFirstChar = munchedWord.charAt(0);
        }
        else {
            return "";
        }
        if (Character.isLetter(tFirstChar) && !isWord(munchedWord)) {
            return TokenData.currentKeyword = "'ID'";
        }
        else if(Character.isDigit(tFirstChar)) {
            return TokenData.currentKeyword = "'NUM'";
        }
        else if (isWord(munchedWord)) {
            return TokenData.currentKeyword = "'KEYWORD'";
        }
        return "";
    }

    //Return the value of the lexeme if its a ID, NUM, or a KEYWORD
    public static String value(String munchedWord) {
        if (TokenData.currentKeyword.equals("'ID'") ) {
            TokenData.currentTokenValue = TokenData.currentKeyword;
            return munchedWord;
        }
        else if (TokenData.currentKeyword.equals("'NUM'")) {
            TokenData.currentTokenValue = TokenData.currentKeyword;
            return munchedWord;
        }
        else if(TokenData.currentKeyword.equals("'KEYWORD'")) {
            TokenData.currentTokenValue = TokenData.currentKeyword;
            return munchedWord;
        }
        return "";
    }

    public static String maxMunch(char charToMunch, int currentLine){
        //Letter Logic
        if (Character.isLetter(charToMunch) || charToMunch == '_') {
            munchedString += charToMunch;
            if (munchedNumber.length() > 0) {
                attachLostParent = true;
                lostChar = munchedString;
                return munchedNumber;
            }
            if (charToMunch == '_') {
                if (Character.isLetter(TokenData.nextChar)) {
                    return NOTHINGWASMUNCHED;
                }
                else if (reportLexicalError(charToMunch)){
                    return munchedString;
                }
                else {
                    return munchedString;
                }
            }
        }
        //Digit logic
        else if (Character.isDigit(charToMunch)) {
            if (TokenData.lastChar == '_') {
                munchedString = "";
            }
            if (Character.isWhitespace(TokenData.lastChar)){
                munchedNumber += charToMunch;
            }
            else if (!Character.isWhitespace(TokenData.lastChar)) {
                if (Character.isDigit(TokenData.lastChar)) {
                    char[] hasNumber = munchedString.toCharArray();
                    boolean anyNumber = false;
                    for (char c : hasNumber) {
                        if (Character.isDigit(c)) {
                            anyNumber = true;
                        }
                    }
                    if (anyNumber) {
                        munchedString += charToMunch;
                    }
                    else {
                        munchedNumber += charToMunch;
                    }
                }
                else if (Character.isLetter(TokenData.lastChar)) {
                    munchedString += charToMunch;
                }
                else {
                    munchedNumber += charToMunch;
                }
            }
        }
        // Symbol logic
        else if (!Character.isDigit(charToMunch) && !Character.isLetter(charToMunch) && !Character.isWhitespace(charToMunch)) {
            if (reportLexicalError(charToMunch)) {
                if (Character.isLetter(TokenData.lastChar) || TokenData.lastChar == '_'){
                    wrongInput = true;
                    return munchedString;
                }
                if (Character.isDigit(TokenData.lastChar)) {
                    wrongInput = true;
                    char[] hasNumber = munchedString.toCharArray();
                    boolean anyNumber = false;
                    for (char c : hasNumber) {
                        if (Character.isDigit(c)) {
                            anyNumber = true;
                        }
                    }
                    if (anyNumber) {
                        return munchedString;
                    }
                    else {
                        return munchedNumber;
                    }
                }
                System.out.println("\nIllegal character at " + position(currentLine, currentCharInLine) + ". Character is '" + charToMunch + "'.\nExiting program...");
                System.exit(0);
            }
            //Allows for symbols that came after a letter to be sent
            if (Character.isLetter(TokenData.lastChar)) {
                symbolNext = true;
                attachLostParent = false;
                return munchedString;
            }
            else if (Character.isDigit(TokenData.lastChar)) {
                symbolNext = true;
                attachLostParent = false;
                if (munchedString.length() > 0) {
                    return munchedString;
                }
                return munchedNumber;
            }
            // =< logic
            if (charToMunch == '=' && TokenData.nextChar == '<') {
                munchedSymbol += charToMunch;
                munchedSymbol += TokenData.nextChar;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // >= logic
            if (charToMunch == '>' && TokenData.nextChar == '='){
                munchedSymbol += charToMunch;
                munchedSymbol += TokenData.nextChar;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // != logic
            if (charToMunch == '!' && TokenData.nextChar == '='){
                munchedSymbol += charToMunch;
                munchedSymbol += TokenData.nextChar;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // := logic
            if (charToMunch == ':' && TokenData.nextChar == '='){
                munchedSymbol += charToMunch;
                munchedSymbol += TokenData.nextChar;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // < logic
            if (charToMunch == '<' && TokenData.lastChar != '='){
                munchedSymbol += charToMunch;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // = logic
            if (charToMunch == '=' && TokenData.lastChar != '>' && TokenData.lastChar != '!' && TokenData.lastChar != ':'){
                munchedSymbol += charToMunch;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // > logic
            if (charToMunch == '>' && TokenData.nextChar != '='){
                munchedSymbol += charToMunch;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
            // +, -, *, /, (, ), ;, : logic
            if (charToMunch == '+' || charToMunch == '-' || charToMunch == '*' || charToMunch == '/' || charToMunch == '(' || charToMunch == ')' || charToMunch == ';' || charToMunch == ':') {
                munchedSymbol += charToMunch;
                symbolNext = false;
                attachLostParent = false;
                return munchedSymbol;
            }
        }
        // Space logic
        if (Character.isWhitespace(charToMunch)) {
            if (Character.isLetter(TokenData.lastChar) || munchedString.length() > 0) {
                if (attachLostParent) {
                    lostChar += munchedString;
                    attachLostParent = false;
                    return lostChar;
                }
                return munchedString;
            }
            if (Character.isDigit(TokenData.lastChar) || munchedNumber.length() > 0) {
                return munchedNumber;
            }
            if (!Character.isLetter(TokenData.lastChar) && !Character.isDigit(TokenData.lastChar) && !Character.isWhitespace(TokenData.lastChar)) {
                return munchedSymbol;
            }
        }
        return NOTHINGWASMUNCHED;
    }

    //Testing for keywords
    private static boolean isWord(String munchedWord) {
        Boolean works = false;
        for (int i = 0; i < keywords.length; i++) {
            if (munchedWord.equals(keywords[i])) {
                works = true;
            }
        }
        return works;
    }
}
