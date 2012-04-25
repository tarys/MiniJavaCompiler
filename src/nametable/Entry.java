package nametable;

import sa.SymbolsInfo;

import java.util.LinkedList;
import java.util.List;

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
    private int valueType;
    /**
     * contains value of Entry. For instance, variable value.
     */
    private Object value;
    private Entry parentEntry;
    private List<Entry> children;

    public Entry(String name, int valueType, Object value, Entry parentEntry) {
        this.name = name;
        this.valueType = valueType;
        this.value = value;
        this.parentEntry = parentEntry;
        this.children = new LinkedList<Entry>();
    }

    public Entry getParentEntry() {
        return parentEntry;
    }

    public void setParent(Entry parentEntry) {
        this.parentEntry = parentEntry;
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
    public int getValueType() {
        return valueType;
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
            if (child.getValueType() == typeIdFilter) {
                filteredEntries.add(child);
            }
        }

        return children;
    }

    public void addChild(Entry child) {
        child.setParent(this);
        this.getChildren().add(child);
    }

    public void setChildren(List<Entry> children) {
        for (Entry child : children) {
            child.setParent(this);
        }
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("[name='");
        resultBuffer.append(getName());
        resultBuffer.append("'; type=");
        switch (valueType) {
           case SymbolsInfo.BOOLEAN_TYPE:
               resultBuffer.append("boolean variable");
               break;
           case SymbolsInfo.CHAR_TYPE:
               resultBuffer.append("char variable");
               break;
           case SymbolsInfo.FLOAT_TYPE:
               resultBuffer.append("float variable");
               break;
           case SymbolsInfo.INTEGER_TYPE:
               resultBuffer.append("int variable");
               break;
           case SymbolsInfo.STRING_TYPE:
               resultBuffer.append("String variable");
               break;
            case SymbolsInfo.reference_type:
                resultBuffer.append("reference type");
                break;
            case SymbolsInfo.block:
                resultBuffer.append("block declaration");
                break;
            default:
                resultBuffer.append("unknown");
        }
        resultBuffer.append("; value=");
        resultBuffer.append(value);
        resultBuffer.append("]");

        return resultBuffer.toString();
    }
}
