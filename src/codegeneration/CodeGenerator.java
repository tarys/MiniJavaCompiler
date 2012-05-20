package codegeneration;

import general.Analyzer;
import general.AnalyzerDecorator;
import nametable.entries.Entry;
import nametable.entries.TemporaryEntry;
import semantic.SemanticException;

import java.util.LinkedList;
import java.util.List;

public class CodeGenerator extends AnalyzerDecorator {
    private static int maxTempVariableIndex = 0;

    private Object getLastQuadResult(TemporaryEntry arg) {
        List<Quad> code = arg.getByteCode();
        Quad lastQuad = code.get(code.size() - 1);
        return lastQuad.getResult();
    }

    private TemporaryEntry generateConstCode(Object value, TemporaryEntry entry) {
        entry.addQuad(new Quad(Operation.CONST, null, null, value));
        return entry;
    }

    private TemporaryEntry generateBinaryOperation(TemporaryEntry arg1, TemporaryEntry arg2, Operation operation, TemporaryEntry result) throws SemanticException {
        result.addAllQuads(arg1.getByteCode());
        result.addAllQuads(arg2.getByteCode());
        Quad newQuad = new Quad(operation, getLastQuadResult(arg1), getLastQuadResult(arg2), "T[" + maxTempVariableIndex++ + "]");
        result.addQuad(newQuad);
        return result;
    }

    private List<Quad> optimizeCode(List<Quad> byteCode) {
        List<Quad> result = new LinkedList<Quad>();
        int quadCount = 0;
        for (Quad quad : byteCode) {
            switch (quad.getOperation()) {
                case CONST:
                    break;
                default:
                    quad.setQuadNumber(++quadCount);
                    result.add(quad);
            }
        }
        return result;
    }

    public CodeGenerator(Analyzer analyzer) {
        super(analyzer);
        maxTempVariableIndex = 0;
    }

    @Override
    public TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException {
        TemporaryEntry result = getAnalyzer().unaryMinusExpression(arg);
        Quad newQuad = new Quad(Operation.SUB, 0, getLastQuadResult(arg), "T[" + maxTempVariableIndex++ + "]");
        arg.addQuad(newQuad);
        return result;
    }

    @Override
    public void breakExpression() throws SemanticException {
        getAnalyzer().breakExpression();
    }

    @Override
    public TemporaryEntry identifierExpression(String name) throws SemanticException {
        TemporaryEntry result = getAnalyzer().identifierExpression(name);
        result.getByteCode().add(new Quad(Operation.CONST, null, null, "'" + name + "'"));
        return result;
    }

    @Override
    public TemporaryEntry orExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.OR, getAnalyzer().orExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry andExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.AND, getAnalyzer().andExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry notEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.NEQ, getAnalyzer().notEqualExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry equalExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.EQ, getAnalyzer().equalExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry greaterEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.GE, getAnalyzer().greaterEqualExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry greaterExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.GT, getAnalyzer().greaterExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry lowerEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.LE, getAnalyzer().lowerEqualExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry lowerExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.LT, getAnalyzer().lowerExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry divideExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.DIV, getAnalyzer().divideExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry timesExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.MULT, getAnalyzer().timesExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry minusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.SUB, getAnalyzer().minusExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry plusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException {
        return generateBinaryOperation(arg1, arg2, Operation.ADD, getAnalyzer().plusExpression(arg1, arg2));
    }

    @Override
    public TemporaryEntry parenthesisExpression(TemporaryEntry arg) throws SemanticException {
        return getAnalyzer().parenthesisExpression(arg);
    }

    @Override
    public TemporaryEntry exclamationExpression(TemporaryEntry arg) throws SemanticException {
        TemporaryEntry result = getAnalyzer().exclamationExpression(arg);
        result.addAllQuads(arg.getByteCode());
        Quad newQuad = new Quad(Operation.NOT, getLastQuadResult(arg), null, "T[" + maxTempVariableIndex++ + "]");
        result.addQuad(newQuad);
        return result;
    }

    @Override
    public TemporaryEntry systemReadInExpression() throws SemanticException {
        TemporaryEntry result = getAnalyzer().systemReadInExpression();
        Quad newQuad = new Quad(Operation.READ, null, null, "T[" + maxTempVariableIndex++ + "]");
        result.addQuad(newQuad);
        return result;
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
        TemporaryEntry result = getAnalyzer().methodCallExpression(className, methodName);
        return result;
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
    public void ifStatement(TemporaryEntry conditionExpression, Entry thenBlock, Entry elseBlock, Entry result) throws SemanticException {
        getAnalyzer().ifStatement(conditionExpression, thenBlock, elseBlock, result);
        List<Quad> thenCode = thenBlock.getByteCode();
        Quad lastThenQuad = thenCode.get(thenCode.size() - 1);
        Quad ifFalseQuad = new Quad(Operation.BZ, getLastQuadResult(conditionExpression), lastThenQuad, null);
        result.addQuad(ifFalseQuad);
        result.addAllQuads(thenCode);
        if (elseBlock != null) {
            List<Quad> elseCode = elseBlock.getByteCode();
            Quad lastElseQuad = elseCode.get(elseCode.size() - 1);
            Quad elseOverJumpQuad = new Quad(Operation.BR, lastElseQuad, null, null);
            ifFalseQuad.setArgument2(elseOverJumpQuad);
            result.addQuad(elseOverJumpQuad);
            result.addAllQuads(elseCode);
        }
    }

    @Override
    public TemporaryEntry assignmentStatement(String name, TemporaryEntry expression, Entry result) throws SemanticException {
        assignmentStatement(name, expression);
        result.addAllQuads(expression.getByteCode());
        return expression;
    }

    @Override
    public TemporaryEntry assignmentStatement(String name, TemporaryEntry expression) throws SemanticException {
        getAnalyzer().assignmentStatement(name, expression);
        Quad newQuad = new Quad(Operation.STORE, getLastQuadResult(expression), null, "'" + name + "'");
        if (maxTempVariableIndex > 0) {
            // decreace max index because we have saved value to memory
            maxTempVariableIndex--;
        }
        expression.addQuad(newQuad);
        return expression;
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
        return generateConstCode(value, getAnalyzer().charTypeExpression(value));
    }

    @Override
    public TemporaryEntry stringTypeExpression(Object value) {
        return generateConstCode(value, getAnalyzer().stringTypeExpression(value));
    }

    @Override
    public TemporaryEntry booleanTypeExpression(Object value) {
        return generateConstCode(value, getAnalyzer().booleanTypeExpression(value));
    }

    @Override
    public TemporaryEntry floatTypeExpression(Object value) {
        return generateConstCode(value, getAnalyzer().floatTypeExpression(value));
    }

    @Override
    public TemporaryEntry integerTypeExpression(Object value) {
        return generateConstCode(value, getAnalyzer().integerTypeExpression(value));
    }

    @Override
    public TemporaryEntry newExpression(String className) {
        return getAnalyzer().newExpression(className);
    }

    @Override
    public TemporaryEntry systemOutPrintlnStatement(TemporaryEntry expression) {
        expression.addQuad(new Quad(Operation.PRINTLN, getLastQuadResult(expression), null, null));
        return expression;
    }

    @Override
    public Entry methodCallStatement(TemporaryEntry expression) {
        return super.methodCallStatement(expression);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void block(List<Entry> statements, Entry block) {
        getAnalyzer().block(statements, block);
        for (Entry statement : statements) {
            List<Quad> code = block.getByteCode();
            code.addAll(statement.getByteCode());
        }
    }

    @Override
    public void block(List<Entry> variablesEntriesList, List<Entry> statements, Entry block) {
        getAnalyzer().block(variablesEntriesList, statements, block);
        for (Entry variable : variablesEntriesList) {
            List<Quad> code = block.getByteCode();
            code.addAll(variable.getByteCode());
        }
        for (Entry statement : statements) {
            List<Quad> code = block.getByteCode();
            code.addAll(statement.getByteCode());
        }
    }

    @Override
    public void methodDeclaration(Entry innerBlock) throws SemanticException {
        getAnalyzer().methodDeclaration(innerBlock);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void mainMethodDeclaration(Entry innerBlock, Entry result) throws SemanticException {
        getAnalyzer().mainMethodDeclaration(innerBlock, result);
        List<Quad> code = optimizeCode(innerBlock.getByteCode());
        result.addAllQuads(code);
    }

}
