package codegeneration;

import la.LexicalAnalyzer;
import org.junit.Test;
import sa.LR1Analyzer;

import java.util.List;

public class CodeGeneratorTest {
    @Test
    public void testUnaryMinus() throws Exception {
        LR1Analyzer parser = new LR1Analyzer(new LexicalAnalyzer("" +
                "public class MainClass{" +
                "   public static void main (String[] args){" +
                "       int q = -12;" +
                "       q = q + 2;" +
                "       q = q - 2;" +
                "       q = q * 2;" +
                "       q = q / 2;" +
                "   }" +
                "}"));
        parser.parse();
        List<Quad> code = parser.getByteCode();
        for (Quad quad : code) {
            System.out.println(quad);
        }
    }
}
