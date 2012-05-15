package nametable;

import nametable.entries.*;

import java.util.LinkedList;
import java.util.List;

/**
 * responsible for correct building of name table
 */
public class NameTableBuilder {
    private List<Entry> nameTable;
    private List<Entry> topLevelEntries;

    public NameTableBuilder() {
        this.topLevelEntries = new LinkedList<Entry>();
    }

    public List<Entry> getNameTable() {
        return topLevelEntries;
    }

    public void declareProgram(List<Entry> classes) throws NameTableException {
        Entry program = new ProgramEntry();
        program.addChildren(classes);
        removeFromTopLevel(classes);
        addTopLevelEntry(program);
    }

    public Entry declareMainClass(String name, List<? extends Entry> fieldsList, List<? extends Entry> methodsList) throws NameTableException {
        Entry declaredMainClass = new MainClassEntry(name);
        if (fieldsList != null) {
            declaredMainClass.addChildren(fieldsList);
            removeFromTopLevel(fieldsList);
        }
        if (methodsList != null) {
            declaredMainClass.addChildren(methodsList);
            removeFromTopLevel(methodsList);
        }
        addTopLevelEntry(declaredMainClass);

        return declaredMainClass;
    }

    public Entry declareMainMethod(Entry innerBlock) throws NameTableException {
        Entry mainMethodEntry = new MainMethodEntry();
        mainMethodEntry.addChild(innerBlock);
        removeFromTopLevel(innerBlock);
        addTopLevelEntry(mainMethodEntry);

        return mainMethodEntry;
    }

    public Entry declareClass(String name, List<? extends Entry> fieldsList, List<? extends Entry> methodsList) throws NameTableException {
        Entry declaredClass = new ClassEntry(name);
        if (fieldsList != null) {
            declaredClass.addChildren(fieldsList);
            removeFromTopLevel(fieldsList);
        }
        if (methodsList != null) {
            declaredClass.addChildren(methodsList);
            removeFromTopLevel(methodsList);
        }
        addTopLevelEntry(declaredClass);

        return declaredClass;
    }

    public Entry declareField(String name, String type) throws NameTableException {
        Entry declaredField = new FieldEntry(name, type);
        addTopLevelEntry(declaredField);

        return declaredField;
    }

    public Entry declareMethod(String name, String returnType, List<? extends Entry> paramsList, Entry innerBlock) throws NameTableException {
        Entry declaredMethod = new MethodEntry(name, returnType);
        if (paramsList != null) {
            declaredMethod.addChildren(paramsList);
            removeFromTopLevel(paramsList);
        }
        if (innerBlock != null) {
            declaredMethod.addChild(innerBlock);
            removeFromTopLevel(innerBlock);
        }
        addTopLevelEntry(declaredMethod);

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
        if (variablesEntriesList != null && !variablesEntriesList.isEmpty()) {
            declaredBlock.addChildren(variablesEntriesList);
            removeFromTopLevel(variablesEntriesList);
        }
        if (statements != null && !statements.isEmpty()) {
            declaredBlock.addChildren(statements);
            removeFromTopLevel(statements);
        }
        addTopLevelEntry(declaredBlock);

        return declaredBlock;
    }

    public Entry declareBlock(List<? extends Entry> variablesEntriesList) throws NameTableException {
        return declareBlock(variablesEntriesList, null);
    }

    public Entry declareVariable(String name, String type) throws NameTableException {
        Entry declaredVariable = new VariableEntry(name, type);
        addTopLevelEntry(declaredVariable);

        return declareVariable(name, type);
    }

    public Entry declareWhileStatement(Entry innerBlock) {
        Entry whileEntry = new WhileEntry();
        whileEntry.addChild(innerBlock);
        removeFromTopLevel(innerBlock);

        return whileEntry;
    }

    public Entry declareIfStatement(Entry thenBlock, Entry elseBlock) {
        IfEntry ifEntry = new IfEntry();
        ifEntry.setThenBlock((BlockEntry) thenBlock);
        removeFromTopLevel(thenBlock);
        if (elseBlock != null) {
            ifEntry.setTElseBlock((BlockEntry) elseBlock);
            removeFromTopLevel(elseBlock);
        }

        return ifEntry;
    }

    public Entry declareIfStatement(Entry thenBlock) {
        return declareIfStatement(thenBlock, null);
    }

    public List<Entry> lookUp(String entryName) {
        List<Entry> result = new LinkedList<Entry>();
        for (Entry topLevelEntry : topLevelEntries) {
            if (topLevelEntry.getName().equals(entryName)) {
                result.add(topLevelEntry);
            }
        }
        return result;
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
