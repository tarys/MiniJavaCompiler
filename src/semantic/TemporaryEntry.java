package semantic;

import nametable.entries.VariableEntry;

public class TemporaryEntry extends VariableEntry {
    private static int temporaryEntriesCount;

    public TemporaryEntry(String valueType) {
        super("T[" + ++temporaryEntriesCount + "]", valueType);
    }
}
