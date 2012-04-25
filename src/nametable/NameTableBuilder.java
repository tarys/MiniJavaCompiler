package nametable;

import sa.SymbolsInfo;

import java.util.List;

/**
 * responsible for correct bui
 */
public class NameTableBuilder {
    private NameTable nameTable;
    private int blockCounter;

    public NameTableBuilder(NameTable nameTable) {
        this.setNameTable(nameTable);
        this.blockCounter = 0;
    }

    public void addReferenceType(String name) {

    }

    public NameTable getNameTable() {
        return nameTable;
    }

    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
    }

    public Entry declareEntity(String name, Integer valueType) throws NameTableException {
        return declareEntity(name, valueType, null);
    }

    public Entry declareEntity(String name, Integer valueType, Object value) throws NameTableException {
        if (!getNameTable().containsAtTopLevel(name)) {
            Entry newEntry = new Entry(name, valueType, value, null);
            getNameTable().addTopLevelEntry(newEntry);
            return newEntry;
        } else {
            throw new NameTableException(getNameTable().getTopLevelEntry(name));
        }
    }

    public Entry declareBlock(List<Entry> variablesEntriesList) throws NameTableException {
        // declaring new block
        Entry declaredBlock = declareEntity("block#" + ++blockCounter, SymbolsInfo.block);
        declaredBlock.setChildren(variablesEntriesList);
        getNameTable().removeFromTopLevel(variablesEntriesList);

        return declaredBlock;
    }

    public Entry declareMainMethod(Entry innerBlockName) throws NameTableException {
        Entry mainMethodEntry = declareEntity("main", SymbolsInfo.main_method_declaration);
        mainMethodEntry.addChild(innerBlockName);
        getNameTable().removeFromTopLevel(innerBlockName);

        return mainMethodEntry;
    }

    public Entry declareMethod(String name, List<Entry> paramsList, Integer returnTypeId, Entry innerBlock) throws NameTableException {
        Entry declaredMethod = declareEntity(name, SymbolsInfo.method_declaration, returnTypeId);
        if (paramsList != null) {
            declaredMethod.setChildren(paramsList);
            getNameTable().removeFromTopLevel(paramsList);
        }
        if (innerBlock != null) {
            declaredMethod.addChild(innerBlock);
            getNameTable().removeFromTopLevel(innerBlock);
        }

        return declaredMethod;
    }

    public Entry declareMethod(String name, List<Entry> paramsList, Integer returnTypeId) throws NameTableException {
        return declareMethod(name, paramsList, returnTypeId, null);
    }
}
