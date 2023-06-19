import fpinjava.common.Effect;
import fpinjava.common.Function;
import fpinjava.common.Result;
import helpclasses.Input;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Aufgabe2Analysator {
    public Function<CharStream, Lexer> lexer = HalsteadLexer::new;
    public java.util.Map<String, Integer> operand, operator;
    public String input, output;
    public STGroup templates;
    public ST halstead_st;

    private Aufgabe2Analysator(String path) {
        operand = new HashMap<>();
        operator = new HashMap<>();
        templates = new STGroupFile("src/main/resources/aufgabe2operator.stg");
        halstead_st = templates.getInstanceOf("halstead");
        input = path;
        Result<Input<Token>> rTokenReader = TokenReader.lexFile(input, lexer);
        rTokenReader.forEach(rt -> rt.stream().forEach(f));
    }
    public static Aufgabe2Analysator analysator(String path) {
        return new Aufgabe2Analysator(path);
    }

    public List<Integer> getParameter() {
        List<Integer> list = new LinkedList<>();
        list.add(this.operator.values().stream().mapToInt(Integer::intValue).sum());    //0 - N1
        list.add(this.operand.values().stream().mapToInt(Integer::intValue).sum());     //1 - N2
        list.add(this.operator.size());                                                 //2 - n1
        list.add(this.operand.size());                                                  //3 - n2
        list.add(list.get(0) + list.get(1));                                            //4 - N
        list.add(list.get(2) + list.get(3));                                            //5 - n
        return list;
    }

    public List<Double> getMetriken() {
        List<Integer> params = getParameter();
        List<Double> metriken = new LinkedList<>();
        metriken.add(params.get(4) * (Math.log(params.get(5)) / Math.log(2)) * 100 / 100.0);        //0 - V
        metriken.add(((double) params.get(2)/2) * ((double) params.get(1)/(double) params.get(3))); //1 - D
        metriken.add(metriken.get(0) * metriken.get(1));                                            //2 - E
        return metriken;
    }

    private String get_output() {
        List<Integer> params = getParameter();
        List<Double> metriken = getMetriken();
        //Stringtemplate wird befüllt
        halstead_st.add("map1", this.operator).add("map2", this.operand).add("N1", params.get(0))
                .add("N2", params.get(1)).add("n1", params.get(2)).add("n2", params.get(3)).add("N", params.get(4))
                .add("n", params.get(5)).add("V", metriken.get(0)).add("D", metriken.get(1)).add("E", metriken.get(2));
        return halstead_st.render();
    }





    //void methode um Codeduplizierung zu verhindern
    private void fill_map(java.util.Map<String, Integer> map, String value) {
        if(map.containsKey(value)) {
            map.put(value, map.get(value) + 1);
        } else {
            map.put(value, 1);
        }
    }
    /*Zu testenden Lexer hier im switch case bearbeiten*/
    private Effect<Token> f = t -> {
        switch (t.getType()) {
            case HalsteadLexer.OPERAND:
                fill_map(this.operand, t.getText());
                break;
            case HalsteadLexer.OPERATOR:
                fill_map(this.operator, t.getText());
                break;
        }
    };

    public static void main(String[] args) {
        Aufgabe2Analysator analysator = new Aufgabe2Analysator("src/main/resources/test/Aufgabe2Testfileeval.c");
        System.out.println(analysator.get_output());
    }
}
