package nametable.entries;

public class MethodParameterEntry extends VariableEntry {

    public MethodParameterEntry(String name, String valueType) {
        super(name, valueType);
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodParameterEntry)) return false;
        if (!super.equals(o)) return false;

        MethodParameterEntry that = (MethodParameterEntry) o;

        if (!getName().equals(that.getName())) return false;

        return true;
    }
}
