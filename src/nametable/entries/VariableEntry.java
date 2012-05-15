package nametable.entries;

public class VariableEntry extends Entry {
    private String type;

    public VariableEntry(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        StringBuffer result = new StringBuffer(superString);
        result.insert(result.indexOf("]") - 1, "; value type = '");
        result.insert(result.indexOf("]") - 1, getType());
        result.insert(result.indexOf("]") - 1, "'");

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableEntry)) return false;
        if (!super.equals(o)) return false;

        VariableEntry that = (VariableEntry) o;

        if (!getName().equals(that.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();

        return result;
    }

}
