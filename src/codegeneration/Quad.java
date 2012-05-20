package codegeneration;

public class Quad {
    private Operation operation;
    private Object argument1;
    private Object argument2;
    private Object result;

    public Quad(Operation operation, Object argument1, Object argument2, Object result) {
        this.operation = operation;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.result = result;
    }

    public Operation getOperation() {
        return operation;
    }

    public Object getArgument1() {
        return argument1;
    }

    public void setArgument1(Object argument1) {
        this.argument1 = argument1;
    }

    public Object getArgument2() {
        return argument2;
    }

    public void setArgument2(Object argument2) {
        this.argument2 = argument2;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "(" + operation +
                ", " + ((argument1 == null) ? "--" : argument1) +
                ", " + ((argument2 == null) ? "--" : argument2) +
                ", " + ((result == null) ? "--" : result) +
                ')';
    }
}
