package semantic;

import nametable.NameTableBuilder;
import nametable.entries.*;

import java.util.List;

public class SemanticAnalyzer {
    public static final String BREAK_USED_BUT_CYCLE_NOT_DECLARED = "\"break\" used but cycle not declared";
    public static final String NOT_BOOLEAN_EXPRESSION = "Not boolean expression";
    public static final String INCOMPATIBLE_TYPES = "Incompatible types";
    public static final String CHAR_TYPE = "char";
    public static final String STRING_TYPE = "String";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String FLOAT_TYPE = "float";
    public static final String INTEGER_TYPE = "int";
    public static final String VOID_TYPE = "void";
    public static final String NEITHER_INTEGER_NOR_FLOAT_TYPE = "Neither integer nor float type";
    public static final String CLASS_NOT_DECLARED = "Class not declared: ";
    public static final String NO_SUCH_FIELD_IN_CLASS = "No such field in class: ";

    private NameTableBuilder nameTableBuilder;
    private boolean breakFlag;

    public SemanticAnalyzer(NameTableBuilder nameTableBuilder) {
        this.nameTableBuilder = nameTableBuilder;
    }

    public void checkNotUsedBreak() throws SemanticException {
        if (isBreakFlag()) {
            throw new SemanticException(BREAK_USED_BUT_CYCLE_NOT_DECLARED);
        }
    }

    public String unaryMinus(String arg) throws SemanticException {
        if (arg.equals(INTEGER_TYPE) || arg.equals(FLOAT_TYPE)) {
            return arg;
        } else {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public void breakExpression() throws SemanticException {
        setBreakFlag(true);
    }

    public String isClassDeclared(String className) throws SemanticException {
        List<Entry> candidates = getNameTableBuilder().lookUp(className);
        boolean declared = false;
        for (Entry candidate : candidates) {
            if (candidate instanceof ClassEntry) {
                ClassEntry classEntry = (ClassEntry) candidate;
                if (classEntry.getName().equals(className)) {
                    declared = true;
                    break;
                }
            }
        }
        if (!declared) {
            throw new SemanticException(CLASS_NOT_DECLARED + className);
        }
        return className;
    }


    public String isVariableOrMethodParameterOrFieldInCurrentClassDeclared(String name) throws SemanticException {
        return null;
    }

    public String orExpression(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(BOOLEAN_TYPE) || !arg2.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(NOT_BOOLEAN_EXPRESSION);
        }
        return BOOLEAN_TYPE;
    }

    public String andExpression(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(BOOLEAN_TYPE) || !arg2.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(NOT_BOOLEAN_EXPRESSION);
        }
        return BOOLEAN_TYPE;
    }

    public String notEqualExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }

    public String equalExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }

    public String greaterEqualExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }

    public String greaterExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }

    public String lowerEqualExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }


    public String lowerExpression(String arg1, String arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return BOOLEAN_TYPE;
    }

    public String divideExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return FLOAT_TYPE;
        } else {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String timesExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String minusExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String plusExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String parenthesisExpression(String arg) throws SemanticException {
        return arg;
    }

    public String exclamationExpression(String arg) throws SemanticException {
        if (!arg.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(NOT_BOOLEAN_EXPRESSION);
        }
        return BOOLEAN_TYPE;
    }

    public String systemReadInExpression() throws SemanticException {
        return "String";
    }

    public String instanceofExpression(String instanceName, String className) throws SemanticException {

        return BOOLEAN_TYPE;
    }

    public String methodCallExpression(String methodName, List<String> actualParameters) throws SemanticException {
        return null;
    }

    public String methodCallExpression(String methodName) throws SemanticException {
        return null;
    }

    public String methodCallExpression(String className, String methodName, List<String> actualParameters) throws SemanticException {
        return null;
    }

    public String methodCallExpression(String className, String methodName) throws SemanticException {
        List<Entry> callCandidates = getNameTableBuilder().lookUp(className);
        MethodEntry callMethod = null;
        for (Entry candidate : callCandidates) {
            if (candidate instanceof MethodEntry) {
                MethodEntry candidateMethod = (MethodEntry) candidate;
                if (callMethod.getName().equals(methodName)) {
                    callMethod = candidateMethod;
                }
            }
        }
        if (callMethod == null) {
            throw new SemanticException("No such method in class: " + className + "#" + methodName);
        }
        return callMethod.getReturnType();
    }

    public String fieldCallExpression(String className, String fieldName) throws SemanticException {
        List<Entry> callCandidates = getNameTableBuilder().lookUp(className);
        FieldEntry callField = null;
        for (Entry candidate : callCandidates) {
            if (candidate instanceof FieldEntry) {
                FieldEntry candidateField = (FieldEntry) candidate;
                if (callField.getName().equals(fieldName)) {
                    callField = candidateField;
                }
            }
        }
        if (callField == null) {
            throw new SemanticException(NO_SUCH_FIELD_IN_CLASS + className + "#" + fieldName);
        }
        return callField.getValueType();
    }

    public void whileStatement(String conditionExpression) throws SemanticException {
        if (!conditionExpression.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(NOT_BOOLEAN_EXPRESSION);
        }
        setBreakFlag(false);
    }

    public void ifStatement(String conditionExpression) throws SemanticException {
        if (!conditionExpression.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(NOT_BOOLEAN_EXPRESSION);
        }
    }

    public void assignmentStatement(String name, String expressionType) throws SemanticException {
        List<Entry> assignCandidates = getNameTableBuilder().lookUp(name);
        boolean assignSuccess = false;
        for (Entry assignCandidate : assignCandidates) {
            if (assignCandidate instanceof ClassEntry) {
                ClassEntry candidate = (ClassEntry) assignCandidate;
                if (candidate.getName().equals(expressionType)) {
                    assignSuccess = true;
                    break;
                }
            } else if (assignCandidate instanceof FieldEntry) {
                FieldEntry candidate = (FieldEntry) assignCandidate;
                if (candidate.getValueType().equals(expressionType)) {
                    assignSuccess = true;
                    break;
                }
            } else if (assignCandidate instanceof MethodParameterEntry) {
                MethodParameterEntry candidate = (MethodParameterEntry) assignCandidate;
                if (candidate.getValueType().equals(expressionType)) {
                    assignSuccess = true;
                    break;
                }
            } else if (assignCandidate instanceof VariableEntry) {
                VariableEntry candidate = (VariableEntry) assignCandidate;
                if (candidate.getValueType().equals(expressionType)) {
                    assignSuccess = true;
                    break;
                }
            }
        }
        if (!assignSuccess) {
            throw new SemanticException(INCOMPATIBLE_TYPES);
        }
    }

    public NameTableBuilder getNameTableBuilder() {
        return nameTableBuilder;
    }

    public void setNameTableBuilder(NameTableBuilder nameTableBuilder) {
        this.nameTableBuilder = nameTableBuilder;
    }

    private void areArgumentsComparable(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(arg2) && (!isNumericType(arg1) || arg1.equals(BOOLEAN_TYPE) || arg1.equals(CHAR_TYPE))) {
            throw new SemanticException(NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    private boolean isNumericType(String arg) {
        return (arg.equals(INTEGER_TYPE) || arg.equals(FLOAT_TYPE));
    }

    private void setBreakFlag(boolean breakFlag) {
        this.breakFlag = breakFlag;
    }

    private boolean isBreakFlag() {
        return breakFlag;
    }
}
