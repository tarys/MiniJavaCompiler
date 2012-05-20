package nametable.entries;

public class ProgramEntry extends Entry {

    public ProgramEntry() {
        super("Program");
    }

    public MainClassEntry getMainClass() {
        for (Entry entry : getChildren()) {
            if (entry instanceof MainClassEntry) {
                return (MainClassEntry) entry;
            }
        }
        return null;
    }
}
