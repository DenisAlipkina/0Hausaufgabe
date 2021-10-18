import fpinjava.common.Function;
import fpinjava.common.Result;
import fpinjava.common.Tuple;
import helpclasses.Input;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;


public class TokenReader implements Input<Token> {
    private Lexer lexer;
    private TokenReader(Lexer lexer){
        this.lexer = lexer;
    }
    public static Input<Token> tokenReader(Lexer lexer){
        return new TokenReader(lexer);
    }
    public static Result<Input<Token>> lexFile(String filename, Function<CharStream,Lexer> lexer) {

        CharStream input = null;
        try {
            if (filename.length() > 0) input = new ANTLRFileStream(filename);
            else input = new ANTLRInputStream(System.in);

            return Result.success(tokenReader(lexer.apply(input)));
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;

    }
    @Override
    public Result<Tuple<Token, Input<Token>>> read() {
        Token t = lexer.nextToken();
        return t.getType() == Token.EOF
                ? Result.empty()
                : Result.success(new Tuple<>(t,this));
    }

    @Override
    public void close() throws Exception {

    }
}