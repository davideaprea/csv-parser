package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfColumn;
import dto.UnexpectedCharacterTest;
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
    void testUnexpectedCharacterException() {
        List.of(
                new UnexpectedCharacterTest("John\nDoe", '\n'),
                new UnexpectedCharacterTest("John\rDoe", '\r'),
                new UnexpectedCharacterTest("John\"Doe", '"'),
                new UnexpectedCharacterTest("\"Double quotes are so written: \" \"\"", '"'),
                new UnexpectedCharacterTest("\"First\"  \"", '"'),
                new UnexpectedCharacterTest("\"First \"a\"\"", 'a')
        ).forEach(test -> {
            final UnexpectedCharacterException e = Assertions.assertThrows(UnexpectedCharacterException.class, () -> {
                final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);

                for (int i = 0; i < test.input().length(); i++) {
                    columnBuilder.append(test.input().charAt(i));
                }

                columnBuilder.build();
            });

            Assertions.assertEquals(test.unexpectedCharacter(), e.unexpectedCharacter);
        });
    }

    @Test
    void testInvalidColumns() {
        List.of("\"John", "\"", "\"First \"\"")
                .forEach(input -> Assertions.assertThrows(UnexpectedEndOfColumn.class, () -> {
                    final CSVColumnBuilder columnBuilder = new CSVColumnBuilder(CSVColumnSeparator.COMMA);

                    for (int i = 0; i < input.length(); i++) {
                        columnBuilder.append(input.charAt(i));
                    }

                    columnBuilder.build();
                }));
    }
}
