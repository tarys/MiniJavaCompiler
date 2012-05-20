package general;

import nametable.NameTableBuilder;
import nametable.entries.Entry;
import nametable.entries.TemporaryEntry;
import semantic.SemanticException;

import java.util.List;

public interface Analyzer {
    void checkNotUsedBreak() throws SemanticException;

    TemporaryEntry unaryMinusExpression(TemporaryEntry arg) throws SemanticException;

    void breakExpression() throws SemanticException;

    String isClassDeclared(String className) throws SemanticException;

    TemporaryEntry identifierExpression(String name) throws SemanticException;

    TemporaryEntry orExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry andExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry notEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry equalExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry greaterEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry greaterExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry lowerEqualExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry lowerExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry divideExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry timesExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry minusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry plusExpression(TemporaryEntry arg1, TemporaryEntry arg2) throws SemanticException;

    TemporaryEntry parenthesisExpression(TemporaryEntry arg) throws SemanticException;

    TemporaryEntry exclamationExpression(TemporaryEntry arg) throws SemanticException;

    TemporaryEntry systemReadInExpression() throws SemanticException;

    TemporaryEntry instanceofExpression(TemporaryEntry instanceType, TemporaryEntry classNameEntry) throws SemanticException;

    TemporaryEntry methodCallExpression(String methodName, List<TemporaryEntry> actualParameters) throws SemanticException;

    TemporaryEntry methodCallExpression(String methodName) throws SemanticException;

    TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName, List<TemporaryEntry> actualParameters) throws SemanticException;

    TemporaryEntry methodCallExpression(TemporaryEntry className, String methodName) throws SemanticException;

    TemporaryEntry fieldCallExpression(TemporaryEntry className, String fieldName) throws SemanticException;

    void whileStatement(TemporaryEntry conditionExpression, Entry expression) throws SemanticException;

    void ifStatement(TemporaryEntry conditionExpression, Entry expression) throws SemanticException;

    TemporaryEntry assignmentStatement(String name, TemporaryEntry expression, Entry RESULT) throws SemanticException;

    NameTableBuilder getNameTableBuilder();

    void setNameTableBuilder(NameTableBuilder nameTableBuilder);

    void methodDeclaration() throws SemanticException;

    void methodDeclaration(String returnType, TemporaryEntry expression) throws SemanticException;

    TemporaryEntry charTypeExpression(Object value);

    TemporaryEntry stringTypeExpression(Object value);

    TemporaryEntry booleanTypeExpression(Object value);

    TemporaryEntry floatTypeExpression(Object value);

    TemporaryEntry integerTypeExpression(Object value);

    TemporaryEntry newExpression(String className);

    TemporaryEntry systemOutPrintlnStatement(TemporaryEntry expression);

    Entry methodCallStatement(TemporaryEntry expression);

    void block(List<Entry> statements, Entry block);

    void methodDeclaration(Entry innerBlock) throws SemanticException;

    void methodDeclaration(String returnType, TemporaryEntry expression, Entry innerBlock) throws SemanticException;

    void mainMethodDeclaration(Entry innerBlock, Entry RESULT) throws SemanticException;
}
