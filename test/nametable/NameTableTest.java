package nametable;

import la.LexicalAnalyzer;
import nametable.entries.Entry;
import nametable.entries.ProgramEntry;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

import java.util.LinkedList;
import java.util.List;

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
        Entry nameTable = parser.getNameTable();
        Entry resultEntry = nameTable.lookUpGlobal(new ProgramEntry());
        Assert.assertNotNull(resultEntry);
        nameTable.printEntriesTree();
    }

    @Test
    public void testListContains() throws Exception {
        List<Integer> list = new LinkedList<Integer>();
        Integer i1 = new Integer(1);
        list.add(i1);
        list.add(new Integer(2));
        Integer i2 = new Integer(1);

        Assert.assertFalse(i1 == i2);
        Assert.assertTrue(i1.equals(i2));
        Assert.assertTrue(list.contains(i2));

    }
}
