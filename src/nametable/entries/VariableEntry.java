package nametable.entries;

public class VariableEntry extends Entry{
    private String type;
    private Object value;

    public VariableEntry(String name, String type, Object value) {
        super(name);
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableEntry)) return false;
        if (!super.equals(o)) return false;

        VariableEntry that = (VariableEntry) o;

        if (!getName().equals(that.getName())) return false;
        if (!type.equals(that.type)) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
