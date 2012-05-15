package semantic;

import nametable.NameTableBuilder;
import nametable.entries.*;

import java.util.List;

public class SemanticAnalyzer {
    public static final String CHAR_TYPE = "char";
    public static final String STRING_TYPE = "String";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String FLOAT_TYPE = "float";
    public static final String INTEGER_TYPE = "int";
    public static final String VOID_TYPE = "void";

    private NameTableBuilder nameTableBuilder;
    private boolean breakFlag;

    public SemanticAnalyzer(NameTableBuilder nameTableBuilder) {
        this.nameTableBuilder = nameTableBuilder;
    }

    public void checkNotUsedBreak() throws SemanticException {
        if (isBreakFlag()) {
            throw new SemanticException(SemanticException.BREAK_USED_BUT_CYCLE_NOT_DECLARED);
        }
    }

    public String unaryMinus(String arg) throws SemanticException {
        if (arg.equals(INTEGER_TYPE) || arg.equals(FLOAT_TYPE)) {
            return arg;
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public void breakExpression() throws SemanticException {
        setBreakFlag(true);
    }

    public String isClassDeclared(String className) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(className);
        return declaredClass.getName();
    }

    public String isVariableOrMethodParameterOrFieldInCurrentClassDeclared(String name) throws SemanticException {
        List<Entry> candidates = getNameTableBuilder().lookUp(name);
        String resultType = null;
        for (Entry candidate : candidates) {
            if (candidate instanceof FieldEntry) {
                FieldEntry field = (FieldEntry) candidate;
                if (field.getName().equals(name)) {
                    resultType = field.getValueType();
                    break;
                }
            } else if (candidate instanceof MethodParameterEntry) {
                MethodParameterEntry methodParameter = (MethodParameterEntry) candidate;
                if (methodParameter.getName().equals(name)) {
                    resultType = methodParameter.getValueType();
                    break;
                }
            } else if (candidate instanceof VariableEntry) {
                VariableEntry variable = (VariableEntry) candidate;
                if (variable.getName().equals(name)) {
                    resultType = variable.getValueType();
                    break;
                }
            }
        }
        if (resultType == null) {
            throw new SemanticException(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER + name);
        }
        return resultType;
    }


    public String orExpression(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(BOOLEAN_TYPE) || !arg2.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        return BOOLEAN_TYPE;
    }

    public String andExpression(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(BOOLEAN_TYPE) || !arg2.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
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
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String timesExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String minusExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String plusExpression(String arg1, String arg2) throws SemanticException {
        if (isNumericType(arg1) && isNumericType(arg2)) {
            return (arg1.equals(INTEGER_TYPE) && arg2.equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    public String parenthesisExpression(String arg) throws SemanticException {
        return arg;
    }

    public String exclamationExpression(String arg) throws SemanticException {
        if (!arg.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        return BOOLEAN_TYPE;
    }

    public String systemReadInExpression() throws SemanticException {
        return "String";
    }

    public String instanceofExpression(String instanceType, String className) throws SemanticException {
        // just looking for already declared class
        lookUpDeclaredClass(className);
        return BOOLEAN_TYPE;
    }

    public String methodCallExpression(String methodName, List<String> actualParameters) throws SemanticException {
        MethodEntry callMethod = lookUpDeclaredMethod(methodName);
        //checking actual parameters' types
        checkActualParameters(actualParameters, callMethod);

        return callMethod.getReturnType();
    }

    public String methodCallExpression(String methodName) throws SemanticException {
        MethodEntry callMethod = lookUpDeclaredMethod(methodName);
        return callMethod.getReturnType();
    }

    public String methodCallExpression(String className, String methodName, List<String> actualParameters) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(className);
        MethodEntry callMethod = declaredClass.getMethod(methodName);
        //checking actual parameters' types
        checkActualParameters(actualParameters, callMethod);

        return callMethod.getReturnType();
    }

    public String methodCallExpression(String className, String methodName) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(className);
        MethodEntry callMethod = declaredClass.getMethod(methodName);

        return callMethod.getReturnType();
    }

    public String fieldCallExpression(String className, String fieldName) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(className);
        FieldEntry callField = declaredClass.getField(fieldName);

        return callField.getValueType();
    }

    public void whileStatement(String conditionExpression) throws SemanticException {
        if (!conditionExpression.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        setBreakFlag(false);
    }

    public void ifStatement(String conditionExpression) throws SemanticException {
        if (!conditionExpression.equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
    }

    public void assignmentStatement(String name, String expressionType) throws SemanticException {
        List<Entry> assignCandidates = getNameTableBuilder().lookUp(name);
        if (assignCandidates.isEmpty()) {
            throw new SemanticException(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER + name);
        }
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
            throw new SemanticException(SemanticException.INCOMPATIBLE_TYPES);
        }
    }

    public NameTableBuilder getNameTableBuilder() {
        return nameTableBuilder;
    }

    public void setNameTableBuilder(NameTableBuilder nameTableBuilder) {
        this.nameTableBuilder = nameTableBuilder;
    }

    private ClassEntry lookUpDeclaredClass(String className) throws SemanticException {
        ClassEntry declaredClass = null;
        List<Entry> candidates = getNameTableBuilder().lookUp(className);
        for (Entry candidate : candidates) {
            if (candidate instanceof ClassEntry) {
                ClassEntry classEntry = (ClassEntry) candidate;
                if (classEntry.getName().equals(className)) {
                    declaredClass = classEntry;
                    break;
                }
            }
        }
        if (declaredClass == null) {
            throw new SemanticException(SemanticException.CLASS_NOT_DECLARED + className);
        }

        return declaredClass;
    }

    private MethodEntry lookUpDeclaredMethod(String methodName) throws SemanticException {
        MethodEntry declaredMethod = null;
        List<Entry> candidates = getNameTableBuilder().lookUp(methodName);
        for (Entry candidate : candidates) {
            if (candidate instanceof MethodEntry) {
                MethodEntry methodEntry = (MethodEntry) candidate;
                if (methodEntry.getName().equals(methodName)) {
                    declaredMethod = methodEntry;
                    break;
                }
            }
        }
        if (declaredMethod == null) {
            throw new SemanticException("No such method in current class: " + "#" + methodName);
        }

        return declaredMethod;
    }

    private void checkActualParameters(List<String> actualParameters, MethodEntry callMethod) throws SemanticException {
        List<MethodParameterEntry> formalParameters = callMethod.getParameters();
        if (formalParameters.size() != actualParameters.size()) {
            throw new SemanticException(SemanticException.WRONG_METHOD_PARAMETERS_AMOUNT);
        }
        for (int i = 0; i < formalParameters.size(); i++) {
            if (!formalParameters.get(i).getValueType().equals(actualParameters.get(i))) {
                throw new SemanticException(SemanticException.INCOMPATIBLE_TYPES);
            }
        }
    }

    private void areArgumentsComparable(String arg1, String arg2) throws SemanticException {
        if (!arg1.equals(arg2) && (!isNumericType(arg1) || arg1.equals(BOOLEAN_TYPE) || arg1.equals(CHAR_TYPE))) {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
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
