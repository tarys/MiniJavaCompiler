package nametable.entries;

public class VariableEntry extends Entry {
    private String valueType;

    public VariableEntry(String name, String valueType) {
        super(name);
        this.valueType = valueType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        StringBuffer result = new StringBuffer(superString);
        result.insert(result.lastIndexOf("]") - 1, "; value type = '");
        result.insert(result.lastIndexOf("]") - 1, getValueType());
        result.insert(result.lastIndexOf("]") - 1, "'");

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
        result = 31 * result + valueType.hashCode();

        return result;
    }

}
