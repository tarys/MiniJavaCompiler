package nametable;

import java.util.LinkedList;
import java.util.List;


public class NameTable {
    private List<Entry> topLevelEntries;

    public NameTable() {
        this.topLevelEntries = new LinkedList<Entry>();
    }

    public List<Entry> getTopLevelEntries() {
        return topLevelEntries;
    }

    public void setTopLevelEntries(List<Entry> topLevelEntries) {
        this.topLevelEntries = topLevelEntries;
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
        getTopLevelEntries().remove(entry);
    }

    public void removeFromTopLevel(List<Entry> entries) {
        getTopLevelEntries().removeAll(entries);
    }
}
