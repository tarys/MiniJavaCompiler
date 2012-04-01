package la;

import java.io.Serializable;
import java.util.LinkedList;

public enum LexemeType implements Serializable {

    CHAR, DELIMITER, FLOAT, IDENTIFIER, INTEGER, BOOLEAN, KEYWORD, OPERATOR, STRING, EOF;

    public static String[] getValues() {
        LinkedList<String> result = new LinkedList<String>();
        for (LexemeType value : LexemeType.values()) {
            result.add(value.name());
        }
        return result.toArray(new String[0]);
    }
}