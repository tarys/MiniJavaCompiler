package nametable.entries;

public class BreakEntry extends Entry{
    private static int breakCount = 0;

    public BreakEntry() {
        super("Break#" + ++breakCount);
    }
}
