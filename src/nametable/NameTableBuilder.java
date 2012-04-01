package nametable;

/**
 * responsible for correct bui
 */
public class NameTableBuilder {
    private NameTable nameTable;

    public NameTableBuilder(NameTable nameTable) {
        this.setNameTable(nameTable);
    }

    public void addReferenceType(String name) {

    }

    public NameTable getNameTable() {
        return nameTable;
    }

    public void addVariable(Integer typeId, String name) {
        if(!getNameTable().contains(name)) {
            Entry newEntry = new Entry(name, typeId, null, null);
            getNameTable().addTopLevelEntry(newEntry);
        }
    }

    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
    }
}
