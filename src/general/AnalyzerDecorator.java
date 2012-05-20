package general;

import nametable.NameTableBuilder;
import nametable.entries.Entry;
import nametable.entries.TemporaryEntry;
import semantic.SemanticException;

import java.util.List;

public class AnalyzerDecorator implements Analyzer {
    private Analyzer analyzer;

    public AnalyzerDecorator(Analyzer analyzer) {
        this.setAnalyzer(analyzer);
    }

    protected Analyzer getAnalyzer() {
        return analyzer;
    }

    protected void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void checkNotUsedBreak() throws SemanticException {
        getAnalyzer().checkNotUsedBreak();
    }

    @Override
    public TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException {
        return getAnalyzer().unaryMinusExpression(arg);
    }

    @Override
    public void breakExpression() throws SemanticException {
        getAnalyzer().breakExpression();
    }

    @Override
    public String isClassDeclared(String className) throws SemanticException {
        return getAnalyzer().isClassDeclared(className);
    }

    @Override
    public TemporaryEntry identifierExpression(String name) throws SemanticException {
        return getAnalyzer().identifierExpression(name);
    }

    @Override
    public TemporaryEntry orExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().orExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry andExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().andExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry notEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().notEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry equalExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().equalExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry greaterEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().greaterEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry greaterExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().greaterExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry lowerEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().lowerEqualExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry lowerExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().lowerExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry divideExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().divideExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry timesExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().timesExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry minusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().minusExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry plusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return getAnalyzer().plusExpression(arg1, arg2);
    }

    @Override
    public TemporaryEntry parenthesisExpression(TemporaryEntry arg) throws SemanticException {
        return getAnalyzer().parenthesisExpression(arg);
    }

    @Override
    public TemporaryEntry exclamationExpression(TemporaryEntry arg) throws SemanticException {
        return getAnalyzer().exclamationExpression(arg);
    }

    @Override
    public TemporaryEntry systemReadInExpression() throws SemanticException {
        return getAnalyzer().systemReadInExpression();
    }

    @Override
    public TemporaryEntry instanceofExpression(TemporaryEntry instanceType, TemporaryEntry classNameEntry) throws SemanticException {
        return getAnalyzer().instanceofExpression(instanceType, classNameEntry);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        return getAnalyzer().methodCallExpression(methodName, actualParameters);
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName) throws SemanticException {
        return getAnalyzer().methodCallExpression(methodName);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        return getAnalyzer().methodCallExpression(className, methodName, actualParameters);
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName) throws SemanticException {
        return getAnalyzer().methodCallExpression(className, methodName);
    }

    @Override
    public TemporaryEntry fieldCallExpression(TemporaryEntry className, String fieldName) throws SemanticException {
        return getAnalyzer().fieldCallExpression(className, fieldName);
    }

    @Override
    public void whileStatement(TemporaryEntry conditionExpression, Entry expression) throws SemanticException {
        getAnalyzer().whileStatement(conditionExpression, expression);
    }

    @Override
    public void ifStatement(TemporaryEntry conditionExpression, Entry expression) throws SemanticException {
        getAnalyzer().ifStatement(conditionExpression, expression);
    }

    @Override
    public TemporaryEntry assignmentStatement(String name, TemporaryEntry expression, Entry result) throws SemanticException {
        return getAnalyzer().assignmentStatement(name, expression, result);
    }

    @Override
    public NameTableBuilder getNameTableBuilder() {
        return getAnalyzer().getNameTableBuilder();
    }

    @Override
    public void setNameTableBuilder(NameTableBuilder nameTableBuilder) {
        getAnalyzer().setNameTableBuilder(nameTableBuilder);
    }

    @Override
    public void methodDeclaration() throws SemanticException {
        getAnalyzer().methodDeclaration();
    }

    @Override
    public void methodDeclaration(String returnType, TemporaryEntry expression) throws SemanticException {
        getAnalyzer().methodDeclaration(returnType, expression);
    }

    @Override
    public TemporaryEntry charTypeExpression(Object value) {
        return getAnalyzer().charTypeExpression(value);
    }

    @Override
    public TemporaryEntry stringTypeExpression(Object value) {
        return getAnalyzer().stringTypeExpression(value);
    }

    @Override
    public TemporaryEntry booleanTypeExpression(Object value) {
        return getAnalyzer().booleanTypeExpression(value);
    }

    @Override
    public TemporaryEntry floatTypeExpression(Object value) {
        return getAnalyzer().floatTypeExpression(value);
    }

    @Override
    public TemporaryEntry integerTypeExpression(Object value) {
        return getAnalyzer().integerTypeExpression(value);
    }

    @Override
    public TemporaryEntry newExpression(String className) {
        return getAnalyzer().newExpression(className);
    }

    @Override
    public TemporaryEntry systemOutPrintlnStatement(TemporaryEntry expression) {
        return getAnalyzer().systemOutPrintlnStatement(expression);
    }

    @Override
    public Entry methodCallStatement(TemporaryEntry expression) {
        return getAnalyzer().methodCallStatement(expression);
    }

    @Override
    public void block(List<Entry> statements, Entry block) {
        getAnalyzer().block(statements, block);
    }

    @Override
    public void methodDeclaration(Entry innerBlock) throws SemanticException {
        getAnalyzer().methodDeclaration(innerBlock);
    }

    @Override
    public void methodDeclaration(String returnType, TemporaryEntry expression, Entry innerBlock) throws SemanticException {
        getAnalyzer().methodDeclaration(returnType, expression, innerBlock);
    }

    @Override
    public void mainMethodDeclaration(Entry innerBlock, Entry result) throws SemanticException {
        getAnalyzer().mainMethodDeclaration(innerBlock, result);
    }
}
