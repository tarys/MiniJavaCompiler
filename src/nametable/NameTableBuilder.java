package nametable;

import nametable.entries.*;

import java.util.List;

/**
 * responsible for correct bui
 */
public class NameTableBuilder {
    private NameTable nameTable;

    public NameTableBuilder(NameTable nameTable) {
        this.setNameTable(nameTable);
    }

    public NameTable getNameTable() {
        return nameTable;
    }

    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
    }


    public Entry declareField(String name, String type, Object value) {

        Entry declaredField = new FieldEntry(name, type, value);
        getNameTable().addTopLevelEntry(declaredField);
        return declaredField;
    }

    public Entry declareField(String name, String type) {
        return declareField(name, type, null);
    }

    public Entry declareMainMethod(Entry innerBlock) throws NameTableException {
        Entry mainMethodEntry = new MainMethodEntry();
        getNameTable().addTopLevelEntry(mainMethodEntry);
        mainMethodEntry.addChild(innerBlock);
        getNameTable().removeFromTopLevel(innerBlock);

        return mainMethodEntry;
    }

    public Entry declareMethod(String name, String returnType, List<? extends Entry> paramsList, Entry innerBlock) throws NameTableException {
        Entry declaredMethod = new MethodEntry(name, returnType);
        getNameTable().addTopLevelEntry(declaredMethod);
        if (paramsList != null) {
            declaredMethod.addChildren(paramsList);
            getNameTable().removeFromTopLevel(paramsList);
        }
        if (innerBlock != null) {
            declaredMethod.addChild(innerBlock);
            getNameTable().removeFromTopLevel(innerBlock);
        }

        return declaredMethod;
    }

    public Entry declareMethod(String name, String returnType, List<? extends Entry> paramsList) throws NameTableException {
        return declareMethod(name, returnType, paramsList, null);
    }

    public Entry declareMethodParameter(String name, String type) {
        Entry declaredMethodParameter = new MethodParameterEntry(name, type);
        getNameTable().addTopLevelEntry(declaredMethodParameter);
        return declaredMethodParameter;
    }

    public Entry declareBlock(List<? extends Entry> variablesEntriesList) throws NameTableException {
        Entry declaredBlock = new BlockEntry();
        getNameTable().addTopLevelEntry(declaredBlock);
        declaredBlock.addChildren(variablesEntriesList);
        getNameTable().removeFromTopLevel(variablesEntriesList);

        return declaredBlock;
    }

    public Entry declareBlock() throws NameTableException {
        return declareBlock(null);
    }

    public Entry declareVariable(String name, String type, Object value) {
        Entry declaredVariable = new VariableEntry(name, type, value);
        getNameTable().addTopLevelEntry(declaredVariable);
        return declaredVariable;
    }

    public Entry declareVariable(String name, String type) {
        return declareVariable(name, type, null);
    }
}
