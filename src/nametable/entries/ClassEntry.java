package nametable.entries;

import semantic.SemanticAnalyzer;
import semantic.SemanticException;

import java.util.LinkedList;
import java.util.List;

public class ClassEntry extends Entry {

    public ClassEntry(String name) {
        super(name);
    }

    public List<FieldEntry> getFields() {
        List<FieldEntry> result = new LinkedList<FieldEntry>();
        List<Entry> children = getChildren();
        if (children != null && !children.isEmpty()) {
            for (Entry child : children) {
                if (child instanceof FieldEntry) {
                    result.add((FieldEntry) child);
                }
            }
        }

        return result;
    }
    public FieldEntry getField(String name) throws SemanticException {
        FieldEntry result = null;
        for (FieldEntry candidateFieldEntry : this.getFields()) {
            if (candidateFieldEntry.getName().equals(name)) {
                result = candidateFieldEntry;
                break;
            }
        }
        if (result == null) {
            throw new SemanticException(SemanticAnalyzer.NO_SUCH_FIELD_IN_CLASS + this.getName() + "#" + name);
        }
        return result;
    }

    public List<MethodEntry> getMethods() {
        List<MethodEntry> result = new LinkedList<MethodEntry>();
        List<Entry> children = getChildren();
        if (children != null && !children.isEmpty()) {
            for (Entry child : children) {
                if (child instanceof MethodEntry) {
                    result.add((MethodEntry) child);
                }
            }
        }

        return result;
    }
    public MethodEntry getMethod(String name) throws SemanticException {
        MethodEntry result = null;
        for (MethodEntry candidateMethodEntry : this.getMethods()) {
            if (candidateMethodEntry.getName().equals(name)) {
                result = candidateMethodEntry;
                break;
            }
        }
        if (result == null) {
            throw new SemanticException(SemanticAnalyzer.NO_SUCH_METHOD + this.getName() + "#" + name);
        }

        return result;
    }
}
