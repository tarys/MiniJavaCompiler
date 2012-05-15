package semantic;

public class SemanticException extends Exception {
    public static final String BREAK_USED_BUT_CYCLE_NOT_DECLARED = "\"break\" used but cycle not declared";
    public static final String NOT_BOOLEAN_EXPRESSION = "Not boolean expression";
    public static final String INCOMPATIBLE_TYPES = "Incompatible types";
    public static final String NEITHER_INTEGER_NOR_FLOAT_TYPE = "Neither integer nor float type";
    public static final String CLASS_NOT_DECLARED = "Class not declared: ";
    public static final String NO_SUCH_FIELD_IN_CLASS = "No such field in class: ";
    public static final String NO_SUCH_METHOD = "No such method in class: ";
    public static final String WRONG_METHOD_PARAMETERS_AMOUNT = "Wrong method parameters amount";
    public static final String NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER = "Not declared but used variable, field or method parameter: ";

    public SemanticException() {
    }

    public SemanticException(String message) {
        super(message);
    }
}
