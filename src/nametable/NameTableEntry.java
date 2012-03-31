package nametable;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains info about declared entry - class, method, field, local variable etc.
 * Also contains SUB-TABLE for inner entries
 */
public abstract class NameTableEntry {
    private String name;
    private boolean declared;
    private NameTableEntry parentEntry;

    public NameTableEntry(String name, NameTableEntry parentEntry) {
        this.name = name;
        this.parentEntry = parentEntry;
    }

    public NameTableEntry getParentEntry() {
        return parentEntry;
    }

    public void setParentEntry(NameTableEntry parentEntry) {
        this.parentEntry = parentEntry;
    }

    public boolean isDeclared() {
        return declared;
    }

    public void setDeclared(boolean declared) {
        this.declared = declared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
