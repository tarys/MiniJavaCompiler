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
}
