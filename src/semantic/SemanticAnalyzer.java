package semantic;

import general.Analyzer;
import nametable.NameTableBuilder;
import nametable.entries.*;

import java.util.LinkedList;
import java.util.List;

public class SemanticAnalyzer implements Analyzer {
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

    @Override
    public void checkNotUsedBreak() throws SemanticException {
        if (isBreakFlag()) {
            throw new SemanticException(SemanticException.BREAK_USED_BUT_LOOP_NOT_DECLARED);
        }
    }

    @Override
    public TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException {
        if (isNumericType(arg.getValueType())) {
            return arg;
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    @Override
    public BreakEntry breakStatement() throws SemanticException {
        setBreakFlag(true);
        return new BreakEntry();
    }

    @Override
    public String isClassDeclared(String className) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(className);
        return declaredClass.getName();
    }

    @Override
    public TemporaryEntry identifierExpression(String name) throws SemanticException {
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
            } else if (candidate instanceof ClassEntry){
                ClassEntry classEntry = (ClassEntry) candidate;
                if(classEntry.getName().equals(name)){
                    resultType = name;
                    break;
                }
            }
        }
        if (resultType == null) {
            throw new SemanticException(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER + name);
        }
        return new TemporaryEntry(resultType);
    }


    @Override
    public TemporaryEntry orExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (!arg1.getValueType().equals(BOOLEAN_TYPE) || !arg2.getValueType().equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry andExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (!arg1.getValueType().equals(BOOLEAN_TYPE) || !arg2.getValueType().equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry notEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry equalExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry greaterEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry greaterExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry lowerEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry lowerExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        areArgumentsComparable(arg1, arg2);
        return new TemporaryEntry(BOOLEAN_TYPE);
    }


    @Override
    public TemporaryEntry divideExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (isNumericType(arg1.getValueType()) && isNumericType(arg2.getValueType())) {
            return new TemporaryEntry(FLOAT_TYPE);
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    @Override
    public TemporaryEntry timesExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (isNumericType(arg1.getValueType()) && isNumericType(arg2.getValueType())) {
            String valueType = (arg1.getValueType().equals(INTEGER_TYPE) && arg2.getValueType().equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
            return new TemporaryEntry(valueType);
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    @Override
    public TemporaryEntry minusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (isNumericType(arg1.getValueType()) && isNumericType(arg2.getValueType())) {
            String valueType = (arg1.getValueType().equals(INTEGER_TYPE) && arg2.getValueType().equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
            return new TemporaryEntry(valueType);
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    @Override
    public TemporaryEntry plusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (isNumericType(arg1.getValueType()) && isNumericType(arg2.getValueType())) {
            String valueType = (arg1.getValueType().equals(INTEGER_TYPE) && arg2.getValueType().equals(INTEGER_TYPE)) ? INTEGER_TYPE : FLOAT_TYPE;
            return new TemporaryEntry(valueType);
        } else {
            throw new SemanticException(SemanticException.NEITHER_INTEGER_NOR_FLOAT_TYPE);
        }
    }

    @Override
    public TemporaryEntry parenthesisExpression(TemporaryEntry arg) throws SemanticException {
        return arg;
    }

    @Override
    public TemporaryEntry exclamationExpression(TemporaryEntry arg) throws SemanticException {
        if (!arg.getValueType().equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry systemReadInExpression() throws SemanticException {
        return new TemporaryEntry("String");
    }

    @Override
    public TemporaryEntry instanceofExpression(TemporaryEntry instanceType, TemporaryEntry classNameEntry) throws SemanticException {
        // just looking for already declared class
        lookUpDeclaredClass(classNameEntry.getValueType());
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        MethodEntry callMethod = lookUpDeclaredMethod(methodName);
        //checking actual parameters' types
        checkActualParameters(actualParameters, callMethod);

        return new TemporaryEntry(callMethod);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName) throws SemanticException {
        MethodEntry callMethod = lookUpDeclaredMethod(methodName);
        //checking actual parameters' types
        checkActualParameters(new LinkedList<TemporaryEntry>(), callMethod);
        return new TemporaryEntry(callMethod);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry classObject, String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(classObject.getValueType());
        MethodEntry callMethod = declaredClass.getMethod(methodName);
        //checking actual parameters' types
        checkActualParameters(actualParameters, callMethod);

        return new TemporaryEntry(callMethod);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry classObject, String methodName) throws SemanticException {
        /* checking actual parameters' types
           because there are no actual parameters we create empty list
        */
        return this.methodCallExpression(classObject, methodName, new LinkedList<TemporaryEntry>());
    }

    @Override
    public TemporaryEntry fieldCallExpression(TemporaryEntry classObject, String fieldName) throws SemanticException {
        ClassEntry declaredClass = lookUpDeclaredClass(classObject.getValueType());
        FieldEntry callField = declaredClass.getField(fieldName);

        return new TemporaryEntry(callField.getValueType());
    }

    @Override
    public void whileStatement(TemporaryEntry conditionExpression, Entry innerBlock, Entry result) throws SemanticException {
        if (!conditionExpression.getValueType().equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
        setBreakFlag(false);
    }

    @Override
    public void ifStatement(TemporaryEntry conditionExpression, Entry thenBlock, Entry elseBlock, Entry result) throws SemanticException {
        if (!conditionExpression.getValueType().equals(BOOLEAN_TYPE)) {
            throw new SemanticException(SemanticException.NOT_BOOLEAN_EXPRESSION);
        }
    }

    @Override
    public TemporaryEntry assignmentStatement(String name, TemporaryEntry expression, Entry result) throws SemanticException {
        return assignmentStatement(name, expression);
    }

    @Override
    public TemporaryEntry assignmentStatement(String name, TemporaryEntry expression) throws SemanticException {
        List<Entry> assignCandidates = getNameTableBuilder().lookUp(name);
        if (assignCandidates.isEmpty()) {
            throw new SemanticException(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER + name);
        }
        boolean assignSuccess = false;
        String expectedType = "";
        for (Entry assignCandidate : assignCandidates) {
            if (assignCandidate instanceof ClassEntry) {
                ClassEntry candidate = (ClassEntry) assignCandidate;
                expectedType = candidate.getName();
            } else if (assignCandidate instanceof FieldEntry) {
                FieldEntry candidate = (FieldEntry) assignCandidate;
                expectedType = candidate.getValueType();
            } else if (assignCandidate instanceof MethodParameterEntry) {
                MethodParameterEntry candidate = (MethodParameterEntry) assignCandidate;
                expectedType = candidate.getValueType();
            } else if (assignCandidate instanceof VariableEntry) {
                VariableEntry candidate = (VariableEntry) assignCandidate;
                expectedType = candidate.getValueType();
            }
            if (expectedType.equals(FLOAT_TYPE) && expression.getValueType().equals(INTEGER_TYPE)) {
                assignSuccess = true;
                break;
            } else if (expectedType.equals(expression.getValueType())) {
                assignSuccess = true;
                break;
            }
        }
        if (!assignSuccess) {
            throw new SemanticException(SemanticException.INCOMPATIBLE_TYPES + "expected '" + expectedType + "' but was '"
                    + expression.getValueType() + "'");
        }
        return expression;
    }

    @Override
    public NameTableBuilder getNameTableBuilder() {
        return nameTableBuilder;
    }

    @Override
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
            throw new SemanticException(SemanticException.NOT_DECLARED_CLASS + className);
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

    private void checkActualParameters(List<TemporaryEntry> actualParameters, MethodEntry callMethod) throws SemanticException {
        List<MethodParameterEntry> formalParameters = callMethod.getParameters();
        if (formalParameters.size() != actualParameters.size()) {
            throw new SemanticException(SemanticException.WRONG_METHOD_PARAMETERS_AMOUNT);
        }
        for (int i = 0; i < formalParameters.size(); i++) {
            String formalValueType = formalParameters.get(i).getValueType();
            String actualValueType = actualParameters.get(i).getValueType();
            if (!formalValueType.equals(actualValueType)) {
                throw new SemanticException(SemanticException.INCOMPATIBLE_TYPES + "'" + formalValueType + "' expected but '" + actualValueType + "' found");
            }
        }
    }

    private void areArgumentsComparable(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        if (!arg1.getValueType().equals(arg2) && (!isNumericType(arg1.getValueType()) || arg1.getValueType().equals(BOOLEAN_TYPE) || arg1.getValueType().equals(CHAR_TYPE))) {
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

    public void methodDeclaration() throws SemanticException {
        checkNotUsedBreak();
    }

    public void methodDeclaration(String returnType, TemporaryEntry expression) throws SemanticException {
        checkNotUsedBreak();
        if (!returnType.equals(expression.getValueType())) {
            throw new SemanticException(SemanticException.ILLEGAL_RETURN_TYPE + "'" + returnType + "' expected but '" + expression.getValueType() + "' found");
        }
    }

    @Override
    public TemporaryEntry charTypeExpression(Object value) {
        return new TemporaryEntry(CHAR_TYPE);
    }

    @Override
    public TemporaryEntry stringTypeExpression(Object value) {
        return new TemporaryEntry(STRING_TYPE);
    }

    @Override
    public TemporaryEntry booleanTypeExpression(Object value) {
        return new TemporaryEntry(BOOLEAN_TYPE);
    }

    @Override
    public TemporaryEntry floatTypeExpression(Object value) {
        return new TemporaryEntry(FLOAT_TYPE);
    }

    @Override
    public TemporaryEntry integerTypeExpression(Object value) {
        return new TemporaryEntry(INTEGER_TYPE);
    }

    @Override
    public TemporaryEntry newExpression(String className) {
        return new TemporaryEntry(className);
    }

    @Override
    public TemporaryEntry systemOutPrintlnStatement(TemporaryEntry expression) {
        return expression;
    }

    @Override
    public Entry methodCallStatement(TemporaryEntry expression) {
        return expression;
    }

    @Override
    public void block(List<Entry> statements, Entry block) {
    }

    @Override
    public void methodDeclaration(Entry innerBlock) throws SemanticException {
        methodDeclaration();
    }

    @Override
    public void methodDeclaration(String returnType, TemporaryEntry expression, Entry innerBlock) throws SemanticException {
        methodDeclaration(returnType, expression);
    }

    @Override
    public void mainMethodDeclaration(Entry innerBlock, Entry RESULT) throws SemanticException {
        checkNotUsedBreak();
    }

    @Override
    public void block(List<Entry> variablesEntriesList, List<Entry> statements, Entry result) {
    }

    @Override
    public void assignmentStatement(String name) {
    }

    @Override
    public void methodDeclaration(List<Entry> paramsList, Entry result) throws SemanticException {
        methodDeclaration();
    }

    @Override
    public void methodDeclaration(List<Entry> paramsList, Entry innerBlock, Entry result) throws SemanticException {
        methodDeclaration();
    }

    @Override
    public void methodDeclaration(String returnType, List<Entry> paramsList, TemporaryEntry expression, Entry innerBlock) throws SemanticException {
        methodDeclaration(returnType, expression);

    }

    @Override
    public void methodDeclaration(String returnType, List<Entry> paramsList, TemporaryEntry expression, Entry innerBlock, Entry result) throws SemanticException {
        methodDeclaration(returnType, expression);
    }
}
