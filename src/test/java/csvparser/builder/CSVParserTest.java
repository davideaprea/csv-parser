package csvparser.builder;

import csvparser.CSVParser;
import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.MalformedFileException;
import csvparser.exception.UnexpectedCharacterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVParserTest {
    @Test
    void testSingleField() {
        String input = "abc";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("abc")),
                result
        );
    }

    @Test
    void testSimpleRow() {
        String input = "a,b,c";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a", "b", "c")),
                result
        );
    }

    @Test
    void testMultipleRows() {
        String input = "a,b,c\r\n1,2,3";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(
                        List.of("a", "b", "c"),
                        List.of("1", "2", "3")
                ),
                result
        );
    }

    @Test
    void testEmptyFieldBetweenCommas() {
        String input = "a,,c";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a", "", "c")),
                result
        );
    }

    @Test
    void testTrailingComma() {
        String input = "a,b,";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a", "b", "")),
                result
        );
    }

    @Test
    void testEmptyRow() {
        String input = "\r\n";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of(""), List.of()),
                result
        );
    }

    @Test
    void testSpacesArePreservedOutsideQuotes() {
        String input = " a , b ";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of(" a ", " b ")),
                result
        );
    }

    @Test
    void testQuotedFieldWithComma() {
        String input = "\"a,b\",c";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a,b", "c")),
                result
        );
    }

    @Test
    void testQuotedFieldWithEscapedQuote() {
        String input = "\"a\"\"b\",c";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a\"b", "c")),
                result
        );
    }

    @Test
    void testQuotedEmptyField() {
        String input = "\"\",x";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("", "x")),
                result
        );
    }

    @Test
    void testFullyQuotedRow() {
        String input = "\"a\",\"b\",\"c\"";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a", "b", "c")),
                result
        );
    }

    @Test
    void testQuotedFieldWithLFInside() {
        String input = "\"a\nb\",x";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a\nb", "x")),
                result
        );
    }

    @Test
    void testQuotedFieldWithCRLFInside() {
        String input = "\"a\r\nb\",x";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("a\r\nb", "x")),
                result
        );
    }

    @Test
    void testMultipleRowsWithQuotedNewLines() {
        String input = "a,\"b\r\nb\"\r\nc,d";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(
                        List.of("a", "b\r\nb"),
                        List.of("c", "d")
                ),
                result
        );
    }

    @Test
    void testOnlyCommas() {
        String input = ",,,";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("", "", "", "")),
                result
        );
    }

    @Test
    void testOnlyTwoQuotesMeansEmptyString() {
        String input = "\"\"";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("")),
                result
        );
    }

    @Test
    void testEOFAfterQuotedField() {
        String input = "\"abc\"";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(
                List.of(List.of("abc")),
                result
        );
    }

    @Test
    void testEmptyInputReturnsEmptyList() {
        String input = "";

        List<List<String>> result = CSVParser.parse(input, CSVColumnSeparator.COMMA);

        Assertions.assertEquals(List.of(), result);
    }

    @Test
    void testMalformedFileExceptionCases() {
        String[] cases = {
                "abc\r\ndef\r",
                "abc\r\n\"def",
                "abc,def\r\nghi\r\n,123,456",
                "\"",
                "\"abc",
                "\"a\"\"b"
        };

        for (String csv : cases) {
            Assertions.assertThrows(MalformedFileException.class, () -> CSVParser.parse(csv, CSVColumnSeparator.COMMA));
        }
    }

    @Test
    void testUnexpectedCharacterExceptionCases() {
        List<String> cases = List.of(
                "a\nb",
                "\"abc\"x",
                "\"abc\" xyz",
                "a,b\nc",
                "a,b\r\nc\nd",
                "a,\"b\"c",
                "\"ab\"c",
                " \"abc\"",
                "a, \"b\"",
                "\"abc\";",
                "a,b\rx",
                "a,b\r c",
                "a\"bc",
                "a,b\r\nc,d\r\n1,2\n3"
        );

        for(int i = 0; i < cases.size(); i++) {
            final String csv = cases.get(i);

            Assertions.assertThrows(UnexpectedCharacterException.class, () -> CSVParser.parse(csv, CSVColumnSeparator.COMMA));
        }
    }
}
