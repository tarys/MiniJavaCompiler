package codegeneration;

import la.LexicalAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import sa.LR1Analyzer;
import semantic.SemanticException;

public class CodeGeneratorTest {
    @Test
    public void testUnaryMinus() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       int q = -12;" +
                "   }" +
                "}"));
            parser.parse();
    }
}
