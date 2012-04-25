package nametable;

import nametable.entries.Entry;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class NameTable {
    private Entry root;
    private NameTable parent;
    private List<NameTable> children;

    public NameTable() {
        this.children = new LinkedList<NameTable>();
    }

    public NameTable getParent() {
        return parent;
    }

    public void setParent(NameTable parentEntry) {
        this.parent = parentEntry;
    }

    public List<NameTable> getChildren() {
        return children;
    }

    public void addChild(NameTable child) {
        if (child != null) {
            child.setParent(this);
            this.getChildren().add(child);
        }
    }

    public void addChildren(List<NameTable> children) {
        if (children != null) {
            for (NameTable child : children) {
                child.setParent(this);
            }
            this.children.addAll(children);
        }
    }

    public void setRoot(Entry root) {
        this.root = root;
    }

    public Entry getRoot() {
        return root;
    }

    public Entry lookUpGlobal(Entry entryToFind, Entry scopeEntry) {

        return lookUpAscending(entryToFind, scopeEntry);
    }

    public Entry lookUpLocal(Entry entryToFind, Entry scopeEntry) {
        Entry result = null;
        if (entryToFind.equals(scopeEntry)) {
            result = scopeEntry;
        } else {
            List<Entry> children = scopeEntry.getChildren();
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

    private Entry lookUpAscending(Entry entryToFind, Entry scopeEntry) {
        Entry result = lookUpLocal(entryToFind, scopeEntry);
        if (result == null) {
            Entry parentScope = scopeEntry.getParent();
            if (parentScope != null) {
                result = lookUpAscending(entryToFind, parentScope);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();
        int indentLevel = 0;
        buildString(getRoot(), indentLevel, resultBuffer);
        return resultBuffer.toString();
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
