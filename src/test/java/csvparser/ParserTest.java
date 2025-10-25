package csvparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void test() {
        List<String> values = parser.parse("abc,def");

        System.out.println("Line values: " + values);

        Assertions.assertEquals(2, values.size());
        Assertions.assertEquals("abc", values.getFirst());
        Assertions.assertEquals("def", values.get(1));
    }

    @Test
    void testEscape() {
        List<String> values = parser.parse("\"abc\"\",def\"");

        Assertions.assertEquals("abc\"", values.getFirst());
        Assertions.assertEquals("def", values.get(1));
    }

    @Test
    void testBlockWithSpecialCharacters() {
        List<String> values = parser.parse("\"abc def, hil\",def");

        Assertions.assertEquals("abc\"", values.getFirst());
        Assertions.assertEquals("def", values.get(1));
    }
}
