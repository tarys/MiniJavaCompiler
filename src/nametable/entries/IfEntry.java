package nametable.entries;

public class IfEntry extends Entry {
    private static int ifBlocksCount;
    private BlockEntry thenBlock;
    private BlockEntry elseBlock;

    public IfEntry() {
        super("If#" + ++ifBlocksCount);
    }

    public void setThenBlock(BlockEntry thenBlock) {
        if (this.thenBlock != null) {
            getChildren().remove(this.thenBlock);
            if (thenBlock != null) {
                addChild(thenBlock);
            }
            this.thenBlock = thenBlock;
        } else {
            if (thenBlock != null) {
                addChild(thenBlock);
            }
            this.thenBlock = thenBlock;
        }
    }

    public BlockEntry getThenBlock() {
        return thenBlock;
    }

    public void setTElseBlock(BlockEntry elseBlock) {
        if (this.elseBlock != null) {
            getChildren().remove(this.elseBlock);
            if (elseBlock != null) {
                addChild(elseBlock);
            }
            this.elseBlock = elseBlock;
        } else {
            if (elseBlock != null) {
                addChild(elseBlock);
            }
            this.elseBlock = elseBlock;
        }
    }

    public BlockEntry getElseBlock() {
        return elseBlock;
    }
}
