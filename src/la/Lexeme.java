package la;

import java_cup.runtime.Symbol;

/**
 * @author Taras Slipets
 */
public class Lexeme extends Symbol {



    public Lexeme(Object value, int startFrom, int lexemeCode) throws LexicalAnalyzerException {
        super(lexemeCode, startFrom, startFrom + value.toString().length(), value);
    }

    private LexemeBuilder.Type type;


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

    public void setType(LexemeBuilder.Type type) {
        this.type = type;
    }
}
