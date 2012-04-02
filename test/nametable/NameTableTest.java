package nametable;

import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

public class NameTableTest {
    @Test
    public void testPositiveParse() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       int c = 10;" +
                "       int d;" +
                "       print(\"Hello, World!\");" +
                "   }" +
                "   public int print(String str){" +
                "       return 1;" +
                "   }" +
                "}"));
        parser.parse();
        NameTable nameTable = parser.getNameTableBuilder().getNameTable();
        Assert.assertTrue(nameTable.containsAtTopLevel("main"));
        for (Entry entry : nameTable.getTopLevelEntries()) {
            System.out.println(entry);
        }
    }
}
