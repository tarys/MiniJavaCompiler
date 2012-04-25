package nametable;

import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

public class NameTableTest {
    @Test
    public void testPositiveParse() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class BeforeClass{" +
                "   public int f1 = 3;" +
                "   public boolean b2 = true;" +
                "   public void m1(){" +
                "       float var1 = 31.52;" +
                "   }" +
                "}" +
                "public class MainClass{" +
                "   public int field1 = 15;" +
                "   public String field2 = \"str\";" +
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
        NameTable nameTable = parser.getNameTable();
        System.out.println(nameTable);
    }
}
