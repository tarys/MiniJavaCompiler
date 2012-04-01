package la;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Taras Slipets
 */
public class LexicalAnalyzerTest {

    public static final int START_ANALYZE_POSITION = 0;
    public static LexicalAnalyzer lexicalAnalyzer;

    @BeforeClass
    public static void setUpClass() throws Exception {
        lexicalAnalyzer = new LexicalAnalyzer();
    }

    @Test
    public void testGetIdLexeme() throws Exception {
        String codeText = "test id string";
        Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.IDENTIFIER);
        System.out.println("\n === JUnit4 Test: testGetIdLexeme() method ===");
        System.out.println("\nInput: \"" + codeText + "\"");
        System.out.println("Found lexeme: " + lexeme);
    }

    @Test
    public void testGetCharLexeme() throws Exception {
        String codeText = "'\u0412'";
        Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.CHAR);
        System.out.println("\n === JUnit4 Test: testGetCharLexeme() method ===");
        System.out.println("\nInput: \"" + codeText + "\"");
        System.out.println("Found lexeme: " + lexeme);
    }

    @Test
    public void testGetDelimiterLexeme() throws Exception {
        String[] codeTexts = {"\t",
                "(",
                ")",
                "{",
                "}",
                ";",
                ",",
                " ",
                "\n"
        };
        System.out.println("\n === JUnit4 Test: testGetDelimiterLexeme() method ===");
        for (String codeText : codeTexts) {
            try {
                Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.DELIMITER);
                System.out.println("\nInput: \"" + codeText + "\"");
                System.out.println("Found lexeme: " + lexeme);
            } catch (LexicalAnalyzerException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetFloatLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetFloatLexeme() method ===");
        String[] codeTexts = {"10.45",
                "-10.45",
                "10.45e+1",
                "10.45E+1",
                "10.45E-1",
                "10.45E-1",
                "-10.45E-1",
                "3.4028235E+38",};
        for (String codeText : codeTexts) {
            try {
                Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.FLOAT);
                System.out.println("\nInput: \"" + codeText + "\"");
                System.out.println("Found lexeme: " + lexeme);
            } catch (LexicalAnalyzerException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetIntegerLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetIntegerLexeme() method ===");
        String[] codeTexts = {"10",
                "-10",
                "2147483647"};
        for (String codeText : codeTexts) {
            try {
                Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.INTEGER);
                System.out.println("\nInput: \"" + codeText + "\"");
                System.out.println("Found lexeme: " + lexeme);
            } catch (LexicalAnalyzerException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testGetBooleanLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetBooleanLexeme() method ===");
        String[] codeTexts = {"true",
                "false"};
        for (String codeText : codeTexts) {
            try {
                Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.BOOLEAN);
                System.out.println("\nInput: \"" + codeText + "\"");
                System.out.println("Found lexeme: " + lexeme);
            } catch (LexicalAnalyzerException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetKeywordLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetKeywordLexeme() method ===");
        String[] codeTexts = {"boolean",
                "byte",
                "char",
                "class",
                "else",
                "float",
                "if",
                "instanceof",
                "int",
                "main",
                "new",
                "public",
                "return",
                "static",
                "String",
                "System.in.read",
                "System.out.println",
                "break",
                "void",
                "while"};
        for (String codeText : codeTexts) {
            Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.KEYWORD);
            System.out.println("\nInput: \"" + codeText + "\"");
            System.out.println("Found lexeme: " + lexeme);
        }
    }

    @Test
    public void testGetOperatorLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetOperatorLexeme() method ===");
        String[] codeTexts = {".",
                "*",
                "/",
                "+",
                "-",
                "<",
                "<=",
                ">",
                ">=",
                "instanceof",
                "==",
                "!=",
                "!",
                "&&",
                "||",
                "=",
                "System.out.println",
                "System.in.readln",
                "break",
                ","};
        for (String codeText : codeTexts) {
            try {
                Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, START_ANALYZE_POSITION, LexemeBuilder.Type.OPERATOR);
                System.out.println("\nInput: \"" + codeText + "\"");
                System.out.println("Found lexeme: " + lexeme);
            } catch (LexicalAnalyzerException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetStringLexeme() throws Exception {
        System.out.println("\n === JUnit4 Test: testGetStringLexeme() method ===");
        String codeText = "\npublic String field1 = \"str\";\n";
        lexicalAnalyzer.sourceCodeText = codeText;
        Lexeme lexeme = lexicalAnalyzer.getLexeme(codeText, 24, LexemeBuilder.Type.STRING);
        System.out.println("\nInput: \"" + codeText + "\"");
        System.out.println("Found lexeme: " + lexeme);
        Assert.assertEquals("\"str\"", lexeme.getValue().toString());
    }

    @Test
    public void testBool() throws Exception {
        System.out.println("\n === JUnit4 Test: testBool() method ===");
        System.out.println("123 " + true);
        System.out.println("123 " + false);
    }

    @Test
    public void testStringUtil() throws Exception {
        System.out.println("\n === JUnit4 Test: testStringUtil() method ===");
        String source = "public String field1 = \"123\";";
        System.out.println("inputString: " + source);
        String fetchedSubstring = lexicalAnalyzer.fetchStringLiteral(source, source, 23);
        System.out.println("fetchedSubstring: '" + fetchedSubstring + "'");
        Assert.assertEquals("\"123\"", fetchedSubstring);

    }

}
