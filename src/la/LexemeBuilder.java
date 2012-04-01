package la;

import sa.SymbolsInfo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Custom Lexeme factory
 */
public class LexemeBuilder {
    private static TreeMap<Integer, String> lexemeCodingTable;

    static {
        int i = 0;
        LexemeBuilder.lexemeCodingTable = new TreeMap<Integer, String>();
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.EOF, LexemeBuilder.Type.EOF.name());

        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.INTEGER_TYPE, LexemeBuilder.Type.INTEGER.name());
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.FLOAT_TYPE, LexemeBuilder.Type.FLOAT.name());
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.CHAR_TYPE, LexemeBuilder.Type.CHAR.name());
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.STRING_TYPE, LexemeBuilder.Type.STRING.name());
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.BOOLEAN_TYPE, LexemeBuilder.Type.BOOLEAN.name());

        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.IDENTIFIER, LexemeBuilder.Type.IDENTIFIER.name());

        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.BOOLEAN_KEYWORD, "boolean");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.BYTE_KEYWORD, "byte");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.CHAR_KEYWORD, "char");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.CLASS_KEYWORD, "class");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.ELSE_KEYWORD, "else");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.FLOAT_KEYWORD, "float");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.IF_KEYWORD, "if");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.INT_KEYWORD, "int");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.MAIN_KEYWORD, "main");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.NEW_KEYWORD, "new");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.PUBLIC_KEYWORD, "public");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.RETURN_KEYWORD, "return");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.STATIC_KEYWORD, "static");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.STRING_KEYWORD, "String");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.VOID_KEYWORD, "void");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.WHILE_KEYWORD, "while");

        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.STOP, ".");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.TIMES, "*");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.DIVIDE, "/");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.PLUS, "+");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.MINUS, "-");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.LOWER, "<");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.LOWER_EQUAL, "<=");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.GREATER, ">");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.GREATER_EQUAL, ">=");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.INSTANCEOF, "instanceof");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.EQUAL, "==");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.NOT_EQUAL, "!=");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.EXCLAMATION, "!");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.AND, "&&");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.AND, "&&");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.OR, "||");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.ASSIGN, "=");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.SYSTEM_OUT_PRINTLN, "System.out.println");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.SYSTEM_IN_READ, "System.in.read");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.BREAK, "break");

        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.SEMICOLON, ";");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.COMMA, ",");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.LEFT_SQUARE_PARENTHESIS, "[");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.RIGHT_SQUARE_PARENTHESIS, "]");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.LEFT_PARENTHESIS, "(");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.RIGHT_PARENTHESIS, ")");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.LEFT_FIG_PARENTHESIS, "{");
        LexemeBuilder.lexemeCodingTable.put(SymbolsInfo.RIGHT_FIG_PARENTHESIS, "}");
    }

    static Lexeme buildLexeme(int startAnalyzePosition, Type lexemeType, String lexemeText)
            throws LexicalAnalyzerException {
        Lexeme lexeme = new Lexeme(lexemeText, startAnalyzePosition,
                                   getLexemeCode(lexemeText.toString(), lexemeType));
        lexeme.setType(lexemeType);
        return lexeme;
    }

    protected static int getLexemeCode(String lexemeText, Type type) throws LexicalAnalyzerException {
        Integer result = getLexemeCode(lexemeText);
        if (result == null) {
            /*if no exact matches lexeme trying to find out code by  lexeme type*/
            result = getLexemeCode(type);
        }
        if (result == null) {
            throw new LexicalAnalyzerException(LexicalAnalyzerException.NO_SUCH_LEXEME);
        }
        return result;
    }

    protected static Integer getLexemeCode(String lexemeText) {
        Integer result = null;
        Iterator<Integer> lexemeTableKeysIterator = getLexemeCodingTable().keySet().iterator();
        while (lexemeTableKeysIterator.hasNext()) {
            Integer currentKey = lexemeTableKeysIterator.next();
            String currentValue = getLexemeCodingTable().get(currentKey);
            if (currentValue.equals(lexemeText)) {
                result = currentKey;
            }
        }

        return result;
    }

    protected static Integer getLexemeCode(Type lexemeType) {
        Integer result = null;
        Iterator<Integer> lexemeTableKeysIterator = getLexemeCodingTable().keySet().iterator();
        while (lexemeTableKeysIterator.hasNext()) {
            Integer currentKey = lexemeTableKeysIterator.next();
            String currentValue = getLexemeCodingTable().get(currentKey);
            if (currentValue.equals(lexemeType.name())) {
                result = currentKey;
            }
        }

        return result;
    }

    public static TreeMap<Integer, String> getLexemeCodingTable() {
        return lexemeCodingTable;
    }

    public enum Type {

        CHAR, DELIMITER, FLOAT, IDENTIFIER, INTEGER, BOOLEAN, KEYWORD, OPERATOR, STRING, EOF;

        public static String[] getValues() {
            LinkedList<String> result = new LinkedList<String>();
            for (Type value : Type.values()) {
                result.add(value.name());
            }
            return result.toArray(new String[0]);
        }
    }
}
