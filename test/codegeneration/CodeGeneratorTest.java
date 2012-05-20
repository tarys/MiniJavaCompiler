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
        expected.add("(SUB, 0, 12, T[0])");
        expected.add("(STORE, T[0], --, 'q')");
        expected.add("(ADD, 'q', 2, T[1])");
        expected.add("(STORE, T[1], --, 'q')");
        expected.add("(ADD, 2, 5, T[2])");
        expected.add("(SUB, 'q', T[2], T[3])");
        expected.add("(SUB, 10, 15, T[4])");
        expected.add("(DIV, 'q', T[4], T[5])");
        expected.add("(ADD, T[3], T[5], T[6])");
        expected.add("(STORE, T[6], --, 'q')");
        expected.add("(MULT, 'q', 2, T[7])");
        expected.add("(STORE, T[7], --, 'q')");
        expected.add("(DIV, 'q', 2, T[8])");
        expected.add("(STORE, T[8], --, 'q')");
        int i = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
//            Assert.assertEquals(expected.get(i++), quad.toString());
        }
    }
    @Test
    public void testPrintln() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       System.out.println(\"Hello, world!\");" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        expected.add("(PRINTLN, \"Hello, world!\", --, --)");
        int i = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
            Assert.assertEquals(expected.get(i++), quad.toString());
        }
    }
}
