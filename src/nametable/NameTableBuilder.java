package nametable;

import java.util.List;

/**
 * responsible for correct bui
 */
public class NameTableBuilder {
    private List<Entry> nameTable;

    public NameTableBuilder(List<Entry> nameTable) {
        this.nameTable = nameTable;
    }

    public List<Entry> getNameTable() {
        return nameTable;
    }

    public void addReferenceType(String name) {

    }

    public void addVariable(Integer typeId, String name) {

    }
}
