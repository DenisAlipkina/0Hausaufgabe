import Inf2.Result.Result;
import Inf2.function.Function;
import helpclasses.Input;
import inout.Effect;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import java.io.IOException;


public class App {
    /*Zu testenden Lexer hier eingeben*/
    public static Function<CharStream,Lexer> lexer = IPFilter::new;

    public static void main(String[] args) {
        try {
            String input = "src/main/java/textfile.txt";
            Result<Input<Token>> rTokenReader = TokenReader.lexFile(input, lexer);
            rTokenReader.forEach(rt->rt.stream().forEach(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Zu testenden Lexer hier im switch case bearbeiten*/
    private static Effect<Token> f = t -> {
        int line = t.getLine();
        int pos = t.getCharPositionInLine();
        String text = t.getText();
        switch(t.getType()) {
            case IPFilter.IP4:
                System.out.printf("line %d:%d Found IPv4: %s\n", line, pos, text);
                break;
            case IPFilter.IP6:
                System.out.printf("line %d:%d Found IPv6: %s\n", line, pos, text);
                break;
        }
    };
}