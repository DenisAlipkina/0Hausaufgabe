import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;

public class Aufgabe4_3GrammatikAktionen {

    public static void main(String[] args) throws IOException {
        //Aufgabe4_3GrammatikAktionen ga = new Aufgabe4_3GrammatikAktionen();
        //System.out.println(calcValueOf("a=5\nif(a>3) a+5\n else a+1\n"));
        //System.out.println(calcValueOf("a=5\nif(a>3)a=7\nelse a=10\n\na*2\n"));
    }

    public Integer calcValueOf(String input) throws IOException {
        /*if(!input.endsWith("\n")) input = input + "\n";
        Aufgabe4_3Parser parser = new Aufgabe4_3Parser(null); // share single parser instance
        parser.setBuildParseTree(false); // don't need trees
        ANTLRInputStream inputstream = new ANTLRInputStream(input+"\n");
        Aufgabe4_3Lexer lexer = new Aufgabe4_3Lexer(inputstream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser.setInputStream(tokens); // notify parser of new token stream
        parser.prog().stat(0);
        return parser.prog().stat(1).v;*/

        Reader inputString = new StringReader(input);
        BufferedReader br = new BufferedReader(inputString);
        String expr = br.readLine(); // get first expression
        int line = 1;
        Aufgabe4_3Parser parser = new Aufgabe4_3Parser(null); // share single parser instance
        parser.setBuildParseTree(false); // don't need trees
        while ( expr!=null ) { // while we have more expressions
            // create new lexer and token stream for each line (expression)
            ANTLRInputStream inputStream = new ANTLRInputStream(expr+"\n");
            Aufgabe4_3Lexer lexer = new Aufgabe4_3Lexer(inputStream);
            lexer.setLine(line); // notify lexer of input position
            lexer.setCharPositionInLine(0);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser.setInputStream(tokens); // notify parser of new token stream
            parser.stat(); // start the parser
            expr = br.readLine(); // see if there's another line
            line++;
        }
        return parser.result;
    }
}
