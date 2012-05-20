package general;

import nametable.NameTableBuilder;
import nametable.entries.TemporaryEntry;
import semantic.SemanticException;

import java.util.List;

public class AnalyzerDecorator implements Analyzer {
    private Analyzer analyzer;

    public AnalyzerDecorator(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void checkNotUsedBreak() throws SemanticException {
        analyzer.checkNotUsedBreak();
    }

    @Override
    public TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException {
        return analyzer.unaryMinusExpression(arg);
    }

    @Override
    public void breakExpression() throws SemanticException {
        analyzer.breakExpression();
    }

    @Override
    public String isClassDeclared(String className) throws SemanticException {
        return analyzer.isClassDeclared(className);
    }

    @Override
    public TemporaryEntry identifierExpression(String name) throws SemanticException {
        return analyzer.identifierExpression(name);
    }

    @Override
    public TemporaryEntry orExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.orExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry andExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.andExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry notEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.notEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry equalExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.equalExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry greaterEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.greaterEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry greaterExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.greaterExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry lowerEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.lowerEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry lowerExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.lowerExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry divideExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.divideExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry timesExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.timesExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry minusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.minusExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry plusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return analyzer.plusExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry parenthesisExpression(TemporaryEntry arg) throws SemanticException {
        return analyzer.parenthesisExpression(arg);
    }

    @Override
    public TemporaryEntry exclamationExpression(TemporaryEntry arg) throws SemanticException {
        return analyzer.exclamationExpression(arg);
    }

    @Override
    public TemporaryEntry systemReadInExpression() throws SemanticException {
        return analyzer.systemReadInExpression();
    }

    @Override
    public TemporaryEntry instanceofExpression(TemporaryEntry instanceType, TemporaryEntry classNameEntry) throws SemanticException {
        return analyzer.instanceofExpression(instanceType, classNameEntry);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        return analyzer.methodCallExpression(methodName, actualParameters);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName) throws SemanticException {
        return analyzer.methodCallExpression(methodName);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        return analyzer.methodCallExpression(className, methodName, actualParameters);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName) throws SemanticException {
        return analyzer.methodCallExpression(className, methodName);
    }

    @Override
    public TemporaryEntry fieldCallExpression(TemporaryEntry className, String fieldName) throws SemanticException {
        return analyzer.fieldCallExpression(className, fieldName);
    }

    @Override
    public void whileStatement(TemporaryEntry conditionExpression) throws SemanticException {
        analyzer.whileStatement(conditionExpression);
    }

    @Override
    public void ifStatement(TemporaryEntry conditionExpression) throws SemanticException {
        analyzer.ifStatement(conditionExpression);
    }

    @Override
    public void assignmentStatement(String name, TemporaryEntry expression) throws SemanticException {
       analyzer.assignmentStatement(name,expression);
    }

    @Override
    public NameTableBuilder getNameTableBuilder() {
        return analyzer.getNameTableBuilder();
    }

    @Override
    public void setNameTableBuilder(NameTableBuilder nameTableBuilder) {
        analyzer.setNameTableBuilder(nameTableBuilder);
    }

    @Override
    public void methodDeclaration() throws SemanticException {
        analyzer.methodDeclaration();
    }

    @Override
    public void methodDeclaration(String returnType, TemporaryEntry expression) throws SemanticException {
        analyzer.methodDeclaration(returnType,expression);
    }

    @Override
    public TemporaryEntry charTypeExpression(Object value) {
        return analyzer.charTypeExpression(value);
    }

    @Override
    public TemporaryEntry stringTypeExpression(Object value) {
        return analyzer.stringTypeExpression(value);
    }

    @Override
    public TemporaryEntry booleanTypeExpression(Object value) {
        return analyzer.booleanTypeExpression(value);
    }

    @Override
    public TemporaryEntry floatTypeExpression(Object value) {
        return analyzer.floatTypeExpression(value);
    }

    @Override
    public TemporaryEntry integerTypeExpression(Object value) {
        return analyzer.integerTypeExpression(value);
    }

    @Override
    public TemporaryEntry newExpression(String className) {
        return analyzer.newExpression(className);
    }
}
