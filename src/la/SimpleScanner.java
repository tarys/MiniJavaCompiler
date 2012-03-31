//package la;// Simple Example Scanner Class
//
//import java_cup.runtime.ComplexSymbolFactory;
//import java_cup.runtime.Scanner;
//import java_cup.runtime.Symbol;
//import java_cup.runtime.SymbolFactory;
//import sa.SymbolsInfo;
//
//import java.io.File;
//import java.io.FileReader;
//
//public class SimpleScanner implements Scanner {
//    private FileReader inputFileReader;
//    private int currPos;
//
//    public SimpleScanner(File inputFile) throws Exception {
//        inputFileReader = new FileReader(inputFile);
//        currPos = 0;
//        advance();
//    }
//
//    /* single lookahead character */
//    protected int next_char;
//    // since cup v11 we use SymbolFactories rather than Symbols
//    private SymbolFactory sf = new ComplexSymbolFactory();
//
//    /* advance input by one character */
//    protected void advance() throws java.io.IOException {
//        next_char = inputFileReader.read();
//        currPos++;
//    }
//
//    /* initialize the scanner */
//    public void init()
//            throws java.io.IOException {
//        advance();
//    }
//
//    /* recognize and return the next complete token */
//    public Symbol next_token() throws Exception {
//        for (; ; ) {
//            int startPos = currPos;
//            switch (next_char) {
//                case '0':
//                case '1':
//                case '2':
//                case '3':
//                case '4':
//                case '5':
//                case '6':
//                case '7':
//                case '8':
//                case '9':
//                    /* parse a decimal integer */
//                    int i_val = 0;
//                    do {
//                        i_val = i_val * 10 + (next_char - '0');
//                        advance();
//                    } while (next_char >= '0' && next_char <= '9');
//                    Symbol number = sf.newSymbol("NUMBER", SymbolsInfo.NUMBER, new Integer(i_val));
//                    number.left = startPos;
//                    number.right = currPos;
//                    return number;
//                case ';':
//                    advance();
//                    return sf.newSymbol("SEMI", SymbolsInfo.SEMI);
//                case '+':
//                    advance();
//                    return sf.newSymbol("PLUS", SymbolsInfo.PLUS);
//                case '-':
//                    advance();
//                    return sf.newSymbol("MINUS", SymbolsInfo.MINUS);
//                case '*':
//                    advance();
//                    return sf.newSymbol("TIMES", SymbolsInfo.TIMES);
//                case '/':
//                    advance();
//                    return sf.newSymbol("DIVIDE", SymbolsInfo.DIVIDE);
//                case '%':
//                    advance();
//                    return sf.newSymbol("MOD", SymbolsInfo.MOD);
//                case '(':
//                    advance();
//                    return sf.newSymbol("LPAREN", SymbolsInfo.LPAREN);
//                case ')':
//                    advance();
//                    return sf.newSymbol("RPAREN", SymbolsInfo.RPAREN);
//                case -1:
//                    return sf.newSymbol("EOF", SymbolsInfo.EOF);
//                default:
//                    throw new Exception("Lexical error at " + currPos + " position");
//            }
//        }
//    }
//}
