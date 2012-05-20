package nametable.entries;

import codegeneration.Quad;
import nametable.entries.VariableEntry;

import java.util.Collection;
import java.util.List;

public class TemporaryEntry extends VariableEntry {
    private static int temporaryEntriesCount;

    public TemporaryEntry(String valueType) {
        super("Temp#" + ++temporaryEntriesCount, valueType);
    }

}
