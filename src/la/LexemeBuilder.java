package la;

/**
 * Custom Lexeme factory
 */
public class LexemeBuilder {
    static Lexeme buildLexeme(int startAnalyzePosition, Lexeme.Type lexemeType, String lexemeText)
            throws LexicalAnalyzerException {
        return new Lexeme(lexemeType, lexemeText, startAnalyzePosition);
    }
}
