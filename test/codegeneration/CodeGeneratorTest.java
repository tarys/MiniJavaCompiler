package codegeneration;

import junit.framework.Assert;
import la.LexicalAnalyzer;
import org.junit.Test;
import sa.LR1Analyzer;

import java.util.LinkedList;
import java.util.List;

public class CodeGeneratorTest {
    @Test
    public void testArithmeticOperations() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       float q = -12;" +
                "       q = q + 2;" +
                "       q = q - (2 + 5) + q/(10 - 15);" +
                "       q = q * 2;" +
                "       q = q / 2;" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (SUB, 0, 12, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 'q')");
        expected.add(i++ + ". (ADD, 'q', 2, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 'q')");
        expected.add(i++ + ". (ADD, 2, 5, T[0])");
        expected.add(i++ + ". (SUB, 'q', T[0], T[1])");
        expected.add(i++ + ". (SUB, 10, 15, T[2])");
        expected.add(i++ + ". (DIV, 'q', T[2], T[3])");
        expected.add(i++ + ". (ADD, T[1], T[3], T[4])");
        expected.add(i++ + ". (STORE, T[4], --, 'q')");
        expected.add(i++ + ". (MULT, 'q', 2, T[4])");
        expected.add(i++ + ". (STORE, T[4], --, 'q')");
        expected.add(i++ + ". (DIV, 'q', 2, T[4])");
        expected.add(i++ + ". (STORE, T[4], --, 'q')");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
            Assert.assertEquals(expected.get(k++), quad.toString());
        }
    }

    @Test
    public void testBooleanOperations() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       boolean b = false;" +
                "       boolean c = true;" +
                "       b = b || c && !b || (1 >= 2);" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (STORE, false, --, 'b')");
        expected.add(i++ + ". (STORE, true, --, 'c')");
        expected.add(i++ + ". (NOT, 'b', --, T[0])");
        expected.add(i++ + ". (AND, 'c', T[0], T[1])");
        expected.add(i++ + ". (OR, 'b', T[1], T[2])");
        expected.add(i++ + ". (GE, 1, 2, T[3])");
        expected.add(i++ + ". (OR, T[2], T[3], T[4])");
        expected.add(i++ + ". (STORE, T[4], --, 'b')");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
            Assert.assertEquals(expected.get(k++), quad.toString());
        }
    }

    @Test
    public void testIf() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       if(true){" +
                "           boolean b = true;" +
                "           boolean b2 = true;" +
                "       } else {" +
                "           int a = 1;" +
                "           int ab = 1;" +
                "       }" +
                "       if (false){" +
                "           String s1 = \"123\";" +
                "           String s2 = \"123\";" +
                "           String s3 = \"123\";" +
                "       }" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (BZ, true, <5>, --)");
        expected.add(i++ + ". (STORE, true, --, 'b')");
        expected.add(i++ + ". (STORE, true, --, 'b2')");
        expected.add(i++ + ". (BR, <7>, --, --)");
        expected.add(i++ + ". (STORE, 1, --, 'a')");
        expected.add(i++ + ". (STORE, 1, --, 'ab')");
        expected.add(i++ + ". (BZ, false, <11>, --)");
        expected.add(i++ + ". (STORE, \"123\", --, 's1')");
        expected.add(i++ + ". (STORE, \"123\", --, 's2')");
        expected.add(i++ + ". (STORE, \"123\", --, 's3')");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
            Assert.assertEquals(expected.get(k++), quad.toString());
        }
    }

    @Test
    public void testPrintlnRead() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       String s = System.in.read();" +
                "       System.out.println(\"Hello, world!\");" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (READ, --, --, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 's')");
        expected.add(i++ + ". (PRINTLN, \"Hello, world!\", --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
            Assert.assertEquals(expected.get(k++), quad.toString());
        }
    }
}
