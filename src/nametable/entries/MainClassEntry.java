package nametable.entries;

public class MainClassEntry extends ClassEntry {
    public MainClassEntry(String name) {
        super(name);
    }

    public MainMethodEntry getMainMethod(){
        for (Entry entry : getChildren()) {
            if (entry instanceof MainMethodEntry) {
                return (MainMethodEntry) entry;
            }
        }
        return null;
    }

}
