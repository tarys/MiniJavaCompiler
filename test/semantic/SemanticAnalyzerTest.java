package semantic;

import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;

public class SemanticAnalyzerTest {
    @Test
    public void testNotDeclaredVar() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       int s = -15;" +
                "       q = System.in.read();" +
                "   }" +
                "}"));
        try {
            parser.parse();
        } catch (SemanticException e) {
            Assert.assertTrue(e.getMessage().startsWith(SemanticException.NOT_DECLARED_BUT_USED_VARIABLE_FIELD_OR_METHOD_PARAMETER));
        }
    }
}
