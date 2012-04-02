package nametable;

import sa.SymbolsInfo;

import java.util.LinkedList;
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


    public Entry declareEntity(Integer typeId, String name) throws NameTableException {
        return declareEntity(typeId, name, null);
    }

    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
    }

    public Entry declareEntity(Integer typeId, String name, Object value) throws NameTableException {
        if (!getNameTable().containsAtTopLevel(name)) {
            Entry newEntry = new Entry(name, typeId, value, null);
            newEntry.setDeclared(true);
            getNameTable().addTopLevelEntry(newEntry);
            return newEntry;
        } else {
            throw new NameTableException(getNameTable().getTopLevelEntry(name));
        }
    }

    public Entry declareBlock(List<Entry> variablesEntriesList) throws NameTableException {
        // declaring new block
        Entry declaredBlock = declareEntity(SymbolsInfo.block, "block#" + ++blockCounter);
        declaredBlock.setChildren(variablesEntriesList);
        getNameTable().removeFromTopLevel(variablesEntriesList);

        return declaredBlock;
    }

    public Entry declareMainMethod(Entry innerBlockName) throws NameTableException {
        Entry mainMethodEntry = declareEntity(SymbolsInfo.main_method_declaration, "main");
        mainMethodEntry.addChild(innerBlockName);
        getNameTable().removeFromTopLevel(innerBlockName);

        return mainMethodEntry;
    }

    public Entry declareMethod(String name, List<Entry> paramsList, Integer returnTypeId, Entry innerBlock) throws NameTableException {
        Entry declaredMethod = declareEntity(SymbolsInfo.method_declaration, name, returnTypeId);
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
