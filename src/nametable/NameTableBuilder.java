package nametable;

import nametable.entries.*;

import java.util.LinkedList;
import java.util.List;

/**
 * responsible for correct building of name table
 */
public class NameTableBuilder {
    private Entry nameTable;
    private List<Entry> topLevelEntries;

    public NameTableBuilder() {
        this.topLevelEntries = new LinkedList<Entry>();
    }

    public Entry getNameTable() {
        return nameTable;
    }

    public void setNameTable(Entry nameTable) {
        this.nameTable = nameTable;
    }

    public void declareProgram(List<Entry> classes) throws NameTableException {
        Entry program = new ProgramEntry();
        addTopLevelEntry(program);
        program.addChildren(classes);
        removeFromTopLevel(classes);
        setNameTable(program);
    }

    public Entry declareMainClass(String name, List<? extends Entry> fieldsList, List<? extends Entry> methodsList) throws NameTableException {
        Entry declaredMainClass = new MainClassEntry(name);
        addTopLevelEntry(declaredMainClass);
        if (fieldsList != null) {
            declaredMainClass.addChildren(fieldsList);
            removeFromTopLevel(fieldsList);
        }
        if (methodsList != null) {
            declaredMainClass.addChildren(methodsList);
            removeFromTopLevel(methodsList);
        }
        return declaredMainClass;
    }

    public Entry declareMainMethod(Entry innerBlock) throws NameTableException {
        Entry mainMethodEntry = new MainMethodEntry();
        addTopLevelEntry(mainMethodEntry);
        mainMethodEntry.addChild(innerBlock);
        removeFromTopLevel(innerBlock);

        return mainMethodEntry;
    }

    public Entry declareClass(String name, List<? extends Entry> fieldsList, List<? extends Entry> methodsList) throws NameTableException {
        Entry declaredClass = new ClassEntry(name);
        addTopLevelEntry(declaredClass);
        if (fieldsList != null) {
            declaredClass.addChildren(fieldsList);
            removeFromTopLevel(fieldsList);
        }
        if (methodsList != null) {
            declaredClass.addChildren(methodsList);
            removeFromTopLevel(methodsList);
        }
        return declaredClass;
    }

    public Entry declareField(String name, String type, Object value) throws NameTableException {

        Entry declaredField = new FieldEntry(name, type, value);
        addTopLevelEntry(declaredField);
        return declaredField;
    }

    public Entry declareField(String name, String type) throws NameTableException {
        return declareField(name, type, null);
    }

    public Entry declareMethod(String name, String returnType, List<? extends Entry> paramsList, Entry innerBlock) throws NameTableException {
        Entry declaredMethod = new MethodEntry(name, returnType);
        addTopLevelEntry(declaredMethod);
        if (paramsList != null) {
            declaredMethod.addChildren(paramsList);
            removeFromTopLevel(paramsList);
        }
        if (innerBlock != null) {
            declaredMethod.addChild(innerBlock);
            removeFromTopLevel(innerBlock);
        }

        return declaredMethod;
    }

    public Entry declareMethod(String name, String returnType, List<? extends Entry> paramsList) throws NameTableException {
        return declareMethod(name, returnType, paramsList, null);
    }

    public Entry declareMethodParameter(String name, String type) throws NameTableException {
        Entry declaredMethodParameter = new MethodParameterEntry(name, type);
        addTopLevelEntry(declaredMethodParameter);
        return declaredMethodParameter;
    }

    public Entry declareBlock(List<? extends Entry> variablesEntriesList, List<? extends Entry> statements) throws NameTableException {
        Entry declaredBlock = new BlockEntry();
        addTopLevelEntry(declaredBlock);
        if (variablesEntriesList != null && !variablesEntriesList.isEmpty()) {
            declaredBlock.addChildren(variablesEntriesList);
            removeFromTopLevel(variablesEntriesList);
        }
        if (statements != null && !statements.isEmpty()) {
            declaredBlock.addChildren(statements);
            removeFromTopLevel(statements);
        }

        return declaredBlock;
    }

    public Entry declareBlock(List<? extends Entry> variablesEntriesList) throws NameTableException {
        return declareBlock(variablesEntriesList, null);
    }

    public Entry declareVariable(String name, String type, Object value) throws NameTableException {
        Entry declaredVariable = new VariableEntry(name, type, value);
        addTopLevelEntry(declaredVariable);
        return declaredVariable;
    }

    public Entry declareVariable(String name, String type) throws NameTableException {
        return declareVariable(name, type, null);
    }

    public Entry declareWhileStatement(Entry innerBlock) {
        Entry whileEntry = new BlockEntry();
        whileEntry.addChild(innerBlock);
        removeFromTopLevel(innerBlock);

        return whileEntry;
    }

    public Entry declareIfStatement(Entry thenBlock, Entry elseBlock) {
        Entry ifEntry = new BlockEntry();
        ifEntry.addChild(thenBlock);
        removeFromTopLevel(thenBlock);
        if (elseBlock != null) {
            ifEntry.addChild(elseBlock);
            removeFromTopLevel(elseBlock);
        }
        return ifEntry;
    }

    public Entry declareIfStatement(Entry thenBlock) {
        return declareIfStatement(thenBlock, null);
    }

    private List<Entry> getTopLevelEntries() {
        return topLevelEntries;
    }

    private void addTopLevelEntry(Entry entry) throws NameTableException {
        List<Entry> topEntries = getTopLevelEntries();
        for (Entry topEntry : topEntries) {
            if (topEntry.equals(entry)) {
                throw new NameTableException(entry);
            }
        }
        topEntries.add(entry);
    }

    private void removeFromTopLevel(Entry entry) {
        if (entry != null) {
            getTopLevelEntries().remove(entry);
        }
    }

    private void removeFromTopLevel(List<? extends Entry> entries) {
        if (entries != null) {
            getTopLevelEntries().removeAll(entries);
        }
    }
}
