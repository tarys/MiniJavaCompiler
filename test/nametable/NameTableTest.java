package nametable;

import java_cup.runtime.Symbol;
import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

import java.util.List;

public class NameTableTest {
    @Test
    public void testAddVariable() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                 "public class MainClass{" +
                                                                 "   public static void main (String[] args){" +
                                                                 "       int c;" +
                                                                 "       System.out.println(\"Hello, world!\");" +
                                                                 "   }" +
                                                                 "}"));
        Symbol parseTree = parser.parse();
        NameTable nameTable = parser.getNameTableBuilder().getNameTable();
        Assert.assertTrue(nameTable.contains("c"));
        for (Entry entry : nameTable.getTopLevelEntries()) {
            System.out.println(entry);
        }
    }
}
