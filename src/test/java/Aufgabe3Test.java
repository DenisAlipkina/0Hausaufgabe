import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Aufgabe3Test {
    Random random = new Random();
    @ParameterizedTest
    @CsvSource({"'(prog (stat (expr (expr (term (fact 2))) + (term (fact 2))) \\n))', '2+2'",
    "'(prog (stat (expr (expr (expr (term (fact 2))) + (term (fact 2))) - (term (fact 2))) \\n))', '2+2-2'",
    "'(prog (stat (expr (expr (term (fact 2))) + (term (fact ( (expr (expr (term (fact 2))) - (term (fact 2))) )))) \\n))','2+(2-2)'",
    "'(prog (stat (expr (expr (expr (term (term (fact 2)) * (fact 1))) + (term (term (fact ( (expr (expr (term (fact 2))) - (term (fact 2))) ))) / (fact 3))) + (term (fact 2))) \\n))','2*1+(2-2)/3+2'"})
    void testAufgabe1(String expected, String given) {
        if(random.nextBoolean()) given += "\n";
        assertTrue(expected.equals(Aufgabe3SyntaxErkenner.inputToTreeString(given, 1)));
    }

    @ParameterizedTest
    @CsvSource({"'(prog (stat (expr (term (fact 2)) + (term (fact 2))) \\n))', '2+2'",
            "'(prog (stat (expr (term (fact 2)) + (term (fact 2)) - (term (fact 2))) \\n))', '2+2-2'",
            "'(prog (stat (expr (term (fact 2)) + (term (fact ( (expr (term (fact 2)) - (term (fact 2))) )))) \\n))','2+(2-2)'",
            "'(prog (stat (expr (term (fact 2) * (fact 1)) + (term (fact ( (expr (term (fact 2)) - (term (fact 2))) )) / (fact 3)) + (term (fact 2))) \\n))','2*1+(2-2)/3+2'"})
    void testAufgabe2(String expected, String given) {
        if(random.nextBoolean()) given += "\n";
        assertTrue(expected.equals(Aufgabe3SyntaxErkenner.inputToTreeString(given, 2)));
    }
    @ParameterizedTest
    @CsvSource({"'(prog (stat (expr (expr 2) + (expr 2)) \\n))', '2+2'",
            "'(prog (stat (expr (expr (expr 2) + (expr 2)) - (expr 2)) \\n))', '2+2-2'",
            "'(prog (stat (expr (expr 2) + (expr ( (expr (expr 2) - (expr 2)) ))) \\n))','2+(2-2)'",
            "'(prog (stat (expr (expr (expr 2) * (expr (expr 1) + (expr ( (expr (expr 2) - (expr 2)) )))) / (expr (expr 3) + (expr 2))) \\n))','2*1+(2-2)/3+2'"})
    void testAufgabe3(String expected, String given) {
        if(random.nextBoolean()) given += "\n";
        assertTrue(expected.equals(Aufgabe3SyntaxErkenner.inputToTreeString(given, 3)));
    }
}