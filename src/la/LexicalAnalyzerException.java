package la;

/**
 * @author Taras Slipets
 */
public class LexicalAnalyzerException extends Exception {
    public static final String NO_SUCH_LEXEME = "No such lexeme - cannot find appropriate code";
    public static final String NO_CLOSING_GAP_FOR_CHAR_FOUND = "No closing gap for char found";
    public static final String NO_CLOSING_GAP_FOR_STRING_FOUND = "No closing gap for string found";
    public static final String ILLEGAL_CHARACTERS_IN_DECLARATION = "Illegal characters in ";
    public static final String OUT_OF_FLOAT_RANGE = "Out of float range";
    public static final String OUT_OF_INTEGER_RANGE = "Out of integer range";
    public static final String ILLEGAL_INPUT_CHARACTER = "Illegal input character";
    public static final String INCORRECT_BOOLEAN_VALUE = "Incorrect boolean value";

    public LexicalAnalyzerException(String s) {
         super(s);
    }

    public LexicalAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexicalAnalyzerException(Exception e) {
        super(e);
    }
}
