package semantic;

import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

public class SemanticAnalyzerTest {
    @Test
    public void testNotDeclaredVarFieldParameter() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       int s = -15;" +
                "       q = System.in.read();" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER));
        }
    }

    @Test
    public void testNotDeclaredClass() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass s;" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NOT_DECLARED_CLASS));
        }
    }

    @Test
    public void testNotDeclaredField() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "}" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass s;" +
                "       s.d;" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NO_SUCH_FIELD_IN_CLASS));
        }
    }

    @Test
    public void testNotDeclaredMethod() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "class MyClass{" +
                "" +
                "}" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       MyClass s;" +
                "       s.d();" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NO_SUCH_METHOD_IN_CLASS));
        }
    }

    @Test
    public void testIncompatibleAssignment() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       boolean s;" +
                "       int k;" +
                "       s = k;" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.INCOMPATIBLE_TYPES));
        }
    }
    @Test
    public void testNotBooleanIfCondition() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       if(1){" +
                "           int s = 14;" +
                "       }" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NOT_BOOLEAN_EXPRESSION));
        }
    }
    @Test
    public void testNotBooleanWhileCondition() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       while(1){" +
                "           int s = 14;" +
                "       }" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NOT_BOOLEAN_EXPRESSION));
        }
    }
    @Test
    public void testBreakWithoutWhile() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       break;" +
                "   }" +
                "}"));
        try {
            parser.parse();
            Assert.fail();
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.BREAK_USED_BUT_LOOP_NOT_DECLARED));
        }
    }
}
