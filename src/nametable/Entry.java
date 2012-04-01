package nametable;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Contains info about declared entry - class, method, field, local variable etc.
 * Also contains SUB-TABLE for inner entries
 */
public class Entry {
    private String name;
    /**
     * type id from SymbolsInfo interface
     *
     * @see sa.SymbolsInfo
     */
    private int type;
    /**
     * contains value of Entry. For instance, variable value.
     */
    private Object value;
    private boolean declared;
    private Entry parentEntry;
    private List<Entry> children;

    protected Entry(String name, int type, Object value, Entry parentEntry) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.parentEntry = parentEntry;
        this.children = new LinkedList<Entry>();
    }

    public Entry getParentEntry() {
        return parentEntry;
    }

    public void setParentEntry(Entry parentEntry) {
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * type id from SymbolsInfo interface
     *
     * @see sa.SymbolsInfo
     */
    public int getType() {
        return type;
    }

    public List<Entry> getChildren() {
        return children;
    }

    /**
     * @param typeIdFilter type id from SymbolsInfo interface
     * @return list of children entries with of given type
     * @see sa.SymbolsInfo
     */
    public List<Entry> getChildren(int typeIdFilter) {
        List<Entry> filteredEntries = new LinkedList<Entry>();
        for (Entry child : getChildren()) {
            if (child.getType() == typeIdFilter) {
                filteredEntries.add(child);
            }
        }

        return children;
    }

    public void addChild(Entry child) {
        this.getChildren().add(child);
    }

    public void setChildren(List<Entry> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "[" +
               "name='" + name + '\'' +
               "; typeId=" + type +
               "; value=" + value +
               "; declared=" + declared +
               ']';
    }
}
