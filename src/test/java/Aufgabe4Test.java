import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Aufgabe4Test {


    @ParameterizedTest
    @CsvSource({"'7', '2+5'",
            "'25', '9+4*4'",
            "'0','5*(2-2)'",
            "'4','2*1+(2-2)/3+2'",
            "'256','2^2^3 \n'",
            "'8','2^2*2 \n'",
            "'1','3<5^2 \n'",
            "'0','2^8<10 \n'",
            "'0','a=5+2 \n'",
            "'14','a=5+2 \n a+7 \n'",
            "'14','a=5+2 \n b=12-5 \n a+b \n'",
            "'9','a=5+2 \n b=a-5 \n a+b \n'",
            "'0','clear",
            "'6','a=5+2\nclear\na+6'",
            "'5','a=5+2\nclear\na=2\na+3\n'",
            "'5','a=5\nb=3\nmax=a>b?a:b\nmax\n'"})
    void testAufgabe2(Integer expected, String given) {
        Aufgabe4_2ParseTreeVisitor ptv = new Aufgabe4_2ParseTreeVisitor();
        assertEquals(expected, ptv.calcValueOf(given));
    }

    @ParameterizedTest
    @CsvSource({"'7', '2+5'",
            "'25', '9+4*4'",
            "'0','5*(2-2)'",
            "'4','2*1+(2-2)/3+2'",
            "'256','2^2^3 \n'",
            "'8','2^2*2 \n'",
            "'1','3<5^2 \n'",
            "'0','2^8<10 \n'",
            "'0','a=5+2 \n'",
            "'14','a=5+2 \n a+7 \n'",
            "'14','a=5+2 \n b=12-5 \n a+b \n'",
            "'9','a=5+2 \n b=a-5 \n a+b \n'",
            "'0','clear",
            "'6','a=5+2\nclear\na+6'",
            "'5','a=5+2\nclear\na=2\na+3\n'",
            "'5','a=5 \n b=3 \n max=a>b?a:b \n max \n'"})
    void testAufgabe3(Integer expected, String given) throws IOException {
        Aufgabe4_3GrammatikAktionen ga = new Aufgabe4_3GrammatikAktionen();
        assertEquals(expected, ga.calcValueOf(given));
    }

    @ParameterizedTest
    @CsvSource({"'7', '2+5'",
            "'25', '9+4*4'",
            "'0','5*(2-2)'",
            "'4','2*1+(2-2)/3+2'",
            "'256','2^2^3 \n'",
            "'8','2^2*2 \n'",
            "'1','3<5^2 \n'",
            "'0','2^8<10 \n'",
            "'0','a=5+2 \n'",
            "'14','a=5+2\n a+7'",
            "'14','a=5+2\n b=12-5\n a+b'",
            "'9','a=5+2\n b=a-5\n a+b'",
            "'0','clear",
            "'6','a=5+2\nclear\na+6'",
            "'5','a=5+2\nclear\na=2\na+3\n'",
            "'5','a=5\nb=3\nmax=a>b?a:b\nmax\n'"})
    void testAufgabe4(Integer expected, String given) {
        Aufgabe4_4ParseTreeListener ptl = new Aufgabe4_4ParseTreeListener();
        assertEquals(expected, ptl.calcValueOf(given));
    }
}
