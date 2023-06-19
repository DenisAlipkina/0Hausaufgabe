import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Aufgabe4_4ParseTreeListener {

    public static Map<String, Integer> variables = new HashMap<>();
    public static Integer tempValue = 0;

    public Integer calcValueOf(String input){
        if(!input.endsWith("\n")) input = input + "\n";
        ANTLRInputStream inputstream = new ANTLRInputStream(input);
        Aufgabe4_2Lexer lexer = new Aufgabe4_2Lexer(inputstream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Aufgabe4_2Parser parser = new Aufgabe4_2Parser(tokens);
        ParseTree tree = parser.prog(); // parse
        ParseTreeWalker walker = new ParseTreeWalker();
        EvaluatorWithProps evalProp = new EvaluatorWithProps();
        walker.walk(evalProp, tree);
        int value = evalProp.result;
        return value;
    }


    public static void main(String[] args) {

        Aufgabe4_4ParseTreeListener ptv = new Aufgabe4_4ParseTreeListener();
        System.out.println(ptv.calcValueOf("a=5"));
        System.out.println(ptv.calcValueOf("b=2"));
        System.out.println(ptv.calcValueOf("max = a > b ? a : b"));
        System.out.println(ptv.calcValueOf("max"));
    }

    public class EvaluatorWithProps extends Aufgabe4_2BaseListener {

        public Integer getInt(String var_name) {
            return variables.containsKey(var_name) ? variables.get(var_name) : 0;
        }

        public void setInt(String var_name, Integer var_value) {
            variables.put(var_name, var_value);
        }

        /** maps nodes to integers with Map<ParseTree,Integer> */
        ParseTreeProperty<Integer> values = new ParseTreeProperty<Integer>();
        int result = 0;
        public void setValue(ParseTree node, int value) {
            tempValue = value;
            values.put(node, value);
        }
        public int getValue(ParseTree node) { return values.get(node); }

        @Override
        public void exitConExpr(Aufgabe4_2Parser.ConExprContext ctx) {
            if(getValue(ctx.expr(0)) == 1) {
                setValue(ctx,getValue(ctx.expr(1)));
            } else if(getValue(ctx.expr(0)) == 0) {
                setValue(ctx,getValue(ctx.expr(2)));
            } else {
                setValue(ctx,0);
            }
        }

        @Override
        public void exitExprNewLine(Aufgabe4_2Parser.ExprNewLineContext ctx) {
            result = getValue(ctx.expr());
        }

        @Override
        public void exitClear(Aufgabe4_2Parser.ClearContext ctx) {
            variables = new HashMap<>();
            tempValue = 0;
        }

        @Override
        public void exitCompare(Aufgabe4_2Parser.CompareContext ctx) {
            int left = getValue(ctx.expr(0));
            int right = getValue(ctx.expr(1));
            if (ctx.op.getType() == Aufgabe4_2Parser.EQUAL) {
                setValue(ctx,left == right ? 1 : 0);
            } else if(ctx.op.getType() == Aufgabe4_2Parser.BIGER) {
                setValue(ctx,left > right ? 1 : 0);
            } else {
                setValue(ctx,left < right ? 1 : 0);
            }
        }

        @Override
        public void exitVar(Aufgabe4_2Parser.VarContext ctx) {
            String intText = ctx.VAR().getText();
            setValue(ctx, getInt(intText));
            super.exitVar(ctx);
        }

        @Override
        public void exitInitvar(Aufgabe4_2Parser.InitvarContext ctx) {
            setInt(ctx.VAR().getText(), tempValue);
            super.exitInitvar(ctx);
        }

        @Override
        public void exitPow(Aufgabe4_2Parser.PowContext ctx) {
            int left = getValue(ctx.expr(0));
            int right = getValue(ctx.expr(1));
            setValue(ctx,(int) Math.pow(left, right)); // must be DIV
        }

        @Override
        public void exitMulDiv(Aufgabe4_2Parser.MulDivContext ctx) {
            int left = getValue(ctx.expr(0)); // e '+' e # Add
            int right = getValue(ctx.expr(1));
            if(ctx.op.getType() == Aufgabe4_2Parser.MUL) {
                setValue(ctx, left * right);
            }else {
                setValue(ctx, left / right);
            }
        }

        @Override
        public void exitBrackets(Aufgabe4_2Parser.BracketsContext ctx) {
            setValue(ctx, getValue(ctx.expr()));
        }

        @Override
        public void exitAddSub(Aufgabe4_2Parser.AddSubContext ctx) {
            int left = getValue(ctx.expr(0)); // e '+' e # Add
            int right = getValue(ctx.expr(1));
            if(ctx.op.getType() == Aufgabe4_2Parser.ADD) {
                setValue(ctx, left + right);
            }else {
                setValue(ctx, left - right);
            }
        }

        @Override
        public void exitEveryRule(ParserRuleContext ctx) {
            super.exitEveryRule(ctx);
        }

        @Override
        public void exitInt(Aufgabe4_2Parser.IntContext ctx) {
            String intText = ctx.Number().getText();
            setValue(ctx, Integer.parseInt(intText));
        }
    }
}
