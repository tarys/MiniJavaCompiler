package codegeneration;

import junit.framework.Assert;
import la.LexicalAnalyzer;
import org.junit.Test;
import sa.LR1Analyzer;
import semantic.SemanticException;

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
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
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
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
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
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }

    @Test
    public void testIf() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       if(true || false && (2 <= 1)){" +
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
        expected.add(i++ + ". (LE, 2, 1, T[0])");
        expected.add(i++ + ". (AND, false, T[0], T[1])");
        expected.add(i++ + ". (OR, true, T[1], T[2])");
        expected.add(i++ + ". (BZ, T[2], <8>, --)");
        expected.add(i++ + ". (STORE, true, --, 'b')");
        expected.add(i++ + ". (STORE, true, --, 'b2')");
        expected.add(i++ + ". (BR, <10>, --, --)");
        expected.add(i++ + ". (STORE, 1, --, 'a')");
        expected.add(i++ + ". (STORE, 1, --, 'ab')");
        expected.add(i++ + ". (BZ, false, <14>, --)");
        expected.add(i++ + ". (STORE, \"123\", --, 's1')");
        expected.add(i++ + ". (STORE, \"123\", --, 's2')");
        expected.add(i++ + ". (STORE, \"123\", --, 's3')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }

    @Test
    public void testWhile() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       boolean k = false;" +
                "       while(true || false && true || false){" +
                "           int i = 2;" +
                "           if (i > 10){" +
                "               break;" +
                "           }" +
                "           i = 4;" +
                "       }" +
                "       k = k || true;" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (STORE, false, --, 'k')");
        expected.add(i++ + ". (AND, false, true, T[0])");
        expected.add(i++ + ". (OR, true, T[0], T[1])");
        expected.add(i++ + ". (OR, T[1], false, T[2])");
        expected.add(i++ + ". (BZ, T[2], <12>, --)");
        expected.add(i++ + ". (STORE, 2, --, 'i')");
        expected.add(i++ + ". (GT, 'i', 10, T[2])");
        expected.add(i++ + ". (BZ, T[2], <10>, --)");
        expected.add(i++ + ". (BR, <12>, --, --)");
        expected.add(i++ + ". (STORE, 4, --, 'i')");
        expected.add(i++ + ". (BR, <2>, --, --)");
        expected.add(i++ + ". (OR, 'k', true, T[2])");
        expected.add(i++ + ". (STORE, T[2], --, 'k')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }

    @Test
    public void testNew() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "       public int a;" +
                "}" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass mc = new MyClass();" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (OBJ, MyClass, --, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 'mc')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }

    @Test
    public void testNotInitialized() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "       public int a;" +
                "}" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass mc;" +
                "       int b = mc.a;" +
                "   }" +
                "}"));
        try {
            parser.parse();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.ENTITY_DECLARED_BUT_NOT_INITIALIZED));
        }

    }

    @Test
    public void testFieldCall() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "       public int a;" +
                "}" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass mc = new MyClass();" +
                "       int b = mc.a;" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (OBJ, MyClass, --, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 'mc')");
        expected.add(i++ + ". (FCALL, 'mc', a, T[0])");
        expected.add(i++ + ". (STORE, T[0], --, 'b')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }

    @Test
    public void testThisMethodCall() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public int a(int i, String s){" +
                "       System.out.println(s);" +
                "       return i + 2;" +
                "   }" +
                "" +
                "   public static void main (String[] args){" +
                "       int c = 1;" +
                "       c = 4 + c + a(3, \"str\");" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (STORE, 1, --, 'c')");
        expected.add(i++ + ". (ADD, 4, 'c', T[0])");
        expected.add(i++ + ". (PUSH, \"str\", --, --)");
        expected.add(i++ + ". (PUSH, 3, --, --)");
        expected.add(i++ + ". (MCALL, THIS, a, --)");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 'i')");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 's')");
        expected.add(i++ + ". (PRINTLN, 's', --, --)");
        expected.add(i++ + ". (ADD, 'i', 2, T[0])");
        expected.add(i++ + ". (PUSH, T[0], --, --)");
        expected.add(i++ + ". (RETURN, <14>, --, --)");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (ADD, T[0], T[1], T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 'c')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }
    @Test
    public void testMethodCall() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "   public int a(int i, String s){" +
                "       System.out.println(s);" +
                "       return i + 2;" +
                "   }" +
                "}" +
                "" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass mc = new MyClass();" +
                "       int c = 1;" +
                "       c = 4 + c + mc.a(3, \"str\");" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (OBJ, MyClass, --, T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 'mc')");
        expected.add(i++ + ". (STORE, 1, --, 'c')");
        expected.add(i++ + ". (ADD, 4, 'c', T[0])");
        expected.add(i++ + ". (PUSH, \"str\", --, --)");
        expected.add(i++ + ". (PUSH, 3, --, --)");
        expected.add(i++ + ". (MCALL, 'mc', a, --)");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 'i')");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 's')");
        expected.add(i++ + ". (PRINTLN, 's', --, --)");
        expected.add(i++ + ". (ADD, 'i', 2, T[0])");
        expected.add(i++ + ". (PUSH, T[0], --, --)");
        expected.add(i++ + ". (RETURN, <16>, --, --)");
        expected.add(i++ + ". (POP, --, --, T[1])");
        expected.add(i++ + ". (ADD, T[0], T[1], T[1])");
        expected.add(i++ + ". (STORE, T[1], --, 'c')");
        expected.add(i++ + ". (RETURN, --, --, --)");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }
    @Test
    public void testInstanceof() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "   public int a(int i, String s){" +
                "       System.out.println(s);" +
                "       return i + 2;" +
                "   }" +
                "}" +
                "" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass mc = new MyClass();" +
                "       int c = 1;" +
                "       if (mc instanceof MyClass){" +
                "           c = 4 + c + mc.a(3, \"str\");" +
                "       }" +
                "       System.out.println(\"Well done!\");" +
                "   }" +
                "}"));
        parser.parse();
        List<String> expected = new LinkedList<String>();
        int i = 1;
        expected.add(i++ + ". (STORE, T[1], --, 'c')");
        int k = 0;
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
//            Assert.assertEquals(expected.get(k++), quad.toString());
            System.out.println(quad);
        }
    }
}
