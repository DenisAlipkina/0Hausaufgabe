import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Aufgabe0 {

    /*Bespieleingabe: line from 0,3 to 7,8 + Enter + (Strg + D)*/
    public static void main(String[] args) throws Exception {
        CharStream input = null;
        // Pick an input stream (filename from commandline or stdin)
        if (args.length > 0) input = new ANTLRFileStream(args[0]);
        else input = new ANTLRInputStream(System.in);
        // Create the lexer
        GraphicsLexer lex = new GraphicsLexer(input);
        // Create a buffer of tokens between lexer and parser
        CommonTokenStream tokens = new CommonTokenStream(lex);
        // Create the parser, attaching it to the token buffer
        GraphicsParser p = new GraphicsParser(tokens);
        p.file(); // launch parser at rule file
    }
}