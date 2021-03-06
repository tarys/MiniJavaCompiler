package la;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taras Slipets
 */
public class LexicalAnalyzer implements Scanner {

    public static final String COMMENT_REGEX_STRING = "/\\*(.*|\\s)*\\*/";
    public static final String ID_REGEX = "([a-zA-Z]{1}\\w{0,79})";
    public static final String CHAR_REGEX = "(\\'(.|\\p{ASCII})\\')";
    public static final String BOOLEAN_REGEX = "(true|false)";
    public static final String DELIMITER_REGEX = "(\\t|\\n|\\[|\\]|[(){};, ])";
    public static final String FLOAT_REGEX = "([-]{0,1}\\d*\\.\\d+([eE][+-]\\d+){0,1})";
    public static final String INTEGER_REGEX = "([-]{0,1}\\d+)";
    public static final String KEYWORD_REGEX = "(boolean"
            + "|break"
            + "|char"
            + "|class"
            + "|else"
            + "|float"
            + "|if"
            + "|instanceof"
            + "|int"
            + "|main"
            + "|new"
            + "|public"
            + "|return"
            + "|static"
            + "|String"
            + "|System\\.in\\.read"
            + "|System\\.out\\.println"
            + "|void"
            + "|while)";
    public static final String OPERATOR_REGEX = "(\\." +
            "|\\*" +
            "|\\/" +
            "|\\+" +
            "|\\-" +
            "|<(\\=){0,1}" +
            "|>(\\=){0,1}" +
            "|instanceof" +
            "|==" +
            "|!=" +
            "|\\!" +
            "|&&" +
            "|\\|\\|" +
            "|\\=" +
            "|System\\.in\\.read" +
            "|System\\.out\\.println" +
            "|break)";

    public static final String STRING_REGEX = "(\"(.|\\s)*\")";
    protected String sourceCodeText;
    protected int currentMarkerPosition;

    public LexicalAnalyzer() {
        this.currentMarkerPosition = 0;
    }

    public LexicalAnalyzer(String sourceCodeText) {
        this.sourceCodeText = sourceCodeText;
        this.currentMarkerPosition = 0;
    }

    public Symbol next_token() throws Exception {
        Lexeme result;
        LexemeType[] lexemeTypes = LexemeType.values();
        HashMap<LexemeType, Lexeme> resultCandidates = new HashMap<LexemeType, Lexeme>();
        skipWhitespaces();
        for (LexemeType lexemeType : lexemeTypes) {
            try {
                if (lexemeType.equals(LexemeType.EOF)
                        && (getCurrentMarkerPosition() != getSourceCodeText().length())) {
                    continue;
                }
                resultCandidates
                        .put(lexemeType, getLexeme(getSourceCodeText(), getCurrentMarkerPosition(), lexemeType));
            } catch (LexicalAnalyzerException ex) {
                // this means that lexicalAnalyzer cannot find lexeme of given type
                if (ex.getMessage().startsWith(LexicalAnalyzerException.NO_CLOSING_GAP_FOR_STRING_FOUND)
                        || ex.getMessage().startsWith(LexicalAnalyzerException.NO_CLOSING_GAP_FOR_CHAR_FOUND)
                        || ex.getMessage().startsWith(LexicalAnalyzerException.OUT_OF_FLOAT_RANGE)
                        || ex.getMessage().startsWith(LexicalAnalyzerException.OUT_OF_INTEGER_RANGE)) {
                    // but for this kind of errors we must stop analyzing
                    throw new LexicalAnalyzerException(ex);
                }

            }
        }
        if (resultCandidates.size() == 0) {
            // this means that analyzer hasn't found any type of lexeme
            throw new LexicalAnalyzerException(
                    LexicalAnalyzerException.ILLEGAL_INPUT_CHARACTER + " near position " + getCurrentMarkerPosition());
        }
        if (resultCandidates.containsKey(LexemeType.OPERATOR)) {
            result = resultCandidates.get(LexemeType.OPERATOR);
        } else if (resultCandidates.containsKey(LexemeType.KEYWORD)) {
            Lexeme identifier = resultCandidates.get(LexemeType.IDENTIFIER);
            Lexeme keyword = resultCandidates.get(LexemeType.KEYWORD);
            if (identifier != null) {
                /*
                    if lexeme starts with substring that is keyword
                    we ignore it and assume lexeme as identifier.
                    For instance:
                    "classIdentifier" starts with "class" keyword but
                    regardless it is identifier
                    */
                result = (identifier.getLength() > keyword.getLength()) ? identifier : keyword;
            } else {
                result = keyword;
            }
        } else if (resultCandidates.containsKey(LexemeType.FLOAT)) {
            result = resultCandidates.get(LexemeType.FLOAT);
        } else if (resultCandidates.containsKey(LexemeType.BOOLEAN)) {
            result = resultCandidates.get(LexemeType.BOOLEAN);
        } else {
            // fetching first and unique lexeme
            result = resultCandidates.get(resultCandidates.keySet().iterator().next());
        }
        // increasing current marker position
        setCurrentMarkerPosition(getCurrentMarkerPosition() + result.getLength());
        if (getCurrentMarkerPosition() > getSourceCodeText().length()) {
            // we've reached end of source to analyze
            setCurrentMarkerPosition(getSourceCodeText().length());
        }

        return result;
    }

    public String getSourceCodeText() {
        return sourceCodeText;
    }

    public int getCurrentMarkerPosition() {
        return currentMarkerPosition;
    }

    public void setCurrentMarkerPosition(int currentMarkerPosition) {
        this.currentMarkerPosition = currentMarkerPosition;
    }

    public Lexeme getLexeme(final String sourceCodeText, int startAnalyzePosition, LexemeType lexemeType) throws
            LexicalAnalyzerException {
        /* deleting comments */
        if (startAnalyzePosition == sourceCodeText.length()) {
            return LexemeBuilder.buildLexeme(LexemeType.EOF, "", startAnalyzePosition, startAnalyzePosition);
        }
        String notAnalyzedSourceSubstring = sourceCodeText.replaceAll(COMMENT_REGEX_STRING, "")
                .substring(startAnalyzePosition);
        Matcher matcher;
        /* setting appropriate matcher for lexeme */
        switch (lexemeType) {
            case IDENTIFIER:
                matcher = Pattern.compile(ID_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case CHAR:
                notAnalyzedSourceSubstring = fetchCharLiteral(notAnalyzedSourceSubstring, sourceCodeText);
                matcher = Pattern.compile(CHAR_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case DELIMITER:
                matcher = Pattern.compile(DELIMITER_REGEX).matcher(notAnalyzedSourceSubstring.substring(0, 1));
                break;
            case FLOAT:
                matcher = Pattern.compile(FLOAT_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case BOOLEAN:
                matcher = Pattern.compile(BOOLEAN_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case INTEGER:
                matcher = Pattern.compile(INTEGER_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case KEYWORD:
                matcher = Pattern.compile(KEYWORD_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case OPERATOR:
                matcher = Pattern.compile(OPERATOR_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            case STRING:
                notAnalyzedSourceSubstring = fetchStringLiteral(notAnalyzedSourceSubstring, sourceCodeText,
                        startAnalyzePosition);
                matcher = Pattern.compile(STRING_REGEX).matcher(notAnalyzedSourceSubstring);
                break;
            default:
                throw new IllegalArgumentException("No such lexeme type");
        }
        /* fetching pattern from text*/
        if (matcher.lookingAt()) {
            String lexemeText = matcher.group(1);
            Object lexemeValue = lexemeText;
            if (!lexemeType.equals(LexemeType.KEYWORD) && !lexemeType.equals(LexemeType.OPERATOR)
                    && isKeyword(lexemeText)) {
                throw new LexicalAnalyzerException("\"" + lexemeText + "\" is a keyword");
            }
            if (lexemeType.equals(LexemeType.INTEGER)) {
                try {
                    lexemeValue = Integer.parseInt(lexemeText);
                } catch (NumberFormatException e) {
                    throw new LexicalAnalyzerException(LexicalAnalyzerException.OUT_OF_INTEGER_RANGE + " for " +
                            lexemeText);
                }
            }
            if (lexemeType.equals(LexemeType.FLOAT)) {
                try {
                    float f = Float.parseFloat(lexemeText);
                    if ((f <= Float.NEGATIVE_INFINITY) || (f >= Float.POSITIVE_INFINITY)) {
                        throw new LexicalAnalyzerException(LexicalAnalyzerException.OUT_OF_FLOAT_RANGE + " for " +
                                lexemeText);
                    }
                    lexemeValue = new Float(f);
                } catch (NumberFormatException e) {
                    throw new LexicalAnalyzerException(LexicalAnalyzerException.OUT_OF_FLOAT_RANGE + " for " +
                            lexemeText);
                }
            }
            if (lexemeType.equals(LexemeType.BOOLEAN)) {
                if (lexemeText.toLowerCase().equals("true") || lexemeText.toLowerCase().equals("false")) {
                    lexemeValue = new Boolean(lexemeText);
                } else {
                    throw new LexicalAnalyzerException(LexicalAnalyzerException.INCORRECT_BOOLEAN_VALUE
                            + " for \"" + lexemeText + "\"");
                }
            }
            int lexemeStartPosition = startAnalyzePosition;
            int lexemeEndPosition = startAnalyzePosition + lexemeText.length();
            Lexeme lexeme = LexemeBuilder.buildLexeme(lexemeType, lexemeValue, lexemeStartPosition, lexemeEndPosition);
            return lexeme;
        } else {
            throw new LexicalAnalyzerException(
                    LexicalAnalyzerException.ILLEGAL_CHARACTERS_IN_DECLARATION + lexemeType.name()
                            .toLowerCase()
                            + " declaration");
        }
    }

    private String fetchCharLiteral(String notAnalyzedSourceSubstring, String sourceCodeText)
            throws LexicalAnalyzerException {
        String result;
        int searchStartIndex = sourceCodeText.indexOf(notAnalyzedSourceSubstring);
        int startIndex = sourceCodeText.indexOf("\'", searchStartIndex);
        if (startIndex == currentMarkerPosition) {
            int endIndex = sourceCodeText.indexOf("\'", startIndex + 1) + 1;
            if (endIndex == 0) {
                // closing gap not found
                throw new LexicalAnalyzerException(
                        LexicalAnalyzerException.NO_CLOSING_GAP_FOR_CHAR_FOUND + " near " + currentMarkerPosition
                                + " position");
            } else {
                result = sourceCodeText.substring(startIndex, endIndex);
            }
        } else {
            result = "";
        }
        return result;

    }

    private void skipWhitespaces() {
        int currentMarkerPosition = getCurrentMarkerPosition();
        if (currentMarkerPosition < getSourceCodeText().length()) {
            int nextMarkerPosition = currentMarkerPosition + 1;
            while (getSourceCodeText().substring(currentMarkerPosition, nextMarkerPosition).matches("\\s")) {
                setCurrentMarkerPosition(nextMarkerPosition);
                currentMarkerPosition = getCurrentMarkerPosition();
                nextMarkerPosition = currentMarkerPosition + 1;
            }
        }
    }

    public String fetchStringLiteral(String notAnalyzedSourceSubstring, String sourceCodeText,
                                     int currentMarkerPosition) throws LexicalAnalyzerException {
        String result;
        int searchStartIndex = sourceCodeText.indexOf(notAnalyzedSourceSubstring);
        int startIndex = sourceCodeText.indexOf("\"", searchStartIndex);
        if (startIndex == currentMarkerPosition) {
            int endIndex = sourceCodeText.indexOf("\"", startIndex + 1) + 1;
            if (endIndex == 0) {
                // closing gap not found
                throw new LexicalAnalyzerException(
                        LexicalAnalyzerException.NO_CLOSING_GAP_FOR_STRING_FOUND + " near " + currentMarkerPosition
                                + "" +
                                " position");
            } else {
                result = sourceCodeText.substring(startIndex, endIndex);
            }
        } else {
            result = "";
        }
        return result;
    }

    private boolean isKeyword(String lexemeText) {
        return Pattern.compile(KEYWORD_REGEX).matcher(lexemeText).matches();
    }

    public void setSourceCodeText(String sourceText) {
        this.setCurrentMarkerPosition(0);
        this.sourceCodeText = sourceText;
    }
}
