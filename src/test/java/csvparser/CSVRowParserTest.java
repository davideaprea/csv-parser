package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;
import dto.InvalidRowParsingTest;
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
                new RowParsingTest("\"Anna\",\t\"Smith\t\",25,\"London\"", List.of("Anna", "Smith\t", "25", "London")),
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
                        List.of("Mark", "Smith", " 28 ", "London")
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
                ),
                new RowParsingTest(
                        "Name,\"Address \"\"\"",
                        List.of("Name", "Address \"")
                ),
                new RowParsingTest(
                        "\"Double quotes are so written: \"\"\" ,hello",
                        List.of("Double quotes are so written: \"", "hello")
                ),
                new RowParsingTest(",john,", List.of("", "john", ""))
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

    @Test
    void testInvalidRows() {
        List<InvalidRowParsingTest> testCases = List.of(
                new InvalidRowParsingTest("John\nDoe,30,New York", UnexpectedCharacterException.class),
                new InvalidRowParsingTest("Anna\rSmith,25,London", UnexpectedCharacterException.class),
                new InvalidRowParsingTest("\"John,Doe,30,New York", UnexpectedEndOfRow.class),
                new InvalidRowParsingTest("\"Anna\",\"Smith,25,London", UnexpectedEndOfRow.class),
                new InvalidRowParsingTest("John\"Doe,30,New York", UnexpectedCharacterException.class),
                new InvalidRowParsingTest("\"Double quotes are so written: \" \"\" ,hello", UnexpectedCharacterException.class),
                new InvalidRowParsingTest("First,\"", UnexpectedEndOfRow.class),
                new InvalidRowParsingTest("\"First\"  \", second", UnexpectedCharacterException.class),
                new InvalidRowParsingTest("\"First \"\", second", UnexpectedEndOfRow.class),
                new InvalidRowParsingTest("\"First \"a\"\", second", UnexpectedCharacterException.class)
        );

        for(int i = 0; i < testCases.size(); i++) {
            final InvalidRowParsingTest test = testCases.get(i);

            try {
                Assertions.assertThrows(test.exception(), () -> parser.parse(test.row()));
            } catch (Throwable e) {
                throw new AssertionError("Test n. " + (i + 1) + " failed with input " + test.row(), e);
            }
        }
    }
}
