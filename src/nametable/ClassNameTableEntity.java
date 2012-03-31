package nametable;

import java.util.List;

public class ClassNameTableEntity extends NameTableEntry {
    private List<FieldNameTableEntity> fields;
    private List<MethodNameTableEntity> methods;

    public ClassNameTableEntity(String name, NameTableEntry parentEntry) {
        super(name, parentEntry);
    }

    public List<FieldNameTableEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldNameTableEntity> fields) {
        this.fields = fields;
    }

    public List<MethodNameTableEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodNameTableEntity> methods) {
        this.methods = methods;
    }
}
