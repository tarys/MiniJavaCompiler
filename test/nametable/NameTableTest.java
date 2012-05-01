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
                "class ClassBefore{" +
                "   public String field1 = \"str\";" +
                "   public int method1(byte b, char c, String str){" +
                "       b = 'c';" +
                "       if (true){" +
                "           String str2 = \"\";" +
                "           int i = System.in.read();" +
                "       } else {" +
                "           System.out.println('s');" +
                "       }" +
                "       System.out.println(1);" +
                "       return b;" +
                "   }" +
                "}" +
                "public class MainClass{" +
                "   public boolean f1 = -3.4028235E+38;" +
                "   public String f2 = true||false;" +
                "   public static void main (String[] args){" +
                "       int c = -15;" +
                "       System.out.println(\"Hello, world!\");" +
                "   }" +
                "}" +
                "class ClassAfter1{" +
                "   public boolean field1 = true;" +
                "}" +
                "class ClassAfter2{" +
                "   public void method2(ClassAfter2 arg){" +
                "       String str;" +
                "       boolean b2 = 1;" +
                "       int i = System.in.read();" +
                "       while(1>0){" +
                "           char c = -2147483647;" +
                "           if((true && false) || false){" +
                "               MyClass ms;" +
                "               System.out.println(\"Hello, world!\");" +
                "           }" +
                "       }" +
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
