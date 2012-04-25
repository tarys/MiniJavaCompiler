package nametable.entries;

public class MethodEntry extends Entry{
    private String returnType;

    public MethodEntry(String name, String returnType) {
        super(name);
        this.returnType = returnType;
    }
}
