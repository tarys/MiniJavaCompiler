package nametable.entries;

public class BlockEntry extends Entry {
    private static int blockCount = 0;

    public BlockEntry() {
        super("block#" + ++blockCount);
    }
}
