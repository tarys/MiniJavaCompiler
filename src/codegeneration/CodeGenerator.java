package codegeneration;

import general.Analyzer;
import general.AnalyzerDecorator;
import nametable.entries.*;
import semantic.SemanticException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CodeGenerator extends AnalyzerDecorator {
    private static int maxTempVariableIndex = 0;
    private Quad breakQuad;
    private List<String> notInitializedEntities;

    private Object getLastQuadResult(Entry arg) {
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
        ListIterator<Quad> iterator = byteCode.listIterator();
        while (iterator.hasNext()) {
            Quad quad = iterator.next();
            switch (quad.getOperation()) {
                case CONST:
                    List<Quad> linkQuads = findLinksInQuads(quad, byteCode);
                    if (!linkQuads.isEmpty() && iterator.hasNext()) {
                        Quad followingQuad = iterator.next();
                        correctLinksInQuads(followingQuad, linkQuads);
                        if (iterator.hasPrevious()) {
                            iterator.previous();
                        }
                    }
                    break;
                default:
                    quad.setQuadNumber(++quadCount);
                    result.add(quad);
            }
        }
        return result;
    }

    private void correctLinksInQuads(Quad followingQuad, List<Quad> byteCode) {
        for (Quad inputQuad : byteCode) {
            if (inputQuad.getOperation().equals(Operation.BZ)) {
                inputQuad.setArgument2(followingQuad);
            } else if (inputQuad.getOperation().equals(Operation.BR)
                    || inputQuad.getOperation().equals(Operation.BRBACK)) {
                inputQuad.setArgument1(followingQuad);
            }
        }
    }

    private List<Quad> findLinksInQuads(Quad quad, List<Quad> byteCode) {
        List<Quad> result = new LinkedList<Quad>();
        for (Quad inputQuad : byteCode) {
            if (inputQuad.getOperation().equals(Operation.BZ)) {
                Quad candidate = (Quad) inputQuad.getArgument2();
                if (candidate.equals(quad)) {
                    result.add(inputQuad);
                }
            } else if (inputQuad.getOperation().equals(Operation.BR)
                    || inputQuad.getOperation().equals(Operation.BRBACK)) {
                Quad candidate = (Quad) inputQuad.getArgument1();
                if (candidate.equals(quad)) {
                    result.add(inputQuad);
                }
            }
        }
        return result;
    }

    public CodeGenerator(Analyzer analyzer) {
        super(analyzer);
        maxTempVariableIndex = 0;
        notInitializedEntities = new LinkedList<String>();
    }

    @Override
    public TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException {
        TemporaryEntry result = getAnalyzer().unaryMinusExpression(arg);
        Quad newQuad = new Quad(Operation.SUB, 0, getLastQuadResult(arg), "T[" + maxTempVariableIndex++ + "]");
        arg.addQuad(newQuad);
        return result;
    }

    @Override
    public BreakEntry breakStatement() throws SemanticException {
        BreakEntry result = getAnalyzer().breakStatement();
        this.breakQuad = new Quad(Operation.BR, null, null, null);
        result.addQuad(breakQuad);
        return result;
    }

    @Override
    public TemporaryEntry identifierExpression(String name) throws SemanticException {
        TemporaryEntry result = getAnalyzer().identifierExpression(name);
        if (notInitializedEntities.contains(name)) {
            throw new SemanticException(SemanticException.ENTITY_DECLARED_BUT_NOT_INITIALIZED + "'" + name + "'");
        }
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
        TemporaryEntry result = getAnalyzer().instanceofExpression(instanceType, classNameEntry);
        result.addQuad(new Quad(Operation.INSTNCF, getLastQuadResult(instanceType), getLastQuadResult(classNameEntry), "T[" + maxTempVariableIndex++ + "]"));
        return result;
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        TemporaryEntry result = getAnalyzer().methodCallExpression(methodName, actualParameters);
        MethodEntry method = result.getCallMethod();
        if ((method != null)) {
            Quad methodCall = new Quad(Operation.MCALL, "THIS", methodName, null);
            Quad returnQuad = new Quad(Operation.RETURN, null, null, null);
            ListIterator<TemporaryEntry> iterator = actualParameters.listIterator(actualParameters.size());
            while (iterator.hasPrevious()) {
                result.addQuad(new Quad(Operation.PUSH, getLastQuadResult(iterator.previous()), null, null));
            }
            result.addQuad(methodCall);
            result.addAllQuads(method.getByteCode());
            Quad popQuad = new Quad(Operation.POP, null, null, "T[" + maxTempVariableIndex + "]");
            returnQuad.setArgument1(popQuad);
            result.addQuad(returnQuad);
            result.addQuad(popQuad);
        }
        return result;
    }

    @Override
    public TemporaryEntry methodCallExpression(String methodName) throws SemanticException {
        return this.methodCallExpression(methodName, new LinkedList<TemporaryEntry>());
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry classObject, String methodName, List<TemporaryEntry> actualParameters) throws SemanticException {
        TemporaryEntry result = getAnalyzer().methodCallExpression(classObject, methodName, actualParameters);
        MethodEntry method = result.getCallMethod();
        if ((method != null)) {
            Quad methodCall = new Quad(Operation.MCALL, getLastQuadResult(classObject), methodName, null);
            Quad returnQuad = new Quad(Operation.RETURN, null, null, null);
            ListIterator<TemporaryEntry> iterator = actualParameters.listIterator(actualParameters.size());
            while (iterator.hasPrevious()) {
                result.addQuad(new Quad(Operation.PUSH, getLastQuadResult(iterator.previous()), null, null));
            }
            result.addQuad(methodCall);
            result.addAllQuads(method.getByteCode());
            Quad popQuad = new Quad(Operation.POP, null, null, "T[" + maxTempVariableIndex + "]");
            returnQuad.setArgument1(popQuad);
            result.addQuad(returnQuad);
            result.addQuad(popQuad);
        }
        return result;
    }

    @Override
    public TemporaryEntry methodCallExpression(TemporaryEntry classObject, String methodName) throws SemanticException {
        return methodCallExpression(classObject, methodName, new LinkedList<TemporaryEntry>());
    }

    @Override
    public TemporaryEntry fieldCallExpression(TemporaryEntry classObject, String fieldName) throws SemanticException {
        TemporaryEntry result = getAnalyzer().fieldCallExpression(classObject, fieldName);
        Quad newQuad = new Quad(Operation.FCALL, getLastQuadResult(classObject), fieldName, "T[" + maxTempVariableIndex++ + "]");
        result.addQuad(newQuad);
        return result;
    }

    @Override
    public void whileStatement(TemporaryEntry conditionExpression, Entry innerBlock, Entry result) throws SemanticException {
        getAnalyzer().whileStatement(conditionExpression, innerBlock, result);
        List<Quad> innerCode = innerBlock.getByteCode();
        Quad firstConditionQuad = conditionExpression.getByteCode().get(0);
        Quad lastInnerQuad = innerCode.get(innerCode.size() - 1);
        result.addAllQuads(conditionExpression.getByteCode());
        Quad returnToConditionQuad = new Quad(Operation.BRBACK, firstConditionQuad, null, null);
        Quad exitWhileQuad = new Quad(Operation.BZ, getLastQuadResult(conditionExpression), returnToConditionQuad, null);
        result.addQuad(exitWhileQuad);
        if (breakQuad != null) {
            breakQuad.setArgument1(returnToConditionQuad);
            breakQuad = null;
        }
        result.addAllQuads(innerBlock.getByteCode());
        result.addQuad(returnToConditionQuad);
    }

    @Override
    public void ifStatement(TemporaryEntry conditionExpression, Entry thenBlock, Entry elseBlock, Entry result) throws SemanticException {
        getAnalyzer().ifStatement(conditionExpression, thenBlock, elseBlock, result);
        List<Quad> thenCode = thenBlock.getByteCode();
        Quad lastThenQuad = thenCode.get(thenCode.size() - 1);
        result.addAllQuads(conditionExpression.getByteCode());
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
    public void assignmentStatement(String name) {
        getAnalyzer().assignmentStatement(name);
        notInitializedEntities.add(name);
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
        if (notInitializedEntities.contains(name)) {
            notInitializedEntities.remove(name);
        }
        Quad newQuad = new Quad(Operation.STORE, getLastQuadResult(expression), null, "'" + name + "'");
        if (maxTempVariableIndex > 0) {
            // decreace max index because we have saved value to memory
            maxTempVariableIndex--;
        }
        expression.addQuad(newQuad);
        return expression;
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
        TemporaryEntry result = getAnalyzer().newExpression(className);
        Quad newQuad = new Quad(Operation.OBJ, className, null, "T[" + maxTempVariableIndex++ + "]");
        result.addQuad(newQuad);
        return result;
    }

    @Override
    public TemporaryEntry systemOutPrintlnStatement(TemporaryEntry expression) {
        expression.addQuad(new Quad(Operation.PRINTLN, getLastQuadResult(expression), null, null));
        return expression;
    }

    @Override
    public Entry methodCallStatement(TemporaryEntry expression) {
        getAnalyzer().methodCallStatement(expression);
        return expression;
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
    public void mainMethodDeclaration(Entry innerBlock, Entry result) throws SemanticException {
        getAnalyzer().mainMethodDeclaration(innerBlock, result);
        innerBlock.addQuad(new Quad(Operation.RETURN, null, null, null));
        List<Quad> code = innerBlock.getByteCode();
        code = optimizeCode(code);
        result.addAllQuads(code) ;
    }

    @Override
    public void methodDeclaration(String returnType, List<Entry> paramsList, TemporaryEntry expression, Entry innerBlock, Entry result) throws SemanticException {
        getAnalyzer().methodDeclaration(returnType, paramsList, expression, innerBlock, result);
        ListIterator<Entry> iterator = paramsList.listIterator();
        while (iterator.hasNext()) {
            MethodParameterEntry param = (MethodParameterEntry) iterator.next();
            result.addQuad(new Quad(Operation.POP, null, null, "T[" + maxTempVariableIndex + "]"));
            result.addQuad(new Quad(Operation.STORE, "T[" + maxTempVariableIndex + "]", null, "'" + param.getName() + "'"));
        }
        result.addAllQuads(innerBlock.getByteCode());
        result.addAllQuads(expression.getByteCode());
        Object lastQuadResult = getLastQuadResult(expression);
        result.addQuad(new Quad(Operation.PUSH, lastQuadResult, null, null));
    }
}
