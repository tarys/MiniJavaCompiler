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
                "   public int flield1 = 15;" +
                "   public String flield2 = \"str\";" +
                "   public static void main (String[] args){" +
                "       int c = 10;" +
                "       int c;" +
                "       print(\"Hello, World!\");" +
                "   }" +
                "   public int print(String str){" +
                "       getValue();" +
                "       return 1;" +
                "   }" +
                "   public void doNothing(){" +
                "       int c = 5;" +
                "   }" +
                "}"));
        parser.parse();
        NameTable nameTable = parser.getNameTableBuilder().getNameTable();
        Assert.assertTrue(nameTable.containsAtTopLevel("main"));
        System.out.println(nameTable);
    }
}
