import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class Aufgabe2Test {
    @ParameterizedTest
    @CsvSource({"15, 15, 11, 4, 30, 15, 117.20671786825557, 20.625, 2417.3885560327712, src/main/resources/test/Aufgabe2Testfileggt1.c",
    "17, 18, 9, 6, 35, 15, 136.74117084629816, 13.5, 1846.0058064250252, src/main/resources/test/Aufgabe2Testfileggt2.c",
    "20, 19, 10, 12, 39, 22, 173.9178331268546, 7.916666666666666, 1376.8495122542654, src/main/resources/test/Aufgabe2Testfilemain.c",
    "157, 122, 22, 30, 279, 52, 1590.4226813613648, 44.733333333333334, 71144.90794623172, src/main/resources/test/Aufgabe2Testfileeval.c",
    "29, 26, 10, 11, 55, 21, 241.57745825283183, 11.818181818181818, 2855.0063248061942, src/main/resources/test/Aufgabe2Testfileextract.c",
    "213, 177, 25, 43, 390, 68, 2374.1105080876328, 51.45348837209303, 122156.26742195088, src/main/resources/test/Aufgabe2TestfileBeispiel.c"})
    void testCProgramms(int N1, int N2, int n1, int n2, int N, int n, double V, double D, double E, String path) {

        Aufgabe2Analysator analysator = Aufgabe2Analysator.analysator(path);
        List<Integer> params = analysator.getParameter();
        List<Double> metriken = analysator.getMetriken();

        assertTrue(
                params.get(0) == N1 &&
                        params.get(1) == N2 &&
                        params.get(2) == n1 &&
                        params.get(3) == n2 &&
                        params.get(4) == N &&
                        params.get(5) == n &&
                        metriken.get(0) == V &&
                        metriken.get(1) == D &&
                        metriken.get(2) == E
        );
    }

}