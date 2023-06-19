import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

public class Aufgabe3SyntaxErkenner {

    public static String inputToTreeString(String input, int aufgaben_nummer) {
        ANTLRInputStream inputstream = checkInput(input);
        Lexer lexer;
        CommonTokenStream tokenStream;
        ParseTree parseTree;
        switch (aufgaben_nummer) {
            case(1):
                lexer = new Aufgabe3_1Lexer(inputstream);
                tokenStream = new CommonTokenStream(lexer);
                Aufgabe3_1Parser parser1 = new Aufgabe3_1Parser(tokenStream);
                parseTree = parser1.prog();
                return parseTree.toStringTree(parser1);
            case(2):
                lexer = new Aufgabe3_2Lexer(inputstream);
                tokenStream = new CommonTokenStream(lexer);
                Aufgabe3_2Parser parser2 = new Aufgabe3_2Parser(tokenStream);
                parseTree = parser2.prog();
                return parseTree.toStringTree(parser2);
            case(3):
                lexer = new Aufgabe3_3Lexer(inputstream);
                tokenStream = new CommonTokenStream(lexer);
                Aufgabe3_3Parser parser3 = new Aufgabe3_3Parser(tokenStream);
                parseTree = parser3.prog();
                return parseTree.toStringTree(parser3);
            default:
                return "Unexpected value: " + aufgaben_nummer;
        }
    }

    private static ANTLRInputStream checkInput(String input) {
        return !input.endsWith("\n") ? new ANTLRInputStream(input + "\n") : new ANTLRInputStream(input);
    }


    public static void main(String[] args) {
        System.out.println(inputToTreeString("2*1+(2-2)/3+", 1));

    }
}
