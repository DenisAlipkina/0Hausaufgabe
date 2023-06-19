import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;

public class Aufgabe4_2ParseTreeVisitor {
    public static Map<String, Integer> variables = new HashMap<>();

    public Integer calcValueOf(String input) {
        if (!input.endsWith("\n")) input = input + "\n";
        ANTLRInputStream inputstream = new ANTLRInputStream(input);
        Aufgabe4_2Lexer lexer = new Aufgabe4_2Lexer(inputstream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Aufgabe4_2Parser parser = new Aufgabe4_2Parser(tokens);
        ParseTree tree = parser.prog(); // parse
        EvalVisitor eval = new EvalVisitor();
        int value = eval.visit(tree);
        return value;
    }

    public static void main(String[] args) {
        Aufgabe4_2ParseTreeVisitor ptv = new Aufgabe4_2ParseTreeVisitor();
        System.out.println(ptv.calcValueOf("a=5"));
        System.out.println(ptv.calcValueOf("b=2"));
        System.out.println(ptv.calcValueOf("max = a > b ? a : b"));
        System.out.println(ptv.calcValueOf("max"));
    }

    public class EvalVisitor extends Aufgabe4_2BaseVisitor<Integer> {


        public Integer getInt(String var_name) {
            return variables.get(var_name);
        }

        public void setInt(String var_name, Integer var_value) {
            variables.put(var_name, var_value);
        }

        @Override
        public Integer visitProg(Aufgabe4_2Parser.ProgContext ctx) {
            return super.visitProg(ctx);
        }

        @Override
        public Integer visitExprNewLine(Aufgabe4_2Parser.ExprNewLineContext ctx) {
            return visit(ctx.expr()); // return dummy value
        }

        @Override
        public Integer visitNewLine(Aufgabe4_2Parser.NewLineContext ctx) {
            return super.visitNewLine(ctx);//return dummy value
        }

        @Override
        public Integer visitPow(Aufgabe4_2Parser.PowContext ctx) {
            int left = visit(ctx.expr(0)); // get value of left subexpression
            int right = visit(ctx.expr(1)); // get value of right subexpression
            return (int) Math.pow(left, right); // must be DIV
        }

        @Override
        public Integer visitVar(Aufgabe4_2Parser.VarContext ctx) {
            return variables.containsKey(ctx.VAR().getText()) ? getInt(ctx.VAR().getText()) : 0;
            //return getInt(ctx.VAR().getText());
        }

        @Override
        public Integer visitInitvar(Aufgabe4_2Parser.InitvarContext ctx) {
            setInt(ctx.VAR().getText(), visit(ctx.expr()));
            return 0;//dummy value
        }

        @Override
        public Integer visitConExpr(Aufgabe4_2Parser.ConExprContext ctx) {
            if(visit(ctx.expr(0)) == 1) {
                return visit(ctx.expr(1));
            } else if(visit(ctx.expr(0)) == 0) {
                return visit(ctx.expr(2));
            }
            return 0;
        }

        @Override
        public Integer visitCompare(Aufgabe4_2Parser.CompareContext ctx) {
            int left = visit(ctx.expr(0)); // get value of left subexpression
            int right = visit(ctx.expr(1)); // get value of right subexpression
            if (ctx.op.getType() == Aufgabe4_2Parser.EQUAL) {
                return left == right ? 1 : 0;
            } else if (ctx.op.getType() == Aufgabe4_2Parser.BIGER) {
                return left > right ? 1 : 0;
            } else {
                return left < right ? 1 : 0;
            }
        }

        @Override
        public Integer visitClear(Aufgabe4_2Parser.ClearContext ctx) {
            variables = new HashMap<>();
            return 0;
        }

        @Override
        public Integer visitAddSub(Aufgabe4_2Parser.AddSubContext ctx) {
            int left = visit(ctx.expr(0)); // get value of left subexpression
            int right = visit(ctx.expr(1)); // get value of right subexpression
            if (ctx.op.getType() == Aufgabe4_2Parser.ADD) return left + right;
            return left - right; // must be SUB
        }


        @Override
        public Integer visitMulDiv(Aufgabe4_2Parser.MulDivContext ctx) {
            int left = visit(ctx.expr(0)); // get value of left subexpression
            int right = visit(ctx.expr(1)); // get value of right subexpression
            if (ctx.op.getType() == Aufgabe4_2Parser.MUL) return left * right;
            return left / right; // must be DIV
        }

        @Override
        public Integer visitInt(Aufgabe4_2Parser.IntContext ctx) {
            return Integer.valueOf(ctx.Number().getText());
        }

        @Override
        public Integer visitBrackets(Aufgabe4_2Parser.BracketsContext ctx) {
            return visit(ctx.expr()); // return child expr's value
        }
    }
}
