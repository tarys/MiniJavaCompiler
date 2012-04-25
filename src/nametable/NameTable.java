package nametable;

import nametable.entries.Entry;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class NameTable {
    private List<Entry> topLevelEntries;

    public NameTable() {
        this.topLevelEntries = new LinkedList<Entry>();
    }

    public List<Entry> getTopLevelEntries() {
        return topLevelEntries;
    }

    public void addTopLevelEntry(Entry entry) {
        getTopLevelEntries().add(entry);
    }

    public boolean containsAtTopLevel(String entryName) {
        for (Entry entry : getTopLevelEntries()) {
            if (entry.getName().equals(entryName)) {
                return true;
            }
        }
        return false;
    }

    public Entry getTopLevelEntry(String name) {
        for (Entry entry : getTopLevelEntries()) {
            if (entry.getName().equals(name)) {
                return entry;
            }
        }
        return null;
    }

    public void removeFromTopLevel(Entry entry) {
        if (entry != null) {
            getTopLevelEntries().remove(entry);
        }
    }

    public void removeFromTopLevel(List<? extends Entry> entries) {
        if (entries != null) {
            getTopLevelEntries().removeAll(entries);
        }
    }

    @Override
    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();
        ListIterator<Entry> iterator = getTopLevelEntries().listIterator();
        while (iterator.hasNext()) {
            Entry currEntry = iterator.next();
            int indentLevel = 0;
            buildString(currEntry, indentLevel, resultBuffer);
        }
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
