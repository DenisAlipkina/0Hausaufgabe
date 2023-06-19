import fpinjava.common.Tuple;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.util.*;

public class Aufgabe5_2 {


    public Graph graph;
    public static void main(String[] args) {
        Aufgabe5_2 a = new Aufgabe5_2();
        System.out.println(a.makeDot("src/main/resources/textfile.txt"));
    }

    public String makeDot(String filename) {
        ANTLRInputStream inputstream = null;
        try {
            inputstream = new ANTLRFileStream(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Aufgabe5_1Lexer lexer = new Aufgabe5_1Lexer(inputstream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Aufgabe5_1Parser parser = new Aufgabe5_1Parser(tokens);
        ParseTree tree = parser.file(); // parse


        ParseTreeWalker walker = new ParseTreeWalker();
        FunctionListener collector = new FunctionListener();
        walker.walk(collector, tree);
        this.graph = collector.graph;
        return collector.graph.toDOT();

    }


    public static class Graph {
        // I'm using org.antlr.v4.runtime.misc: OrderedHashSet, MultiMap
        Set<String> nodes = new OrderedHashSet<String>(); // list of functions
        MultiMap<String, String> edges = // caller->callee
                new MultiMap<String, String>();
        List<String> nodesWhite = new LinkedList<>();
        List<String> nodesRed = new LinkedList<>();
        List<String> nodesGreen = new LinkedList<>();
        List<Tuple<String, String>> kantenred = new LinkedList<>();
        List<Tuple<String, String>> kantengreen = new LinkedList<>();
        List<Tuple<String, String>> kantenblack = new LinkedList<>();

        public void edge(String source, String target) {
            edges.map(source, target);
        }

        public String toDOT() {
            ST stringTemplate = new STGroupFile("src/main/resources/aufgabe5.stg").getInstanceOf("graph");
            stringTemplate.add("nodesWhite", nodesWhite).add("nodesGreen", nodesGreen).add("nodesRed", nodesRed)
                    .add("kantenBlack", kantenblack).add("kantenGreen", kantengreen).add("kantenRed", kantenred);
            return stringTemplate.render();
        }
    }

    public static class FunctionListener extends Aufgabe5_1BaseListener {
        Graph graph = new Graph();
        String currentFunctionName = "";
        boolean operationCall = false;
        boolean functioncall = false;
        boolean returnCall = false;
        boolean ifelseCall = false;
        boolean rekursion = false;
        boolean endrekursion = false;
        boolean funcrekursion = false;
        boolean funcendrekursion = false;


        @Override
        public void enterFuncDecl(Aufgabe5_1Parser.FuncDeclContext ctx) {
            currentFunctionName = ctx.ID().getText();
            graph.nodes.add(currentFunctionName);
        }

        @Override
        public void exitFuncDecl(Aufgabe5_1Parser.FuncDeclContext ctx) {
            if(funcendrekursion)
                graph.nodesRed.add(currentFunctionName);
            else if(funcrekursion)
                graph.nodesGreen.add(currentFunctionName);
            else
                graph.nodesWhite.add(currentFunctionName);
            funcendrekursion = false;
            funcrekursion = false;
        }

        @Override
        public void enterIfElse(Aufgabe5_1Parser.IfElseContext ctx) {
            ifelseCall = true;
        }

        @Override
        public void exitIfElse(Aufgabe5_1Parser.IfElseContext ctx) {
            ifelseCall = false;
        }

        @Override
        public void enterIrrAddSub(Aufgabe5_1Parser.IrrAddSubContext ctx) {
            operationCall = true;
        }

        @Override
        public void enterIrrMultDiv(Aufgabe5_1Parser.IrrMultDivContext ctx) {
            operationCall = true;
        }

        @Override
        public void enterReturn(Aufgabe5_1Parser.ReturnContext ctx) {
            returnCall = true;
        }

        @Override
        public void exitReturn(Aufgabe5_1Parser.ReturnContext ctx) {
            returnCall = false;
            rekursion = false;
            endrekursion = false;
            operationCall = false;
        }

        @Override
        public void enterFuncCall(Aufgabe5_1Parser.FuncCallContext ctx) {
            String funcName = ctx.ID().getText();
            if (funcName.equals(currentFunctionName)) {
                rekursion = true;
                funcrekursion = true;
            }
            if ((functioncall || operationCall) && funcName.equals(currentFunctionName)) {
                endrekursion = true;
                funcendrekursion = true;
            }
            if (endrekursion)
                graph.kantenred.add(new Tuple<>(currentFunctionName, funcName));
            else if (rekursion || (ifelseCall && returnCall))
                graph.kantengreen.add(new Tuple<>(currentFunctionName, funcName));
            else
                graph.kantenblack.add(new Tuple<>(currentFunctionName, funcName));
            functioncall = true;
        }

        @Override
        public void exitFuncCall(Aufgabe5_1Parser.FuncCallContext ctx) {
            String funcName = ctx.ID().getText();
            graph.edge(currentFunctionName, funcName);
            functioncall = false;
        }
    }
}

/*public static class FunctionListener extends Aufgabe5_1BaseListener {
        Graph graph = new Graph();
        String currentFunctionName = "";
        List<Boolean> operation = new LinkedList<>();
        boolean functioncall = false;
        boolean colorSet = false;
        boolean kanteColorSet = false;
        boolean kanteGreenSet = false;
        boolean returnSet = false;
        boolean ifelseSet = false;

        public void addColor(String func, String color) {
            List<String> listOfColors = graph.kantencolors.getOrDefault(new Tuple<>(currentFunctionName, func), new ArrayList<>());
            listOfColors.add(color);
            graph.kantencolors.put(new Tuple<>(currentFunctionName, func), listOfColors);
        }

        @Override
        public void enterFuncDecl(Aufgabe5_1Parser.FuncDeclContext ctx) {
            currentFunctionName = ctx.ID().getText();
            graph.nodes.add(currentFunctionName);
            //graph.nodecolors.put(currentFunctionName, "red");

        }

        @Override
        public void exitFuncDecl(Aufgabe5_1Parser.FuncDeclContext ctx) {
            if (graph.nodecolors.containsKey(currentFunctionName)) {
                if (graph.nodecolors.get(currentFunctionName).equals("green")) {
                    colorSet = true;
                }
            }
            if (!colorSet) {
                graph.nodecolors.put(currentFunctionName, "white");
            }
            colorSet = false;
        }


        @Override
        public void enterIrrAddSub(Aufgabe5_1Parser.IrrAddSubContext ctx) {
            operation.add(true);
            super.enterIrrAddSub(ctx);
        }

        @Override
        public void exitIrrAddSub(Aufgabe5_1Parser.IrrAddSubContext ctx) {
            operation.remove(operation.size() - 1);
            super.exitIrrAddSub(ctx);
        }

        @Override
        public void enterIrrMultDiv(Aufgabe5_1Parser.IrrMultDivContext ctx) {
            operation.add(true);
            super.enterIrrMultDiv(ctx);
        }

        @Override
        public void exitIrrMultDiv(Aufgabe5_1Parser.IrrMultDivContext ctx) {
            operation.remove(operation.size() - 1);
            super.exitIrrMultDiv(ctx);
        }

        @Override
        public void enterReturn(Aufgabe5_1Parser.ReturnContext ctx) {
            returnSet = true;
            super.enterReturn(ctx);
        }

        @Override
        public void exitReturn(Aufgabe5_1Parser.ReturnContext ctx) {
            returnSet = false;
            super.exitReturn(ctx);
        }

        @Override
        public void enterIfElse(Aufgabe5_1Parser.IfElseContext ctx) {
            ifelseSet = true;
            super.enterIfElse(ctx);
        }

        @Override
        public void exitIfElse(Aufgabe5_1Parser.IfElseContext ctx) {
            ifelseSet = false;
            super.exitIfElse(ctx);
        }

        @Override
        public void enterFuncCall(Aufgabe5_1Parser.FuncCallContext ctx) {

            String funcName = ctx.ID().getText();

            if (funcName.equals(currentFunctionName)) {
                if(!operation.isEmpty()) {
                    if(operation.get(operation.size() - 1)){
                        if (!colorSet) {
                            graph.nodecolors.put(currentFunctionName, "red");
                            colorSet = true;
                        }
                        if(!kanteColorSet) {
                            addColor(funcName, "red");
                            kanteColorSet = true;
                        }
                    }
                }
            }
            if (functioncall && funcName.equals(currentFunctionName)) {
                if (!colorSet) {
                    graph.nodecolors.put(currentFunctionName, "red");
                    colorSet = true;
                }
                if(!kanteColorSet) {
                    addColor(funcName, "red");
                    kanteColorSet = true;
                }
            } else if (!functioncall && funcName.equals(currentFunctionName)) {
                if (!colorSet) {
                    graph.nodecolors.put(currentFunctionName, "green");
                }
                if(!kanteColorSet && returnSet) {
                    addColor(funcName, "green");
                    kanteGreenSet = true;
                }
            } else if(!functioncall) {
                if(!kanteColorSet && returnSet && ifelseSet) {
                    addColor(funcName, "green");
                    kanteGreenSet = true;
                }
            }
            functioncall = true;
            super.enterFuncCall(ctx);
        }

        @Override
        public void exitFuncCall(Aufgabe5_1Parser.FuncCallContext ctx) {
            String funcName = ctx.ID().getText();
            graph.edge(currentFunctionName, funcName);


            if (kanteGreenSet) {
                kanteColorSet = true;
            }


            if (!kanteColorSet) {
                addColor(funcName, "black");
            }
            kanteGreenSet = false;
            kanteColorSet = false;
            functioncall = false;
        }
    }
}*/