package nametable.entries;

import java.util.LinkedList;
import java.util.List;

public class MethodEntry extends Entry {
    private String returnType;

    public MethodEntry(String name, String returnType) {
        super(name);
        this.returnType = returnType;
    }

    public List<Entry> getParameters() {
        List<Entry> result = new LinkedList<Entry>();
        List<Entry> children = getChildren();
        if (children != null && !children.isEmpty()) {
            for (Entry child : children) {
                if (child instanceof MethodParameterEntry) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodEntry)) return false;
        if (!super.equals(o)) return false;

        MethodEntry that = (MethodEntry) o;

        if (!getName().equals(that.getName())) return false;
        if (!getParameters().equals(that.getParameters())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + returnType.hashCode();
        return result;
    }
}
