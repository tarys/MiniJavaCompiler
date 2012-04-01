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

    public void addTopLevelEntry(Entry entry) {
        getTopLevelEntries().add(entry);
    }
    public boolean contains(String entryName){
        for (Entry entry : getTopLevelEntries()) {
            if(entry.getName().equals(entryName)){
                return true;
            }
        }
        return false;
    }
}
