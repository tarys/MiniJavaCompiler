package nametable.entries;

public class MethodParameterEntry extends Entry{
    private String type;

    public MethodParameterEntry(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodParameterEntry)) return false;
        if (!super.equals(o)) return false;

        MethodParameterEntry that = (MethodParameterEntry) o;

        if (!type.equals(that.type)) return false;
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
