package sa;

import java_cup.runtime.Symbol;
import la.LexicalAnalyzer;
import la.LexicalAnalyzerException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Taras Slipets
 */
public class LR1AnalyzerTest {
    @Test
    public void testPositiveParsing() throws Exception {
        System.out.println("\n === JUnit4 Test: testPositiveParsing() method ===");
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                 "class ClassBefore{" +
                                                                 "   public String field1 = \"str\";" +
                                                                 "   public int method1(byte b, char c, String str){" +
                                                                 "       b = 'c';" +
                                                                 "       if (true){" +
                                                                 "           String str = \"\";" +
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
                                                                 "   public String f1 = true||false;" +
                                                                 "   public static void main (String[] args){" +
                                                                 "       int c = -15;" +
                                                                 "       System.out.println(\"Hello, world!\");" +
                                                                         "" +
                                                                 "   }" +
                                                                 "}" +
                                                                 "class ClassAfter1{" +
                                                                 "   public boolean field1 = true;" +
                                                                 "}" +
                                                                 "class ClassAfter2{" +
                                                                 "   public void method1(ClassAfter2 arg){" +
                                                                 "       String str;" +
                                                                 "       boolean b2 = 1;" +
                                                                 "       int i = System.in.read();" +
                                                                 "       while(1>0){" +
                                                                 "           char c = -2147483647;" +
                                                                 "           if((true && false) || false){" +
                                                                 "               System.out.println(\"Hello, "
                                                                 + "world!\");"
                                                                 +
                                                                 "           }" +
                                                                 "       }" +
                                                                 "       return;" +
                                                                 "   }" +
                                                                 "}"));
        Symbol parseTree = parser.parse();
        System.out.println("Parsing finished successfully!");
    }

    @Test
    public void testNegativeIntegerParsing() throws Exception {
        System.out.println("\n === JUnit4 Test: testNegativeIntegerParsing() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass{" +
                                                                     "   public char c = -2147483648;" +
                                                                     "   public static void main (String[] args){}" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(LexicalAnalyzerException.OUT_OF_INTEGER_RANGE));
            e.printStackTrace();
        }
    }

    @Test
    public void testNegativeFloatParsing() throws Exception {
        System.out.println("\n === JUnit4 Test: testNegativeFloatParsing() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass{" +
                                                                     "   public boolean f1 = -3.4128235E+38;" +
                                                                     "   public static void main (String[] args){}" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(LexicalAnalyzerException.OUT_OF_FLOAT_RANGE));
            e.printStackTrace();
        }
    }

    @Test
    public void testNegativeStringParsing() throws Exception {
        System.out.println("\n === JUnit4 Test: testNegativeStringParsing() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass{" +
                                                                     "   public String f1 = \"String;" +
                                                                     "   public static void main (String[] args){}" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(LexicalAnalyzerException.NO_CLOSING_GAP_FOR_STRING_FOUND));
            e.printStackTrace();
        }
    }

    @Test
    public void testNegativeCharParsing() throws Exception {
        System.out.println("\n === JUnit4 Test: testNegativeCharParsing() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass{" +
                                                                     "   public char f1 = 'c;" +
                                                                     "   public static void main (String[] args){}" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(LexicalAnalyzerException.NO_CLOSING_GAP_FOR_CHAR_FOUND));
            e.printStackTrace();
        }
    }

    @Test
    public void testIllegalInputCharacter() throws Exception {
        System.out.println("\n === JUnit4 Test: testIllegalInputCharacter() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass#{" +
                                                                     "   public char f1 = 'c;" +
                                                                     "   public static void main (String[] args){}" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(LexicalAnalyzerException.ILLEGAL_INPUT_CHARACTER));
            e.printStackTrace();
        }
    }

    @Test
    public void testNoMainClass() throws Exception {
        System.out.println("\n === JUnit4 Test: testNoMainClass() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "class MainClass{" +
                                                                     "   public char f1 = 'c';" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNoMainMethod() throws Exception {
        System.out.println("\n === JUnit4 Test: testNoMainMethod() method ===");
        try {
            LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                                                                     "public class MainClass{" +
                                                                     "   public char f1 = 'c';" +
                                                                     "}"));
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
