package nametable.entries;

public class WhileEntry extends Entry {
    private static int whileBlocksCount = 0;

    public WhileEntry() {
        super("While#" + ++whileBlocksCount);
    }

}
