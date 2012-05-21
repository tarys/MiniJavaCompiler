package codegeneration;

public class Quad {
    private static int totalQuadCount;

    private Operation operation;
    private Object argument1;
    private Object argument2;
    private Object result;
    private int quadNumber;

    public Quad(Operation operation, Object argument1, Object argument2, Object result) {
        this.operation = operation;
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.result = result;
        this.quadNumber = totalQuadCount++;
    }

    public int getQuadNumber() {
        return quadNumber;
    }

    public void setQuadNumber(int quadNumber) {
        this.quadNumber = quadNumber;
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
        String operationString = operation.equals(Operation.BRBACK) ? "BR" : operation.toString();
        String arg1String = "--";
        if (argument1 != null) {
            switch (operation) {
                case BR:
                    arg1String = "<" + (((Quad) argument1).getQuadNumber() + 1) + ">";
                    break;
                case BRBACK:
                    arg1String = "<" + (((Quad) argument1).getQuadNumber()) + ">";
                    break;
                case PUSH:
                    if (argument1 instanceof Quad) {
                        arg1String = "<" + (((Quad) argument1).getQuadNumber() + 1) + ">";
                    } else {
                        arg1String = argument1.toString();
                    }
                    break;
                case RETURN:
                    if (argument1 instanceof Quad) {
                        arg1String = "<" + (((Quad) argument1).getQuadNumber()) + ">";
                    } else {
                        arg1String = argument1.toString();
                    }
                    break;
                default:
                    arg1String = argument1.toString();
            }
        }
        String arg2String = "--";
        if (argument2 != null) {
            switch (operation) {
                case BZ:
                    arg2String = "<" + (((Quad) argument2).getQuadNumber() + 1) + ">";
                    break;
                default:
                    arg2String = argument2.toString();
            }
        }
        Object resultString = "--";
        if (result != null) {
            resultString = result;
        }
        return quadNumber + ". (" + operationString +
                ", " + arg1String +
                ", " + arg2String +
                ", " + resultString +
                ')';
    }
}
