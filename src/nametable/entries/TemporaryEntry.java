package nametable.entries;

import codegeneration.Quad;
import nametable.entries.VariableEntry;

import java.util.List;

public class TemporaryEntry extends VariableEntry {
    private List<Quad> byteCode;
    private static int temporaryEntriesCount;

    public TemporaryEntry(String valueType) {
        super("T[" + ++temporaryEntriesCount + "]", valueType);
    }

    public List<Quad> getByteCode() {
        return byteCode;
    }
}
