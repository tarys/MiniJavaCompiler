package nametable;

import nametable.entries.Entry;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class NameTable {
    private Entry root;

    public NameTable() {
    }

    public void setRoot(Entry root) {
        this.root = root;
    }

    public Entry getRoot() {
        return root;
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
