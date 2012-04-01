package la;

import java_cup.runtime.Symbol;
import sa.SymbolsInfo;

import java.util.TreeMap;

/**
 * @author Taras Slipets
 */
public class Lexeme extends Symbol {



    public Lexeme(LexemeBuilder.Type lexemeType, Object value, int startFrom) throws LexicalAnalyzerException {
        super(LexemeBuilder.getLexemeCode(value.toString(), lexemeType), startFrom, startFrom + value.toString().length(), value);
        this.type = lexemeType;
    }

    protected LexemeBuilder.Type type;


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

    public LexemeBuilder.Type getType() {
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

}
