package la;

import java_cup.runtime.Symbol;
import sa.SymbolsInfo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * @author Taras Slipets
 */
public class Lexeme extends Symbol {

    protected static final TreeMap<Integer, String> lexemeCodingTable;

    static {
        int i = 0;
        lexemeCodingTable = new TreeMap<Integer, String>();
        lexemeCodingTable.put(SymbolsInfo.EOF,Type.EOF.name());

        lexemeCodingTable.put(SymbolsInfo.INTEGER_TYPE,Type.INTEGER.name());
        lexemeCodingTable.put(SymbolsInfo.FLOAT_TYPE,Type.FLOAT.name());
        lexemeCodingTable.put(SymbolsInfo.CHAR_TYPE,Type.CHAR.name());
        lexemeCodingTable.put(SymbolsInfo.STRING_TYPE,Type.STRING.name());
        lexemeCodingTable.put(SymbolsInfo.BOOLEAN_TYPE,Type.BOOLEAN.name());

        lexemeCodingTable.put(SymbolsInfo.IDENTIFIER,Type.IDENTIFIER.name());

        lexemeCodingTable.put(SymbolsInfo.BOOLEAN_KEYWORD,"boolean");
        lexemeCodingTable.put(SymbolsInfo.BYTE_KEYWORD,"byte");
        lexemeCodingTable.put(SymbolsInfo.CHAR_KEYWORD,"char");
        lexemeCodingTable.put(SymbolsInfo.CLASS_KEYWORD,"class");
        lexemeCodingTable.put(SymbolsInfo.ELSE_KEYWORD,"else");
        lexemeCodingTable.put(SymbolsInfo.FLOAT_KEYWORD,"float");
        lexemeCodingTable.put(SymbolsInfo.IF_KEYWORD,"if");
        lexemeCodingTable.put(SymbolsInfo.INT_KEYWORD,"int");
        lexemeCodingTable.put(SymbolsInfo.MAIN_KEYWORD,"main");
        lexemeCodingTable.put(SymbolsInfo.NEW_KEYWORD,"new");
        lexemeCodingTable.put(SymbolsInfo.PUBLIC_KEYWORD,"public");
        lexemeCodingTable.put(SymbolsInfo.RETURN_KEYWORD,"return");
        lexemeCodingTable.put(SymbolsInfo.STATIC_KEYWORD,"static");
        lexemeCodingTable.put(SymbolsInfo.STRING_KEYWORD,"String");
        lexemeCodingTable.put(SymbolsInfo.VOID_KEYWORD,"void");
        lexemeCodingTable.put(SymbolsInfo.WHILE_KEYWORD,"while");

        lexemeCodingTable.put(SymbolsInfo.STOP,".");
        lexemeCodingTable.put(SymbolsInfo.TIMES,"*");
        lexemeCodingTable.put(SymbolsInfo.DIVIDE,"/");
        lexemeCodingTable.put(SymbolsInfo.PLUS,"+");
        lexemeCodingTable.put(SymbolsInfo.MINUS,"-");
        lexemeCodingTable.put(SymbolsInfo.LOWER,"<");
        lexemeCodingTable.put(SymbolsInfo.LOWER_EQUAL,"<=");
        lexemeCodingTable.put(SymbolsInfo.GREATER,">");
        lexemeCodingTable.put(SymbolsInfo.GREATER_EQUAL,">=");
        lexemeCodingTable.put(SymbolsInfo.INSTANCEOF,"instanceof");
        lexemeCodingTable.put(SymbolsInfo.EQUAL,"==");
        lexemeCodingTable.put(SymbolsInfo.NOT_EQUAL,"!=");
        lexemeCodingTable.put(SymbolsInfo.EXCLAMATION,"!");
        lexemeCodingTable.put(SymbolsInfo.AND,"&&");
        lexemeCodingTable.put(SymbolsInfo.AND,"&&");
        lexemeCodingTable.put(SymbolsInfo.OR,"||");
        lexemeCodingTable.put(SymbolsInfo.ASSIGN,"=");
        lexemeCodingTable.put(SymbolsInfo.SYSTEM_OUT_PRINTLN,"System.out.println");
        lexemeCodingTable.put(SymbolsInfo.SYSTEM_IN_READ,"System.in.read");
        lexemeCodingTable.put(SymbolsInfo.BREAK,"break");

        lexemeCodingTable.put(SymbolsInfo.SEMICOLON,";");
        lexemeCodingTable.put(SymbolsInfo.COMMA,",");
        lexemeCodingTable.put(SymbolsInfo.LEFT_SQUARE_PARENTHESIS,"[");
        lexemeCodingTable.put(SymbolsInfo.RIGHT_SQUARE_PARENTHESIS,"]");
        lexemeCodingTable.put(SymbolsInfo.LEFT_PARENTHESIS,"(");
        lexemeCodingTable.put(SymbolsInfo.RIGHT_PARENTHESIS,")");
        lexemeCodingTable.put(SymbolsInfo.LEFT_FIG_PARENTHESIS,"{");
        lexemeCodingTable.put(SymbolsInfo.RIGHT_FIG_PARENTHESIS,"}");
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

    public Lexeme(Type lexemeType, Object value, int startFrom) throws LexicalAnalyzerException {
        super(getLexemeCode(value.toString(), lexemeType), startFrom, startFrom + value.toString().length(), value);
        this.type = lexemeType;
    }

    protected Type type;


    @Override
    public String toString() {
        Object printableValue = getValue();
        if (printableValue.equals("\t")) {
            printableValue = "\\t";
        }
        if (printableValue.equals("\n")) {
            printableValue = "\\n";
        }
        if (printableValue instanceof String) {
            printableValue = ((String) printableValue).replace("\"", "");
        }
        return "[type=" + getType().toString().toUpperCase() + "; code=" + getCode() + "; value=\""
                + printableValue + "\"" +
                "; left position=" + left +
                "; right position=" + right + "]";
    }

    public Type getType() {
        return type;
    }

    public int getCode() {
        return super.sym;
    }

    public Object getValue() {
        return super.value;
    }

    public int getLength() {
        return getValue().toString().length();
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
