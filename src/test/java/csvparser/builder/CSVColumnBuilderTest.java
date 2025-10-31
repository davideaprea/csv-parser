package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfColumn;
import dto.ParsingExceptionTest;
import dto.ValidColumnParsingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CSVColumnBuilderTest {
    @Test
    void testValidColumns() {
        final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);

        List.of(
                new ValidColumnParsingTest("John", "John"),
                new ValidColumnParsingTest("John Doe", "John Doe"),
                new ValidColumnParsingTest("\"John Doe, NYC\"", "John Doe, NYC"),
                new ValidColumnParsingTest("   John Doe\t", "   John Doe\t"),
                new ValidColumnParsingTest("\",\nJohn Doe, \rNYC,\"", ",\nJohn Doe, \rNYC,"),
                new ValidColumnParsingTest("\t\"John Doe\"    ", "John Doe"),
                new ValidColumnParsingTest(",", ""),
                new ValidColumnParsingTest("    \"John \"\"Doe\"\"\"    ", "John \"Doe\"")
        ).forEach(test -> {
            for (int i = 0; i < test.input().length(); i++) {
                columnBuilder.append(test.input().charAt(i));
            }

            Assertions.assertEquals(test.expectedOutput(), columnBuilder.build());
        });
    }

    @Test
    void testInvalidColumns() {
        List.of(
                new ParsingExceptionTest("John\nDoe", UnexpectedCharacterException.class),
                new ParsingExceptionTest("John\rDoe", UnexpectedCharacterException.class),
                new ParsingExceptionTest("\"John", UnexpectedEndOfColumn.class),
                new ParsingExceptionTest("John\"Doe", UnexpectedCharacterException.class),
                new ParsingExceptionTest("\"Double quotes are so written: \" \"\"", UnexpectedCharacterException.class),
                new ParsingExceptionTest("\"", UnexpectedEndOfColumn.class),
                new ParsingExceptionTest("\"First\"  \"", UnexpectedCharacterException.class),
                new ParsingExceptionTest("\"First \"\"", UnexpectedEndOfColumn.class),
                new ParsingExceptionTest("\"First \"a\"\"", UnexpectedCharacterException.class)
        ).forEach(test -> Assertions.assertThrows(test.exception(), () -> {
            final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);

            for (int i = 0; i < test.input().length(); i++) {
                columnBuilder.append(test.input().charAt(i));
            }

            columnBuilder.build();
        }));
    }
}
