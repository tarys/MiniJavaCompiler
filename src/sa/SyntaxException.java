package sa;

import la.Lexeme;

public class SyntaxException extends Exception{
    private Lexeme lexeme;

    public SyntaxException(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public SyntaxException(String message, Lexeme lexeme) {
        super(message);
        this.lexeme = lexeme;
    }

    public SyntaxException(String message, Throwable cause, Lexeme lexeme) {
        super(message, cause);
        this.lexeme = lexeme;
    }

    public SyntaxException(Throwable cause, Lexeme lexeme) {
        super(cause);
        this.lexeme = lexeme;
    }
}
