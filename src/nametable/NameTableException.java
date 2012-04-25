package nametable;


import nametable.entries.Entry;

public class NameTableException extends Exception {
    private Entry conflictEntry;

    public NameTableException(Entry conflictEntry) {
        this.conflictEntry = conflictEntry;
    }

    public NameTableException(String message, Entry conflictEntry) {
        super(message);
        this.conflictEntry = conflictEntry;
    }

    public NameTableException(String message, Throwable cause, Entry conflictEntry) {
        super(message, cause);
        this.conflictEntry = conflictEntry;
    }

    public NameTableException(Throwable cause, Entry conflictEntry) {
        super(cause);
        this.conflictEntry = conflictEntry;
    }

    @Override
    public String getMessage() {
        return conflictEntry.getClass().getSimpleName().replaceAll("Entry","") + " \"" + conflictEntry.getName() + "\" already defined";
    }
}
