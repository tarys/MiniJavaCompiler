package nametable.entries;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains info about declared entry - class, method, field, local variable etc.
 * Also contains SUB-TABLE for inner entries
 */
public abstract class Entry {
    private String name;
    /**
     * contains value of Entry. For instance, variable value.
     */
    private Entry parent;
    private List<Entry> children;

    protected Entry(String name) {
        this.name = name;
        this.parent = null;
        this.children = new LinkedList<Entry>();
    }

    public Entry getParent() {
        return parent;
    }

    public void setParent(Entry parentEntry) {
        this.parent = parentEntry;
    }

    public String getName() {
        return name;
    }

    public List<Entry> getChildren() {
        return children;
    }

    public void addChild(Entry child) {
        if (child != null) {
            child.setParent(this);
            this.getChildren().add(child);
        }
    }

    public void addChildren(List<? extends Entry> children) {
        if (children != null) {
            for (Entry child : children) {
                child.setParent(this);
            }
            this.children.addAll(children);
        }
    }

    @Override
    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("[name='");
        resultBuffer.append(getName());
        resultBuffer.append("'; type='");
        resultBuffer.append(getClass().getSimpleName().replaceAll("Entry", ""));
        resultBuffer.append("']");

        return resultBuffer.toString();
    }
}
