package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import dto.RowParsingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVRowParserTest {
    private final CSVRowParser parser = new CSVRowParser(CSVColumnSeparator.COMMA);

    @Test
    void testValidRows() {
        List<RowParsingTest> validRows = List.of(
                new RowParsingTest("John,Doe,30,New York", List.of("John", "Doe", "30", "New York")),
                new RowParsingTest("Anna,Smith,25,London", List.of("Anna", "Smith", "25", "London")),
                new RowParsingTest("Luke,Green,40,Chicago", List.of("Luke", "Green", "40", "Chicago")),
                new RowParsingTest("John,,30,New York", List.of("John", "", "30", "New York")),
                new RowParsingTest(",Anna,,London", List.of("", "Anna", "", "London")),
                new RowParsingTest("\" John \",\" Doe \",30,\" New York \"", List.of(" John ", " Doe ", "30", " New York ")),
                new RowParsingTest("\"Anna\",\t\"Smith\t\",25,\"London\"", List.of("Anna", "\tSmith\t", "25", "London")),
                new RowParsingTest("\"He said \"\"Hello\"\"\",Doe,30,\"New York\"", List.of("He said \"Hello\"", "Doe", "30", "New York")),
                new RowParsingTest(
                        "\"Quote \"\"inside\"\" field\",\"Another \"\"quoted\"\" field\",42,\"City\"",
                        List.of("Quote \"inside\" field", "Another \"quoted\" field", "42", "City")
                ),
                new RowParsingTest("\"Line1\nLine2\",30,\"London\"", List.of("Line1\nLine2", "30", "London")),
                new RowParsingTest("\"Multi\nline\nfield\",\"Test\",25,\"City\"", List.of("Multi\nline\nfield", "Test", "25", "City")),
                new RowParsingTest(
                        "\"Value, with, commas\",\"Second field\",50,\"City, Name\"",
                        List.of("Value, with, commas", "Second field", "50", "City, Name")
                ),
                new RowParsingTest(
                        "\"Mark\" , \"Smith\" , 28 , \"London\"",
                        List.of("Mark ", " Smith ", " 28 ", " London")
                ),
                new RowParsingTest("123,456,78.9,\"$100.00\"", List.of("123", "456", "78.9", "$100.00")),
                new RowParsingTest(
                        "-1,0,999,\"Special chars: !@#$%^&*()\"",
                        List.of("-1", "0", "999", "Special chars: !@#$%^&*()")
                ),
                new RowParsingTest(
                        "\" Leading space \", \" Trailing space\", \"Quotes \"\" inside\", \"New\nline\", \"Comma, inside\", \"\", \"Just text\"",
                        List.of(
                                " Leading space ",
                                " Trailing space",
                                "Quotes \" inside",
                                "New\nline",
                                "Comma, inside",
                                "",
                                "Just text"
                        )
                )
        );

        for (int i = 0; i < validRows.size(); i++) {
            final RowParsingTest test = validRows.get(i);

            List<String> columns;

            try {
                columns = parser.parse(test.rowToTest());
            } catch (Throwable e) {
                throw new AssertionError("Valid row n. " + (i + 1) + " with value " + test.rowToTest() + " threw an exception: " + e.getMessage(), e);
            }

            try {
                Assertions.assertEquals(test.expectedColumns(), columns);
            } catch (Throwable e) {
                throw new AssertionError("Test n. " + (i + 1) + " failed. Actual: " + columns + "; Expected: " + test.expectedColumns() + ";");
            }
        }
    }
}
