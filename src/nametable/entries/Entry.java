package nametable.entries;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Contains info about declared entry - class, method, field, local variable etc.
 * Also contains SUB-TABLE for inner entries
 */
public abstract class Entry {
    private String name;
    /**
     * contains value of Entry. For instance, variable value.
     */
    private Entry parent;
    private List<Entry> children;

    protected Entry(String name) {
        this.name = name;
        this.parent = null;
        this.children = new LinkedList<Entry>();
    }

    public Entry getParent() {
        return parent;
    }

    public void setParent(Entry parentEntry) {
        this.parent = parentEntry;
    }

    public String getName() {
        return name;
    }

    public List<Entry> getChildren() {
        return children;
    }

    public void addChild(Entry child) {
        if (child != null) {
            child.setParent(this);
            this.getChildren().add(child);
        }
    }

    public void addChildren(List<? extends Entry> children) {
        if (children != null) {
            for (Entry child : children) {
                child.setParent(this);
            }
            this.children.addAll(children);
        }
    }

    @Override
    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("[name='");
        resultBuffer.append(getName());
        resultBuffer.append("'; type='");
        resultBuffer.append(getClass().getSimpleName().replaceAll("Entry", ""));
        resultBuffer.append("']");

        return resultBuffer.toString();
    }

    public void printEntriesTree() {
        StringBuffer resultBuffer = new StringBuffer();
        int indentLevel = 0;
        buildString(this, indentLevel, resultBuffer);
        System.out.println(resultBuffer.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;

        Entry entry = (Entry) o;

        if (!name.equals(entry.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Entry lookUpGlobal(Entry entryToFind) {
        Entry result = lookUpLocal(entryToFind);
        if (result == null) {
            Entry parent = this.getParent();
            while (parent != null) {
                result = parent.lookUpLocal(entryToFind);
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }

    public Entry lookUpLocal(Entry entryToFind) {
        Entry result = null;
        if (this.equals(entryToFind)) {
            result = this;
        } else {
            List<Entry> children = this.getChildren();
            if (children != null && !children.isEmpty()) {
                ListIterator<Entry> iterator = children.listIterator();
                while (iterator.hasNext()) {
                    Entry child = iterator.next();
                    if (child.equals(entryToFind)) {
                        result = child;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private void buildString(Entry entry, int indentLevel, StringBuffer resultBuffer) {
        for (int i = 0; i < indentLevel; i++) {
            resultBuffer.append("\t");
        }
        resultBuffer.append(entry.toString());
        resultBuffer.append("\n");
        ListIterator<Entry> childrenEntriesListIterator = entry.getChildren().listIterator();
        while (childrenEntriesListIterator.hasNext()) {
            Entry child = childrenEntriesListIterator.next();
            buildString(child, indentLevel + 1, resultBuffer);
        }
    }
}
