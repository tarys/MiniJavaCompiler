package semantic;

import nametable.NameTableBuilder;

public class SemanticAnalyzer {
    private NameTableBuilder nameTableBuilder;
    private boolean breakFlag;

    public SemanticAnalyzer(NameTableBuilder nameTableBuilder) {
        this.nameTableBuilder = nameTableBuilder;
    }


    public void setBreakFlag(boolean breakFlag) {
        this.breakFlag = breakFlag;
    }

    public boolean isBreakFlag() {
        return breakFlag;
    }

    public void checkNotUsedBreak() throws SemanticException {
        throw new SemanticException("\"break\" used but cycle not declared");
    }
}
