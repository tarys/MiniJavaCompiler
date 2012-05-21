package nametable.entries;

import codegeneration.Quad;
import nametable.entries.VariableEntry;

import java.util.Collection;
import java.util.List;

public class TemporaryEntry extends VariableEntry {
    private static int temporaryEntriesCount;
    private MethodEntry callMethod;

    public TemporaryEntry(String valueType) {
        super("Temp#" + ++temporaryEntriesCount, valueType);
    }

    public TemporaryEntry(MethodEntry callMethod) {
        super("Temp#" + ++temporaryEntriesCount, callMethod.getReturnType());
        this.callMethod = callMethod;
    }

    public MethodEntry getCallMethod() {
        return callMethod;
    }
}
