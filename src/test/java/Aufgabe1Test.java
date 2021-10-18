import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.Assert.assertEquals;


public class Aufgabe1Test {
    @ParameterizedTest
    @CsvSource({"10:00, 10:00", "13:00, 13:00", "23:59:59,23:59:59",
    "bajdh12:23kaok, 12:23", "23:60,''", "24:00, ''", "00:00, 00:00",
    "12:bd:1:hs:12,''", "7:12,''"})
    void testTime(String input, String expected) {
        TimeFilter filter = new TimeFilter(CharStreams.fromString(input));
        Token t = filter.nextToken();
        String result = "";
        if(t.getType() == filter.TIME) result = t.getText();
        assertEquals(result, expected);
    }

    @ParameterizedTest
    @CsvSource({"127.0.00.111,''","239.12.99.0,239.12.99.0","0.0.0.0,0.0.0.0",
    "Die IP ist:127.0.91.255,127.0.91.255","Satzende 1.0.91.255., 1.0.91.255",
    "asjhdash1jh45.67.3.dfhjsf,''","sdhj:33.44.55.66.sajdhk,33.44.55.66",
    "abcd:ef12:3456:7890:abcd:ef12:3456:7890,''"})
    void testIP4(String input, String expected) {
        IPFilter filter = new IPFilter(CharStreams.fromString(input));
        Token t = filter.nextToken();
        String result = "";
        if(t.getType() == filter.IP4) result = t.getText();
        assertEquals(result, expected);
    }
    @ParameterizedTest
    @CsvSource({"129.238.0.13,''","0:00:000:0000:f:ff:FF:FfFF,0:00:000:0000:f:ff:FF:FfFF",
    "sadnajabe3:klma:12:fFA3:45:aB2:0000:991D:Fel1hsdb,a:12:fFA3:45:aB2:0000:991D:Fe",
    "a:12:fFg3:45:aB2:0000:991D:Fe,''"})
    void testIP6(String input, String expected) {
        IPFilter filter = new IPFilter(CharStreams.fromString(input));
        Token t = filter.nextToken();
        String result = "";
        if(t.getType() == filter.IP6) result = t.getText();
        assertEquals(result, expected);
    }


}