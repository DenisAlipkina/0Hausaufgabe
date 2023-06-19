import fpinjava.common.Tuple;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class Aufgabe5Test {

    @ParameterizedTest
    @CsvSource({"src/main/resources/test/Aufgabe5Input.txt"})
    void testAufgabe2(String inputFilename) {
        Aufgabe5_2 a = new Aufgabe5_2();
        a.makeDot(inputFilename);
        List<String> nodesWhite = a.graph.nodesWhite;
        List<String> nodesRed = a.graph.nodesRed;
        List<String> nodesGreen = a.graph.nodesGreen;
        List<Tuple<String, String>> kantenred = a.graph.kantenred;
        List<Tuple<String, String>> kantengreen = a.graph.kantengreen;
        List<Tuple<String, String>> kantenblack = a.graph.kantenblack;
        assertTrue(kantenblack.toString().equals("[(main,sum), (main,sum2), (main,binomi), (main,fib), (main,kgV), (main,ackermann), (main,mcCarthy), (binomi,fact), (binomi,fact), (binomi,fact), (kgV,ggT), (collatz,even)]") &&
                        kantengreen.toString().equals("[(add,identity), (add,add), (add2,add2), (add2,identity), (sum,add), (sum2,add2), (ggT,ggT), (ggT,ggT), (mcCarthy,mcCarthy), (ackermann,ackermann), (ackermann,ackermann)]") &&
                        kantenred.toString().equals("[(sum,sum), (sum2,sum2), (fact,fact), (fib,fib), (fib,fib), (mcCarthy,mcCarthy), (ackermann,ackermann), (collatz,collatz), (collatz,collatz)]") &&
                nodesWhite.toString().equals("[main, identity, binomi, kgV]") &&
                nodesGreen.toString().equals("[add, add2, ggT]") &&
                nodesRed.toString().equals("[sum, sum2, fact, fib, mcCarthy, ackermann, collatz]"));



        //assertEquals(expected, a.makeDot(inputFilename));
    }

}


